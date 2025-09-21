package main.java.ru.github.calculator;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;

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
        double Stonks = ((double) (depositSumm * depositYears * depositPercent) / 100) + (double) (depositSumm * depositMonth * depositPercent / 100) / 12;
        BigDecimal sumStonks = new BigDecimal(Stonks);
        BigDecimal roundedsumStonks = sumStonks.setScale(2, RoundingMode.HALF_UP);
        float summTotal = roundedsumStonks + depositSumm;
        System.out.println("Сумма на счете в конце депозита составляет : " + summTotal);
        System.out.println("Из них прибыль по депозиту составляет : " + roundedsumStonks);

    }
}

class DepositCalculateProfite {
    public static void main(String[] args) throws IOException {
        System.out.println("Добро пожаловать в простой депозитный клаькулятор!");
        depositCalculate calculate = new depositCalculate();
        calculate.startCalculate();
    }
}



















class UserDepositSumm {
    public int GetDepositSumm() throws IOException {
        System.out.print("Введите сумму депозита (от 1 до 100 000 000): ");
        GetUserInput s = new GetUserInput();
        int number = GetUserInput.UserInput(1, 100000000); // преобразуем строку в число
        return number;
    }
}





class UserDepositPercent {
    public int GetDepositPercent() throws IOException {
        System.out.print("Введите процент депозита (от 1 до 50): ");
        GetUserInput p = new GetUserInput();
        int percent = p.UserInput(1,50); //
        return percent;
    }
}








class UserDepositYears {
    public int GetDepositYears() throws IOException {
        System.out.print("Введите срок депозита в годах (от 0 до 15): ");
        GetUserInput y = new GetUserInput();
         int years = y.UserInput(0,15);
        return years;
    }
}







class UserDepositMonth {
    public int GetDepositMonth(int y) throws IOException {
        GetUserInput m = new GetUserInput();
        boolean asd = false;
        int month = 0;
        if (y == 0){
            System.out.println("Вы планируете открыть депозит на срок менее 1 года, количество месяцев должно быть 1 или больше");
            System.out.print("Введите срок депозита в месяцах (от 1 до 11): ");
            month = m.UserInput(1, 11);
            if (month == 0) {
                System.out.print("Общий срок депозита составляет 0 лет и 0 месяцев, повторите попытку:");
            }
        }else {
            System.out.print("Введите срок депозита в месяцах (от 0 до 11): ");
            month = m.UserInput(0,11);
        }
        return month;
    }
}



class GetUserInput {
    public static int UserInput(int from, int to) throws IOException {
        // преобразуем строку в число
        boolean asd = false;
        int inputus = 0;
        while (!asd){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String input = reader.readLine();  // читаем строку из консоли
            inputus = Integer.parseInt(input); // пробуем преобразовать
        } catch (NumberFormatException e) {
            System.out.print("Ошибка: необходимо ввести целое число! Повторный ввод:");
        }
            if (inputus < from || inputus > to) {
                System.out.print("Вы ввели неверное число, оно должно быть от " + from + " до "+ to + ", повторите попытку:");
            } else {
                asd = true;}

        }
        return inputus;
    }
}
