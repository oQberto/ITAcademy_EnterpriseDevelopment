package by.alex.mobile_operator.dao;

import by.alex.mobile_operator.entity.plan.InternetPlan;
import by.alex.mobile_operator.entity.plan.Plan;
import by.alex.mobile_operator.entity.plan.enums.PlanType;
import by.alex.mobile_operator.entity.planFilter.PlanFilter;
import by.alex.mobile_operator.entity.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.FileUtil;
import util.MapperUtil;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PlanDaoTest {
    private final PlanDao planDao = PlanDao.getInstance();
    private static List<Plan> plans;

    @BeforeAll
    static void initTestData() throws IOException {
        var testData = FileUtil.readFromFileToString("src/test/resources/plan.json");
        plans = MapperUtil.map(testData);
    }

    @BeforeEach
    void initData() {
        plans.forEach(planDao::save);
    }

    @Test
    void getAll() {
        List<Plan> actualResult = planDao.getAll();
        assertThat(actualResult).hasSize(8);

        List<Integer> planIds = actualResult.stream()
                .map(Plan::getId)
                .toList();
        assertThat(planIds).isEqualTo(plans.stream().map(Plan::getId).toList());
    }

    @Test
    void getByIdIfPlanExists() {
        Plan plan = plans.get(2);

        Optional<Plan> actualResult = planDao.getById(3);

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(plan);
    }

    @Test
    void getByIdIfPlanDoesNotExist() {
        Optional<Plan> actualResult = planDao.getById(999);

        assertThat(actualResult).isEmpty();
    }

    @Test
    void deleteExistingPlan() {
        boolean actualResult = planDao.delete(3);

        assertThat(actualResult).isTrue();
        assertThat(planDao.getAll()).hasSize(7);
    }

    @Test
    void deleteNotExistingPlan() {
        boolean actualResult = planDao.delete(999);

        assertThat(actualResult).isFalse();
        assertThat(planDao.getAll()).hasSize(8);
    }

    @Test
    void updateExistingPlan() {
        Plan plan = plans.get(0);
        plan.setSubscriptionFee(9.0);
        plan.setName("Summer Plan");

        boolean actualResult = planDao.update(plan);
        Optional<Plan> updatedPlan = planDao.getById(plan.getId());

        assertThat(actualResult).isTrue();
        assertThat(updatedPlan).isPresent();
        assertThat(plan).isEqualTo(updatedPlan.get());
    }

    @Test
    void updateNotExistingPlan() {
        Plan plan1 = plans.get(7);
        planDao.delete(8);
        plan1.setName("New name");

        boolean actualResult = planDao.update(plan1);
        Optional<Plan> updatedPlan = planDao.getById(plan1.getId());

        assertThat(actualResult).isFalse();
        assertThat(updatedPlan).isEmpty();
    }

    @Test
    void saveNotExistingPlan() {
        Plan plan = InternetPlan.builder()
                .id(9)
                .name("Plan9")
                .subscriptionFee(10.0)
                .planType(PlanType.INTERNET)
                .users(List.of(new User(), new User()))
                .internetTraffic(15)
                .build();

        Plan actualResult = planDao.save(plan);

        assertNotNull(actualResult);
        assertThat(planDao.getAll()).hasSize(9);
    }

    @Test
    void saveExistingPlan() {
        Plan plan = plans.get(0);

        Plan actualResult = planDao.save(plan);

        assertNull(actualResult);
    }

    @Test
    void sortBySubscriptionFee() {
        List<Plan> expectedList = plans.stream()
                .sorted(Comparator.comparing(Plan::getSubscriptionFee))
                .collect(Collectors.toList());

        List<Plan> actualResult = planDao.sortBySubscriptionFeeAscending();

        assertEquals(actualResult, expectedList);
    }

    @Test
    void countNumberOfCustomers() {
        Integer actualResult = planDao.countNumberOfCustomers();

        assertThat(actualResult).isEqualTo(23);
    }

    @Test
    void getPlanByChosenPrice() {
        List<Plan> actualResult = planDao.getPlanByChosenPrice(getPlanFilter());
        assertThat(actualResult).hasSize(2);

        List<Integer> plansIds = actualResult.stream()
                .map(Plan::getId)
                .toList();
        assertThat(plansIds).contains(1, 8);
    }

    @Test
    void getPlanByPlanType() {
        List<Plan> actualResult = planDao.getPlanByPlanType(getPlanFilter());
        assertThat(actualResult).hasSize(4);

        List<Integer> plansIds = actualResult.stream()
                .map(Plan::getId)
                .toList();
        assertThat(plansIds).contains(2, 4, 6, 8);
    }

    @AfterEach
    void cleanData() {
        planDao.getAll().clear();
    }

    private PlanFilter getPlanFilter() {
        return PlanFilter.builder()
                .subscriptionFeeFrom(10.0)
                .subscriptionFeeTo(16.0)
                .planType(PlanType.PHONE)
                .build();
    }
}