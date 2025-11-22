package ru.github.calculator.history;

import ru.github.calculator.dto.DepositValueForHistory;
import ru.github.calculator.view.View;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CalculatingHistory {
    private static final double KOPEYKA_TO_RUBLES = 100.0;
    static final List<DepositValueForHistory> CALCULATING_HISTORY = new ArrayList<>();
    private final View view;

    public CalculatingHistory(View view) {
        this.view = view;
    }

    public void save(DepositValueForHistory depositCalculationHistory) {
        CALCULATING_HISTORY.add(depositCalculationHistory);
    }

    public String getHistoryAsString() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        symbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        StringBuilder historyString = new StringBuilder("Your Calculations: %n");
        for (DepositValueForHistory history : CALCULATING_HISTORY) {
            historyString.append("_____________________________________________%n")
                    .append("Number of Calculation: ").append(CALCULATING_HISTORY.indexOf(history) + 1).append("%n")
                    .append("  Time: ").append(history.time()).append("%n")
                    .append("  Deposit Amount: ").append(df.format(history.depositAmount())).append("%n")
                    .append("  Term: ").append(history.term()).append(" months").append("%n")
                    .append("  Percent: ").append(history.percent()).append(" %% ").append("%n")
                    .append("  Total Amount: ").append(df.format(history.resultAmount() / KOPEYKA_TO_RUBLES)).append("%n")
                    .append("  Profit: ").append(df.format(history.resultStonks() / KOPEYKA_TO_RUBLES)).append("%n");
        }
        return historyString.toString();
    }

    public boolean handleHistory() throws IOException {
        view.write("%nDo you want to see the calculation depositCalculationHistory? Type \"yes\" or \"no\" %n");
        while (true) {
            String choice = view.read();
            if (Objects.equals(choice, "yes")) {
                view.write(getHistoryAsString());
                break;
            }
            if (Objects.equals(choice, "no")) {
                break;
            }
            view.write("Type only \"yes\" or \"no\" %n");
        }
        view.write("%nDo you want to calculate again? Type \"yes\" or \"no\" %n");
        while (true) {
            String continueCalculating = view.read();
            if (Objects.equals(continueCalculating, "yes")) {
                return true;
            }
            if (Objects.equals(continueCalculating, "no")) {
                return false;
            }
            view.write("%n Type only \"yes\" or \"no\" %n");
        }
    }
}