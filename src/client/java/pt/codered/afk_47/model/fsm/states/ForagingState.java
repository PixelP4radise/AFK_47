package pt.codered.afk_47.model.fsm.states;

import pt.codered.afk_47.model.data.AFKData;
import pt.codered.afk_47.model.fsm.AFKFSMContext;
import pt.codered.afk_47.model.fsm.AFKState;
import pt.codered.afk_47.model.fsm.AFKStateAdapter;
import pt.codered.afk_47.util.HumanLookController;

public class ForagingState extends AFKStateAdapter {

    private ForagingSubState subState = ForagingSubState.DECIDING_TARGET;
    private int statetimer = 0;

    // Helper
    private final HumanLookController lookController = new HumanLookController();

    public ForagingState(AFKFSMContext context, AFKData data) {
        super(context, data);
    }

    enum ForagingSubState {
        DECIDING_TARGET, MOVING, BREAKING, IDLE_AFTER_BREAKING
    }

    @Override
    public void tick() {
        updateRotation();

        if (statetimer > 0) {
            statetimer--;
            return;
        }

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