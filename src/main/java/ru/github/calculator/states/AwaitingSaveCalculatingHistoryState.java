package ru.github.calculator.states;

import ru.github.calculator.dto.DepositValueForHistory;
import ru.github.calculator.history.CalculatingHistory;
import ru.github.calculator.inputoutput.UserIO;

public class AwaitingSaveCalculatingHistoryState implements State {
    private final UserIO userIO;
    private final StateControl statesMachine;

    public AwaitingSaveCalculatingHistoryState(UserIO userIO, StateControl statesMachine) {
        this.statesMachine = statesMachine;
        this.userIO = userIO;
    }

    @Override
    public void handle() {
        try {
            CalculatingHistory history = new CalculatingHistory();
            DepositValueForHistory valueForHistory = statesMachine.getValueForCollection();
            history.saveDataForCollectionHistory(valueForHistory);
            statesMachine.clearCalculatingValue();
            statesMachine.setNextState(AvaibleStates.AwaitingHistoryDisplayState);
        } catch (NullPointerException e) {
            userIO.postValueToUser("%n Viewing history is currently unavailable, but you can count again.%n");
            statesMachine.clearCalculatingValue();
            statesMachine.setNextState(AvaibleStates.AwaitingContinueCalculatingState);

        }
    }
}