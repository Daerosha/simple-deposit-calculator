package ru.github.calculator.inputoutput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInputAndOutput {

    public String getDataForCalculation() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }

    public void post(String text) {
        System.out.printf(text);
    }
}



