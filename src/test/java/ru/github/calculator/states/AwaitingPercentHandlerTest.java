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
class AwaitingPercentHandlerTest {

    @Mock
    private View view;

    @Test
    void shouldSetPercentAndTransitionToCalculating() throws IOException {
        StateContext context = new StateContext();
        AwaitingPercentHandler handler = new AwaitingPercentHandler();
        when(view.read()).thenReturn("10");

        handler.handle(context, view);

        assertEquals(10, context.getPercent());
        assertEquals(State.CALCULATING, context.getCurrentState());
    }

    @Test
    void shouldShowErrorForInvalidInput() throws IOException {
        StateContext context = new StateContext();
        context.setCurrentState(State.AWAITING_PERCENT);
        AwaitingPercentHandler handler = new AwaitingPercentHandler();
        when(view.read()).thenReturn("abc");

        handler.handle(context, view);

        assertEquals(State.AWAITING_PERCENT, context.getCurrentState());
    }

    @Test
    void shouldShowErrorForNonPositiveInput() throws IOException {
        StateContext context = new StateContext();
        context.setCurrentState(State.AWAITING_PERCENT);
        AwaitingPercentHandler handler = new AwaitingPercentHandler();
        when(view.read()).thenReturn("-5");

        handler.handle(context, view);

        assertEquals(State.AWAITING_PERCENT, context.getCurrentState());
    }
}
