package ru.github.calculator.inputoutput;

import java.io.IOException;

public class UserDepositPercent {
    private static final int MAX_PERCENT = 50;
    private static final int MIN_PERCENT = 1;

    public int getDepositPercent() throws IOException {
        System.out.print("Введите процент депозита (от " + MIN_PERCENT + " до " + MAX_PERCENT + "): ");
        UserInputAndOutput depositPercent = new UserInputAndOutput();
        return depositPercent.userInput(MIN_PERCENT, MAX_PERCENT);
    }
}
