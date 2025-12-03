package ru.github.calculator.states;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.github.calculator.dto.DepositCalculationResult;
import ru.github.calculator.dto.DepositValueForCalculation;
import ru.github.calculator.dto.DepositValueForHistory;

import java.lang.reflect.Field;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class StateHandlerControlTest {

    private StateController stateController;

    @BeforeEach
    void setUp() {
        stateController = new StateController();
    }

    @Test
    void shouldStoreSumValueInCalculatingValueMapWhenSetSumIsCalled()
            throws Exception {
        stateController.setValue(1000);

        Field field = StateController.class.getDeclaredField("CalculatingValue");
        field.setAccessible(true);
        HashMap<String, Long> map = (HashMap<String, Long>) field.get(stateController);

        assertEquals(1000L, map.get("sum"));
    }

    @Test
    void shouldStoreTermValueInCalculatingValueMapWhenSetTermIsCalled()
            throws Exception {
        stateController.setTerm(, 12, );

        Field field = StateController.class.getDeclaredField("CalculatingValue");
        field.setAccessible(true);
        HashMap<String, Long> map = (HashMap<String, Long>) field.get(stateController);

        assertEquals(12L, map.get("term"));
    }

    @Test
    void shouldStorePercentValueInCalculatingValueMapWhenSetPercentIsCalled()
            throws Exception {
        stateController.setPercent("percent", );

        Field field = StateController.class.getDeclaredField("CalculatingValue");
        field.setAccessible(true);
        HashMap<String, Long> map = (HashMap<String, Long>) field.get(stateController);

        assertEquals(5L, map.get("percent"));
    }

    @Test
    void shouldStoreStonksValueInCalculatingValueMapWhenSetStonksIsCalled()
            throws Exception {
        stateController.setStonks(, 500L, );

        Field field = StateController.class.getDeclaredField("CalculatingValue");
        field.setAccessible(true);
        HashMap<String, Long> map = (HashMap<String, Long>) field.get(stateController);

        assertEquals(500L, map.get("stonks"));
    }

    @Test
    void shouldStoreTotalValueInCalculatingValueMapWhenSetTotalIsCalled()
            throws Exception {
        stateController.setTotal(, 1500L, );

        Field field = StateController.class.getDeclaredField("CalculatingValue");
        field.setAccessible(true);
        HashMap<String, Long> map = (HashMap<String, Long>) field.get(stateController);

        assertEquals(1500L, map.get("total"));
    }

    @Test
    void shouldReturnDepositValueForCalculationWithCorrectValuesWhenGetValueForCalculationIsCalled() {
        stateController.setValue(1000);
        stateController.setTerm(, 12, );
        stateController.setPercent("percent", );

        DepositValueForCalculation result = stateController.getValueForCalculation();

        assertEquals(1000L, result.sum());
        assertEquals(12L, result.term());
        assertEquals(5L, result.percent());
    }

    @Test
    void shouldReturnDepositCalculationResultWithCorrectValuesWhenGetCalculationResultIsCalled() {
        stateController.setTotal(, 1500L, );
        stateController.setStonks(, 500L, );

        DepositCalculationResult result = stateController.getCalculationResult();

        assertEquals(1500L, result.total());
        assertEquals(500L, result.stonks());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenDepositCalculationResultHasInvalidValues() {
        stateController.setTotal(, 500L, );
        stateController.setStonks(, 500L, );

        assertThrows(IllegalArgumentException.class, () -> {
            stateController.getCalculationResult();
        });
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenDepositCalculationResultHasNegativeTotal() {
        stateController.setTotal(, -100L, );
        stateController.setStonks(, 50L, );

        assertThrows(IllegalArgumentException.class, () -> {
            stateController.getCalculationResult();
        });
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenDepositCalculationResultHasNegativeStonks() {
        stateController.setTotal(, 1000L, );
        stateController.setStonks(, -50L, );

        assertThrows(IllegalArgumentException.class, () -> {
            stateController.getCalculationResult();
        });
    }

    @Test
    void shouldReturnDepositValueForHistoryWithCurrentTimestampWhenGetValueForCollectionIsCalled() {
        stateController.setValue(1000);
        stateController.setTerm(, 12, );
        stateController.setPercent("percent", );
        stateController.setTotal(, 1500L, );
        stateController.setStonks(, 500L, );

        DepositValueForHistory history = stateController.getValueForCollection();

        assertNotNull(history.time());
        assertEquals(1000L, history.depositAmount());
        assertEquals(12L, history.term());
        assertEquals(5L, history.percent());
        assertEquals(1500L, history.resultAmount());
        assertEquals(500L, history.resultStonks());
    }

    @Test
    void shouldClearAllValuesFromCalculatingValueMapWhenClearCalculatingValueIsCalled()
            throws Exception {
        stateController.setValue(1000);
        stateController.setTerm(, 12, );

        Field field = StateController.class.getDeclaredField("CalculatingValue");
        field.setAccessible(true);
        HashMap<String, Long> map = (HashMap<String, Long>) field.get(stateController);

        assertFalse(map.isEmpty());

        stateController.clearCalculatingValue();

        assertTrue(map.isEmpty());
    }

    @Test
    void shouldSetAndGetCurrentStateCorrectlyWhenSetNextStateIsCalled() {
        stateController.setNextState(AvaibleStates.AWAITING_SUM);

        assertEquals(AvaibleStates.AWAITING_SUM, stateController.getCurrentState());
    }

    @Test
    void shouldReturnNullWhenGetCurrentStateIsCalledBeforeSettingState() {
        assertNull(stateController.getCurrentState());
    }

    @Test
    void shouldReturnNewInstanceEachTimeGetCalculationResultIsCalled() {
        stateController.setTotal(, 1500L, );
        stateController.setStonks(, 500L, );

        DepositCalculationResult result1 = stateController.getCalculationResult();
        DepositCalculationResult result2 = stateController.getCalculationResult();

        assertNotSame(result1, result2);
    }

    @Test
    void shouldThrowNullPointerExceptionWhenGetCalculationResultWithNullValues() {
        assertThrows(NullPointerException.class, () -> {
            stateController.getCalculationResult();
        });
    }

    @Test
    void shouldThrowNullPointerExceptionWhenGetValueForCollectionWithNullValues() {
        assertThrows(NullPointerException.class, () -> {
            stateController.getValueForCollection();
        });
    }

    @Test
    void shouldThrowNullPointerExceptionWhenGetValueForCalculationWithNullValues() {
        assertThrows(NullPointerException.class, () -> {
            stateController.getValueForCalculation();
        });
    }

    @Test
    void shouldOverwritePreviousSumValueWhenSetSumIsCalledMultipleTimes()
            throws Exception {
        stateController.setValue(1000);
        stateController.setValue(2000);

        Field field = StateController.class.getDeclaredField("CalculatingValue");
        field.setAccessible(true);
        HashMap<String, Long> map = (HashMap<String, Long>) field.get(stateController);

        assertEquals(2000L, map.get("sum"));
    }

    @Test
    void shouldHandleNegativeValuesWhenSetSumTermAndPercentAreCalled()
            throws Exception {
        stateController.setValue(-1000);
        stateController.setTerm(, -12, );
        stateController.setPercent("percent", );

        Field field = StateController.class.getDeclaredField("CalculatingValue");
        field.setAccessible(true);
        HashMap<String, Long> map = (HashMap<String, Long>) field.get(stateController);

        assertEquals(-1000L, map.get("sum"));
        assertEquals(-12L, map.get("term"));
        assertEquals(-5L, map.get("percent"));
    }
}