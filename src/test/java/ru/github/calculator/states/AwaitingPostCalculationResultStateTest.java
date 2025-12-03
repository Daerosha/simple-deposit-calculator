package ru.github.calculator.states;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.github.calculator.dto.DepositCalculationResult;
import ru.github.calculator.inputoutput.UserIO;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AwaitingPostCalculationResultStateTest {

    @Mock
    private UserIO userIO;

    @Mock
    private StateControl statesMachine;

    @InjectMocks
    private AwaitingPostCalculationResultState service;

    @Test
    void shouldFormatAndDisplayTotalAndStonksWhenHandleIsCalled() {
        DepositCalculationResult mockResult = new DepositCalculationResult(150000L, 50000L);
        when(statesMachine.getCalculationResult()).thenReturn(mockResult);

        service.handle();

        verify(statesMachine, times(1)).getCalculationResult();
        verify(userIO, times(1)).postValueToUser(contains("1 500.00"));
        verify(userIO, times(1)).postValueToUser(contains("500.00"));
        verify(statesMachine, times(1)).setNextState(AvaibleStates.AwaitingSaveCalculatingHistoryState);
    }

    @Test
    void shouldFormatLargeNumbersWithGroupingSeparatorWhenHandleIsCalled() {
        DepositCalculationResult mockResult = new DepositCalculationResult(12345678900L, 500000000L);
        when(statesMachine.getCalculationResult()).thenReturn(mockResult);

        service.handle();

        verify(userIO, times(1)).postValueToUser(contains("123 456 789.00"));
        verify(userIO, times(1)).postValueToUser(contains("5 000 000.00"));
    }

    @Test
    void shouldFormatDecimalValuesCorrectlyWhenHandleIsCalled() {
        DepositCalculationResult mockResult = new DepositCalculationResult(100L, 0L);
        when(statesMachine.getCalculationResult()).thenReturn(mockResult);

        service.handle();

        verify(userIO, times(1)).postValueToUser(contains("1.00"));
        verify(userIO, times(1)).postValueToUser(contains("0.00"));
    }

    @Test
    void shouldFormatKopeykiValuesCorrectlyWhenHandleIsCalled() {
        DepositCalculationResult mockResult = new DepositCalculationResult(1L, 0L);
        when(statesMachine.getCalculationResult()).thenReturn(mockResult);

        service.handle();

        verify(userIO, times(1)).postValueToUser(contains("0.01"));
        verify(userIO, times(1)).postValueToUser(contains("0.00"));
    }

    @Test
    void shouldTransitionToAwaitingSaveCalculatingHistoryStateWhenHandleIsCalled() {
        DepositCalculationResult mockResult = new DepositCalculationResult(100000L, 50000L);
        when(statesMachine.getCalculationResult()).thenReturn(mockResult);

        service.handle();

        verify(statesMachine, times(1)).setNextState(AvaibleStates.AwaitingSaveCalculatingHistoryState);
    }
}