package ru.github.calculator.states;

import ru.github.calculator.inputoutput.UserIO;

import java.io.IOException;

public class AwaitingPercentState implements State {
    private static final int MAX_PERCENT = 50;
    private static final int MIN_PERCENT = 1;
    private final StateControl statesMachine;
    private final UserIO userIO;

    public AwaitingPercentState(UserIO userIO, StateControl statesMachine) {
        this.userIO = userIO;
        this.statesMachine = statesMachine;

    }

    @Override
    public void handle() {
        userIO.postValueToUser("%nEnter the integer deposit percentage (between " + MIN_PERCENT + " and " + MAX_PERCENT + "):");
        try {
            int percent = Integer.parseInt(userIO.getValueFromUser());
            if (percent >= MIN_PERCENT && percent <= MAX_PERCENT) {
                statesMachine.setPercent(percent);
                statesMachine.setNextState(AvaibleStates.AwaitingCalculatingState);
            }
        } catch (NumberFormatException | IOException e) {
            userIO.postValueToUser("%nError: You entered a number that is too large or not an integer when entering the percentage!");
        }

    }
}
