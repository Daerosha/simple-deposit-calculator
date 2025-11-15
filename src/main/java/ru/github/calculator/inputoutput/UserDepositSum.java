package ru.github.calculator.inputoutput;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class UserDepositSum implements UserDepositValue {
    private static final int MAX_SUM = 30000000;
    private static final int MIN_SUM = 1;
    private final UserInputAndOutput userDepositSumm;

    public UserDepositSum(UserInputAndOutput userDepositSumm) {
        this.userDepositSumm = userDepositSumm;
    }

    @Override
    public int getDepositValue() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        DecimalFormat df = new DecimalFormat("#,##0", symbols);
        userDepositSumm.post("%nEnter the integer deposit amount (from " + df.format(MIN_SUM) + " to " + df.format(MAX_SUM) + "):");
        while (true) {
            try {
                String input = userDepositSumm.getDataForCalculation();
                int sum = Integer.parseInt(input);
                if (sum < MIN_SUM || sum > MAX_SUM) {
                    userDepositSumm.post("%nYou entered an incorrect number it should be between " + df.format(MIN_SUM) + " and " + df.format(MAX_SUM) + ", please try again:");
                } else {
                    return sum;
                }
            } catch (NumberFormatException | IOException e) {
                userDepositSumm.post("%nError: You entered a number that is too large or not an integer when entering the sum! Re-enter:");
            }
        }
    }
}