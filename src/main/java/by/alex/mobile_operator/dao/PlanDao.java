package by.alex.mobile_operator.dao;

import by.alex.mobile_operator.entity.plan.InternetPlan;
import by.alex.mobile_operator.entity.plan.Plan;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlanDao implements CompanyController<Plan, Integer> {
    /**
     * The list works as a database
     */
    @Getter
    private final List<Plan> plans = new ArrayList<>();

    {
        fillDataBase();
    }

    @Override
    public List<Plan> getAll() {
        return plans;
    }

    @Override
    public Optional<Plan> getById(Integer id) {
        return plans.stream()
                .filter(plan -> plan.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean delete(Integer id) {
        var plan = getById(id);
        return plan.map(plans::remove).orElse(false);
    }

    @Override
    public boolean update(Plan entity) {
        var planId = getById(entity.getId())
                .map(Plan::getId)
                .orElse(null);

        if (entity.getId().equals(planId)) {
            delete(planId);
            return save(entity);
        }

        return false;
    }

    @Override
    public boolean save(Plan entity) {
        var plan = getById(entity.getId());

        if (plan.isEmpty()) {
            plan.map(plans::add);
            return true;
        }

        return false;
    }

    public List<Plan> sortBySubscriptionFee() {
        return null;
    }

    public Integer countNumberOfCustomers() {
        return null;
    }

    public Optional<Plan> getPlanByChosenParams(Object... args) {
        return Optional.empty();
    }

    private void fillDataBase() {
        plans.add(InternetPlan.builder()
                .id(1)
                .name("plan1")
                .subscriptionFee(12.5)
                .internetTraffic(15)
                .build());
    }
}
