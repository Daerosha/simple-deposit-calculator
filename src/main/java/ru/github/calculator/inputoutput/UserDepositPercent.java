package ru.github.calculator.inputoutput;

import java.io.IOException;

public class UserDepositPercent implements UserDepositValue {
    private static final int MAX_PERCENT = 50;
    private static final int MIN_PERCENT = 1;
    private final UserInputAndOutput userDepositPercent;

    public UserDepositPercent(UserInputAndOutput userDepositPercent) {
        this.userDepositPercent = userDepositPercent;
    }

    @Override
    public int getDepositValue()  {
        userDepositPercent.post("%nEnter the deposit percentage (between " + MIN_PERCENT + " and " + MAX_PERCENT + "):");
        while (true) {
            try {
                int percent = Integer.parseInt(userDepositPercent.getDataForCalculation());
                if (percent < MIN_PERCENT || percent > MAX_PERCENT) {
                    userDepositPercent.post("%nYou entered an incorrect number, it must be between " + MIN_PERCENT + " and " + MAX_PERCENT + ", please try again:");
                } else {
                    return percent;
                }
            } catch (NumberFormatException | IOException e) {
                userDepositPercent.post("%nError: You entered a number that is too large or not an integer when entering the percentage! Re-enter:");
            }

        }
    }
}