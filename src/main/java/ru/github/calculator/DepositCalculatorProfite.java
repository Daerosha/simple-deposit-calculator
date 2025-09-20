package ru.github.calculator;

import ru.github.calculator.calculate.DepositCalculator;
import ru.github.calculator.dto.DepositCalculationResult;
import ru.github.calculator.dto.DepositValueForCalculation;
import ru.github.calculator.inputoutput.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

class DepositCalculatorProfite {
    private static final double KOPEYKA_TO_RUBLES = 100.0;

    public void main(String[] args) throws IOException {
        UserInputAndOutput userIo = new UserInputAndOutput();
        userIo.post("Добро пожаловать в простой депозитный калькулятор!%n");
        UserDepositValue depositSum = new UserDepositSum(userIo);
        UserDepositValue depositTerm = new UserDepositTerm(userIo);
        UserDepositValue depositPercent = new UserDepositPercent(userIo);
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
                userIo.post("Сумма на счете в конце депозита составляет: " + df.format(calculationsResult.total() / KOPEYKA_TO_RUBLES) + "%nИз них прибыль по депозиту составляет: " + df.format(calculationsResult.stonks() / KOPEYKA_TO_RUBLES));
                break;
            } catch (IllegalArgumentException e) {
                userIo.post("Ошибка при подсчете, попробуйте ввести данные еще раз%n" + e.getMessage() + "%n");
            }
        }
    }
}
