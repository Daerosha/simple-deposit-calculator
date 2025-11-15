package ru.github.calculator.calculate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.github.calculator.dto.DepositCalculationResult;
import ru.github.calculator.dto.DepositValueForCalculation;

import static org.junit.jupiter.api.Assertions.*;

class DepositCalculatorTest {
    private static final long KOPEYKI = 100L;
    private final DepositCalculator calculator = new DepositCalculator();

    @Test
    public void shouldCalculateCorrectlyWhenValuesAreSimple() {
        //Arrange
        int sum = 1000;
        int term = 12;
        int percent = 50;
        DepositValueForCalculation valueForCalculation = new DepositValueForCalculation(sum, term, percent);
        //Act
        DepositCalculationResult result = calculator.startCalculate(valueForCalculation);
        //Assert
        assertEquals(1500 * KOPEYKI, result.total());
        assertEquals(500 * KOPEYKI, result.stonks());
        assertTrue(result.total() > result.stonks());
    }


    @Test
    public void shouldCalculateCorrectlyWhenValuesAreAtUpperBoundary() {
        int sum = 30000000;
        int term = 180;
        int percent = 50;
        DepositValueForCalculation valueForCalculation = new DepositValueForCalculation(sum, term, percent);

        DepositCalculationResult result = calculator.startCalculate(valueForCalculation);

        assertEquals(255000000 * KOPEYKI, result.total());
        assertEquals(225000000 * KOPEYKI, result.stonks());
        assertTrue(result.total() > result.stonks());
    }


    @Test
    public void shouldCalculateCorrectlyWhenValuesAreMinimal() {
        int sum = 1;
        int term = 1;
        int percent = 1;
        DepositValueForCalculation valueForCalculation = new DepositValueForCalculation(sum, term, percent);

        DepositCalculationResult result = calculator.startCalculate(valueForCalculation);

        assertEquals(KOPEYKI, result.total());
        assertEquals(0, result.stonks());
        assertTrue(result.total() > result.stonks());
    }

    @Test
    public void shouldThrowArithmeticExceptionWhenValuesCauseOverflow() {
        int sum = 300000000;
        int term = 100000000;
        int percent = 3000;
        DepositValueForCalculation valueForCalculation = new DepositValueForCalculation(sum, term, percent);

        assertThrows(ArithmeticException.class, () -> calculator.startCalculate(valueForCalculation));
    }


    @Test
    public void shouldThrowIllegalArgumentExceptionWhenValuesIsZero() {
        int sum = 0;
        int term = 0;
        int percent = 0;
        DepositValueForCalculation valueForCalculation = new DepositValueForCalculation(sum, term, percent);

        assertThrows(IllegalArgumentException.class, () -> calculator.startCalculate(valueForCalculation));
    }

    @ParameterizedTest
    @CsvSource({"-1,100,50", "100,-1,50", "100,100,-1"})
    public void shouldThrowIllegalArgumentExceptionWhenValuesIsNegative(int sum, int term, int percent) {
        DepositValueForCalculation valueForCalculation = new DepositValueForCalculation(sum, term, percent);

        assertThrows(IllegalArgumentException.class, () -> calculator.startCalculate(valueForCalculation));
    }


    @ParameterizedTest
    @CsvSource({"100,0,50", "100,20,0", "100,0,0"})
    public void shouldThrowIllegalArgumentExceptionWhenTermOrPercentIsZero(int sum, int term, int percent) {
        DepositValueForCalculation valueForCalculation = new DepositValueForCalculation(sum, term, percent);

        DepositCalculationResult result = calculator.startCalculate(valueForCalculation);

        assertEquals(result.total(), sum * KOPEYKI);
        assertEquals(result.stonks(), 0);
        assertTrue(result.total() > result.stonks());
    }
}