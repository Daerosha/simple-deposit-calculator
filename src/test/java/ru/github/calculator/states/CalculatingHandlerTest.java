package ru.github.calculator.states;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.github.calculator.calculate.DepositCalculator;
import ru.github.calculator.dto.DepositCalculationResult;
import ru.github.calculator.dto.DepositValueForCalculation;
import ru.github.calculator.view.View;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculatingHandlerTest {

    @Mock
    private View view;
    @Mock
    private DepositCalculator calculator;

    @Test
    void shouldCalculateAndTransitionToDisplayingResult() throws IOException {
        StateContext context = new StateContext();
        context.setSum(1000);
        context.setTerm(12);
        context.setPercent(10);
        DepositCalculationResult expectedResult = new DepositCalculationResult(110000, 10000);
        when(calculator.startCalculate(new DepositValueForCalculation(1000, 12, 10))).thenReturn(expectedResult);
        CalculatingHandler handler = new CalculatingHandler(calculator);

        handler.handle(context, view);

        assertEquals(expectedResult, context.getCalculationResult());
        assertEquals(State.DISPLAYING_RESULT, context.getCurrentState());
    }

    @Test
    void shouldHandleCalculationErrorAndTransitionToAwaitingSum() throws IOException {
        StateContext context = new StateContext();
        context.setSum(0);
        context.setTerm(0);
        context.setPercent(0);
        when(calculator.startCalculate(new DepositValueForCalculation(0, 0, 0))).thenThrow(new IllegalArgumentException());
        CalculatingHandler handler = new CalculatingHandler(calculator);

        handler.handle(context, view);

        assertEquals(State.AWAITING_SUM, context.getCurrentState());
    }
}
