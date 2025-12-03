package ru.github.calculator.states;

import ru.github.calculator.dto.DepositCalculationResult;
import ru.github.calculator.inputoutput.UserIO;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class AwaitingPostCalculationResultState implements State {
    private static final double KOPEYKA_TO_RUBLES = 100.0;
    private final UserIO userIO;
    private final StateControl statesMachine;


    public AwaitingPostCalculationResultState(UserIO userIO, StateControl statesMachine) {
        this.statesMachine = statesMachine;
        this.userIO = userIO;
    }

    @Override
    public void handle() {
        try {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setGroupingSeparator(' ');
            symbols.setDecimalSeparator('.');
            DecimalFormat df = new DecimalFormat("#,##0.00", symbols);

            DepositCalculationResult calculationResult = statesMachine.getCalculationResult();
            userIO.postValueToUser("%nThe balance at the end of the deposit is: " + df.format(calculationResult.total() / KOPEYKA_TO_RUBLES) + "%nOf this amount, the profit on the deposit is: " + df.format(calculationResult.stonks() / KOPEYKA_TO_RUBLES) + "%n");
            statesMachine.setNextState(AvaibleStates.AwaitingSaveCalculatingHistoryState);
        } catch (NullPointerException e) {
            userIO.postValueToUser("%nYou have entered incorrect data, please re-enter");
            statesMachine.clearCalculatingValue();
            statesMachine.setNextState(AvaibleStates.AwaitingSumState);
        }
    }
}