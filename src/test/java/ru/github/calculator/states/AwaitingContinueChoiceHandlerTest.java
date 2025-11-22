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
class AwaitingContinueChoiceHandlerTest {

    @Mock
    private View view;

    @Test
    void shouldTransitionToAwaitingSumWhenUserSaysYes() throws IOException {
        StateContext context = new StateContext();
        AwaitingContinueChoiceHandler handler = new AwaitingContinueChoiceHandler();
        when(view.read()).thenReturn("yes");

        handler.handle(context, view);

        assertEquals(State.AWAITING_SUM, context.getCurrentState());
    }

    @Test
    void shouldTransitionToExitingWhenUserSaysNo() throws IOException {
        StateContext context = new StateContext();
        AwaitingContinueChoiceHandler handler = new AwaitingContinueChoiceHandler();
        when(view.read()).thenReturn("no");

        handler.handle(context, view);

        assertEquals(State.EXITING, context.getCurrentState());
    }
}
