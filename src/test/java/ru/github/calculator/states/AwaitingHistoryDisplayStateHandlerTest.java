package ru.github.calculator.states;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.github.calculator.dto.DepositValueForHistory;
import ru.github.calculator.history.CalculatingHistory;
import ru.github.calculator.inputoutput.UserIO;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AwaitingHistoryDisplayStateHandlerTest {

    @Mock
    private UserIO userIO;

    @Mock
    private StateController statesMachine;

    @InjectMocks
    private AwaitingHistoryDisplayStateHandler awaitingHistoryDisplayStateService;

    @Test
    void shouldDisplayHistoryWithDataWhenUserEntersYes()
            throws IOException {
        CalculatingHistory history = new CalculatingHistory();
        DepositValueForHistory testData = new DepositValueForHistory(
                LocalDateTime.now(),
                1000L,
                12L,
                5L,
                150000L,
                50000L
        );
        history.saveDataForCollectionHistory(testData);
        when(userIO.getValueFromUser()).thenReturn("yes");

        awaitingHistoryDisplayStateService.handle();

        verify(userIO, times(1)).getValueFromUser();
        verify(userIO, times(1)).postValueToUser("Your Calculations: %n");
        verify(userIO, times(1)).postValueToUser("%nDo you want to see the calculation history? Type yes or no:");
        verify(userIO, times(1)).postValueToUser(contains("Number of Calculation: 1"));
        verify(userIO, times(1)).postValueToUser(contains("Deposit Amount: 1 000.00"));
        verify(userIO, times(1)).postValueToUser(contains("Term: 12 months"));
        verify(userIO, times(1)).postValueToUser(contains("Percent: 5 %%"));
        verify(userIO, times(1)).postValueToUser(contains("Total Amount: 1 500.00"));
        verify(userIO, times(1)).postValueToUser(contains("Profit: 500.00"));
        verify(statesMachine, times(1)).setNextState(AvaibleStates.AWAITING_CONTINUE_CALCULATING);
    }

    @Test
    void shouldTransitionToContinueCalculatingWithoutHistoryDisplayWhenUserEntersNo()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("no");

        awaitingHistoryDisplayStateService.handle();

        verify(userIO, times(1)).postValueToUser("%nDo you want to see the calculation history? Type yes or no:");
        verify(userIO, times(1)).getValueFromUser();
        verify(userIO, never()).postValueToUser("Your Calculations: %n");
        verify(userIO, never()).postValueToUser(contains("Number of Calculation"));
        verify(statesMachine, times(1)).setNextState(AvaibleStates.AWAITING_CONTINUE_CALCULATING);
    }

    @Test
    void shouldDisplayErrorMessageWhenIOExceptionOccursDuringInput()
            throws IOException {
        when(userIO.getValueFromUser()).thenThrow(new IOException());

        awaitingHistoryDisplayStateService.handle();

        verify(userIO, times(1)).getValueFromUser();
        verify(userIO, times(1)).postValueToUser("%nYou can only enter yes or no");
        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }

    @Test
    void shouldNotTransitionStateWhenUserEntersNonEnglishValue()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("да");

        awaitingHistoryDisplayStateService.handle();

        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }

    @Test
    void shouldNotTransitionStateWhenUserEntersNumericValue()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("12345");

        awaitingHistoryDisplayStateService.handle();

        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }

    @Test
    void shouldNotTransitionStateWhenUserEntersSpecialCharacters()
            throws IOException {
        when(userIO.getValueFromUser()).thenReturn("#!@#%^~");

        awaitingHistoryDisplayStateService.handle();

        verify(statesMachine, never()).setNextState(any(AvaibleStates.class));
    }
}