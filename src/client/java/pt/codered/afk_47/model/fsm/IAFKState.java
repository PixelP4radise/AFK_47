package pt.codered.afk_47.model.fsm;

public interface IAFKState {
    void tick();

    boolean stop();

    boolean startMining();

    boolean startFishing();

    boolean startBestiary();

    boolean startDungeoneering();

    boolean startFarming();

    boolean startForaging();

    AFKState getState();
}
