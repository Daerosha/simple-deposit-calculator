package ru.github.calculator.states;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.github.calculator.dto.DepositCalculationResult;
import ru.github.calculator.dto.DepositValueForCalculation;
import ru.github.calculator.dto.DepositValueForHistory;

import java.lang.reflect.Field;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class StateControlTest {

    private StateControl stateControl;

    @BeforeEach
    void setUp() {
        stateControl = new StateControl();
    }

    @Test
    void shouldStoreSumValueInCalculatingValueMapWhenSetSumIsCalled() throws Exception {
        stateControl.setSum(1000);

        Field field = StateControl.class.getDeclaredField("CalculatingValue");
        field.setAccessible(true);
        HashMap<String, Long> map = (HashMap<String, Long>) field.get(stateControl);

        assertEquals(1000L, map.get("sum"));
    }

    @Test
    void shouldStoreTermValueInCalculatingValueMapWhenSetTermIsCalled() throws Exception {
        stateControl.setTerm(12);

        Field field = StateControl.class.getDeclaredField("CalculatingValue");
        field.setAccessible(true);
        HashMap<String, Long> map = (HashMap<String, Long>) field.get(stateControl);

        assertEquals(12L, map.get("term"));
    }

    @Test
    void shouldStorePercentValueInCalculatingValueMapWhenSetPercentIsCalled() throws Exception {
        stateControl.setPercent(5);

        Field field = StateControl.class.getDeclaredField("CalculatingValue");
        field.setAccessible(true);
        HashMap<String, Long> map = (HashMap<String, Long>) field.get(stateControl);

        assertEquals(5L, map.get("percent"));
    }

    @Test
    void shouldStoreStonksValueInCalculatingValueMapWhenSetStonksIsCalled() throws Exception {
        stateControl.setStonks(500L);

        Field field = StateControl.class.getDeclaredField("CalculatingValue");
        field.setAccessible(true);
        HashMap<String, Long> map = (HashMap<String, Long>) field.get(stateControl);

        assertEquals(500L, map.get("stonks"));
    }

    @Test
    void shouldStoreTotalValueInCalculatingValueMapWhenSetTotalIsCalled() throws Exception {
        stateControl.setTotal(1500L);

        Field field = StateControl.class.getDeclaredField("CalculatingValue");
        field.setAccessible(true);
        HashMap<String, Long> map = (HashMap<String, Long>) field.get(stateControl);

        assertEquals(1500L, map.get("total"));
    }

    @Test
    void shouldReturnDepositValueForCalculationWithCorrectValuesWhenGetValueForCalculationIsCalled() {
        stateControl.setSum(1000);
        stateControl.setTerm(12);
        stateControl.setPercent(5);

        DepositValueForCalculation result = stateControl.getValueForCalculation();

        assertEquals(1000L, result.sum());
        assertEquals(12L, result.term());
        assertEquals(5L, result.percent());
    }

    @Test
    void shouldReturnDepositCalculationResultWithCorrectValuesWhenGetCalculationResultIsCalled() {
        stateControl.setTotal(1500L);
        stateControl.setStonks(500L);

        DepositCalculationResult result = stateControl.getCalculationResult();

        assertEquals(1500L, result.total());
        assertEquals(500L, result.stonks());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenDepositCalculationResultHasInvalidValues() {
        stateControl.setTotal(500L);
        stateControl.setStonks(500L);

        assertThrows(IllegalArgumentException.class, () -> {
            stateControl.getCalculationResult();
        });
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenDepositCalculationResultHasNegativeTotal() {
        stateControl.setTotal(-100L);
        stateControl.setStonks(50L);

        assertThrows(IllegalArgumentException.class, () -> {
            stateControl.getCalculationResult();
        });
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenDepositCalculationResultHasNegativeStonks() {
        stateControl.setTotal(1000L);
        stateControl.setStonks(-50L);

        assertThrows(IllegalArgumentException.class, () -> {
            stateControl.getCalculationResult();
        });
    }

    @Test
    void shouldReturnDepositValueForHistoryWithCurrentTimestampWhenGetValueForCollectionIsCalled() {
        stateControl.setSum(1000);
        stateControl.setTerm(12);
        stateControl.setPercent(5);
        stateControl.setTotal(1500L);
        stateControl.setStonks(500L);

        DepositValueForHistory history = stateControl.getValueForCollection();

        assertNotNull(history.time());
        assertEquals(1000L, history.depositAmount());
        assertEquals(12L, history.term());
        assertEquals(5L, history.percent());
        assertEquals(1500L, history.resultAmount());
        assertEquals(500L, history.resultStonks());
    }

    @Test
    void shouldClearAllValuesFromCalculatingValueMapWhenClearCalculatingValueIsCalled() throws Exception {
        stateControl.setSum(1000);
        stateControl.setTerm(12);

        Field field = StateControl.class.getDeclaredField("CalculatingValue");
        field.setAccessible(true);
        HashMap<String, Long> map = (HashMap<String, Long>) field.get(stateControl);

        assertFalse(map.isEmpty());

        stateControl.clearCalculatingValue();

        assertTrue(map.isEmpty());
    }

    @Test
    void shouldSetAndGetCurrentStateCorrectlyWhenSetNextStateIsCalled() {
        stateControl.setNextState(AvaibleStates.AwaitingSumState);

        assertEquals(AvaibleStates.AwaitingSumState, stateControl.getCurrentState());
    }

    @Test
    void shouldReturnNullWhenGetCurrentStateIsCalledBeforeSettingState() {
        assertNull(stateControl.getCurrentState());
    }

    @Test
    void shouldReturnNewInstanceEachTimeGetCalculationResultIsCalled() {
        stateControl.setTotal(1500L);
        stateControl.setStonks(500L);

        DepositCalculationResult result1 = stateControl.getCalculationResult();
        DepositCalculationResult result2 = stateControl.getCalculationResult();

        assertNotSame(result1, result2);
    }

    @Test
    void shouldThrowNullPointerExceptionWhenGetCalculationResultWithNullValues() {
        assertThrows(NullPointerException.class, () -> {
            stateControl.getCalculationResult();
        });
    }

    @Test
    void shouldThrowNullPointerExceptionWhenGetValueForCollectionWithNullValues() {
        assertThrows(NullPointerException.class, () -> {
            stateControl.getValueForCollection();
        });
    }

    @Test
    void shouldThrowNullPointerExceptionWhenGetValueForCalculationWithNullValues() {
        assertThrows(NullPointerException.class, () -> {
            stateControl.getValueForCalculation();
        });
    }

    @Test
    void shouldOverwritePreviousSumValueWhenSetSumIsCalledMultipleTimes() throws Exception {
        stateControl.setSum(1000);
        stateControl.setSum(2000);

        Field field = StateControl.class.getDeclaredField("CalculatingValue");
        field.setAccessible(true);
        HashMap<String, Long> map = (HashMap<String, Long>) field.get(stateControl);

        assertEquals(2000L, map.get("sum"));
    }

    @Test
    void shouldHandleNegativeValuesWhenSetSumTermAndPercentAreCalled() throws Exception {
        stateControl.setSum(-1000);
        stateControl.setTerm(-12);
        stateControl.setPercent(-5);

        Field field = StateControl.class.getDeclaredField("CalculatingValue");
        field.setAccessible(true);
        HashMap<String, Long> map = (HashMap<String, Long>) field.get(stateControl);

        assertEquals(-1000L, map.get("sum"));
        assertEquals(-12L, map.get("term"));
        assertEquals(-5L, map.get("percent"));
    }
}