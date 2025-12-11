package ru.github.calculator.states;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.github.calculator.inputoutput.UserIO;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AwaitingContinueCalculatingStateHandlerTest {

    @Mock
    private UserIO userIO;

    @Mock
    private StateController statesMachine;

    @InjectMocks
    private AwaitingContinueCalculatingStateHandler awaitingContinueCalculatingservice;

    @Test
    void shouldTransitionToAwaitingSumStateWhenUserEntersYes()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("yes");

        awaitingContinueCalculatingservice.handle();

        verify(userIO, times(1)).postValueToUser("%nDo you want to calculate again? Type yes or no:");
        verify(userIO, times(1)).getValueFromUser();
        verify(statesMachine, times(1)).setNextState(AvaibleStates.AWAITING_SUM);
    }

    @Test
    void shouldTransitionToAwaitingExitDepositCalculatorWhenUserEntersNo()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("no");

        awaitingContinueCalculatingservice.handle();

        verify(userIO, times(1)).postValueToUser("%nDo you want to calculate again? Type yes or no:");
        verify(userIO, times(1)).getValueFromUser();
        verify(statesMachine, times(1)).setNextState(AvaibleStates.AWAITING_EXIT_DEPOSIT_CALCULATOR);
    }

    @Test
    void shouldNotTransitionStateWhenUserEntersInvalidValue()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("maybe");

        awaitingContinueCalculatingservice.handle();

        verify(userIO, times(1)).postValueToUser("%nDo you want to calculate again? Type yes or no:");
        verify(userIO, times(1)).getValueFromUser();
        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }

    @Test
    void shouldDisplayErrorAndNotTransitionStateWhenIOExceptionOccurs()
            throws IOException {
        when(userIO.getValueFromUser()).thenThrow(new IOException());

        awaitingContinueCalculatingservice.handle();

        verify(userIO, times(1)).postValueToUser("%nDo you want to calculate again? Type yes or no:");
        verify(userIO, times(1)).getValueFromUser();
        verify(userIO, times(1)).postValueToUser("%n You can only enter yes or no:");
        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }

    @Test
    void shouldNotTransitionStateWhenUserEntersNonEnglishValue()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("да");

        awaitingContinueCalculatingservice.handle();

        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }

    @Test
    void shouldNotTransitionStateWhenUserEntersNumericValue()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("12345");

        awaitingContinueCalculatingservice.handle();

        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }

    @Test
    void shouldNotTransitionStateWhenUserEntersSpecialCharacters()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("#!@#%^~");

        awaitingContinueCalculatingservice.handle();

        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }
}