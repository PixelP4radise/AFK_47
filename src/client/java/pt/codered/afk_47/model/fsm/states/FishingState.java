package pt.codered.afk_47.model.fsm.states;

import pt.codered.afk_47.model.data.AFKData;
import pt.codered.afk_47.model.fsm.AFKFSMContext;
import pt.codered.afk_47.model.fsm.AFKState;
import pt.codered.afk_47.model.fsm.AFKStateAdapter;

public class FishingState extends AFKStateAdapter {

    public FishingState(AFKFSMContext context, AFKData data) {
        super(context, data);
    }

    private enum FishingSubState {
        CASTING,
        WAITING,
        REELING,
        IDLE_AFTER_FISHING,
        COMBAT,
    }

    @Override
    public void tick() {

    }

    @Override
    public AFKState getState() {
        return AFKState.FISHING;
    }
}
