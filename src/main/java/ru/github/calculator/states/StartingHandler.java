package ru.github.calculator.states;

import ru.github.calculator.view.View;

import java.io.IOException;

public class StartingHandler implements StateHandler {
    @Override
    public void handle(StateContext context, View view) throws IOException {
        view.write("%nWelcome to the simple deposit calculator!%n");
        context.setCurrentState(State.AWAITING_SUM);
    }
}
