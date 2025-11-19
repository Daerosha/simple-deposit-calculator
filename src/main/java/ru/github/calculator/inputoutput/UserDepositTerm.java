package ru.github.calculator.inputoutput;

import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDepositTerm implements UserDepositValue {
    private static final int MAX_YEARS = 15;
    private static final int MAX_MONTH = MAX_YEARS * 12;
    private static final int MIN_TERM = 1;
    private static final int MONTH_IN_YEAR = 12;
    private static final Pattern PATTERN = Pattern.compile("^(\\d+)([my])$");
    private static final String USER_INPUT_YEAR = "y";
    private static final String USER_INPUT_MONTH = "m";

    private final UserInputAndOutput userDepositTerm;

    public UserDepositTerm(UserInputAndOutput userDepositTerm) {
        this.userDepositTerm = userDepositTerm;
    }

    @Override
    public int getDepositValue() {
        userDepositTerm.post("%nEnter integer terms of months or years (no less than " + MIN_TERM + " month and no more than " + MAX_YEARS + " years)%nWhere 1m means one month and 10y means ten years, only 1 value can be used):");
        while (true) {
            try {
                String numberAndSuffixOfTime = userDepositTerm.getDataForCalculation().trim().toLowerCase();
                Matcher matcher = PATTERN.matcher(numberAndSuffixOfTime);
                if (!matcher.matches()) {
                    userDepositTerm.post("%nYou have entered an incorrect deposit term!%nEnter in terms of months or years (no less than " + MIN_TERM + " month and no more than " + MAX_YEARS + " years)%nWhere 1m means one month and 10y means ten years, only 1 value can be used):");
                    continue;
                }
                int numberOfTime = Integer.parseInt(matcher.group(1));
                String suffixOfTime = matcher.group(2);
                if (Objects.equals(suffixOfTime, USER_INPUT_YEAR) && numberOfTime >= MIN_TERM && numberOfTime <= MAX_YEARS) {
                    return numberOfTime * MONTH_IN_YEAR;
                }
                if (Objects.equals(suffixOfTime, USER_INPUT_MONTH) && numberOfTime >= MIN_TERM && numberOfTime <= MAX_MONTH) {
                    return numberOfTime;
                }
                userDepositTerm.post("%nYou have entered an incorrect deposit term!%nEnter in terms of months or years (no less than " + MIN_TERM + " month and no more than " + MAX_YEARS + " years)%nWhere 1m means one month and 10y means ten years, only 1 value can be used):");
            } catch (NumberFormatException | IOException | NullPointerException e) {
                userDepositTerm.post("%nYou have entered an incorrect deposit term!%nEnter in terms of months or years (no less than " + MIN_TERM + " month and no more than " + MAX_YEARS + " years)%nWhere 1m means one month and 10y means ten years, only 1 value can be used):");
            }
        }
    }
}
