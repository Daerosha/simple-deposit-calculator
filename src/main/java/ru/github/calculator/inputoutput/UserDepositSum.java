package ru.github.calculator.inputoutput;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class UserDepositSum implements UserDepositValue {
    private static final int MAX_SUMM = 30000000;
    private static final int MIN_SUMM = 1;
    private final UserInputAndOutput userDepositSumm;

    public UserDepositSum(UserInputAndOutput userDepositSumm) {
        this.userDepositSumm = userDepositSumm;
    }

    @Override
    public int getDepositValue() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        DecimalFormat df = new DecimalFormat("#,##0", symbols);
        userDepositSumm.post("Введите сумму депозита (от " + df.format(MIN_SUMM) + " до " + df.format(MAX_SUMM) + "): ");
        while (true) {
            try {
                String input = userDepositSumm.getDataForCalculation();
                int sum = Integer.parseInt(input);
                if (sum < MIN_SUMM || sum > MAX_SUMM) {
                    userDepositSumm.post("Вы ввели неверное число, оно должно быть от " + df.format(MIN_SUMM) + " до " + df.format(MAX_SUMM) + ", повторите попытку:");
                } else {
                    return sum;
                }
            } catch (NumberFormatException | IOException e) {
                userDepositSumm.post("Ошибка: вы ввели слишком большое, либо нецелое число при вводе процента! Повторный ввод:");

            }
        }
    }
}