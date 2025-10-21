package ru.github.calculator.inputoutput;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class UserDepositSum implements UserDepositValue {
    private static final int MAX_SUMM = 30000000;
    private static final int MIN_SUMM = 1;
    private final UserInputAndOutput userDepositSumm;

    public UserDepositSum(UserInputAndOutput userDepositSumm) {
        this.userDepositSumm = userDepositSumm;
    }

    @Override
    public int getDepositValue() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        DecimalFormat df = new DecimalFormat("#,##0", symbols);
        userDepositSumm.post("%nEnter the deposit amount (from " + df.format(MIN_SUMM) + " to " + df.format(MAX_SUMM) + "):");
        while (true) {
            try {
                String input = userDepositSumm.getDataForCalculation();
                int sum = Integer.parseInt(input);
                if (sum < MIN_SUMM || sum > MAX_SUMM) {
                    userDepositSumm.post("%nYou entered an incorrect number it should be between " + df.format(MIN_SUMM) + " and " + df.format(MAX_SUMM) + ", please try again:");
                } else {
                    return sum;
                }
            } catch (NumberFormatException | IOException e) {
                userDepositSumm.post("%nError: You entered a number that is too large or not an integer when entering the percentage! Re-enter:");
            }
        }
    }
}