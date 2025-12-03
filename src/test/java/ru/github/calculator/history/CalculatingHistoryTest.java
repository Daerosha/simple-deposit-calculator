package ru.github.calculator.history;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.github.calculator.dto.DepositValueForHistory;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CalculatingHistoryTest {

    private CalculatingHistory history;

//  @BeforeEach
//  void setUp() {
//      history = new CalculatingHistory();
//      CalculatingHistory.CALCULATING_HISTORY.clear();
//  }

    @Test
    void shouldAddCalculationDataToHistoryWhenSaveDataForCollectionHistoryIsCalled() {
        DepositValueForHistory testData = new DepositValueForHistory(
                LocalDateTime.now(),
                10000L,
                12L,
                50L,
                150000L,
                50000L
        );

        history.saveDataForCollectionHistory(testData);

        assertEquals(1, CalculatingHistory.CALCULATING_HISTORY.size());
        assertEquals(testData, CalculatingHistory.CALCULATING_HISTORY.get(0));
    }

    @Test
    void shouldReturnFormattedHistoryStringWhenGetCalculationHistoryIsCalled() {
        DepositValueForHistory testData = new DepositValueForHistory(
                LocalDateTime.now(),
                10000L,
                12L,
                50L,
                150000L,
                50000L
        );

        history.saveDataForCollectionHistory(testData);
        String result = history.getCalculationHistory();

        assertTrue(result.contains("Number of Calculation: 1"));
        assertTrue(result.contains("Deposit Amount: 10 000.00"));
        assertTrue(result.contains("Term: 12 months"));
        assertTrue(result.contains("Percent: 50 %%"));
        assertTrue(result.contains("Total Amount: 1 500.00"));
        assertTrue(result.contains("Profit: 500.00"));
    }

    @Test
    void shouldAppendMultipleCalculationsWithCorrectNumberingWhenSaveDataForCollectionHistoryIsCalledMultipleTimes() {
        DepositValueForHistory firstData = new DepositValueForHistory(
                LocalDateTime.now(),
                1000L,
                12L,
                5L,
                106000L,
                6000L
        );

        DepositValueForHistory secondData = new DepositValueForHistory(
                LocalDateTime.now(),
                2000L,
                24L,
                10L,
                248000L,
                48000L
        );

        history.saveDataForCollectionHistory(firstData);
        history.saveDataForCollectionHistory(secondData);

        String result = history.getCalculationHistory();

        assertTrue(result.contains("Number of Calculation: 1"));
        assertTrue(result.contains("Number of Calculation: 2"));
        assertEquals(2, CalculatingHistory.CALCULATING_HISTORY.size());
    }

    @Test
    void shouldReturnEmptyStringWhenGetCalculationHistoryIsCalledOnEmptyHistory() {
        String result = history.getCalculationHistory();

        assertEquals("", result);
    }

    @Test
    void shouldFormatLargeNumbersWithGroupingSeparatorWhenSaveDataForCollectionHistoryIsCalled() {
        DepositValueForHistory testData = new DepositValueForHistory(
                LocalDateTime.now(),
                12345678L,
                24L,
                25L,
                2469135600L,
                1234567800L
        );

        history.saveDataForCollectionHistory(testData);
        String result = history.getCalculationHistory();

        assertTrue(result.contains("Deposit Amount: 12 345 678.00"));
        assertTrue(result.contains("Total Amount: 24 691 356.00"));
    }

    @Test
    void shouldHandleZeroAndNegativeValuesCorrectlyWhenSaveDataForCollectionHistoryIsCalled() {
        DepositValueForHistory testData = new DepositValueForHistory(
                LocalDateTime.now(),
                -1000L,
                0L,
                -5L,
                -150000L,
                -50000L
        );

        history.saveDataForCollectionHistory(testData);
        String result = history.getCalculationHistory();

        assertTrue(result.contains("Deposit Amount: -1 000.00"));
        assertTrue(result.contains("Term: 0 months"));
        assertTrue(result.contains("Percent: -5 %%"));
        assertTrue(result.contains("Total Amount: -1 500.00"));
        assertTrue(result.contains("Profit: -500.00"));
    }
}