package pt.codered.afk_47.model.fsm.states;

import pt.codered.afk_47.model.data.AFKData;
import pt.codered.afk_47.model.fsm.AFKFSMContext;
import pt.codered.afk_47.model.fsm.AFKState;
import pt.codered.afk_47.model.fsm.AFKStateAdapter;

public class DungeoneeringState extends AFKStateAdapter {
    public DungeoneeringState(AFKFSMContext context, AFKData data) {
        super(context, data);
    }

    @Override
    public void tick() {

    }

    @Override
    public AFKState getState() {
        return AFKState.DUNGEONEERING;
    }
}
