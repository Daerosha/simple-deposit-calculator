package ru.github.calculator.states;

import ru.github.calculator.view.View;

import java.io.IOException;

public interface StateHandler {
    void handle(StateContext context, View view) throws IOException;
}
