package ru.github.calculator.inputoutput;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDepositTermTest {
    private static final int MAX_YEARS = 15;
    private static final int MIN_TERM = 1;

    @Mock
    UserInputAndOutput ioTerm;

    @InjectMocks
    private UserDepositTerm termService;

    @Test
    void shouldGetValueCorrectlyWhenValueInValidMonth() throws IOException {
        Mockito.when(ioTerm.getDataForCalculation()).thenReturn("13m");

        int term = termService.getDepositValue();

        assertEquals(13, term);
        verify(ioTerm, times(1)).getDataForCalculation();
        verify(ioTerm, times(1)).post("%nEnter integer terms of months or years (no less than " + MIN_TERM + " month and no more than " + MAX_YEARS + " years)%nWhere 1m means one month and 10y means ten years, only 1 value can be used):");
        verifyNoMoreInteractions(ioTerm);
    }

    @Test
    void shouldGetValueCorrectlyWhenValueInValidYears() throws IOException {
        Mockito.when(ioTerm.getDataForCalculation()).thenReturn("11y");

        int term = termService.getDepositValue();

        assertEquals(132, term);
        verify(ioTerm, times(1)).getDataForCalculation();
        verify(ioTerm, times(1)).post("%nEnter integer terms of months or years (no less than " + MIN_TERM + " month and no more than " + MAX_YEARS + " years)%nWhere 1m means one month and 10y means ten years, only 1 value can be used):");
        verifyNoMoreInteractions(ioTerm);
    }

    @Test
    void shouldGetValueCorrectlyWhenMonthIsMinAfterValueExceedsLowerBoundary() throws IOException {
        Mockito.when(ioTerm.getDataForCalculation()).thenReturn("0m").thenReturn("1m");

        int term = termService.getDepositValue();

        assertEquals(1, term);
        verify(ioTerm, times(2)).getDataForCalculation();
        verify(ioTerm, times(1)).post("%nEnter integer terms of months or years (no less than " + MIN_TERM + " month and no more than " + MAX_YEARS + " years)%nWhere 1m means one month and 10y means ten years, only 1 value can be used):");
        verify(ioTerm, times(1)).post("%nYou have entered an incorrect deposit term!%nEnter in terms of months or years (no less than " + MIN_TERM + " month and no more than " + MAX_YEARS + " years)%nWhere 1m means one month and 10y means ten years, only 1 value can be used):");
        verifyNoMoreInteractions(ioTerm);
    }

    @Test
    void shouldGetValueCorrectlyWhenMonthIsMaxAfterValueExceedsUpperBoundary() throws IOException {
        Mockito.when(ioTerm.getDataForCalculation()).thenReturn("181m").thenReturn("180m");

        int term = termService.getDepositValue();

        assertEquals(180, term);
        verify(ioTerm, times(2)).getDataForCalculation();
        verify(ioTerm, times(1)).post("%nEnter integer terms of months or years (no less than " + MIN_TERM + " month and no more than " + MAX_YEARS + " years)%nWhere 1m means one month and 10y means ten years, only 1 value can be used):");
        verify(ioTerm, times(1)).post("%nYou have entered an incorrect deposit term!%nEnter in terms of months or years (no less than " + MIN_TERM + " month and no more than " + MAX_YEARS + " years)%nWhere 1m means one month and 10y means ten years, only 1 value can be used):");
        verifyNoMoreInteractions(ioTerm);
    }

    @Test
    void shouldGetValueCorrectlyWhenYearsIsMinAfterValueExceedsLowerBoundary() throws IOException {
        Mockito.when(ioTerm.getDataForCalculation()).thenReturn("0y").thenReturn("1y");

        int term = termService.getDepositValue();

        assertEquals(12, term);
        verify(ioTerm, times(2)).getDataForCalculation();
        verify(ioTerm, times(1)).post("%nEnter integer terms of months or years (no less than " + MIN_TERM + " month and no more than " + MAX_YEARS + " years)%nWhere 1m means one month and 10y means ten years, only 1 value can be used):");
        verify(ioTerm, times(1)).post("%nYou have entered an incorrect deposit term!%nEnter in terms of months or years (no less than " + MIN_TERM + " month and no more than " + MAX_YEARS + " years)%nWhere 1m means one month and 10y means ten years, only 1 value can be used):");
        verifyNoMoreInteractions(ioTerm);
    }

    @Test
    void shouldGetValueCorrectlyWhenYearsIsMaxAfterValueExceedsUpperBoundary() throws IOException {
        Mockito.when(ioTerm.getDataForCalculation()).thenReturn("16y").thenReturn("15y");

        int term = termService.getDepositValue();

        assertEquals(180, term);
        verify(ioTerm, times(2)).getDataForCalculation();
        verify(ioTerm, times(1)).post("%nEnter integer terms of months or years (no less than " + MIN_TERM + " month and no more than " + MAX_YEARS + " years)%nWhere 1m means one month and 10y means ten years, only 1 value can be used):");
        verify(ioTerm, times(1)).post("%nYou have entered an incorrect deposit term!%nEnter in terms of months or years (no less than " + MIN_TERM + " month and no more than " + MAX_YEARS + " years)%nWhere 1m means one month and 10y means ten years, only 1 value can be used):");
        verifyNoMoreInteractions(ioTerm);
    }

    @Test
    void shouldGetValueCorrectlyWhenTermIsValidAfterInvalidValue() throws IOException {
        Mockito.when(ioTerm.getDataForCalculation())
                .thenReturn("12a")
                .thenReturn(null)
                .thenReturn("#!@")
                .thenReturn("9")
                .thenReturn("-13m")
                .thenReturn("-14y")
                .thenReturn("y12")
                .thenReturn("m1")
                .thenReturn("0.5y")
                .thenReturn("0.5m")
                .thenReturn("1*20m")
                .thenReturn("12м")
                .thenReturn("12у")
                .thenReturn("12my")
                .thenReturn("13ym")
                .thenReturn("123123123123123123123m")
                .thenReturn("123123123123123123123y")
                .thenReturn("10y");

        int term = termService.getDepositValue();

        assertEquals(120, term);
        verify(ioTerm, times(1)).post("%nEnter integer terms of months or years (no less than " + MIN_TERM + " month and no more than " + MAX_YEARS + " years)%nWhere 1m means one month and 10y means ten years, only 1 value can be used):");
        verify(ioTerm, times(17)).post("%nYou have entered an incorrect deposit term!%nEnter in terms of months or years (no less than " + MIN_TERM + " month and no more than " + MAX_YEARS + " years)%nWhere 1m means one month and 10y means ten years, only 1 value can be used):");
        verify(ioTerm, times(18)).getDataForCalculation();
        verifyNoMoreInteractions(ioTerm);
    }

    @Test
    void shouldGetValueCorrectlyWhenTermIsMaxAfterValueExceedsUpperBoundaryAndCatchException() throws IOException {
        Mockito.when(ioTerm.getDataForCalculation()).thenReturn("12").thenReturn("asd").thenReturn("16y").thenReturn("0m").thenReturn(null).thenReturn("100m");

        int term = termService.getDepositValue();

        assertEquals(100, term);
        verify(ioTerm, times(1)).post("%nEnter integer terms of months or years (no less than " + MIN_TERM + " month and no more than " + MAX_YEARS + " years)%nWhere 1m means one month and 10y means ten years, only 1 value can be used):");
        verify(ioTerm, times(5)).post("%nYou have entered an incorrect deposit term!%nEnter in terms of months or years (no less than " + MIN_TERM + " month and no more than " + MAX_YEARS + " years)%nWhere 1m means one month and 10y means ten years, only 1 value can be used):");
        verify(ioTerm, times(6)).getDataForCalculation();
        verifyNoMoreInteractions(ioTerm);
    }
}