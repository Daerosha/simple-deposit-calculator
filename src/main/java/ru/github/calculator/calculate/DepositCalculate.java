package ru.github.calculator.calculate;

public class DepositCalculate {
    private static final double MONTH_IN_YEAR = 12.0;
    private static final long KOPEYKI = 100L;

    public long startCalculateStonks(long depositSumm, long depositYears, long depositMonth, long depositPercent) {
        return ((long) (depositSumm * depositPercent * (depositYears + depositMonth / MONTH_IN_YEAR)));
    }

    public long startCalculateSumm(long summStonks, long depositSumm) {
        return summStonks + depositSumm * KOPEYKI;
    }
}


