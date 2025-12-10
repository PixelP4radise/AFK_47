package pt.codered.afk_47.model.fsm.states;

import pt.codered.afk_47.model.data.AFKData;
import pt.codered.afk_47.model.fsm.AFKFSMContext;
import pt.codered.afk_47.model.fsm.AFKState;
import pt.codered.afk_47.model.fsm.AFKStateAdapter;

public class ForagingState extends AFKStateAdapter {
    private ForagingSubState subState = ForagingSubState.DECIDING_TARGET;

    public ForagingState(AFKFSMContext context, AFKData data) {
        super(context, data);
    }

    enum ForagingSubState {
        DECIDING_TARGET,      // Pick the next tree and random block
        MOVING,               // Baritone is walking to the block
        BREAKING,             // Breaking the specific block
        IDLE_AFTER_BREAKING   // Human-like pause
    }

    @Override
    public void tick() {
        switch (subState) {
            case DECIDING_TARGET -> {
            }
            case MOVING -> {
            }
            case BREAKING -> {
            }
            case IDLE_AFTER_BREAKING -> {
            }
        }
    }

    @Override
    public AFKState getState() {
        return AFKState.FORAGING;
    }
}
