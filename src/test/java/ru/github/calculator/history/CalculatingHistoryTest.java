package ru.github.calculator.history;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.github.calculator.dto.DepositValueForHistory;
import ru.github.calculator.view.View;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static ru.github.calculator.history.CalculatingHistory.CALCULATING_HISTORY;


@ExtendWith(MockitoExtension.class)
class CalculatingHistoryTest {
    @Mock
    View view;
    @InjectMocks
    private CalculatingHistory history;

    @AfterEach
    void clearCollection() {
        CALCULATING_HISTORY.clear();
    }

    @Test
    void shouldSaveHistory() {
        LocalDateTime time = LocalDateTime.now();
        DepositValueForHistory value = new DepositValueForHistory(time, 100, 12, 10, 110, 10);
        history.save(value);
        assertEquals(1, CALCULATING_HISTORY.size());
        assertEquals(value, CALCULATING_HISTORY.get(0));
    }

    @Test
    void shouldReturnCorrectHistoryString() {
        LocalDateTime time = LocalDateTime.parse("2025-11-21T23:47:08.033865");
        DepositValueForHistory firstCalculation = new DepositValueForHistory(time, 10000, 12, 50, 1500000, 500000);
        history.save(firstCalculation);

        String historyString = history.getHistoryAsString();

        assertTrue(historyString.contains("Number of Calculation: 1"));
        assertTrue(historyString.contains("Deposit Amount: 10 000.00"));
        assertTrue(historyString.contains("Term: 12 months"));
        assertTrue(historyString.contains("Percent: 50 %%"));
        assertTrue(historyString.contains("Total Amount: 15 000.00"));
        assertTrue(historyString.contains("Profit: 5 000.00"));
    }

    @Test
    void shouldReturnTrueWhenUserWantsToContinue() throws IOException {
        when(view.read()).thenReturn("yes").thenReturn("yes");

        assertTrue(history.handleHistory());

        verify(view, times(1)).write("%nDo you want to see the calculation depositCalculationHistory? Type \"yes\" or \"no\" %n");
        verify(view, times(1)).write(history.getHistoryAsString());
        verify(view, times(1)).write("%nDo you want to calculate again? Type \"yes\" or \"no\" %n");
    }

    @Test
    void shouldReturnFalseWhenUserDoesNotWantToContinue() throws IOException {
        when(view.read()).thenReturn("no").thenReturn("no");

        assertFalse(history.handleHistory());

        verify(view, times(1)).write("%nDo you want to see the calculation depositCalculationHistory? Type \"yes\" or \"no\" %n");
        verify(view, never()).write(history.getHistoryAsString());
        verify(view, times(1)).write("%nDo you want to calculate again? Type \"yes\" or \"no\" %n");
    }
}
