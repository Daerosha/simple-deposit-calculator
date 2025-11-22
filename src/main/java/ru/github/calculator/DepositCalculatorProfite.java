package ru.github.calculator;

import ru.github.calculator.calculate.DepositCalculator;
import ru.github.calculator.history.CalculatingHistory;
import ru.github.calculator.states.*;
import ru.github.calculator.view.ConsoleView;
import ru.github.calculator.view.View;

import java.io.IOException;
import java.util.Map;

class DepositCalculatorProfite {

    public static void main(String[] args) throws IOException {
        View view = new ConsoleView();
        StateContext context = new StateContext();
        DepositCalculator calculator = new DepositCalculator();
        CalculatingHistory history = new CalculatingHistory(view);

        Map<State, StateHandler> handlers = Map.of(
                State.STARTING, new StartingHandler(),
                State.AWAITING_SUM, new AwaitingSumHandler(),
                State.AWAITING_TERM, new AwaitingTermHandler(),
                State.AWAITING_PERCENT, new AwaitingPercentHandler(),
                State.CALCULATING, new CalculatingHandler(calculator),
                State.DISPLAYING_RESULT, new DisplayingResultHandler(history),
                State.AWAITING_HISTORY_CHOICE, new AwaitingHistoryChoiceHandler(history),
                State.AWAITING_CONTINUE_CHOICE, new AwaitingContinueChoiceHandler(),
                State.EXITING, new ExitingHandler()
        );

        while (context.getCurrentState() != State.EXITING) {
            StateHandler handler = handlers.get(context.getCurrentState());
            handler.handle(context, view);
        }
        handlers.get(State.EXITING).handle(context, view);
    }
}
