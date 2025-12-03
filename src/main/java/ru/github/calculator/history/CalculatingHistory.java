package ru.github.calculator.history;

import ru.github.calculator.dto.DepositValueForHistory;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class CalculatingHistory {
    static final List<DepositValueForHistory> CALCULATING_HISTORY = new ArrayList<>();
    private static final StringBuilder CALCULATING_HISTORY_FOR_DISPLAY = new StringBuilder();
    private static final double KOPEYKA_TO_RUBLES = 100.0;


    public void saveDataForCollectionHistory(DepositValueForHistory depositCalculationHistory) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        symbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        CALCULATING_HISTORY.add(depositCalculationHistory);
        CALCULATING_HISTORY_FOR_DISPLAY.append("_____________________________________________" + "%nNumber of Calculation: " + (CALCULATING_HISTORY.indexOf(depositCalculationHistory) + 1) + "%n  Time: " + depositCalculationHistory.time() + "%n  Deposit Amount: " + df.format(depositCalculationHistory.depositAmount()) + "%n  Term: " + depositCalculationHistory.term() + " months" + "%n  Percent: " + depositCalculationHistory.percent() + " %% " + "%n  Total Amount: " + df.format(depositCalculationHistory.resultAmount() / KOPEYKA_TO_RUBLES) + "%n  Profit: " + df.format(depositCalculationHistory.resultStonks() / KOPEYKA_TO_RUBLES) + "%n");
    }

    public String getCalculationHistory() {
        return CALCULATING_HISTORY_FOR_DISPLAY.toString();
    }
}