package ru.github.calculator.inputoutput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class UserInputAndOutput {
    private static final double KOPEYKA_TO_RUBLES = 100.0;

    public void postResult(long total, long stonks) {
        calculateResult(total, stonks);
    }

    private void calculateResult(long totalForOutput, long stonksForOutput) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        symbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        System.out.println("Сумма на счете в конце депозита составляет: " + df.format(totalForOutput / KOPEYKA_TO_RUBLES));
        System.out.println("Из них прибыль по депозиту составляет: " + df.format(stonksForOutput / KOPEYKA_TO_RUBLES));
    }

    public int userInput(int from, int to) throws IOException {
        return getDataForCalculation(from, to);
    }

    private int getDataForCalculation
            (int from, int to) throws IOException {
        boolean isCorrectNumber = false;
        Integer inputus = null;
        while (!isCorrectNumber) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String input = reader.readLine();  // читаем строку из консоли
                inputus = Integer.parseInt(input); // пробуем преобразовать
            } catch (NumberFormatException e) {
                System.out.print("Ошибка: необходимо ввести целое число! Повторный ввод:");
            }
            if (inputus != null) {
                if (inputus < from || inputus > to) {
                    System.out.print("Вы ввели неверное число, оно должно быть от " + from + " до " + to + ", повторите попытку:");
                } else {
                    isCorrectNumber = true;
                }
            }

        }
        return inputus;
    }
}
