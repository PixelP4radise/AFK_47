package pt.codered.afk_47.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

public class HumanLookController {

    private final MinecraftClient mc = MinecraftClient.getInstance();
    private final Random random = new Random();

    // --- State Management ---
    public enum LookState {
        IDLE,           // Not doing anything
        MOVING,         // The main movement (Curve + Intentional Error)
        CORRECTING      // The micro-adjustment (Fixing the error)
    }

    private LookState currentState = LookState.IDLE;

    // Animation Progress
    private int currentTick = 0;
    private int totalTicks = 0;

    // Angles
    private float startYaw, startPitch;
    private float targetYaw, targetPitch;

    // The "True" target to switch to after the error phase
    private Vec3d realTargetPos;

    /**
     * Starts a new look action.
     *
     * @param target        The exact block center to look at.
     * @param durationTicks How fast to move (e.g., 5-10 for fast, 15-20 for slow).
     */
    public void lookAt(Vec3d target, int durationTicks) {
        this.realTargetPos = target;
        this.currentState = LookState.MOVING;

        this.currentTick = 0;
        this.totalTicks = durationTicks;

        // 1. Get current angles
        this.startYaw = mc.player.getYaw();
        this.startPitch = mc.player.getPitch();

        // 2. Calculate true target angles
        float[] trueAngles = getAnglesFor(target);

        // 3. Add "Human Error" (Gaussian Noise) for the initial movement
        // Standard deviation of ~3 degrees makes it look organic
        float errorYaw = (float) (random.nextGaussian() * 3.0);
        float errorPitch = (float) (random.nextGaussian() * 3.0);

        this.targetYaw = trueAngles[0] + errorYaw;
        this.targetPitch = trueAngles[1] + errorPitch;
    }

    /**
     * Call this every tick.
     */
    public void update() {
        if (currentState == LookState.IDLE) return;

        currentTick++;

        // Calculate progress (0.0 to 1.0)
        float progress = (float) currentTick / totalTicks;
        if (progress > 1.0f) progress = 1.0f;

        // Apply "Ease-In-Out" Curve
        float ease = progress * progress * (3.0f - 2.0f * progress);

        // Interpolate & Apply
        float newYaw = interpolateAngle(startYaw, targetYaw, ease);
        float newPitch = MathHelper.lerp(ease, startPitch, targetPitch);

        mc.player.setYaw(newYaw);
        mc.player.setPitch(newPitch);

        // Check if current phase is done
        if (currentTick >= totalTicks) {
            handlePhaseCompletion();
        }
    }

    private void handlePhaseCompletion() {
        switch (currentState) {
            case MOVING:
                // Main movement done. Now trigger the correction.
                startCorrection();
                break;

            case CORRECTING:
                // Correction done. We are finished.
                currentState = LookState.IDLE;
                break;

            default:
                break;
        }
    }

    private void startCorrection() {
        this.currentState = LookState.CORRECTING;
        this.currentTick = 0;

        // Correction is fast and snappy (2-4 ticks)
        this.totalTicks = 2 + random.nextInt(3);

        // Reset Start to current (the position with error)
        this.startYaw = mc.player.getYaw();
        this.startPitch = mc.player.getPitch();

        // Reset Target to the EXACT position (removing the error)
        float[] trueAngles = getAnglesFor(realTargetPos);
        this.targetYaw = trueAngles[0];
        this.targetPitch = trueAngles[1];
    }

    public boolean isFinished() {
        return currentState == LookState.IDLE;
    }

    public LookState getState() {
        return currentState;
    }

    // --- Math Helpers ---

    private float[] getAnglesFor(Vec3d target) {
        double dx = target.x - mc.player.getX();
        double dy = target.y - mc.player.getEyeY();
        double dz = target.z - mc.player.getZ();

        double distanceXZ = Math.sqrt(dx * dx + dz * dz);

        float yaw = (float) (MathHelper.atan2(dz, dx) * (180 / Math.PI)) - 90;
        float pitch = (float) (-(MathHelper.atan2(dy, distanceXZ) * (180 / Math.PI)));

        return new float[]{yaw, pitch};
    }

    private float interpolateAngle(float start, float target, float progress) {
        float diff = target - start;
        while (diff < -180.0F) diff += 360.0F;
        while (diff >= 180.0F) diff -= 360.0F;
        return start + diff * progress;
    }
}