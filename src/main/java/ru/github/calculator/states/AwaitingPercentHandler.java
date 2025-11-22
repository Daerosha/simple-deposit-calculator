package ru.github.calculator.states;

import ru.github.calculator.view.View;

import java.io.IOException;

public class AwaitingPercentHandler implements StateHandler {
    @Override
    public void handle(StateContext context, View view) throws IOException {
        view.write("Enter deposit percent: ");
        try {
            int percent = Integer.parseInt(view.read());
            if (percent <= 0) {
                view.write("Deposit percent must be a positive number.%n");
                return;
            }
            context.setPercent(percent);
            context.setCurrentState(State.CALCULATING);
        } catch (NumberFormatException e) {
            view.write("Invalid input. Please enter a valid number.%n");
        }
    }
}
