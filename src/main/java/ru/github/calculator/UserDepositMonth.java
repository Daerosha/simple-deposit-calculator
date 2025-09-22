package ru.github.calculator;

import java.io.IOException;

class UserDepositMonth {
    public int GetDepositMonth(int y) throws IOException {
        int month;
        if (y == 0) {
            System.out.println("Вы планируете открыть депозит на срок менее 1 года, количество месяцев должно быть 1 или больше");
            System.out.print("Введите срок депозита в месяцах (от 1 до 11): ");
            month = GetUserInput.UserInput(1, 11);
        } else {
            System.out.print("Введите срок депозита в месяцах (от 0 до 11): ");
            month = GetUserInput.UserInput(0, 11);
        }
        return month;
    }
}
