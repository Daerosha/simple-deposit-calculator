package ru.github.calculator.states;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.github.calculator.dto.DepositCalculationResult;
import ru.github.calculator.history.CalculatingHistory;
import ru.github.calculator.view.View;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DisplayingResultHandlerTest {

    @Mock
    private View view;
    @Mock
    private CalculatingHistory history;

    @Test
    void shouldDisplayResultAndTransitionToAwaitingHistoryChoice() throws IOException {
        StateContext context = new StateContext();
        context.setSum(1000);
        context.setTerm(12);
        context.setPercent(10);
        context.setCalculationResult(new DepositCalculationResult(110000, 10000));
        DisplayingResultHandler handler = new DisplayingResultHandler(history);

        handler.handle(context, view);

        verify(view).write("%nThe balance at the end of the deposit is: 1 100.00%nOf this amount, the profit on the deposit is: 100.00");
        verify(history).save(org.mockito.ArgumentMatchers.any());
        assertEquals(State.AWAITING_HISTORY_CHOICE, context.getCurrentState());
    }
}
