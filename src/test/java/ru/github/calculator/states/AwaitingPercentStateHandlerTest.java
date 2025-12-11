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
class AwaitingPercentStateHandlerTest {

    @Mock
    private UserIO userIO;

    @Mock
    private StateController statesMachine;

    @InjectMocks
    private AwaitingPercentStateHandler awaitingPercentService;

    @Test
    void shouldSetPercentAndTransitionToCalculatingStateWhenValidPercentIsProvided()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("20");

        awaitingPercentService.handle();

        verify(statesMachine, times(1)).setPercent("percent", );
        verify(statesMachine, times(1)).setNextState(AvaibleStates.AWAITING_CALCULATING);
    }

    @Test
    void shouldSetMinimumPercentAndTransitionWhenMinimumValueIsProvided()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("1");

        awaitingPercentService.handle();

        verify(statesMachine, times(1)).setPercent("percent", );
        verify(statesMachine, times(1)).setNextState(AvaibleStates.AWAITING_CALCULATING);
    }

    @Test
    void shouldSetMaximumPercentAndTransitionWhenMaximumValueIsProvided()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("50");

        awaitingPercentService.handle();

        verify(statesMachine, times(1)).setPercent("percent", );
        verify(statesMachine, times(1)).setNextState(AvaibleStates.AWAITING_CALCULATING);
    }

    @Test
    void shouldNotSetPercentAndNotTransitionWhenPercentIsBelowMinimum()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("0");

        awaitingPercentService.handle();

        verify(statesMachine, never()).setPercent("percent", );
        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }

    @Test
    void shouldNotSetPercentAndNotTransitionWhenPercentExceedsMaximum()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("51");

        awaitingPercentService.handle();

        verify(statesMachine, never()).setPercent("percent", );
        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }

    @Test
    void shouldDisplayErrorMessageAndNotTransitionWhenInvalidNumberFormatIsProvided()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("abc");

        awaitingPercentService.handle();

        verify(userIO, times(1)).postValueToUser(
                "%nError: You entered a number that is too large or not an integer when entering the percentage!");
        verify(statesMachine, never()).setPercent("percent", );
        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }

    @Test
    void shouldDisplayErrorMessageAndNotTransitionWhenIOExceptionOccurs()
            throws IOException {
        when(userIO.getValueFromUser()).thenThrow(new IOException());

        awaitingPercentService.handle();

        verify(userIO, times(1)).postValueToUser(
                "%nError: You entered a number that is too large or not an integer when entering the percentage!");
        verify(statesMachine, never()).setPercent("percent", );
        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }

    @Test
    void shouldDisplayPromptWithBoundariesWhenHandleIsCalled()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("25");

        awaitingPercentService.handle();

        verify(userIO, times(1)).postValueToUser("%nEnter the integer deposit percentage (between 1 and 50):");
    }

    @Test
    void shouldNotSetPercentAndNotTransitionWhenNegativeValueIsProvided()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("-5");

        awaitingPercentService.handle();

        verify(statesMachine, never()).setPercent("percent", );
        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }

    @Test
    void shouldDisplayErrorMessageAndNotTransitionWhenDecimalValueIsProvided()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("25.5");

        awaitingPercentService.handle();

        verify(userIO, times(1)).postValueToUser(
                "%nError: You entered a number that is too large or not an integer when entering the percentage!");
        verify(statesMachine, never()).setPercent("percent", );
        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }

    @Test
    void shouldDisplayErrorMessageAndNotTransitionWhenLargeNumberBeyondIntegerRangeIsProvided()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("9999999999");

        awaitingPercentService.handle();

        verify(userIO, times(1)).postValueToUser(
                "%nError: You entered a number that is too large or not an integer when entering the percentage!");
        verify(statesMachine, never()).setPercent("percent", );
        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }

    @Test
    void shouldDisplayErrorMessageAndNotTransitionWhenEmptyInputIsProvided()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("");

        awaitingPercentService.handle();

        verify(userIO, times(1)).postValueToUser(
                "%nError: You entered a number that is too large or not an integer when entering the percentage!");
        verify(statesMachine, never()).setPercent("percent", );
        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }

    @Test
    void shouldDisplayErrorMessageAndNotTransitionWhenInputContainsSpecialCharacters()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("!@#$%");

        awaitingPercentService.handle();

        verify(userIO, times(1)).postValueToUser(
                "%nError: You entered a number that is too large or not an integer when entering the percentage!");
        verify(statesMachine, never()).setPercent("percent", );
        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }

    @Test
    void shouldDisplayErrorMessageAndNotTransitionWhenInputContainsLettersAndNumbers()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("12a3");

        awaitingPercentService.handle();

        verify(userIO, times(1)).postValueToUser(
                "%nError: You entered a number that is too large or not an integer when entering the percentage!");
        verify(statesMachine, never()).setPercent("percent", );
        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }
}