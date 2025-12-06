package ru.github.calculator;

import ru.github.calculator.inputoutput.ConsoleIO;
import ru.github.calculator.inputoutput.UserIO;
import ru.github.calculator.states.*;

import java.util.HashMap;
import java.util.Map;

class DepositCalculatorProfite {

    public static void main(String[] args) {
        UserIO userIO = new ConsoleIO();
        Map<AvaibleStates, State> statesMap = new HashMap<>();
        StateControl stateMachine = new StateControl();
        statesMap.put(AvaibleStates.AwaitingStartState, new AwaitingStartState(userIO, stateMachine));
        statesMap.put(AvaibleStates.AwaitingSumState, new AwaitingSumState(userIO, stateMachine));
        statesMap.put(AvaibleStates.AwaitingTermState, new AwaitingTermState(userIO, stateMachine));
        statesMap.put(AvaibleStates.AwaitingPercentState, new AwaitingPercentState(userIO, stateMachine));
        statesMap.put(AvaibleStates.AwaitingCalculatingState, new AwaitingCalculatingState(userIO, stateMachine));
        statesMap.put(AvaibleStates.AwaitingPostCalculationResultState, new AwaitingPostCalculationResultState(userIO, stateMachine));
        statesMap.put(AvaibleStates.AwaitingSaveCalculatingHistoryState, new AwaitingSaveCalculatingHistoryState(userIO, stateMachine));
        statesMap.put(AvaibleStates.AwaitingHistoryDisplayState, new AwaitingHistoryDisplayState(userIO, stateMachine));
        statesMap.put(AvaibleStates.AwaitingContinueCalculatingState, new AwaitingContinueCalculatingState(userIO, stateMachine));
        statesMap.put(AvaibleStates.AwaitingExitDepositCalculatorState, new AwaitingExitDepositCalculatorState(userIO, stateMachine));
        AvaibleStates handler = AvaibleStates.AwaitingStartState;

        while (handler != AvaibleStates.End) {
            statesMap.get(handler).handle();
            handler = stateMachine.getCurrentState();
        }
    }
}