package ru.github.calculator.repository;

import ru.github.calculator.dto.DepositValueForHistory;

import java.util.List;

public interface HistoryRepository {
    void save(DepositValueForHistory dto);

    List<DepositValueForHistory> getAll();

    void clear();
}
