package ru.github.calculator;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class depositCalculate {

    public void startCalculate() throws IOException {
        UserDepositSumm s = new UserDepositSumm();
        int depositSumm = s.GetDepositSumm();
        UserDepositYears y = new UserDepositYears();
        int depositYears = y.GetDepositYears();
        UserDepositMonth m = new UserDepositMonth();
        int depositMonth = m.GetDepositMonth(depositYears);
        UserDepositPercent p = new UserDepositPercent();
        int depositPercent = p.GetDepositPercent();
        long summStonks =(long) (depositSumm*depositPercent*(depositYears+ depositMonth/12.0));
        long summTotal = summStonks + depositSumm*100;
        CalculateResult(summTotal,summStonks);
    }
    private void CalculateResult(long total, long stonks){
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        DecimalFormat df = new DecimalFormat("#,###.00", symbols);
        System.out.println("Сумма на счете в конце депозита составляет: " + df.format(total / 100.0));
        System.out.println("Из них прибыль по депозиту составляет: " + df.format(stonks / 100.0));
    }

}


