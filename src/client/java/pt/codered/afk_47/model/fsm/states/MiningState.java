package pt.codered.afk_47.model.fsm.states;

import pt.codered.afk_47.model.data.AFKData;
import pt.codered.afk_47.model.fsm.AFKContext;
import pt.codered.afk_47.model.fsm.AFKState;
import pt.codered.afk_47.model.fsm.AFKStateAdapter;

public class MiningState extends AFKStateAdapter {
    public MiningState(AFKContext context, AFKData data) {
        super(context, data);
    }

    @Override
    public void tick() {

    }

    @Override
    public AFKState getState() {
        return AFKState.MINING;
    }
}
