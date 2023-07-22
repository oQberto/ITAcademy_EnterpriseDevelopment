package by.alex.mobile_operator.entity.plan;

import by.alex.mobile_operator.entity.plan.enums.AddOn;
import by.alex.mobile_operator.entity.plan.enums.PlanType;
import by.alex.mobile_operator.entity.user.User;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
public abstract class Plan {
    private final Integer id;
    private String name;
    private Double subscriptionFee;
    private PlanType planType;
    private List<AddOn> addOns;
    private List<User> users;
}
