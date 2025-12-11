package ru.github.calculator.dto;

import java.time.LocalDateTime;

public record DepositValueForHistory(
        DepositValueForCalculation input,
        DepositCalculationResult result,
        LocalDateTime time
) {}