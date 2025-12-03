package ru.github.calculator.states;

import ru.github.calculator.calculate.DepositCalculator;
import ru.github.calculator.dto.DepositCalculationResult;
import ru.github.calculator.dto.DepositValueForCalculation;
import ru.github.calculator.inputoutput.UserIO;

public class AwaitingCalculatingStateHandler implements StateHandler {
    private final UserIO userIO;
    private final StateController statesMachine;


    public AwaitingCalculatingStateHandler(UserIO userIO, StateController statesMachine) {
        this.statesMachine = statesMachine;
        this.userIO = userIO;
    }

    @Override
    public void handle() {
        try {
            long sum = statesMachine.getValue(AvaibleValue.SUM);
            long percent = statesMachine.getValue(AvaibleValue.PERCENT);
            long term = statesMachine.getValue(AvaibleValue.TERM);
            DepositCalculator calculator = new DepositCalculator();
            DepositCalculationResult calculationResult = calculator.startCalculate(new DepositValueForCalculation(sum,term,percent));
            statesMachine.setValue(AvaibleValue.TOTAL, calculationResult.total());
            statesMachine.setValue(AvaibleValue.STONKS, calculationResult.stonks());
            statesMachine.setNextState(AvaibleStates.AWAITING_POST_CALCULATION_RESULT);
        } catch (IllegalArgumentException | ArithmeticException | NullPointerException e) {
            userIO.postValueToUser("%nYou have entered incorrect data, please re-enter");
            statesMachine.clearCalculatingValue();
            statesMachine.setNextState(AvaibleStates.AWAITING_SUM);

        }
    }
}
