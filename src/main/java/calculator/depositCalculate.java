package main.java.calculator;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class depositCalculate {
    int depositSumm = 100;
    int depositYears = 10;
    int depositMonth = 0;
    int depositPercent = 10;

    public void startCalculate() throws IOException {
        UserDepositSumm s = new UserDepositSumm();
        depositSumm = s.GetDepositSumm();
        UserDepositYears y = new UserDepositYears();
        depositYears = y.GetDepositYears();
        UserDepositMonth m = new UserDepositMonth();
        depositMonth = m.GetDepositMonth(depositYears);
        UserDepositPercent p = new UserDepositPercent();
        depositPercent = p.GetDepositPercent();
        long summTerm = (depositSumm * depositYears * depositPercent / 100) + (depositSumm * depositMonth * depositPercent / 100) / 12;
        long summTotal = summTerm + depositSumm;
        System.out.println("Сумма на счете в конце депозита составляет : " + summTotal);
        System.out.println("Из них прибыль по депозиту составляет : " + summTerm);

    }
}

class DepositCalculateProfite {
    public static void main(String[] args) throws IOException {
        depositCalculate calculate = new depositCalculate();
        calculate.startCalculate();
    }
}



















class UserDepositSumm {
    public int GetDepositSumm() throws IOException {
        // Оборачиваем System.in (InputStream) в InputStreamReader, а потом в BufferedReader
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Введите сумму депозита (от 0 до 100 000 000): ");
        boolean asd = false;
        int number = 0;
        while (!asd) {
            String input = reader.readLine();  // читаем строку из консоли
            number = Integer.parseInt(input); // преобразуем строку в число
            if (number <= 0 || number >= 100000000) {
                System.out.print("Вы ввели неверное число, оно должно быть от 0 до 100 000 000, повторите попытку:");
            } else {
                asd = true;}
        }
        return number;
    }
}

















class UserDepositPercent {
    public int GetDepositPercent() throws IOException {
        // Оборачиваем System.in (InputStream) в InputStreamReader, а потом в BufferedReader
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Введите процент депозита (от 1 до 50): ");
        boolean asd = false;
        int percent = 0;
        while (!asd) {
            String input = reader.readLine();  // читаем строку из консоли
            percent = Integer.parseInt(input); // преобразуем строку в число
            if (percent <= 0 || percent > 50) {
                System.out.print("Вы ввели неверное число, оно должно быть от 1 до 50, повторите попытку:");
            } else {
                asd = true;}
        }
        return percent;
    }
}








class UserDepositYears {
    public int GetDepositYears() throws IOException {
        // Оборачиваем System.in (InputStream) в InputStreamReader, а потом в BufferedReader
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Введите срок депозита в годах (от 0 до 15): ");
        boolean asd = false;
        int years = 0;
        while (!asd) {
            String input = reader.readLine();  // читаем строку из консоли
            years = Integer.parseInt(input); // преобразуем строку в число
            if (years < 0 || years > 15) {
                System.out.print("Вы ввели неверное число, оно должно быть от 0 до 15, повторите попытку:");
            } else {
                asd = true;}
        }
        return years;
    }
}







class UserDepositMonth {
    public int GetDepositMonth(int y) throws IOException {
        // Оборачиваем System.in (InputStream) в InputStreamReader, а потом в BufferedReader
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean asd = false;
        int month = 0;
        if (y == 0) {
            System.out.print("Введите срок депозита в месяцах (от 1 до 11): ");
            while (!asd) {
                String input = reader.readLine();  // читаем строку из консоли
                month = Integer.parseInt(input); // преобразуем строку в число
                if ( month == 0){
                    System.out.print("Общий срок депозита составляет 0 лет и 0 месяцев, повторите попытку:");
                }
                else if (month < 0 || month > 11) {
                    System.out.print("Вы ввели неверное число, оно должно быть от 0 до 11, повторите попытку:");
                } else {
                    asd = true;
                }
            }

        } else {

            System.out.print("Введите срок депозита в месяцах (от 0 до 11): ");
            while (!asd) {
                String input = reader.readLine();  // читаем строку из консоли
                month = Integer.parseInt(input); // преобразуем строку в число
                if (month < 0 || month > 11) {
                    System.out.print("Вы ввели неверное число, оно должно быть от 0 до 11, повторите попытку:");
                } else {
                    asd = true;
                }
            }
        }
        return month;
    }
}


