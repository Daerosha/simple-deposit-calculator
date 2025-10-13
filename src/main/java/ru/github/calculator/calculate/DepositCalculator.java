package ru.github.calculator.calculate;

import ru.github.calculator.dto.DepositCalculationResult;
import ru.github.calculator.dto.DepositValueForCalculation;

public class DepositCalculator {
    private static final double MONTH_IN_YEAR = 12.0;
    private static final long KOPEYKI = 100L;

    public DepositCalculationResult startCalculate(DepositValueForCalculation valueForCalculating) {
        long summStonks =  (long) ((long) valueForCalculating.sum() * valueForCalculating.term() * valueForCalculating.percent() / MONTH_IN_YEAR);
        long summTotal = summStonks + valueForCalculating.sum() * KOPEYKI;
        return new DepositCalculationResult(summTotal, summStonks);
    }
}


