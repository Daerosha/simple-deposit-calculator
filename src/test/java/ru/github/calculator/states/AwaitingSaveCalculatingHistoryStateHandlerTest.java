package ru.github.calculator.states;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.github.calculator.dto.DepositValueForHistory;
import ru.github.calculator.inputoutput.UserIO;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AwaitingSaveCalculatingHistoryStateHandlerTest {

    @Mock
    private UserIO userIO;

    @Mock
    private StateController statesMachine;

    @InjectMocks
    private AwaitingSaveCalculatingHistoryStateHandler service;

    @Test
    void shouldSaveHistoryClearDataAndTransitionToDisplayWhenHandleIsCalled() {
        DepositValueForHistory mockHistoryData = new DepositValueForHistory(
                LocalDateTime.now(),
                1000L,
                12L,
                5L,
                150000L,
                50000L
        );

        when(statesMachine.getValueForCollection()).thenReturn(mockHistoryData);

        service.handle();

        verify(statesMachine, times(1)).getValueForCollection();
        verify(statesMachine, times(1)).clearCalculatingValue();
        verify(statesMachine, times(1)).setNextState(AvaibleStates.AWAITING_HISTORY_DISPLAY);
        verify(userIO, never()).postValueToUser(anyString());
    }

    @Test
    void shouldClearDataAndTransitionToContinueCalculatingWhenNullPointerExceptionOccurs() {
        when(statesMachine.getValueForCollection()).thenThrow(new NullPointerException());

        service.handle();

        verify(statesMachine, times(1)).clearCalculatingValue();
        verify(statesMachine, times(1)).setNextState(AvaibleStates.AWAITING_CONTINUE_CALCULATING);
        verify(userIO, times(1)).postValueToUser(
                "%n Viewing history is currently unavailable, but you can count again.%n");

    }
}
