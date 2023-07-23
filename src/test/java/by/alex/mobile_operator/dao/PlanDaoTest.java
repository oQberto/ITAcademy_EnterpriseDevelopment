package by.alex.mobile_operator.dao;

import by.alex.mobile_operator.entity.plan.InternetPlan;
import by.alex.mobile_operator.entity.plan.PhonePlan;
import by.alex.mobile_operator.entity.plan.Plan;
import by.alex.mobile_operator.entity.plan.TVPlan;
import by.alex.mobile_operator.entity.plan.enums.PlanType;
import by.alex.mobile_operator.entity.planFilter.PlanFilter;
import by.alex.mobile_operator.entity.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PlanDaoTest {
    private final PlanDao planDao = PlanDao.getInstance();

    @Test
    void getAll() {
        Plan plan1 = planDao.save(getInternetPlan(1));
        Plan plan2 = planDao.save(getPhonePlan(2));
        Plan plan3 = planDao.save(getTVPlan(3));

        List<Plan> actualResult = planDao.getAll();
        assertThat(actualResult).hasSize(3);

        List<Integer> planIds = actualResult.stream()
                .map(Plan::getId)
                .toList();
        assertThat(planIds).contains(plan1.getId(), plan2.getId(), plan3.getId());
    }

    @Test
    void getByIdIfPlanExists() {
        Plan plan = planDao.save(getPhonePlan(1));

        Optional<Plan> actualResult = planDao.getById(1);

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(plan);
    }

    @Test
    void getByIdIfPlanDoesNotExist() {
        Plan plan = planDao.save(getInternetPlan(1));

        Optional<Plan> actualResult = planDao.getById(2);

        assertThat(actualResult).isEmpty();
    }

    @Test
    void deleteExistingPlan() {
        Plan plan = planDao.save(getTVPlan(1));
        Plan plan1 = planDao.save(getInternetPlan(2));

        boolean actualResult = planDao.delete(1);

        assertThat(actualResult).isTrue();
        assertThat(planDao.getAll()).hasSize(1);
    }

    @Test
    void deleteNotExistingPlan() {
        Plan plan = planDao.save(getPhonePlan(1));

        boolean actualResult = planDao.delete(2);

        assertThat(actualResult).isFalse();
        assertThat(planDao.getAll()).hasSize(1);
    }

    @Test
    void updateExistingPlan() {
        Plan plan = planDao.save(getPhonePlan(1));
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
        Plan plan1 = planDao.save(getTVPlan(1));
        Plan plan2 = planDao.save(getInternetPlan(2));
        planDao.delete(1);
        plan1.setName("New name");

        boolean actualResult = planDao.update(plan1);
        Optional<Plan> updatedPlan = planDao.getById(plan1.getId());

        assertThat(actualResult).isFalse();
        assertThat(updatedPlan).isEmpty();
    }

    @Test
    void saveNotExistingPlan() {
        Plan plan = getInternetPlan(1);

        Plan actualResult = planDao.save(plan);

        assertNotNull(actualResult);
    }

    @Test
    void saveExistingPlan() {
        Plan plan = planDao.save(getTVPlan(1));

        Plan actualResult = planDao.save(plan);

        assertNull(actualResult);
    }

    @Test
    void sortBySubscriptionFee() {
        Plan plan1 = planDao.save(getInternetPlan(1));
        Plan plan2 = planDao.save(getPhonePlan(2));
        Plan plan3 = planDao.save(getTVPlan(3));
        List<Plan> expectedList = List.of(plan1, plan3, plan2);

        List<Plan> actualResult = planDao.sortBySubscriptionFee();

        assertEquals(actualResult, expectedList);
    }

    @Test
    void countNumberOfCustomers() {
        Plan plan1 = planDao.save(getInternetPlan(1));
        Plan plan2 = planDao.save(getPhonePlan(2));
        Plan plan3 = planDao.save(getTVPlan(3));
        Integer expectedResult = plan1.getUsers().size()
                + plan2.getUsers().size()
                + plan3.getUsers().size();

        Integer actualResult = planDao.countNumberOfCustomers();

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void getPlanByChosenPrice() {
        Plan plan1 = planDao.save(getInternetPlan(1));
        Plan plan2 = planDao.save(getPhonePlan(2));
        Plan plan3 = planDao.save(getTVPlan(3));
        Plan plan4 = planDao.save(getTVPlan(4));

        List<Plan> actualResult = planDao.getPlanByChosenPrice(getPlanFilter());
        assertThat(actualResult).hasSize(2);

        List<Integer> plansIds = actualResult.stream()
                .map(Plan::getId)
                .toList();
        assertThat(plansIds).contains(plan3.getId(), plan4.getId());
    }

    @Test
    void getPlanByPlanType() {
        Plan plan1 = planDao.save(getInternetPlan(1));
        Plan plan2 = planDao.save(getPhonePlan(2));
        Plan plan3 = planDao.save(getTVPlan(3));
        Plan plan4 = planDao.save(getPhonePlan(4));
        Plan plan5 = planDao.save(getInternetPlan(5));
        Plan plan6 = planDao.save(getPhonePlan(6));

        List<Plan> actualResult = planDao.getPlanByPlanType(getPlanFilter());
        assertThat(actualResult).hasSize(3);

        List<Integer> plansIds = actualResult.stream()
                .map(Plan::getId)
                .toList();
        assertThat(plansIds).contains(plan2.getId(), plan4.getId(), plan6.getId());
    }

    @BeforeEach
    void cleanData() {
        planDao.getAll().clear();
    }

    private Plan getInternetPlan(Integer id) {
        return InternetPlan.builder()
                .id(id)
                .name("Plan1")
                .subscriptionFee(10.0)
                .planType(PlanType.INTERNET)
                .users(List.of(new User(), new User()))
                .internetTraffic(15)
                .build();
    }

    private Plan getPhonePlan(Integer id) {
        return PhonePlan.builder()
                .id(id)
                .name("Plan2")
                .subscriptionFee(17.5)
                .planType(PlanType.PHONE)
                .users(List.of(new User()))
                .internetTraffic(25)
                .includedMinutes(600)
                .minutesToOtherNetworks(100)
                .includedSMS(100)
                .build();
    }

    private Plan getTVPlan(Integer id) {
        return TVPlan.builder()
                .id(id)
                .name("Plan2")
                .subscriptionFee(13.5)
                .planType(PlanType.TV)
                .users(List.of(new User(), new User(), new User()))
                .internetTraffic(25)
                .includedMinutes(600)
                .minutesToOtherNetworks(100)
                .includedSMS(100)
                .tvChannels(150)
                .build();
    }

    private PlanFilter getPlanFilter() {
        return PlanFilter.builder()
                .subscriptionFeeFrom(10.5)
                .subscriptionFeeTo(15.0)
                .planType(PlanType.PHONE)
                .build();
    }
}