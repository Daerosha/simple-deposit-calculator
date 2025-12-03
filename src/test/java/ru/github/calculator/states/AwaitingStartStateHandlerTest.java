package ru.github.calculator.states;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.github.calculator.inputoutput.UserIO;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AwaitingStartStateHandlerTest {

    @Mock
    UserIO ioPercent;
    @Mock
    StateController statesMachine;

    @InjectMocks
    private AwaitingStartStateHandler startService;

    @Test
    void shouldShowWelcomeAndSetAwaitingSumStateWhenHandleIsCalled() {
        startService.handle();

        verify(ioPercent, times(1)).postValueToUser("%nWelcome to the simple deposit calculator!%n");
        verify(statesMachine, times(1)).setNextState(AvaibleStates.AWAITING_SUM);
        verifyNoMoreInteractions(ioPercent, statesMachine);
    }
}
