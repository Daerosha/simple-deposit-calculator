package ru.github.calculator.states;

import java.util.HashMap;
import java.util.Map;

public class StateMachine {
    private final Map<AvaibleStates, StateHandler> statesMap = new HashMap<>();
    private final StateController stateHandler;

    public StateMachine(StateController stateKeeper) {
        this.stateHandler = stateKeeper;
    }

    public void initialState(AvaibleStates initialState) {
        stateHandler.setNextState(initialState);
    }


    public void addState(AvaibleStates stateName, StateHandler stateObject) {
        statesMap.put(stateName, stateObject);

    }

    public AvaibleStates handle() {
        AvaibleStates currentState = stateHandler.getCurrentState();
        if (currentState != AvaibleStates.END) {
            statesMap.get(currentState).handle();
        }
        return currentState;
    }
}
