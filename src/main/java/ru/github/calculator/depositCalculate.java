package ru.github.calculator;


import java.io.IOException;

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
        int summStonks =(int) (depositSumm*depositPercent*(depositYears+ depositMonth/12.0));
        int summTotal = summStonks + depositSumm*100;
        System.out.println("Сумма на счете в конце депозита составляет : " + summTotal/100.00);
        System.out.println("Из них прибыль по депозиту составляет : " + summStonks/100.00);

    }
}


