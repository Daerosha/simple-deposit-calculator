package ru.github.calculator.states;

import ru.github.calculator.inputoutput.UserIO;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AwaitingTermStateHandler implements StateHandler {
    private static final int MAX_TERM = 180;
    private static final int MONTH_IN_YEAR = 12;
    private static final int MIN_TERM = 1;
    private static final Pattern PATTERN = Pattern.compile("^(\\d+)([my])$");
    private static final String USER_INPUT_YEAR = "y";
    private static final String USER_INPUT_MONTH = "m";
    private final StateController statesMachine;
    private final UserIO userIO;
    private int term;

    public AwaitingTermStateHandler(UserIO userIO,
            StateController statesMachine) {
        this.userIO = userIO;
        this.statesMachine = statesMachine;

    }

    @Override
    public void handle() {
        userIO.postValueToUser(
                "%nEnter integer terms of months or years (no less than " + MIN_TERM + " month and no more than " +
                        MAX_TERM / MONTH_IN_YEAR +
                        " years)%nWhere 1m means one month and 10y means ten years, only 1 value can be used):");
        try {
            String numberAndSuffixOfTime = userIO.getValueFromUser().trim().toLowerCase();
            Matcher matcher = PATTERN.matcher(numberAndSuffixOfTime);
            if (!matcher.matches()) {
                userIO.postValueToUser("%nYou have entered an incorrect deposit term!");
                return;
            }
            int numberOfTime = Integer.parseInt(matcher.group(1));
            String suffixOfTime = matcher.group(2);
            switch (suffixOfTime) {
                case USER_INPUT_YEAR -> term = numberOfTime * MONTH_IN_YEAR;
                case USER_INPUT_MONTH -> term = numberOfTime;
            }
            if (term >= MIN_TERM && term <= MAX_TERM) {
                statesMachine.setValue(AvaibleValue.TERM, term);
                statesMachine.setNextState(AvaibleStates.AWAITING_PERCENT);
            }
        } catch (NumberFormatException | IOException | NullPointerException | IllegalStateException e) {
            userIO.postValueToUser("%nYou have entered an incorrect deposit term!");
        }
    }
}


