package ru.github.calculator.states;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.github.calculator.inputoutput.UserIO;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AwaitingStartStateTest {

    @Mock
    UserIO ioPercent;
    @Mock
    StateControl statesMachine;

    @InjectMocks
    private AwaitingStartState startService;

    @Test
    void shouldShowWelcomeAndSetAwaitingSumStateWhenHandleIsCalled() {
        startService.handle();

        verify(ioPercent, times(1)).postValueToUser("%nWelcome to the simple deposit calculator!%n");
        verify(statesMachine, times(1)).setNextState(AvaibleStates.AwaitingSumState);
        verifyNoMoreInteractions(ioPercent, statesMachine);
    }
}
