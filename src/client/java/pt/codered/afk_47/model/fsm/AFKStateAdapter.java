package pt.codered.afk_47.model.fsm;

import pt.codered.afk_47.model.data.AFKData;
import pt.codered.afk_47.model.fsm.states.*;

public abstract class AFKStateAdapter implements IAFKState {
    protected AFKContext context;
    protected AFKData data;

    protected AFKStateAdapter(AFKContext context, AFKData data) {
        this.context = context;
        this.data = data;
    }

    protected void changeState(IAFKState newState) {
        context.changeState(newState);
    }

    @Override
    public boolean stop() {
        changeState(new IdleState(context, data));
        return true;
    }

    @Override
    public boolean startMining() {
        changeState(new MiningState(context, data));
        return true;
    }

    @Override
    public boolean startFishing() {
        changeState(new FishingState(context, data));
        return true;
    }

    @Override
    public boolean startBestiary() {
        changeState(new BestiaryState(context, data));
        return true;
    }

    @Override
    public boolean startDungeoneering() {
        changeState(new DungeoneeringState(context, data));
        return true;
    }

    @Override
    public boolean startFarming() {
        changeState(new FarmingState(context, data));
        return true;
    }

    @Override
    public boolean startForaging() {
        changeState(new ForagingState(context, data));
        return true;
    }
}
