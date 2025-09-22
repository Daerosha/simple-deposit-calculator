package ru.github.calculator;

import java.io.IOException;

class DepositCalculateProfite {
    public static void main(String[] args) throws IOException {
        System.out.println("Добро пожаловать в простой депозитный клаькулятор!");
        depositCalculate calculate = new depositCalculate();
        calculate.startCalculate();
    }
}
