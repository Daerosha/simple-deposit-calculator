package ru.github.calculator;

import java.io.IOException;

class UserDepositPercent {
    public int GetDepositPercent() throws IOException {
        System.out.print("Введите процент депозита (от 1 до 50): ");
//        GetUserInput p = new GetUserInput();
//        int percent = p.UserInput(1,50);
//       return percent;
        return GetUserInput.UserInput(1, 50);

    }
}
