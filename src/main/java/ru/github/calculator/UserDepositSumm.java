package ru.github.calculator;

import java.io.IOException;

class UserDepositSumm {
    public int GetDepositSumm() throws IOException {
        System.out.print("Введите сумму депозита (от 1 до 30 000 000): ");
        return GetUserInput.UserInput(1, 30000000);

    }
}
