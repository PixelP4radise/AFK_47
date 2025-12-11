package pt.codered.afk_47.model.fsm;

import baritone.api.BaritoneAPI;
import net.minecraft.client.MinecraftClient;
import pt.codered.afk_47.model.data.AFKData;
import pt.codered.afk_47.model.fsm.states.*;
import pt.codered.afk_47.util.HumanLookController;

public abstract class AFKStateAdapter implements IAFKState {
    protected AFKFSMContext context;
    protected AFKData data;
    protected final MinecraftClient mc = MinecraftClient.getInstance();
    protected final HumanLookController lookController = new HumanLookController();

    protected AFKStateAdapter(AFKFSMContext context, AFKData data) {
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

    protected void updateRotation() {
        if (!lookController.isFinished()) {
            // Suppress Baritone
            BaritoneAPI.getProvider().getPrimaryBaritone().getLookBehavior().updateTarget(null, true);

            // Apply Human Rotation
            lookController.update();
        }
    }
}
