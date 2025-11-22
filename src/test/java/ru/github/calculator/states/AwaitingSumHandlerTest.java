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
class AwaitingSumHandlerTest {

    @Mock
    private View view;

    @Test
    void shouldSetSumAndTransitionToAwaitingTerm() throws IOException {
        StateContext context = new StateContext();
        AwaitingSumHandler handler = new AwaitingSumHandler();
        when(view.read()).thenReturn("1000");

        handler.handle(context, view);

        assertEquals(1000, context.getSum());
        assertEquals(State.AWAITING_TERM, context.getCurrentState());
    }

    @Test
    void shouldShowErrorForInvalidInput() throws IOException {
        StateContext context = new StateContext();
        context.setCurrentState(State.AWAITING_SUM);
        AwaitingSumHandler handler = new AwaitingSumHandler();
        when(view.read()).thenReturn("abc");

        handler.handle(context, view);

        assertEquals(State.AWAITING_SUM, context.getCurrentState());
    }

    @Test
    void shouldShowErrorForNonPositiveInput() throws IOException {
        StateContext context = new StateContext();
        context.setCurrentState(State.AWAITING_SUM);
        AwaitingSumHandler handler = new AwaitingSumHandler();
        when(view.read()).thenReturn("-100");

        handler.handle(context, view);

        assertEquals(State.AWAITING_SUM, context.getCurrentState());
    }
}
