package ru.github.calculator.states;

import ru.github.calculator.view.View;

import java.io.IOException;

public class AwaitingTermHandler implements StateHandler {
    @Override
    public void handle(StateContext context, View view) throws IOException {
        view.write("Enter deposit term (in months): ");
        try {
            int term = Integer.parseInt(view.read());
            if (term <= 0) {
                view.write("Deposit term must be a positive number.%n");
                return;
            }
            context.setTerm(term);
            context.setCurrentState(State.AWAITING_PERCENT);
        } catch (NumberFormatException e) {
            view.write("Invalid input. Please enter a valid number.%n");
        }
    }
}
