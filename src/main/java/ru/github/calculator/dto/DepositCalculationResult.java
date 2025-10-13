package ru.github.calculator.dto;

public record DepositCalculationResult(long total, long stonks) {
    public DepositCalculationResult {
        if (total < 0) {
            throw new IllegalArgumentException("Сумма на счете в конце депозита не может быть отрицательной: " + total);
        }
        if (stonks < 0) {
            throw new IllegalArgumentException("Прибыль на счете в конце депозита не может быть отрицательной: " + stonks);
        }
        if (total <= stonks) {
            throw new IllegalArgumentException(
                    "Сумма на счете в конце депозита(" + total + ") должна быть больше прибыли (" + stonks + ")"
            );
        }
    }
}
