package ru.github.calculator.states;

import ru.github.calculator.inputoutput.UserIO;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class AwaitingPostCalculationResultStateHandler implements StateHandler {
    private static final double KOPEYKA_TO_RUBLES = 100.0;
    private final UserIO userIO;
    private final StateController statesMachine;


    public AwaitingPostCalculationResultStateHandler(UserIO userIO,
            StateController statesMachine) {
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
            long total = statesMachine.getValue(AvaibleValue.TOTAL);
            long stonks = statesMachine.getValue(AvaibleValue.STONKS);
            userIO.postValueToUser("%nThe balance at the end of the deposit is: " +
                    df.format(total / KOPEYKA_TO_RUBLES) +
                    "%nOf this amount, the profit on the deposit is: " +
                    df.format(stonks / KOPEYKA_TO_RUBLES) + "%n");
            statesMachine.setNextState(AvaibleStates.AWAITING_SAVE_CALCULATION_HISTORY);
        } catch (NullPointerException e) {
            userIO.postValueToUser("%nYou have entered incorrect data, please re-enter");
            statesMachine.clearCalculatingValue();
            statesMachine.setNextState(AvaibleStates.AWAITING_SUM);
        }
    }
}