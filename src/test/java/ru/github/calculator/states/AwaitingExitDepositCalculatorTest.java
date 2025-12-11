package ru.github.calculator.states;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.github.calculator.inputoutput.UserIO;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AwaitingExitDepositCalculatorTest {

    @Mock
    private UserIO userIO;

    @Mock
    private StateController statesMachine;

    @InjectMocks
    private AwaitingExitDepositCalculatorStateHandler awaitingExitDepositservice;

    @Test
    void shouldDisplayThankYouMessageWhenHandleIsCalled() {
        awaitingExitDepositservice.handle();

        verify(userIO, times(1)).postValueToUser("%nThank you for using the deposit calculator!%n");
        verify(statesMachine, times(1)).setNextState(AvaibleStates.END);
        verifyNoMoreInteractions(userIO, statesMachine);

    }
}