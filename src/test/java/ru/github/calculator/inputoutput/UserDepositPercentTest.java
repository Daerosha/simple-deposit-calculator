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
class UserDepositPercentTest {
    private static final int MAX_PERCENT = 50;
    private static final int MIN_PERCENT = 1;

    @Mock
    UserInputAndOutput ioPercent;

    @InjectMocks
    private UserDepositPercent percentService;

    @Test
    void shouldGetValueCorrectlyWhenPercentIsValid() throws IOException {
        Mockito.when(ioPercent.getDataForCalculation()).thenReturn("20");

        int percent = percentService.getDepositValue();

        assertEquals(20, percent);
        verify(ioPercent, times(1)).post("%nEnter the integer deposit percentage (between " + MIN_PERCENT + " and " + MAX_PERCENT + "):");
        verify(ioPercent, times(1)).getDataForCalculation();
        verifyNoMoreInteractions(ioPercent);
    }

    @Test
    void shouldGetValueCorrectlyWhenPercentIsMinAfterValueExceedsLowerBoundary() throws IOException {
        Mockito.when(ioPercent.getDataForCalculation()).thenReturn("0").thenReturn("1");

        int percent = percentService.getDepositValue();

        assertEquals(1, percent);
        verify(ioPercent, times(1)).post("%nEnter the integer deposit percentage (between " + MIN_PERCENT + " and " + MAX_PERCENT + "):");
        verify(ioPercent, times(1)).post("%nYou entered an incorrect number, it must be between " + MIN_PERCENT + " and " + MAX_PERCENT + ", please try again:");
        verify(ioPercent, times(2)).getDataForCalculation();
        verifyNoMoreInteractions(ioPercent);
    }

    @Test
    void shouldGetValueCorrectlyWhenPercentIsMaxAfterValueExceedsUpperBoundary() throws IOException {
        Mockito.when(ioPercent.getDataForCalculation()).thenReturn("51").thenReturn("50");

        int percent = percentService.getDepositValue();

        assertEquals(50, percent);
        verify(ioPercent, times(1)).post("%nEnter the integer deposit percentage (between " + MIN_PERCENT + " and " + MAX_PERCENT + "):");
        verify(ioPercent, times(1)).post("%nYou entered an incorrect number, it must be between " + MIN_PERCENT + " and " + MAX_PERCENT + ", please try again:");
        verify(ioPercent, times(2)).getDataForCalculation();
        verifyNoMoreInteractions(ioPercent);
    }

    @Test
    void shouldGetValueCorrectlyWhenPercentIsMaxAfterInvalidValue() throws IOException {
        UserDepositValue percentService = new UserDepositPercent(ioPercent);
        Mockito.when(ioPercent.getDataForCalculation())
                .thenReturn("asd")                         // NumberFormatException 1
                .thenReturn(null)                          // NumberFormatException 2
                .thenReturn("#!@")                         //  NumberFormatException 3
                .thenReturn("1*20")                        // NumberFormatException 4
                .thenReturn("50.1")                        // NumberFormatException 5
                .thenReturn("0,99")                        // NumberFormatException 6
                .thenReturn("123123123123123123123")       // NumberFormatException 7
                .thenThrow(new IOException(""))              //IOException 8
                .thenThrow(new NumberFormatException(""))    // NumberFormatException 9
                .thenReturn(String.valueOf(10));


        int percent = percentService.getDepositValue();

        assertEquals(10, percent);
        verify(ioPercent, times(1)).post("%nEnter the integer deposit percentage (between " + MIN_PERCENT + " and " + MAX_PERCENT + "):");
        verify(ioPercent, times(9)).post("%nError: You entered a number that is too large or not an integer when entering the percentage! Re-enter:");
        verify(ioPercent, times(10)).getDataForCalculation();
        verifyNoMoreInteractions(ioPercent);
    }

    @Test
    void shouldGetValueCorrectlyWhenPercentIsMaxAfterValueExceedsUpperBoundaryAndCatchException() throws IOException {
        Mockito.when(ioPercent.getDataForCalculation()).thenReturn("52").thenReturn("asd").thenReturn("50");

        int percent = percentService.getDepositValue();

        assertEquals(50, percent);
        verify(ioPercent, times(1)).post("%nEnter the integer deposit percentage (between " + MIN_PERCENT + " and " + MAX_PERCENT + "):");
        verify(ioPercent, times(1)).post("%nYou entered an incorrect number, it must be between " + MIN_PERCENT + " and " + MAX_PERCENT + ", please try again:");
        verify(ioPercent, times(1)).post("%nError: You entered a number that is too large or not an integer when entering the percentage! Re-enter:");
        verify(ioPercent, times(3)).getDataForCalculation();
        verifyNoMoreInteractions(ioPercent);
    }
}

