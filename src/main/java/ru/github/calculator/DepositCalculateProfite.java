package ru.github.calculator;

import ru.github.calculator.calculate.DepositCalculate;
import ru.github.calculator.inputoutput.*;

import java.io.IOException;

class DepositCalculateProfite {
    public void main(String[] args) throws IOException {
        System.out.println("Добро пожаловать в простой депозитный калькулятор!");
        UserDepositSumm s = new UserDepositSumm();
        int depositSummForCalculating = s.getDepositSumm();
        UserDepositYears y = new UserDepositYears();
        int depositYearsForCalculating = y.getDepositYears();
        UserDepositMonth m = new UserDepositMonth();
        int depositMonthForCalculating = m.getDepositMonth(depositYearsForCalculating);
        UserDepositPercent p = new UserDepositPercent();
        int depositPercentForCalculating = p.getDepositPercent();
        DepositCalculate calculate = new DepositCalculate();
        long stonksAfterCalculating = calculate.startCalculateStonks(depositSummForCalculating, depositYearsForCalculating, depositMonthForCalculating, depositPercentForCalculating);
        long totalAfterCalculating = calculate.startCalculateSumm(stonksAfterCalculating, depositSummForCalculating);
        UserInputAndOutput calculateEnd = new UserInputAndOutput();
        calculateEnd.postResult(totalAfterCalculating, stonksAfterCalculating);
    }
}
