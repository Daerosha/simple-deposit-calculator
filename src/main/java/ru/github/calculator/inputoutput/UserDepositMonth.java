package ru.github.calculator.inputoutput;

import java.io.IOException;

public class UserDepositMonth {
    private static final int MAX_MONTH = 11;
    private static final int MIN_MONTH = 0;// убрал final, тк если до этого введено 0 лет, то минимально может быть 1 месяц

    public int getDepositMonth(int years) throws IOException {
        int month;
        if (years == 0) {
            System.out.println("Вы планируете открыть депозит на срок менее 1 года, количество месяцев должно быть 1 или больше");
            System.out.print("Введите срок депозита в месяцах (от 1 до " + MAX_MONTH + "): ");
            UserInputAndOutput depositMonth = new UserInputAndOutput();
            month = depositMonth.userInput(MIN_MONTH + 1, MAX_MONTH);
        } else {
            System.out.print("Введите срок депозита в месяцах (от 0 до " + MAX_MONTH + "): ");
            UserInputAndOutput depositMonth = new UserInputAndOutput();
            month = depositMonth.userInput(MIN_MONTH, MAX_MONTH);
        }
        return month;
    }
}
