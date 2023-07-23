package by.alex.mobile_operator.dao;

import by.alex.mobile_operator.entity.plan.Plan;
import by.alex.mobile_operator.entity.planFilter.PlanFilter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class PlanDao implements CompanyController<Plan, Integer> {
    private static final PlanDao INSTANCE = new PlanDao();
    /**
     * The list works as a database
     */
    private final List<Plan> plans = new ArrayList<>();

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
            save(entity);
            return true;
        }

        return false;
    }

    @Override
    public Plan save(Plan entity) {
        var plan = getById(entity.getId());

        if (plan.isEmpty()) {
            plans.add(entity);
            return entity;
        }

        return null;
    }

    public List<Plan> sortBySubscriptionFee() {
        return plans.stream()
                .sorted(Comparator.comparing(Plan::getSubscriptionFee))
                .collect(Collectors.toList());
    }

    public Integer countNumberOfCustomers() {
        return plans.stream()
                .mapToInt(plan -> plan.getUsers().size())
                .sum();
    }

    public List<Plan> getPlanByChosenPrice(PlanFilter planFilter) {
        return plans.stream()
                .filter(plan -> plan.getSubscriptionFee() > planFilter.getSubscriptionFeeFrom()
                                && plan.getSubscriptionFee() < planFilter.getSubscriptionFeeTo())
                .collect(Collectors.toList());
    }

    public List<Plan> getPlanByPlanType(PlanFilter planFilter) {
        return plans.stream()
                .filter(plan -> plan.getPlanType().equals(planFilter.getPlanType()))
                .collect(Collectors.toList());
    }

    public static PlanDao getInstance() {
        return INSTANCE;
    }
}
