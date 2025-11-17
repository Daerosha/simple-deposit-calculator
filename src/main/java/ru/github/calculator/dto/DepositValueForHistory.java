package ru.github.calculator.dto;

import java.time.LocalDateTime;

public record DepositValueForHistory(LocalDateTime time,int depositAmount, int term,int percent,long resultAmount, long resultStonks ) {

}
