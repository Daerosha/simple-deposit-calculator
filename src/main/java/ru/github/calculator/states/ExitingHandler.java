package ru.github.calculator.states;

import ru.github.calculator.view.View;

import java.io.IOException;

public class ExitingHandler implements StateHandler {
    @Override
    public void handle(StateContext context, View view) throws IOException {
        view.write("%nThank you for using the simple deposit calculator!%n");
    }
}
