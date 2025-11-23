package ru.github.calculator;

import ru.github.calculator.calculate.DepositCalculator;
import ru.github.calculator.dto.DepositCalculationResult;
import ru.github.calculator.dto.DepositValueForCalculation;
import ru.github.calculator.dto.DepositValueForHistory;
import ru.github.calculator.history.CalculatingHistory;
import ru.github.calculator.inputoutput.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;

class DepositCalculatorProfite {
    private static final double KOPEYKA_TO_RUBLES = 100.0;

    public static void main(String[] args) throws IOException {
        UserInputAndOutput userIo = new UserInputAndOutput();
        userIo.post("%nWelcome to the simple deposit calculator!%n");
        UserDepositValue depositSum = new UserDepositSum(userIo);
        UserDepositValue depositTerm = new UserDepositTerm(userIo);
        UserDepositValue depositPercent = new UserDepositPercent(userIo);
        CalculatingHistory history = new CalculatingHistory(userIo);
        DepositCalculator calculator = new DepositCalculator();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        symbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        while (true) {
            int sum = depositSum.getDepositValue();
            int term = depositTerm.getDepositValue();
            int percent = depositPercent.getDepositValue();
            try {
                DepositCalculationResult calculationsResult = calculator.startCalculate(new DepositValueForCalculation(sum, term, percent));
                userIo.post("%nThe balance at the end of the deposit is: " + df.format(calculationsResult.total() / KOPEYKA_TO_RUBLES) + "%nOf this amount, the profit on the deposit is: " + df.format(calculationsResult.stonks() / KOPEYKA_TO_RUBLES));
                LocalDateTime time = LocalDateTime.now();
                boolean continueCalculating = history.saveDataForCollectionHistory(new DepositValueForHistory(time, sum, term, percent, calculationsResult.total() , calculationsResult.stonks() ));
                if (!continueCalculating) {
                    break;
                }
            } catch (IllegalArgumentException e) {
                userIo.post("%nError during calculation, please try entering the data again%n" + e.getMessage() + "%n");
            }
        }
    }
}
