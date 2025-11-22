package ru.github.calculator.states;

import ru.github.calculator.dto.DepositValueForHistory;
import ru.github.calculator.history.CalculatingHistory;
import ru.github.calculator.view.View;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;

public class DisplayingResultHandler implements StateHandler {

    private static final double KOPEYKA_TO_RUBLES = 100.0;
    private final CalculatingHistory history;

    public DisplayingResultHandler(CalculatingHistory history) {
        this.history = history;
    }

    @Override
    public void handle(StateContext context, View view) throws IOException {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        symbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        view.write("%nThe balance at the end of the deposit is: "
                + df.format(context.getCalculationResult().total() / KOPEYKA_TO_RUBLES)
                + "%nOf this amount, the profit on the deposit is: "
                + df.format(context.getCalculationResult().stonks() / KOPEYKA_TO_RUBLES));

        history.save(new DepositValueForHistory(LocalDateTime.now(), context.getSum(), context.getTerm(),
                context.getPercent(), context.getCalculationResult().total(), context.getCalculationResult().stonks()));
        context.setCurrentState(State.AWAITING_HISTORY_CHOICE);
    }
}
