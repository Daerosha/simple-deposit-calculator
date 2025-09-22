package ru.github.calculator;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
