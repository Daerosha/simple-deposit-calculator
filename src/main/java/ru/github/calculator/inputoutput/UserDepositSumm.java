package ru.github.calculator.inputoutput;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class UserDepositSumm {
    private static final int MAX_SUMM = 30000000;
    private static final int MIN_SUMM = 1;

    public int getDepositSumm() throws IOException {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        DecimalFormat df = new DecimalFormat("#,###,###", symbols);
        System.out.print("Введите сумму депозита (от " + df.format(MIN_SUMM) + " до " + df.format(MAX_SUMM) + "): ");
        UserInputAndOutput depositSumm = new UserInputAndOutput();
        return depositSumm.userInput(MIN_SUMM, MAX_SUMM);
    }
}
