package ru.github.calculator.history;

import ru.github.calculator.dto.DepositValueForHistory;
import ru.github.calculator.repository.HistoryRepository;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class CalculatingHistory {
    private static final double KOPEYKA_TO_RUBLES = 100.0;
    private final HistoryRepository repository;


    public CalculatingHistory(HistoryRepository repository) {
        this.repository = repository;
    }

    public void saveDataForCollectionHistory(DepositValueForHistory depositCalculationHistory) {
        repository.save(depositCalculationHistory);
    }

    public String getCalculationHistory() {
        List<DepositValueForHistory> historyList = repository.getAll();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        symbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        StringBuilder CALCULATING_HISTORY_FOR_DISPLAY = new StringBuilder();

        for (DepositValueForHistory history : historyList) {
            CALCULATING_HISTORY_FOR_DISPLAY.append(
                    "_____________________________________________" +
                            "%nNumber of Calculation: " + (historyList.indexOf(history) + 1) +
                            "%n  Time: " + history.time() +
                            "%n  Deposit Amount: " + df.format(history.input().sum()) +
                            "%n  Term: " + history.input().term() + " months" +
                            "%n  Percent: " + history.input().percent() + "%%" +
                            "%n  Total Amount: " + df.format(history.result().total() / KOPEYKA_TO_RUBLES) +
                            "%n  Profit: " + df.format(history.result().stonks() / KOPEYKA_TO_RUBLES) +
                            "%n"
            );
        }
        return CALCULATING_HISTORY_FOR_DISPLAY.toString();
    }
}
