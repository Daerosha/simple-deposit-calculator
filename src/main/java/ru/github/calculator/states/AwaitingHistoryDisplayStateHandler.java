package ru.github.calculator.states;

import ru.github.calculator.history.CalculatingHistory;
import ru.github.calculator.inputoutput.UserIO;

import java.io.IOException;

public class AwaitingHistoryDisplayStateHandler implements StateHandler {
    public static final String USER_AGREES_TO_VIEW_HISTORY = "yes";
    public static final String USER_DECLINES_TO_VIEW_HISTORY = "no";
    private final StateController statesMachine;
    private final UserIO userIO;
    private final CalculatingHistory history;

    public AwaitingHistoryDisplayStateHandler(UserIO userIO,
            StateController statesMachine, CalculatingHistory history) {

        this.userIO = userIO;
        this.statesMachine = statesMachine;
        this.history = history;
    }

    @Override
    public void handle() {
        userIO.postValueToUser(
                "%nDo you want to see the calculation history? Type " + USER_AGREES_TO_VIEW_HISTORY + " or " +
                        USER_DECLINES_TO_VIEW_HISTORY + ":");
        try {
            String continueCalculating = userIO.getValueFromUser();
            switch (continueCalculating) {
                case USER_AGREES_TO_VIEW_HISTORY -> {
                    userIO.postValueToUser("Your Calculations: %n");
                    userIO.postValueToUser(history.getCalculationHistory());
                    statesMachine.setNextState(AvaibleStates.AWAITING_CONTINUE_CALCULATING);
                }
                case USER_DECLINES_TO_VIEW_HISTORY ->
                        statesMachine.setNextState(AvaibleStates.AWAITING_CONTINUE_CALCULATING);
            }
        } catch (IOException e) {
            userIO.postValueToUser(
                    "%nYou can only enter " + USER_AGREES_TO_VIEW_HISTORY + " or " + USER_DECLINES_TO_VIEW_HISTORY);
        } catch (NullPointerException e) {
            userIO.postValueToUser("%n Viewing history is currently unavailable, but you can count again.%n");
            statesMachine.setNextState(AvaibleStates.AWAITING_CONTINUE_CALCULATING);
        }
    }
}

