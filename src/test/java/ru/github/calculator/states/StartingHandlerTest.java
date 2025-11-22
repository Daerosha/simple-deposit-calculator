package ru.github.calculator.states;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.github.calculator.view.View;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StartingHandlerTest {

    @Mock
    private View view;

    @Test
    void shouldWelcomeUserAndTransitionToAwaitingSum() throws IOException {
        StateContext context = new StateContext();
        StartingHandler handler = new StartingHandler();

        handler.handle(context, view);

        verify(view).write("%nWelcome to the simple deposit calculator!%n");
        assertEquals(State.AWAITING_SUM, context.getCurrentState());
    }
}
