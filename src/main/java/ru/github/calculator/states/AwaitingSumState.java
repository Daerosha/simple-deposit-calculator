package ru.github.calculator.states;

import ru.github.calculator.inputoutput.UserIO;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class AwaitingSumState implements State {
    private static final int MAX_SUM = 30000000;
    private static final int MIN_SUM = 1;
    private final UserIO userIO;
    private final StateControl statesMachine;

    public AwaitingSumState(UserIO userIO, StateControl statesMachine) {
        this.statesMachine = statesMachine;
        this.userIO = userIO;
    }

    @Override
    public void handle() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        DecimalFormat df = new DecimalFormat("#,##0", symbols);
        userIO.postValueToUser("%nEnter the integer deposit amount (from " + df.format(MIN_SUM) + " to " + df.format(MAX_SUM) + "):");
        try {
            String input = userIO.getValueFromUser();
            int sum = Integer.parseInt(input);
            if (sum >= MIN_SUM && sum <= MAX_SUM) {
                statesMachine.setSum(sum);
                statesMachine.setNextState(AvaibleStates.AwaitingTermState);
            }
        } catch (NumberFormatException | IOException e) {
            userIO.postValueToUser("%nError: You entered a number that is too large or not an integer when entering the sum!");
        }
    }
}
