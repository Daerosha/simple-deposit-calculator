package ru.github.calculator.dto;

import java.time.LocalDateTime;

public record DepositValueForHistory(LocalDateTime time, long depositAmount, long term, long percent, long resultAmount,
                                     long resultStonks) {

}
