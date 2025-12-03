package ru.github.calculator.states;

import java.util.HashMap;

public class StateController {
    private final HashMap<AvaibleValue, Long> calculatingValue = new HashMap<>();
    private AvaibleStates state;

    public void setValue(AvaibleValue valueName, long value) {
        calculatingValue.put(valueName, value);
    }

    public AvaibleStates getCurrentState() {
        return state;
    }

    public void setNextState(AvaibleStates state) {
        this.state = state;
    }

    public Long getValue(AvaibleValue valueName) {
        return calculatingValue.get(valueName);
    }

    public void clearCalculatingValue() {
        calculatingValue.clear();
    }
}
