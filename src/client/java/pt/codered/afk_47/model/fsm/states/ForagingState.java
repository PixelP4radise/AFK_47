package pt.codered.afk_47.model.fsm.states;

import baritone.api.BaritoneAPI;
import baritone.api.pathing.goals.GoalBlock;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import pt.codered.afk_47.model.data.AFKData;
import pt.codered.afk_47.model.data.TreePosition;
import pt.codered.afk_47.model.fsm.AFKFSMContext;
import pt.codered.afk_47.model.fsm.AFKState;
import pt.codered.afk_47.model.fsm.AFKStateAdapter;
import pt.codered.afk_47.util.TimeUtils;

public class ForagingState extends AFKStateAdapter {
    private ForagingSubState subState = ForagingSubState.DECIDING_TARGET;

    private int statetimer = 0;

    public ForagingState(AFKFSMContext context, AFKData data) {
        super(context, data);
    }

    enum ForagingSubState {
        DECIDING_TARGET,      // Pick the next tree and random block
        MOVING,               // Baritone is walking to the block
        BREAKING,             // Breaking the specific block
        IDLE_AFTER_BREAKING   // Human-like pause
    }

    private TreePosition currentTarget = null;
    private TreePosition nextTarget = null;

    // Track the specific log we want to break at the current target
    private BlockPos targetLogBlock = null;

    @Override
    public void tick() {
        if (statetimer > 0) {
            statetimer--;
            return;
        }

        switch (subState) {
            case DECIDING_TARGET -> {
                // 1. If we don't have a current target, pick one immediately
                if (currentTarget == null) {
//                    currentTarget = //todo: pick a random tree
                }

                // 2. Lock in the specific log we will break when we get there
                if (currentTarget != null) {
                    targetLogBlock = currentTarget.getRandomLog();

                    // Start Pathing
//                    BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess()
//                            .setGoalAndPath(new GoalBlock());//todo: meter aqui o sitio para onde devo ir

                    subState = ForagingSubState.MOVING;
                }
            }

            case MOVING -> {
                // 1. "While walking figure out the second position"
                if (nextTarget == null) {
//                    nextTarget = pickNextTree(); //todo: escolher a proxima arvore
                }

                // 2. Check if we arrived at the root
                if (!BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing()) {
                    // We stopped moving. Are we close?
                    if (isAtLocation(currentTarget.root())) {
                        subState = ForagingSubState.BREAKING;
                        statetimer = TimeUtils.getRandomDelay(2, 5); // Tiny pause before swing
                    } else {
                        // Stuck? Retry pathing
                        BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess()
                                .setGoalAndPath(new GoalBlock(currentTarget.root()));
                    }
                }
            }

            case BREAKING -> {
                if (targetLogBlock == null) {
                    subState = ForagingSubState.IDLE_AFTER_BREAKING;
                    return;
                }

                // 1. Check if the block is already broken (Air)
                if (mc.world.getBlockState(targetLogBlock).isAir()) {
                    // Stop the breaking animation/reset progress
                    mc.interactionManager.cancelBlockBreaking();

                    // Transition
                    subState = ForagingSubState.IDLE_AFTER_BREAKING;
                    statetimer = TimeUtils.getRandomDelay(10, 20); // Short cooldown
                    return;
                }

                // 2. If not broken, keep breaking
                // Look at center of block
                mc.player.lookAt(
                        net.minecraft.command.argument.EntityAnchorArgumentType.EntityAnchor.EYES,
                        new net.minecraft.util.math.Vec3d(
                                targetLogBlock.getX() + 0.5,
                                targetLogBlock.getY() + 0.5,
                                targetLogBlock.getZ() + 0.5
                        )
                );

                // Swing Hand (Visual)
                mc.player.swingHand(Hand.MAIN_HAND);

                // Send Mining Packet (Simulates holding Left Click)
                if (mc.interactionManager != null) {
                    mc.interactionManager.updateBlockBreakingProgress(targetLogBlock, Direction.UP);
                }
            }

            case IDLE_AFTER_BREAKING -> {
                // "And start going to the second..."

                // 1. Promote Next to Current
                if (nextTarget != null) {
                    currentTarget = nextTarget;
                    nextTarget = null; // Will be refilled in the MOVING state
                } else {
                    // Fallback if prediction failed (shouldn't happen)
//                    currentTarget = pickNextTree(); //todo: escolher a proxima arvore
                }

                // 2. Immediately start the cycle again
                subState = ForagingSubState.DECIDING_TARGET;
                statetimer = 0; // No delay, start moving now
            }
        }
    }


    private boolean isAtLocation(BlockPos pos) {
        return mc.player.getBlockPos().getManhattanDistance(pos) <= 1;
    }

    @Override
    public AFKState getState() {
        return AFKState.FORAGING;
    }
}
