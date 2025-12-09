package pt.codered.afk_47.model.fsm.states;

import pt.codered.afk_47.model.data.AFKData;
import pt.codered.afk_47.model.fsm.AFKFSMContext;
import pt.codered.afk_47.model.fsm.AFKState;
import pt.codered.afk_47.model.fsm.AFKStateAdapter;

public class BestiaryState extends AFKStateAdapter {
    public BestiaryState(AFKFSMContext context, AFKData data) {
        super(context, data);
    }

    @Override
    public void tick() {

    }

    @Override
    public AFKState getState() {
        return AFKState.BESTIARY;
    }
}
