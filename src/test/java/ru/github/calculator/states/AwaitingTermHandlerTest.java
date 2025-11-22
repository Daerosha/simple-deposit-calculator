package ru.github.calculator.states;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.github.calculator.view.View;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AwaitingTermHandlerTest {

    @Mock
    private View view;

    @Test
    void shouldSetTermAndTransitionToAwaitingPercent() throws IOException {
        StateContext context = new StateContext();
        AwaitingTermHandler handler = new AwaitingTermHandler();
        when(view.read()).thenReturn("12");

        handler.handle(context, view);

        assertEquals(12, context.getTerm());
        assertEquals(State.AWAITING_PERCENT, context.getCurrentState());
    }

    @Test
    void shouldShowErrorForInvalidInput() throws IOException {
        StateContext context = new StateContext();
        context.setCurrentState(State.AWAITING_TERM);
        AwaitingTermHandler handler = new AwaitingTermHandler();
        when(view.read()).thenReturn("abc");

        handler.handle(context, view);

        assertEquals(State.AWAITING_TERM, context.getCurrentState());
    }

    @Test
    void shouldShowErrorForNonPositiveInput() throws IOException {
        StateContext context = new StateContext();
        context.setCurrentState(State.AWAITING_TERM);
        AwaitingTermHandler handler = new AwaitingTermHandler();
        when(view.read()).thenReturn("0");

        handler.handle(context, view);

        assertEquals(State.AWAITING_TERM, context.getCurrentState());
    }
}
