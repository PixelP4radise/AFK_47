package pt.codered.afk_47.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TreeRecorder {

    private static final TreeRecorder INSTANCE = new TreeRecorder();
    private boolean isRecording = false;

    // Temporary storage for the current session
    private final List<BlockPos> currentLogs = new ArrayList<>();
    private final List<BlockPos> currentRoots = new ArrayList<>();

    public static TreeRecorder get() {
        return INSTANCE;
    }

    // --- KEYBIND ACTION 1: TOGGLE ---
    public void toggleRecording() {
        isRecording = !isRecording;
        MinecraftClient mc = MinecraftClient.getInstance();

        if (isRecording) {
            // START
            currentLogs.clear();
            currentRoots.clear();
            mc.inGameHud.getChatHud().addMessage(Text.of("§a[Recorder] Started recording new Tree."));
        } else {
            // STOP & PRINT
            printResult();
            mc.inGameHud.getChatHud().addMessage(Text.of("§c[Recorder] Stopped recording. Check console/chat."));
        }
    }

    // --- KEYBIND ACTION 2: ADD ROOT (Block under feet) ---
    public void addRoot() {
        if (!isRecording) return;
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;

        // Get block under the player
        BlockPos pos = mc.player.getBlockPos().down();

        if (!currentRoots.contains(pos)) {
            currentRoots.add(pos);
            mc.inGameHud.getChatHud().addMessage(Text.of("§2[+] Added Root: " + pos.toShortString()));
        }
    }

    // --- KEYBIND ACTION 3: ADD LOG (Block looking at) ---
    public void addLog() {
        if (!isRecording) return;
        MinecraftClient mc = MinecraftClient.getInstance();

        // Raycast 10 blocks out
        HitResult hit = mc.player.raycast(10.0, 0.0f, false);

        if (hit.getType() == HitResult.Type.BLOCK) {
            BlockPos pos = ((BlockHitResult) hit).getBlockPos();

            if (!currentLogs.contains(pos)) {
                currentLogs.add(pos);
                mc.inGameHud.getChatHud().addMessage(Text.of("§4[+] Added Log: " + pos.toShortString()));
            }
        }
    }

    // --- OUTPUT GENERATOR ---
    private void printResult() {
        if (currentLogs.isEmpty() && currentRoots.isEmpty()) return;

        String name = "Tree_" + System.currentTimeMillis() % 1000;

        // Format: new Tree("Name", List.of(...), List.of(...));
        StringBuilder sb = new StringBuilder();
        sb.append("new Tree(\"").append(name).append("\", ");

        // Logs
        sb.append("List.of(").append(formatList(currentLogs)).append("), ");

        // Roots
        sb.append("List.of(").append(formatList(currentRoots)).append("))");

        // Print to console and chat
        System.out.println(sb.toString());

        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(
                Text.of("§eClick to Copy (In Console): " + name)
        );
        // If you have a clipboard helper, you can copy 'sb.toString()' directly here.
    }

    private String formatList(List<BlockPos> list) {
        return list.stream()
                .map(p -> String.format("new BlockPos(%d, %d, %d)", p.getX(), p.getY(), p.getZ()))
                .collect(Collectors.joining(", "));
    }

    // --- RENDERING HOOK ---
    // Call this from your WorldRenderEvent
    public List<BlockPos> getLogsToRender() {
        return isRecording ? currentLogs : List.of();
    }

    public List<BlockPos> getRootsToRender() {
        return isRecording ? currentRoots : List.of();
    }
}