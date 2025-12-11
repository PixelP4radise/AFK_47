package pt.codered.afk_47.model.fsm.states;

import pt.codered.afk_47.model.data.AFKData;
import pt.codered.afk_47.model.fsm.AFKFSMContext;
import pt.codered.afk_47.model.fsm.AFKState;
import pt.codered.afk_47.model.fsm.AFKStateAdapter;

/**
 * ForagingState
 * <p>
 * Behavior:
 * - Scans for the nearest oak log that hasn't been visited recently.
 * - Identifies the entire tree connected to that log and adds it to an "ignore list".
 * - Paths to the bottom-most log of that tree.
 * - Breaks EXACTLY ONE log.
 * - Immediately searches for a new, different tree.
 */
public class ForagingState extends AFKStateAdapter {

    public ForagingState(AFKFSMContext context, AFKData data) {
        super(context, data);
    }

    private enum ForagingSubState {
        DECIDING_TARGET,
        MOVING,
        BREAKING
    }

    // State
    private ForagingSubState subState = ForagingSubState.DECIDING_TARGET;
    private int statetimer = 0;


    @Override
    public void tick() {
        // Smooth rotation suppression & update (uses lookController from Adapter)
        updateRotation();

        // Cooldown timer
        if (statetimer > 0) {
            statetimer--;
            return;
        }

        // Main FSM
        switch (subState) {
            case DECIDING_TARGET -> decideTarget();
            case MOVING -> tickMoving();
            case BREAKING -> tickBreaking();
        }
    }

    private void tickBreaking() {

    }

    private void tickMoving() {
        
    }

    private void decideTarget() {

    }

    @Override
    public AFKState getState() {
        return AFKState.FORAGING;
    }

}