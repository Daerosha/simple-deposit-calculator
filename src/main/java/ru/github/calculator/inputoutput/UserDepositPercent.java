package ru.github.calculator.inputoutput;

import java.io.IOException;

public class UserDepositPercent implements UserDepositValue {
    private static final int MAX_PERCENT = 50;
    private static final int MIN_PERCENT = 1;
    private final UserInputAndOutput userDepositPercent;

    public UserDepositPercent(UserInputAndOutput userDepositPercent) {
        this.userDepositPercent = userDepositPercent;
    }

    @Override
    public int getDepositValue()  {
        userDepositPercent.post("Введите процент депозита (от " + MIN_PERCENT + " до " + MAX_PERCENT + "): ");
        while (true) {
            try {
                int percent = Integer.parseInt(userDepositPercent.getDataForCalculation());
                if (percent < MIN_PERCENT || percent > MAX_PERCENT) {
                    userDepositPercent.post("Вы ввели неверное число, оно должно быть от " + MIN_PERCENT + " до " + MAX_PERCENT + ", повторите попытку:");
                } else {
                    return percent;
                }
            } catch (NumberFormatException | IOException e) {
                userDepositPercent.post("Ошибка: вы ввели слишком большое, либо нецелое число при вводе процента! Повторный ввод:");
            }

        }
    }
}