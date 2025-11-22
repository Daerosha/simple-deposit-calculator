package ru.github.calculator.states;

import ru.github.calculator.calculate.DepositCalculator;
import ru.github.calculator.dto.DepositCalculationResult;
import ru.github.calculator.dto.DepositValueForCalculation;
import ru.github.calculator.view.View;

import java.io.IOException;

public class CalculatingHandler implements StateHandler {
    private final DepositCalculator calculator;

    public CalculatingHandler(DepositCalculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public void handle(StateContext context, View view) throws IOException {
        try {
            DepositCalculationResult calculationsResult = calculator.startCalculate(
                    new DepositValueForCalculation(context.getSum(), context.getTerm(), context.getPercent())
            );
            context.setCalculationResult(calculationsResult);
            context.setCurrentState(State.DISPLAYING_RESULT);
        } catch (IllegalArgumentException e) {
            view.write("%nError during calculation, please try entering the data again%n" + e.getMessage() + "%n");
            context.setCurrentState(State.AWAITING_SUM);
        }
    }
}
