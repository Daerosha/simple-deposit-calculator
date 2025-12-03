package ru.github.calculator;

import ru.github.calculator.history.CalculatingHistory;
import ru.github.calculator.inputoutput.ConsoleIO;
import ru.github.calculator.inputoutput.UserIO;
import ru.github.calculator.repository.HistoryCollectionRepository;
import ru.github.calculator.repository.HistoryRepository;
import ru.github.calculator.states.*;

class DepositCalculatorProfite {

    public static void main(String[] args) {
        UserIO userIO = new ConsoleIO();
        StateController stateKeeper = new StateController();
        HistoryRepository repository = new HistoryCollectionRepository();
        CalculatingHistory calculatingHistory = new CalculatingHistory(repository);
        StateMachine machine = new StateMachine(stateKeeper);
        machine.addState(AvaibleStates.AWAITING_START, new AwaitingStartStateHandler(userIO, stateKeeper));
        machine.addState(AvaibleStates.AWAITING_SUM, new AwaitingSumStateHandler(userIO, stateKeeper));
        machine.addState(AvaibleStates.AWAITING_TERM, new AwaitingTermStateHandler(userIO, stateKeeper));
        machine.addState(AvaibleStates.AWAITING_PERCENT, new AwaitingPercentStateHandler(userIO, stateKeeper));
        machine.addState(AvaibleStates.AWAITING_CALCULATING, new AwaitingCalculatingStateHandler(userIO, stateKeeper));
        machine.addState(AvaibleStates.AWAITING_POST_CALCULATION_RESULT,
                new AwaitingPostCalculationResultStateHandler(userIO, stateKeeper));
        machine.addState(AvaibleStates.AWAITING_SAVE_CALCULATION_HISTORY,
                new AwaitingSaveCalculatingHistoryStateHandler(userIO, stateKeeper, calculatingHistory));

        machine.addState(AvaibleStates.AWAITING_HISTORY_DISPLAY,
                new AwaitingHistoryDisplayStateHandler(userIO, stateKeeper, calculatingHistory));
        machine.addState(AvaibleStates.AWAITING_CONTINUE_CALCULATING,
                new AwaitingContinueCalculatingStateHandler(userIO, stateKeeper));
        machine.addState(AvaibleStates.AWAITING_EXIT_DEPOSIT_CALCULATOR,
                new AwaitingExitDepositCalculatorStateHandler(userIO, stateKeeper));
        machine.initialState(AvaibleStates.AWAITING_START);
        AvaibleStates currentState = machine.handle();

        while (currentState != AvaibleStates.END) {
            currentState = machine.handle();
        }
    }
}