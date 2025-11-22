package ru.github.calculator.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConsoleViewTest {

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    private View view;

    @BeforeEach
    void setUp() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
        view = new ConsoleView();
    }

    @AfterEach
    void tearDown() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    @Test
    void shouldWriteToConsole() {
        String message = "Hello, World!";
        view.write(message);
        assertEquals(message, testOut.toString());
    }

    @Test
    void shouldReadFromConsole() throws IOException {
        String input = "test input";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
        view = new ConsoleView(); // Re-initialize to use the new System.in

        String result = view.read();

        assertEquals(input, result);
    }
}
