package ru.github.calculator.dto;

public record DepositValueForCalculation(long sum, long term, long percent) {
    public DepositValueForCalculation {
        if (sum <= 0) {
            throw new IllegalArgumentException("The deposit amount must be greater than zero:" + sum);
        }
        if (term <= 0) {
            throw new IllegalArgumentException(
                    "The deposit term must be greater than zero:" + term);
        }
        if (percent <= 0) {
            throw new IllegalArgumentException(
                    "The deposit percent must be higher than zero:" + percent);
        }
    }
}


