package ru.github.calculator.states;

import ru.github.calculator.dto.DepositCalculationResult;

public class StateContext {
    private State currentState;
    private int sum;
    private int term;
    private int percent;
    private DepositCalculationResult calculationResult;

    public StateContext() {
        this.currentState = State.STARTING;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public DepositCalculationResult getCalculationResult() {
        return calculationResult;
    }

    public void setCalculationResult(DepositCalculationResult calculationResult) {
        this.calculationResult = calculationResult;
    }
}
