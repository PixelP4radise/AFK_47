package pt.codered.afk_47.model.fsm;

import pt.codered.afk_47.model.data.AFKData;
import pt.codered.afk_47.model.fsm.states.IdleState;

public class AFKContext {
    private AFKData data;
    private IAFKState state;

    public AFKContext() {
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
