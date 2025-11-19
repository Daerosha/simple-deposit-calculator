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
class UserDepositSumTest {
    private static final String MAX_SUM = "30 000 000";
    private static final int MIN_SUM = 1;

    @Mock
    UserInputAndOutput ioSum;

    @InjectMocks
    private UserDepositSum sumService;

    @Test
    void shouldGetValueCorrectlyWhenSumtIsValid() throws IOException {
        Mockito.when(ioSum.getDataForCalculation()).thenReturn("100000");

        int sum = sumService.getDepositValue();

        assertEquals(100000, sum);
        verify(ioSum, times(1)).getDataForCalculation();
        verify(ioSum, times(1)).post("%nEnter the integer deposit amount (from " + MIN_SUM + " to " + MAX_SUM + "):");
        verifyNoMoreInteractions(ioSum);
    }

    @Test
    void shouldGetValueCorrectlyWhenSumIsMinAfterValueExceedsLowerBoundary() throws IOException {
        Mockito.when(ioSum.getDataForCalculation()).thenReturn("0").thenReturn("1");

        int sum = sumService.getDepositValue();

        assertEquals(1, sum);
        verify(ioSum, times(2)).getDataForCalculation();
        verify(ioSum, times(1)).post("%nEnter the integer deposit amount (from " + MIN_SUM + " to " + MAX_SUM + "):");
        verify(ioSum, times(1)).post("%nYou entered an incorrect number it should be between " + MIN_SUM + " and " + MAX_SUM + ", please try again:");
        verifyNoMoreInteractions(ioSum);
    }

    @Test
    void shouldGetValueCorrectlyWhenSumIsMaxAfterValueExceedsUpperBoundary() throws IOException {
        Mockito.when(ioSum.getDataForCalculation()).thenReturn("30000001").thenReturn("30000000");

        int sum = sumService.getDepositValue();

        assertEquals(30000000, sum);
        verify(ioSum, times(2)).getDataForCalculation();
        verify(ioSum, times(1)).post("%nEnter the integer deposit amount (from " + MIN_SUM + " to " + MAX_SUM + "):");
        verify(ioSum, times(1)).post("%nYou entered an incorrect number it should be between " + MIN_SUM + " and " + MAX_SUM + ", please try again:");
        verifyNoMoreInteractions(ioSum);
    }

    @Test
    void shouldGetValueCorrectlyWhenSumIsMaxAfterInvalidValue() throws IOException {
        Mockito.when(ioSum.getDataForCalculation())
                .thenReturn("asd")                         // NumberFormatException 1
                .thenReturn(null)                          // NumberFormatException 2
                .thenReturn("#!@")                         //  NumberFormatException 3
                .thenReturn("1*20")                        // NumberFormatException 4
                .thenReturn("30000000.1")                  // NumberFormatException 5
                .thenReturn("0,99")                        // NumberFormatException 6
                .thenReturn("123123123123123123123")       // NumberFormatException 7
                .thenThrow(new IOException(""))              //IOException 8
                .thenThrow(new NumberFormatException(""))    // NumberFormatException 9
                .thenReturn(String.valueOf(10));


        int sum = sumService.getDepositValue();

        assertEquals(10, sum);
        verify(ioSum, times(1)).post("%nEnter the integer deposit amount (from " + MIN_SUM + " to " + MAX_SUM + "):");
        verify(ioSum, times(9)).post("%nError: You entered a number that is too large or not an integer when entering the sum! Re-enter:");
        verify(ioSum, times(10)).getDataForCalculation();
        verifyNoMoreInteractions(ioSum);
    }

    @Test
    void shouldGetValueCorrectlyWhenSumIsMaxAfterValueExceedsUpperBoundaryAndCatchException() throws IOException {
        Mockito.when(ioSum.getDataForCalculation()).thenReturn("99999999").thenReturn("asd").thenReturn("50");

        int sum = sumService.getDepositValue();

        assertEquals(50, sum);
        verify(ioSum, times(1)).post("%nEnter the integer deposit amount (from " + MIN_SUM + " to " + MAX_SUM + "):");
        verify(ioSum, times(1)).post("%nYou entered an incorrect number it should be between " + MIN_SUM + " and " + MAX_SUM + ", please try again:");
        verify(ioSum, times(1)).post("%nError: You entered a number that is too large or not an integer when entering the sum! Re-enter:");
        verify(ioSum, times(3)).getDataForCalculation();
        verifyNoMoreInteractions(ioSum);
    }

}