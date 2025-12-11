package ru.github.calculator.inputoutput;

import java.io.IOException;

public interface UserIO {
    void postValueToUser(String text);

    String getValueFromUser() throws IOException;
}
