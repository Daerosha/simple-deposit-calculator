package ru.github.calculator.states;

import ru.github.calculator.calculate.DepositCalculator;
import ru.github.calculator.dto.DepositCalculationResult;
import ru.github.calculator.dto.DepositValueForCalculation;
import ru.github.calculator.inputoutput.UserIO;

public class AwaitingCalculatingState implements State {
    private final UserIO userIO;
    private final StateControl statesMachine;


    public AwaitingCalculatingState(UserIO userIO, StateControl statesMachine) {
        this.statesMachine = statesMachine;
        this.userIO = userIO;
    }

    @Override
    public void handle() {
        try {
            DepositValueForCalculation valueForCalculation = statesMachine.getValueForCalculation();
            DepositCalculator calculator = new DepositCalculator();
            DepositCalculationResult calculationResult = calculator.startCalculate(valueForCalculation);
            statesMachine.setTotal(calculationResult.total());
            statesMachine.setStonks(calculationResult.stonks());
            statesMachine.setNextState(AvaibleStates.AwaitingPostCalculationResultState);
        } catch (IllegalArgumentException | ArithmeticException | NullPointerException e) {
            userIO.postValueToUser("%nYou have entered incorrect data, please re-enter");
            statesMachine.clearCalculatingValue();
            statesMachine.setNextState(AvaibleStates.AwaitingSumState);

        }
    }
}
