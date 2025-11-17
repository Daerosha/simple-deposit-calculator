package ru.github.calculator.history;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.github.calculator.dto.DepositValueForHistory;
import ru.github.calculator.inputoutput.UserInputAndOutput;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static ru.github.calculator.history.CalculatingHistory.CALCULATING_HISTORY;


@ExtendWith(MockitoExtension.class)
class CalculatingHistoryTest {
    @Mock
    UserInputAndOutput UserIO = new UserInputAndOutput();
    @InjectMocks
    private CalculatingHistory history;

    @AfterEach
    void clearCollection() {
        CALCULATING_HISTORY.removeAll(CALCULATING_HISTORY);
    }


    @Test
    void shouldReturnTrueWhenUserWantsToSeeHistoryAndContinue() throws IOException {
        Mockito.when(UserIO.getDataForCalculation()).thenReturn("yes").thenReturn("yes");
        LocalDateTime time = LocalDateTime.parse("2025-11-21T23:47:08.033865");
        DepositValueForHistory firstCalculation = new DepositValueForHistory(time, 123, 123, 123, 123, 123);

        Boolean needContinue = history.saveDataForCollectionHistory(firstCalculation);

        assertEquals(needContinue, true);
        verify(UserIO, times(1)).post("%nDo you want to see the calculation depositCalculationHistory? Type \"yes\" or \"no\" %n");
        verify(UserIO, times(1)).post("Your Calculations: %n");
        verify(UserIO, times(1)).post("_____________________________________________" +
                "%nNumber of Calculation: " + 1 +
                "%n  Time: " + "2025-11-21T23:47:08.033865" +
                "%n  Deposit Amount: " + "123.00" +
                "%n  Term: " + 123 + " months" +
                "%n  Percent: " + 123 + " %% " +
                "%n  Total Amount: " + 1.23 +
                "%n  Profit: " + 1.23 + "%n");
        verify(UserIO, times(1)).post("%nDo you want to calculate again? Type \"yes\" or \"no\" %n");
        verify(UserIO, Mockito.times(2)).getDataForCalculation();
        verifyNoMoreInteractions(UserIO);
    }

    @Test
    void shouldReturnTrueWhenUserSkipsHistoryButContinuesCalculation() throws IOException {
        Mockito.when(UserIO.getDataForCalculation()).thenReturn("no").thenReturn("yes");
        LocalDateTime time = LocalDateTime.parse("2025-11-21T23:47:08.033865");
        DepositValueForHistory firstCalculation = new DepositValueForHistory(time, 123, 123, 123, 123, 123);

        Boolean needContinue = history.saveDataForCollectionHistory(firstCalculation);

        assertEquals(needContinue, true);
        verify(UserIO, times(1)).post("%nDo you want to see the calculation depositCalculationHistory? Type \"yes\" or \"no\" %n");
        verify(UserIO, times(1)).post("%nDo you want to calculate again? Type \"yes\" or \"no\" %n");
        verify(UserIO, Mockito.times(2)).getDataForCalculation();
        verifyNoMoreInteractions(UserIO);
    }


    @ParameterizedTest
    @CsvSource({"null,2,no,null,5,no",
            "?!2,qwe,no,?!2,qwe,no"})
    void shouldReturnFalseAfterMultipleInvalidInputsButCorrectNoAtEnd(String otherFormatValueHistory, String incorrectValueHistory, String rejectViewingHistory, String otherFormatValueCalculating, String incorrectValueCalculating, String rejectContinueingCalculating) throws IOException {
        Mockito.when(UserIO.getDataForCalculation()).thenReturn(otherFormatValueHistory).thenReturn(incorrectValueHistory).thenReturn(rejectViewingHistory).thenReturn(otherFormatValueCalculating).thenReturn(incorrectValueCalculating).thenReturn(rejectContinueingCalculating);
        LocalDateTime time = LocalDateTime.parse("2025-11-21T23:47:08.033865");

        DepositValueForHistory firstCalculation = new DepositValueForHistory(time, 333, 222, 111, 0, -1000);

        Boolean needContinue = history.saveDataForCollectionHistory(firstCalculation);

        assertEquals(needContinue, false);
        verify(UserIO, times(1)).post("%nDo you want to see the calculation depositCalculationHistory? Type \"yes\" or \"no\" %n");
        verify(UserIO, times(1)).post("%nDo you want to calculate again? Type \"yes\" or \"no\" %n");
        verify(UserIO, times(2)).post("Type only \"yes\" or \"no\" %n");
        verify(UserIO, times(2)).post("%n Type only \"yes\" or \"no\" %n");
        verify(UserIO, Mockito.times(6)).getDataForCalculation();
        verifyNoMoreInteractions(UserIO);
    }


    @ParameterizedTest
    @CsvSource({"yea,2,yes,nope,5,yes",
            "false,YES,yes,YES,true,yes"})
    void shouldReturnTrueAfterMultipleInvalidInputsButCorrectYesAtEnd(String otherFormatValueHistory, String incorrectValueHistory, String acceptViewingHistory, String otherFormatValueCalculating, String incorrectValueCalculating, String acceptContinueingCalculating) throws IOException {
        Mockito.when(UserIO.getDataForCalculation()).thenReturn(otherFormatValueHistory).thenReturn(incorrectValueHistory).thenReturn(acceptViewingHistory).thenReturn(otherFormatValueCalculating).thenReturn(incorrectValueCalculating).thenReturn(acceptContinueingCalculating);
        LocalDateTime time = LocalDateTime.parse("2025-11-21T23:47:08.033865");

        DepositValueForHistory firstCalculation = new DepositValueForHistory(time, 0, 0, 0, 0, 0);

        Boolean needContinue = history.saveDataForCollectionHistory(firstCalculation);

        assertEquals(needContinue, true);
        verify(UserIO, times(1)).post("%nDo you want to see the calculation depositCalculationHistory? Type \"yes\" or \"no\" %n");
        verify(UserIO, times(1)).post("%nDo you want to calculate again? Type \"yes\" or \"no\" %n");
        verify(UserIO, times(2)).post("Type only \"yes\" or \"no\" %n");
        verify(UserIO, times(2)).post("%n Type only \"yes\" or \"no\" %n");
        verify(UserIO, Mockito.times(6)).getDataForCalculation();
        verify(UserIO).post(contains("Deposit Amount: 0.00"));
        verify(UserIO).post(contains("Term: 0 months"));
        verify(UserIO).post(contains("Percent: 0 %%"));
        verify(UserIO).post(contains("Total Amount: 0.00"));
        verify(UserIO).post(contains("Profit: 0.00"));

    }

    @Test
    void shouldHandleMultipleCalculationsWithCorrectHistoryDisplay() throws IOException {
        Mockito.when(UserIO.getDataForCalculation()).thenReturn("no").thenReturn("yes").thenReturn("yes").thenReturn("no");
        LocalDateTime time = LocalDateTime.parse("2025-11-21T23:47:08.033865");
        DepositValueForHistory firstCalculation = new DepositValueForHistory(time, 10000, 12, 50, 1500000, 500000);
        DepositValueForHistory secondCalculation = new DepositValueForHistory(time, 12345, 1000, 0, 0, 0);

        Boolean needContinueFirstItaration = history.saveDataForCollectionHistory(firstCalculation);
        Boolean needContinueSecondItaration = history.saveDataForCollectionHistory(secondCalculation);

        assertEquals(needContinueFirstItaration, true);
        assertEquals(needContinueSecondItaration, false);
        verify(UserIO, times(2)).post("%nDo you want to see the calculation depositCalculationHistory? Type \"yes\" or \"no\" %n");
        verify(UserIO, times(1)).post("Your Calculations: %n");
        verify(UserIO).post(contains("Number of Calculation: 1")); //Проверка первого элемента коллекции
        verify(UserIO).post(contains("Deposit Amount: 10 000.00"));
        verify(UserIO).post(contains("Term: 12 months"));
        verify(UserIO).post(contains("Percent: 50 %%"));
        verify(UserIO).post(contains("Total Amount: 15 000.00"));
        verify(UserIO).post(contains("Profit: 5 000.00"));
        verify(UserIO).post(contains("Number of Calculation: 2")); //Проверка второго элемента коллекции
        verify(UserIO).post(contains("Deposit Amount: 12 345.00"));
        verify(UserIO).post(contains("Term: 1000 months"));
        verify(UserIO).post(contains("Percent: 0 %%"));
        verify(UserIO).post(contains("Total Amount: 0.00"));
        verify(UserIO).post(contains("Profit: 0.00"));
        verify(UserIO, times(2)).post("%nDo you want to calculate again? Type \"yes\" or \"no\" %n");
        verify(UserIO, Mockito.times(4)).getDataForCalculation();
    }
}
