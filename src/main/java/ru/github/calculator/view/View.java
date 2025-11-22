package ru.github.calculator.view;

import java.io.IOException;

public interface View {
    String read() throws IOException;
    void write(String message);
}
