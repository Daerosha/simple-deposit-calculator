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

    private final UserInputAndOutput userDepositTerm;

    public UserDepositTerm(UserInputAndOutput userDepositTerm) {
        this.userDepositTerm = userDepositTerm;
    }

    @Override
    public int getDepositValue() throws IOException {
        userDepositTerm.post("Введите срок депозита в месяцах или годах (не менее " + MIN_TERM + " месяца и не более " + MAX_YEARS + " лет)%nГде 1m - будет означать один месяц, а 10y - десять лет, можно использовать только 1 значение):");
        while (true) {
            String numberAndSuffixOfTime = userDepositTerm.getDataForCalculation().trim().toLowerCase();
            Pattern pattern = Pattern.compile("^(\\d+)([my])$");
            Matcher matcher = pattern.matcher(numberAndSuffixOfTime);
            if (!matcher.matches()) {
                userDepositTerm.post("%nВы ввели некорректный срок депозита!%nВведите в месяцах или годах (не менее " + MIN_TERM + " месяца и не более " + MAX_YEARS + " лет)%nГде 1m - будет означать 1месяц, а 10y - десять лет, можно использовать только 1 значение):");
                continue;
            }
            int numberOfTime = Integer.parseInt(matcher.group(1));
            String suffixOfTime = matcher.group(2);
            if (Objects.equals(suffixOfTime, "y") && numberOfTime >= MIN_TERM && numberOfTime <= MAX_YEARS) {
                return numberOfTime * MONTH_IN_YEAR;
            }
            if (Objects.equals(suffixOfTime, "m") && numberOfTime >= MIN_TERM && numberOfTime <= MAX_MONTH) {
                return numberOfTime;
            }
            userDepositTerm.post("%nВы ввели некорректный срок депозита!%nВведите в месяцах или годах (не менее " + MIN_TERM + " месяца и не более " + MAX_YEARS + " лет)%nГде 1m - будет означать 1месяц, а 10y - десять лет, можно использовать только 1 значение):");
        }
    }
}
