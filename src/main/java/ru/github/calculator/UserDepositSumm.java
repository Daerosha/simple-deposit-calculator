package ru.github.calculator;


import java.io.IOException;

class UserDepositSumm {
    public int GetDepositSumm() throws IOException {
        System.out.print("Введите сумму депозита (от 1 до 100 000 000): ");
//        GetUserInput s = new GetUserInput();
//        int summa = s.UserInput(1, 100000000);
//       return summa;
        return GetUserInput.UserInput(1, 100000000);

    }
}
