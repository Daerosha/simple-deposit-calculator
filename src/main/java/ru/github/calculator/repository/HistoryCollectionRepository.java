package ru.github.calculator.repository;

import ru.github.calculator.dto.DepositValueForHistory;

import java.util.ArrayList;
import java.util.List;

public class HistoryCollectionRepository implements HistoryRepository {
    private final List<DepositValueForHistory> storage = new ArrayList<>();

    @Override
    public void save(DepositValueForHistory depositCalculation) {
        storage.add(depositCalculation);
    }

    @Override
    public List<DepositValueForHistory> getAll() {
        return new ArrayList<>(storage);
    }

    @Override
    public void clear() {
        storage.clear();
    }

}