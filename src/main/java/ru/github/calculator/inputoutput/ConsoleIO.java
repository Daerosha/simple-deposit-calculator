package ru.github.calculator.inputoutput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleIO implements UserIO {

    @Override
    public void postValueToUser(String text) {
        System.out.printf(text);
    }


    @Override
    public String getValueFromUser() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }

}



