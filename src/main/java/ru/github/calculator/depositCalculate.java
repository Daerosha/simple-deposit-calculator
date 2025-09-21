package main.java.ru.github.calculator;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
        int summStonks = (( depositSumm*depositPercent *(depositYears +(depositMonth/12))));
        int summTotal = summStonks + depositSumm*100;
        System.out.println("Сумма на счете в конце депозита составляет : " + summTotal/100.00);
        System.out.println("Из них прибыль по депозиту составляет : " + summStonks/100.00);

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
//        GetUserInput s = new GetUserInput();
//        int summa = s.UserInput(1, 100000000);
//       return summa;
        return GetUserInput.UserInput(1,100000000);

    }
}





class UserDepositPercent {
    public int GetDepositPercent() throws IOException {
        System.out.print("Введите процент депозита (от 1 до 50): ");
//        GetUserInput p = new GetUserInput();
//        int percent = p.UserInput(1,50);
//       return percent;
        return GetUserInput.UserInput(1,50);

    }
}








class UserDepositYears {
    public int GetDepositYears() throws IOException {
        System.out.print("Введите срок депозита в годах (от 0 до 15): ");
        return GetUserInput.UserInput(0,15);
    }
}







class UserDepositMonth {
    public int GetDepositMonth(int y) throws IOException {
        int month ;
        if (y == 0){
            System.out.println("Вы планируете открыть депозит на срок менее 1 года, количество месяцев должно быть 1 или больше");
            System.out.print("Введите срок депозита в месяцах (от 1 до 11): ");
            month = GetUserInput.UserInput(1, 11);
//        GetUserInput m = new GetUserInput();
//        int month = m.UserInput(1,11);
//        return month;
        }else {
            System.out.print("Введите срок депозита в месяцах (от 0 до 11): ");
            month = GetUserInput.UserInput(0,11);
//        GetUserInput m = new GetUserInput();
//        int month = m.UserInput(0,11);
//        return month;

        }
        return month;
    }
}



class GetUserInput {
    public static int UserInput(int from, int to) throws IOException {
        boolean asd = false;
        Integer inputus = null ;
        while (!asd){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String input = reader.readLine();  // читаем строку из консоли
            inputus = Integer.parseInt(input); // пробуем преобразовать
        } catch (NumberFormatException e) {
            System.out.print("Ошибка: необходимо ввести целое число! Повторный ввод:");
        }if (inputus != null){
            if (inputus < from || inputus > to) {
                System.out.print("Вы ввели неверное число, оно должно быть от " + from + " до "+ to + ", повторите попытку:");
            } else {
                asd = true;}}

        }
        return inputus;
    }
}
