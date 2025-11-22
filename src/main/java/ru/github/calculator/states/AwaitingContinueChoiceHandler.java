package ru.github.calculator.states;

import ru.github.calculator.view.View;

import java.io.IOException;
import java.util.Objects;

public class AwaitingContinueChoiceHandler implements StateHandler {
    @Override
    public void handle(StateContext context, View view) throws IOException {
        view.write("%nDo you want to calculate again? Type \"yes\" or \"no\" %n");
        String choice = view.read();
        if (Objects.equals(choice, "yes")) {
            context.setCurrentState(State.AWAITING_SUM);
        } else {
            context.setCurrentState(State.EXITING);
        }
    }
}
