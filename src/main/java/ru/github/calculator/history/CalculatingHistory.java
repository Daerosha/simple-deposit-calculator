package ru.github.calculator.history;

import ru.github.calculator.dto.DepositValueForHistory;
import ru.github.calculator.inputoutput.UserInputAndOutput;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CalculatingHistory {
    private static final double KOPEYKA_TO_RUBLES = 100.0;
    static final List<DepositValueForHistory> CALCULATING_HISTORY = new ArrayList<>();
    private final UserInputAndOutput userIO;

    public CalculatingHistory(UserInputAndOutput userIO) {
        this.userIO = userIO;
    }

    public boolean saveDataForCollectionHistory(DepositValueForHistory depositCalculationHistory) throws IOException {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        symbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        CALCULATING_HISTORY.add(new DepositValueForHistory(depositCalculationHistory.time(), depositCalculationHistory.depositAmount(), depositCalculationHistory.term(), depositCalculationHistory.percent(), depositCalculationHistory.resultAmount(), depositCalculationHistory.resultStonks()));
        userIO.post("%nDo you want to see the calculation depositCalculationHistory? Type \"yes\" or \"no\" %n");
        while (true) {
            String continueCalculating = userIO.getDataForCalculation();
            if (Objects.equals(continueCalculating, "yes")) {
                userIO.post("Your Calculations: %n");
                for (DepositValueForHistory history : CALCULATING_HISTORY) {
                    userIO.post("_____________________________________________" +
                            "%nNumber of Calculation: " + (CALCULATING_HISTORY.indexOf(history) + 1) +

                            "%n  Time: " + history.time() +
                            "%n  Deposit Amount: " + df.format(history.depositAmount()) +
                            "%n  Term: " + history.term() + " months" +
                            "%n  Percent: " + history.percent() + " %% " +
                            "%n  Total Amount: " + df.format(history.resultAmount() / KOPEYKA_TO_RUBLES) +
                            "%n  Profit: " + df.format(history.resultStonks() / KOPEYKA_TO_RUBLES) + "%n");
                }
                break;
            }
            if (Objects.equals(continueCalculating, "no")) {
                break;
            }
            userIO.post("Type only \"yes\" or \"no\" %n");
        }
        userIO.post("%nDo you want to calculate again? Type \"yes\" or \"no\" %n");
        while (true) {
            String continueCalculating = userIO.getDataForCalculation();
            if (Objects.equals(continueCalculating, "yes")) {
                return true;
            }
            if (Objects.equals(continueCalculating, "no")) {
                return false;
            }
            userIO.post("%n Type only \"yes\" or \"no\" %n");
        }
    }
}