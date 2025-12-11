package ru.github.calculator.states;

import ru.github.calculator.inputoutput.UserIO;

public class AwaitingExitDepositCalculatorStateHandler implements StateHandler {
    private final UserIO userIO;
    private final StateController statesMachine;

    public AwaitingExitDepositCalculatorStateHandler(UserIO userIO, StateController statesMachine) {
        this.statesMachine = statesMachine;
        this.userIO = userIO;
    }

    @Override
    public void handle() {
        userIO.postValueToUser("%nThank you for using the deposit calculator!%n");
        statesMachine.setNextState(AvaibleStates.END);
    }

}