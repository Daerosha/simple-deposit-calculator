package ru.github.calculator.states;

import ru.github.calculator.inputoutput.UserIO;

public class AwaitingStartState implements State {
    private final UserIO userIO;
    private final StateControl statesMachine;

    public AwaitingStartState(UserIO userIO, StateControl statesMachine) {
        this.statesMachine = statesMachine;
        this.userIO = userIO;
    }

    @Override
    public void handle() {
        userIO.postValueToUser("%nWelcome to the simple deposit calculator!%n");
        statesMachine.setNextState(AvaibleStates.AwaitingSumState);
    }
}