package by.alex.mobile_operator.dao;

import by.alex.mobile_operator.entity.plan.Plan;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlanDao implements CompanyController<Plan, Integer> {
    /**
     * The list works as a database
     */
    private final List<Plan> plans = new ArrayList<>();

    {
        fillDataBase();
    }

    @Override
    public List<Plan> getAll() {
        return null;
    }

    @Override
    public Optional<Plan> getById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void update(Plan entity) {

    }

    @Override
    public Plan save(Plan entity) {
        return null;
    }

    private void fillDataBase() {

    }
}
