package ru.github.calculator.states;

import ru.github.calculator.dto.DepositCalculationResult;
import ru.github.calculator.dto.DepositValueForCalculation;
import ru.github.calculator.dto.DepositValueForHistory;

import java.time.LocalDateTime;
import java.util.HashMap;

public class StateControl {
    private final HashMap<String, Long> CalculatingValue = new HashMap<>();
    private AvaibleStates state;

    public void setSum(int sum) {
        CalculatingValue.put("sum", (long) sum);
    }

    public void setTerm(int term) {
        CalculatingValue.put("term", (long) term);
    }

    public void setPercent(int percent) {
        CalculatingValue.put("percent", (long) percent);
    }

    public void setStonks(long sumStonks) {
        CalculatingValue.put("stonks", sumStonks);
    }

    public void setTotal(long sumTotal) {
        CalculatingValue.put("total", sumTotal);
    }

    public AvaibleStates getCurrentState() {
        return state;
    }


    public void setNextState(AvaibleStates state) {
        this.state = state;
    }

    public DepositValueForCalculation getValueForCalculation() {
        return new DepositValueForCalculation(CalculatingValue.get("sum"), CalculatingValue.get("term"), CalculatingValue.get("percent"));
    }

    public DepositCalculationResult getCalculationResult() {
        return new DepositCalculationResult(CalculatingValue.get("total"), CalculatingValue.get("stonks"));
    }

    public DepositValueForHistory getValueForCollection() {
        return new DepositValueForHistory(LocalDateTime.now(), CalculatingValue.get("sum"), CalculatingValue.get("term"), CalculatingValue.get("percent"), CalculatingValue.get("total"), CalculatingValue.get("stonks"));
    }

    public void clearCalculatingValue() {
        CalculatingValue.clear();
    }
}
