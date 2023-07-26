package by.alex.mobile_operator.service;

import by.alex.mobile_operator.dao.PlanDao;
import by.alex.mobile_operator.entity.plan.Plan;
import by.alex.mobile_operator.entity.planFilter.PlanFilter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class PlanService {
    private static final PlanService INSTANCE = new PlanService();
    private final PlanDao planDao = PlanDao.getInstance();

    {
        initPlans();
    }

    public Optional<Plan> getPlanByName(String name) {
        return planDao.getPlanByName(name);
    }

    public List<Plan> browseCatalogue() {
        return planDao.getAll();
    }

    public List<Plan> sortPlansByType(PlanFilter planFilter) {
        return planDao.getPlanByPlanType(planFilter);
    }

    public List<Plan> sortPlansByPriceRange(PlanFilter planFilter) {
        return planDao.getPlanByChosenPrice(planFilter);
    }

    public List<Plan> sortPlansBySubscriptionFee() {
        return planDao.sortBySubscriptionFeeAscending();
    }

    public List<Plan> sortByPriceDescending() {
        return planDao.sortBySubscriptionFeeDescending();
    }

    private void initPlans() {
        try {
            planDao.getAll().addAll(
                    new ObjectMapper().readValue(
                            new String(Files.readAllBytes(Path.of("C:\\Users\\ermak\\IdeaProjects\\ITAcademy_EnterpriseDevelopment\\src\\main\\resources\\plan.json"))),
                            new TypeReference<>() {
                            }
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static PlanService getInstance() {
        return INSTANCE;
    }
}
