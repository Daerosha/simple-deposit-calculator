package ru.github.calculator.states;

import ru.github.calculator.inputoutput.UserIO;

public class AwaitingExitDepositCalculatorState implements State {
    private final UserIO userIO;
    private final StateControl statesMachine;

    public AwaitingExitDepositCalculatorState(UserIO userIO, StateControl statesMachine) {
        this.statesMachine = statesMachine;
        this.userIO = userIO;
    }

    @Override
    public void handle() {
        userIO.postValueToUser("%nThank you for using the deposit calculator!%n");
        statesMachine.setNextState(AvaibleStates.End);
    }

}