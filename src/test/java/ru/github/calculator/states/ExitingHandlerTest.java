package ru.github.calculator.states;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.github.calculator.view.View;

import java.io.IOException;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ExitingHandlerTest {

    @Mock
    private View view;

    @Test
    void shouldShowGoodbyeMessage() throws IOException {
        StateContext context = new StateContext();
        ExitingHandler handler = new ExitingHandler();

        handler.handle(context, view);

        verify(view).write("%nThank you for using the simple deposit calculator!%n");
    }
}
