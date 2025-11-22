package ru.github.calculator.states;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.github.calculator.history.CalculatingHistory;
import ru.github.calculator.view.View;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AwaitingHistoryChoiceHandlerTest {

    @Mock
    private View view;
    @Mock
    private CalculatingHistory history;

    @Test
    void shouldShowHistoryAndTransitionToAwaitingContinueChoice() throws IOException {
        StateContext context = new StateContext();
        AwaitingHistoryChoiceHandler handler = new AwaitingHistoryChoiceHandler(history);
        when(view.read()).thenReturn("yes");
        when(history.getHistoryAsString()).thenReturn("History");

        handler.handle(context, view);

        verify(view).write(history.getHistoryAsString());
        assertEquals(State.AWAITING_CONTINUE_CHOICE, context.getCurrentState());
    }

    @Test
    void shouldNotShowHistoryAndTransitionToAwaitingContinueChoice() throws IOException {
        StateContext context = new StateContext();
        AwaitingHistoryChoiceHandler handler = new AwaitingHistoryChoiceHandler(history);
        when(view.read()).thenReturn("no");

        handler.handle(context, view);

        verify(view, never()).write(history.getHistoryAsString());
        assertEquals(State.AWAITING_CONTINUE_CHOICE, context.getCurrentState());
    }
}
