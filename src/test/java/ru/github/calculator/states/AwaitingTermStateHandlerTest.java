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
class AwaitingTermStateHandlerTest {

    @Mock
    private UserIO userIO;

    @Mock
    private StateController statesMachine;

    @InjectMocks
    private AwaitingTermStateHandler awaitingTermService;

    @Test
    void shouldSetTermAndTransitionToPercentStateWhenValidMonthInputIsProvided()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("13m");

        awaitingTermService.handle();

        verify(statesMachine, times(1)).setTerm(, 13, );
        verify(statesMachine, times(1)).setNextState(AvaibleStates.AWAITING_PERCENT);
    }

    @Test
    void shouldSetTermAndTransitionToPercentStateWhenValidYearInputIsProvided()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("11y");

        awaitingTermService.handle();

        verify(statesMachine, times(1)).setTerm(, 132, );
        verify(statesMachine, times(1)).setNextState(AvaibleStates.AWAITING_PERCENT);
    }

    @Test
    void shouldDisplayErrorMessageAndNotTransitionWhenInputDoesNotMatchPattern()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("12");

        awaitingTermService.handle();

        verify(userIO, times(1)).postValueToUser("%nYou have entered an incorrect deposit term!");
        verify(statesMachine, never()).setTerm(, anyInt(), );
        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }

    @Test
    void shouldDisplayErrorMessageAndNotTransitionWhenTermIsBelowMinimum()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("0m");

        awaitingTermService.handle();

        verify(userIO, times(1)).postValueToUser(
                "%nEnter integer terms of months or years (no less than 1 month and no more than 15 years)%nWhere 1m means one month and 10y means ten years, only 1 value can be used):");
        verify(statesMachine, never()).setTerm(, anyInt(), );
        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }

    @Test
    void shouldDisplayErrorMessageAndNotTransitionWhenTermExceedsMaximum()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("181m");

        awaitingTermService.handle();

        verify(userIO, times(1)).postValueToUser(
                "%nEnter integer terms of months or years (no less than 1 month and no more than 15 years)%nWhere 1m means one month and 10y means ten years, only 1 value can be used):");
        verify(statesMachine, never()).setTerm(, anyInt(), );
        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }

    @Test
    void shouldDisplayErrorMessageAndNotTransitionWhenYearInputExceedsMaximum()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("16y");

        awaitingTermService.handle();

        verify(userIO, times(1)).postValueToUser(
                "%nEnter integer terms of months or years (no less than 1 month and no more than 15 years)%nWhere 1m means one month and 10y means ten years, only 1 value can be used):");

        verify(statesMachine, never()).setTerm(, anyInt(), );
        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }

    @Test
    void shouldHandleNumberFormatExceptionAndDisplayErrorMessage()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("abc");

        awaitingTermService.handle();

        verify(userIO, times(1)).postValueToUser("%nYou have entered an incorrect deposit term!");
        verify(statesMachine, never()).setTerm(, anyInt(), );
        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }

    @Test
    void shouldDisplayErrorMessageAndNotTransitionWhenCyrillicLetterIsUsed()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("12м");

        awaitingTermService.handle();

        verify(userIO, times(1)).postValueToUser("%nYou have entered an incorrect deposit term!");
        verify(statesMachine, never()).setTerm(, anyInt(), );
        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }

    @Test
    void shouldDisplayErrorMessageAndNotTransitionWhenSpecialCharactersAreUsed()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("!@#$%");

        awaitingTermService.handle();

        verify(userIO, times(1)).postValueToUser("%nYou have entered an incorrect deposit term!");
        verify(statesMachine, never()).setTerm(, anyInt(), );
        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }

    @Test
    void shouldHandleIOExceptionAndDisplayErrorMessage()
            throws IOException {
        when(userIO.getValueFromUser()).thenThrow(new IOException());

        awaitingTermService.handle();

        verify(userIO, times(1)).postValueToUser("%nYou have entered an incorrect deposit term!");
        verify(statesMachine, never()).setTerm(, anyInt(), );
        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }

    @Test
    void shouldHandleNullInputAndDisplayErrorMessage()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn(null);

        awaitingTermService.handle();

        verify(userIO, times(1)).postValueToUser("%nYou have entered an incorrect deposit term!");
        verify(statesMachine, never()).setTerm(, anyInt(), );
        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }

    @Test
    void shouldSetMinimumTermAndTransitionWhenMinimumMonthInputIsProvided()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("1m");

        awaitingTermService.handle();

        verify(statesMachine, times(1)).setTerm(, 1, );
        verify(statesMachine, times(1)).setNextState(AvaibleStates.AWAITING_PERCENT);
    }

    @Test
    void shouldSetMaximumTermAndTransitionWhenMaximumMonthInputIsProvided()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("180m");

        awaitingTermService.handle();

        verify(statesMachine, times(1)).setTerm(, 180, );
        verify(statesMachine, times(1)).setNextState(AvaibleStates.AWAITING_PERCENT);
    }

    @Test
    void shouldSetMaximumTermAndTransitionWhenMaximumYearInputIsProvided()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("15y");

        awaitingTermService.handle();

        verify(statesMachine, times(1)).setTerm(, 180, );
        verify(statesMachine, times(1)).setNextState(AvaibleStates.AWAITING_PERCENT);
    }

    @Test
    void shouldConvertToLowerCaseAndProcessWhenUpperCaseInputIsProvided()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("12M");

        awaitingTermService.handle();

        verify(statesMachine, times(1)).setTerm(, 12, );
        verify(statesMachine, times(1)).setNextState(AvaibleStates.AWAITING_PERCENT);
    }

    @Test
    void shouldTrimSpacesAndProcessWhenInputWithSpacesIsProvided()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("  12m  ");

        awaitingTermService.handle();

        verify(statesMachine, times(1)).setTerm(, 12, );
        verify(statesMachine, times(1)).setNextState(AvaibleStates.AWAITING_PERCENT);
    }
}