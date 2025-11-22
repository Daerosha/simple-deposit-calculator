package ru.github.calculator.states;

import ru.github.calculator.view.View;

import java.io.IOException;

public class AwaitingSumHandler implements StateHandler {
    @Override
    public void handle(StateContext context, View view) throws IOException {
        view.write("Enter deposit sum: ");
        try {
            int sum = Integer.parseInt(view.read());
            if (sum <= 0) {
                view.write("Deposit sum must be a positive number.%n");
                return;
            }
            context.setSum(sum);
            context.setCurrentState(State.AWAITING_TERM);
        } catch (NumberFormatException e) {
            view.write("Invalid input. Please enter a valid number.%n");
        }
    }
}
