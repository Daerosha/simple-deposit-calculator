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
class AwaitingSumStateHandlerTest {

    @Mock
    private UserIO userIO;

    @Mock
    private StateController statesMachine;

    @InjectMocks
    private AwaitingSumStateHandler awaitingSumService;

    @Test
    void shouldSetSumAndTransitionToTermStateWhenValidSumIsProvided()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("100000");

        awaitingSumService.handle();

        verify(statesMachine, times(1)).setValue(100000);
        verify(statesMachine, times(1)).setNextState(AvaibleStates.AWAITING_TERM);
    }

    @Test
    void shouldSetMinimumSumAndTransitionWhenMinimumValueIsProvided()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("1");

        awaitingSumService.handle();

        verify(statesMachine, times(1)).setValue(1);
        verify(statesMachine, times(1)).setNextState(AvaibleStates.AWAITING_TERM);
    }

    @Test
    void shouldSetMaximumSumAndTransitionWhenMaximumValueIsProvided()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("30000000");

        awaitingSumService.handle();

        verify(statesMachine, times(1)).setValue(30000000);
        verify(statesMachine, times(1)).setNextState(AvaibleStates.AWAITING_TERM);
    }

    @Test
    void shouldDisplayErrorMessageAndNotTransitionWhenSumIsBelowMinimum()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("0");

        awaitingSumService.handle();

        verify(userIO, times(1)).postValueToUser("%nEnter the integer deposit amount (from 1 to 30 000 000):");
        verify(statesMachine, never()).setValue(anyInt());
        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }

    @Test
    void shouldDisplayErrorMessageAndNotTransitionWhenSumExceedsMaximum()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("30000001");

        awaitingSumService.handle();

        verify(userIO, times(1)).postValueToUser("%nEnter the integer deposit amount (from 1 to 30 000 000):");
        verify(statesMachine, never()).setValue(anyInt());
        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }

    @Test
    void shouldDisplayErrorMessageAndNotTransitionWhenInvalidNumberFormatIsProvided()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("abc");

        awaitingSumService.handle();

        verify(userIO, times(1)).postValueToUser(
                "%nError: You entered a number that is too large or not an integer when entering the sum!");
        verify(statesMachine, never()).setValue(anyInt());
        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }

    @Test
    void shouldDisplayErrorMessageAndNotTransitionWhenIOExceptionOccurs()
            throws IOException {
        when(userIO.getValueFromUser()).thenThrow(new IOException());

        awaitingSumService.handle();

        verify(userIO, times(1)).postValueToUser(
                "%nError: You entered a number that is too large or not an integer when entering the sum!");
        verify(statesMachine, never()).setValue(anyInt());
        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }

    @Test
    void shouldDisplayFormattedPromptWithGroupingSeparatorWhenHandleIsCalled()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("100000");

        awaitingSumService.handle();

        verify(userIO, times(1)).postValueToUser(contains("from 1 to 30 000 000"));
    }

    @Test
    void shouldHandleNegativeValueAndDisplayErrorMessage()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("-1000");

        awaitingSumService.handle();

        verify(userIO, times(1)).postValueToUser("%nEnter the integer deposit amount (from 1 to 30 000 000):");
        verify(statesMachine, never()).setValue(anyInt());
        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }

    @Test
    void shouldHandleDecimalValueAndDisplayErrorMessage()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("1000.50");

        awaitingSumService.handle();

        verify(userIO, times(1)).postValueToUser(
                "%nError: You entered a number that is too large or not an integer when entering the sum!");
        verify(statesMachine, never()).setValue(anyInt());
        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }

    @Test
    void shouldHandleLargeNumberBeyondIntegerRangeAndDisplayErrorMessage()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("9999999999");

        awaitingSumService.handle();

        verify(userIO, times(1)).postValueToUser("%nEnter the integer deposit amount (from 1 to 30 000 000):");
        verify(statesMachine, never()).setValue(anyInt());
        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }

    @Test
    void shouldHandleEmptyInputAndDisplayErrorMessage()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("");

        awaitingSumService.handle();

        verify(userIO, times(1)).postValueToUser(
                "%nError: You entered a number that is too large or not an integer when entering the sum!");
        verify(statesMachine, never()).setValue(anyInt());
        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }
}