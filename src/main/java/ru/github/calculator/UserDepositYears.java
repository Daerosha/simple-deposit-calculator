package ru.github.calculator;

import java.io.IOException;

class UserDepositYears {
    public int GetDepositYears() throws IOException {
        System.out.print("Введите срок депозита в годах (от 0 до 15): ");
        return GetUserInput.UserInput(0, 15);
    }
}
