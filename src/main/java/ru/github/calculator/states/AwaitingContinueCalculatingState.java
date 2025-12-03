package ru.github.calculator.states;

import ru.github.calculator.inputoutput.UserIO;

import java.io.IOException;

public class AwaitingContinueCalculatingState implements State {
    public static final String CALCULATION_CONTINUES = "yes";
    public static final String CALCULATION_STOPS = "no";

    private final StateControl statesMachine;
    private final UserIO userIO;

    public AwaitingContinueCalculatingState(UserIO userIO, StateControl statesMachine) {
        this.userIO = userIO;
        this.statesMachine = statesMachine;
    }

    @Override
    public void handle() {
        userIO.postValueToUser("%nDo you want to calculate again? Type " + CALCULATION_CONTINUES + " or " + CALCULATION_STOPS + ":");
        try {
            String continueCalculating = userIO.getValueFromUser();
            switch (continueCalculating) {
                case CALCULATION_CONTINUES -> statesMachine.setNextState(AvaibleStates.AwaitingSumState);
                case CALCULATION_STOPS -> statesMachine.setNextState(AvaibleStates.AwaitingExitDepositCalculatorState);
            }
        } catch (IOException e) {
            userIO.postValueToUser("%n You can only enter " + CALCULATION_CONTINUES + " or " + CALCULATION_STOPS + ":");
        }
    }
}