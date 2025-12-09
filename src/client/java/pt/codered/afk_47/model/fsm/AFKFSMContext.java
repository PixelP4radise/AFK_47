package pt.codered.afk_47.model.fsm;

import pt.codered.afk_47.model.data.AFKData;
import pt.codered.afk_47.model.fsm.states.IdleState;

public class AFKFSMContext {
    private AFKData data;
    private IAFKState state;

    public AFKFSMContext() {
        this.data = new AFKData();
        this.state = new IdleState(this, data);
    }

    public AFKState getState() {
        return state.getState();
    }

    void changeState(IAFKState newState) {
        this.state = newState;
    }

    public void tick() {
        state.tick();
    }

    //getter for data


    //Transitions methods
}
