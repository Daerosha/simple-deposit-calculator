package ru.github.calculator.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleView implements View {
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public String read() throws IOException {
        return reader.readLine();
    }

    @Override
    public void write(String message) {
        System.out.printf(message);
    }
}
