package ru.github.calculator.states;

import ru.github.calculator.dto.DepositCalculationResult;
import ru.github.calculator.dto.DepositValueForCalculation;
import ru.github.calculator.dto.DepositValueForHistory;
import ru.github.calculator.history.CalculatingHistory;
import ru.github.calculator.inputoutput.UserIO;

import java.time.LocalDateTime;

public class AwaitingSaveCalculatingHistoryStateHandler implements StateHandler {
    private final UserIO userIO;
    private final StateController statesMachine;
    private final CalculatingHistory history;

    public AwaitingSaveCalculatingHistoryStateHandler(UserIO userIO,
            StateController statesMachine, CalculatingHistory history) {
        this.statesMachine = statesMachine;
        this.userIO = userIO;
        this.history = history;
    }

    @Override
    public void handle() {
        try {
            long sum = statesMachine.getValue(AvaibleValue.SUM);
            long percent = statesMachine.getValue(AvaibleValue.PERCENT);
            long term = statesMachine.getValue(AvaibleValue.TERM);
            long total = statesMachine.getValue(AvaibleValue.TOTAL);
            long stonks = statesMachine.getValue(AvaibleValue.STONKS);
            history.saveDataForCollectionHistory(
                    new DepositValueForHistory(new DepositValueForCalculation(sum, term, percent),
                            new DepositCalculationResult(total, stonks),
                            LocalDateTime.now()));
            statesMachine.clearCalculatingValue();
            statesMachine.setNextState(AvaibleStates.AWAITING_HISTORY_DISPLAY);
        } catch (NullPointerException e) {
            userIO.postValueToUser("%n Viewing history is currently unavailable, but you can count again.%n");
            statesMachine.clearCalculatingValue();
            statesMachine.setNextState(AvaibleStates.AWAITING_CONTINUE_CALCULATING);
        }
    }
}