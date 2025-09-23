package ru.github.calculator.inputoutput;

import java.io.IOException;

public class UserDepositYears {
    private static final int MAX_YEARS = 15;
    private static final int MIN_YEARS = 0;

    public int getDepositYears() throws IOException {
        System.out.print("Введите срок депозита в годах (от " + MIN_YEARS + " до " + MAX_YEARS + "): ");
        UserInputAndOutput depositYears = new UserInputAndOutput();
        return depositYears.userInput(MIN_YEARS, MAX_YEARS);
    }
}
