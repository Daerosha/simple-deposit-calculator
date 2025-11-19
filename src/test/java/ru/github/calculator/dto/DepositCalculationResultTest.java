package ru.github.calculator.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DepositCalculationResultTest {

    @Test
    void shouldThrowIllegalArgumentExceptionWhenTotalIsNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> new DepositCalculationResult(-1, 1));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenStonksIsNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> new DepositCalculationResult(1, -1));
    }

    @ParameterizedTest
    @CsvSource({"1,1", "1,2", "0,1"})
    void shouldThrowIllegalArgumentExceptionWhenTotalLessOrEqualStonks(int total, int stonks) {
        assertThrows(IllegalArgumentException.class,
                () -> new DepositCalculationResult(total, stonks));
    }

    @Test
    void shouldCreateDtoWhenStonksIsZero() {
        DepositCalculationResult result = new DepositCalculationResult(1000, 0);

        assertEquals(1000, result.total());
        assertEquals(0, result.stonks());
    }

    @Test
    void shouldCreateDtoWhenValuesAreValid() {
        DepositCalculationResult result = new DepositCalculationResult(10001, 10000);

        assertEquals(10001, result.total());
        assertEquals(10000, result.stonks());
    }
}