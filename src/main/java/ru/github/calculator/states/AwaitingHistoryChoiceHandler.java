package ru.github.calculator.states;

import ru.github.calculator.history.CalculatingHistory;
import ru.github.calculator.view.View;

import java.io.IOException;
import java.util.Objects;

public class AwaitingHistoryChoiceHandler implements StateHandler {
    private final CalculatingHistory history;

    public AwaitingHistoryChoiceHandler(CalculatingHistory history) {
        this.history = history;
    }

    @Override
    public void handle(StateContext context, View view) throws IOException {
        view.write("%nDo you want to see the calculation history? Type \"yes\" or \"no\" %n");
        String choice = view.read();
        if (Objects.equals(choice, "yes")) {
            view.write(history.getHistoryAsString());
        }
        context.setCurrentState(State.AWAITING_CONTINUE_CHOICE);
    }
}
