package ru.github.calculator.states;

import ru.github.calculator.inputoutput.UserIO;

public class AwaitingStartStateHandler implements StateHandler {
    private final UserIO userIO;
    private final StateController statesMachine;

    public AwaitingStartStateHandler(UserIO userIO, StateController statesMachine) {
        this.statesMachine = statesMachine;
        this.userIO = userIO;
    }

    @Override
    public void handle() {
        userIO.postValueToUser("%nWelcome to the simple deposit calculator!%n");
        statesMachine.setNextState(AvaibleStates.AWAITING_SUM);
    }
}