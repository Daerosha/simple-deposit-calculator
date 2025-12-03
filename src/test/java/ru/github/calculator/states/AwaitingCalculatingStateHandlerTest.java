package ru.github.calculator.states;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.github.calculator.dto.DepositValueForCalculation;
import ru.github.calculator.inputoutput.UserIO;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AwaitingCalculatingStateHandlerTest {

    @Mock
    private UserIO userIO;

    @Mock
    private StateController statesMachine;

    @InjectMocks
    private AwaitingCalculatingStateHandler service;

    @Test
    void shouldCalculateAndTransitionWhenValidDataIsProvided() {
        DepositValueForCalculation mockValue = new DepositValueForCalculation(1000L, 12L, 5L);
        when(statesMachine.getValueForCalculation()).thenReturn(mockValue);

        service.handle();

        verify(statesMachine, times(1)).setTotal(, anyLong(), );
        verify(statesMachine, times(1)).setStonks(, anyLong(), );
        verify(statesMachine, times(1)).setNextState(AvaibleStates.AWAITING_POST_CALCULATION_RESULT);
        verify(userIO, never()).postValueToUser(anyString());
    }

    @Test
    void shouldDisplayErrorMessageAndRestartWhenDepositCalculatorThrowsIllegalArgumentException() {
        DepositValueForCalculation mockValue = new DepositValueForCalculation(-1000L, 12L, 5L);
        when(statesMachine.getValueForCalculation()).thenReturn(mockValue);

        service.handle();

        verify(userIO, times(1)).postValueToUser("%nYou have entered incorrect data, please re-enter");
        verify(statesMachine, never()).setTotal(, anyLong(), );
        verify(statesMachine, never()).setStonks(, anyLong(), );
        verify(statesMachine, times(1)).setNextState(AvaibleStates.AWAITING_SUM);
        verify(statesMachine, times(1)).clearCalculatingValue();
    }

    @Test
    void shouldDisplayErrorMessageAndRestartWhenArithmeticExceptionOccurs() {
        DepositValueForCalculation mockValue = new DepositValueForCalculation(300000000L, 100000000L, 3000L);
        when(statesMachine.getValueForCalculation()).thenReturn(mockValue);

        service.handle();

        verify(userIO, times(1)).postValueToUser("%nYou have entered incorrect data, please re-enter");
        verify(statesMachine, never()).setTotal(, anyLong(), );
        verify(statesMachine, never()).setStonks(, anyLong(), );
        verify(statesMachine, times(1)).setNextState(AvaibleStates.AWAITING_SUM);
        verify(statesMachine, times(1)).clearCalculatingValue();
    }

    @Test
    void shouldNotSetTotalOrStonksWhenExceptionOccurs() {
        DepositValueForCalculation mockValue = new DepositValueForCalculation(0L, 0L, 0L);
        when(statesMachine.getValueForCalculation()).thenReturn(mockValue);

        service.handle();

        verify(userIO, times(1)).postValueToUser("%nYou have entered incorrect data, please re-enter");
        verify(statesMachine, never()).setTotal(, anyLong(), );
        verify(statesMachine, never()).setStonks(, anyLong(), );
        verify(statesMachine, times(1)).setNextState(AvaibleStates.AWAITING_SUM);
        verify(statesMachine, times(1)).clearCalculatingValue();
    }

    @Test
    void shouldReturnToSumStateNotPostCalculationWhenExceptionOccurs() {
        DepositValueForCalculation mockValue = new DepositValueForCalculation(-1000L, -12L, -5L);
        when(statesMachine.getValueForCalculation()).thenReturn(mockValue);

        service.handle();

        verify(statesMachine, times(1)).setNextState(AvaibleStates.AWAITING_SUM);
        verify(statesMachine, never()).setNextState(AvaibleStates.AWAITING_POST_CALCULATION_RESULT);
    }

    @Test
    void shouldClearPreviousCalculationsBeforeRestartWhenExceptionOccurs() {
        DepositValueForCalculation mockValue = new DepositValueForCalculation(0L, 0L, 0L);
        when(statesMachine.getValueForCalculation()).thenReturn(mockValue);

        service.handle();

        verify(userIO, times(1)).postValueToUser("%nYou have entered incorrect data, please re-enter");
        verify(statesMachine, never()).setTotal(, anyLong(), );
        verify(statesMachine, never()).setStonks(, anyLong(), );
        verify(statesMachine, times(1)).setNextState(AvaibleStates.AWAITING_SUM);
        verify(statesMachine, times(1)).clearCalculatingValue();
    }


    @Test
    void shouldNotProceedWithInvalidDataWhenExceptionOccurs() {
        DepositValueForCalculation mockValue = new DepositValueForCalculation(1000L, 12L, -5L);
        when(statesMachine.getValueForCalculation()).thenReturn(mockValue);

        service.handle();

        verify(userIO, times(1)).postValueToUser("%nYou have entered incorrect data, please re-enter");
        verify(statesMachine, never()).setTotal(, anyLong(), );
        verify(statesMachine, never()).setStonks(, anyLong(), );
        verify(statesMachine, times(1)).setNextState(AvaibleStates.AWAITING_SUM);
        verify(statesMachine, times(1)).clearCalculatingValue();
    }

    @Test
    void shouldDisplayErrorMessageAndRestartWhenNullPointerException() {
        service.handle();

        verify(userIO, times(1)).postValueToUser("%nYou have entered incorrect data, please re-enter");
        verify(statesMachine, never()).setTotal(, anyLong(), );
        verify(statesMachine, never()).setStonks(, anyLong(), );
        verify(statesMachine, times(1)).setNextState(AvaibleStates.AWAITING_SUM);
        verify(statesMachine, times(1)).clearCalculatingValue();
    }

    @Test
    void shouldDisplayErrorMessageAndRestartWhenNullPointerExceptionOccursInHandle() {
        doThrow(new NullPointerException()).when(statesMachine).getValueForCalculation();

        service.handle();

        verify(userIO, times(1)).postValueToUser("%nYou have entered incorrect data, please re-enter");
        verify(statesMachine, never()).setTotal(, anyLong(), );
        verify(statesMachine, never()).setStonks(, anyLong(), );
        verify(statesMachine, times(1)).setNextState(AvaibleStates.AWAITING_SUM);
        verify(statesMachine, times(1)).clearCalculatingValue();
    }
}
