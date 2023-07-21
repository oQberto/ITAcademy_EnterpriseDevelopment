package by.alex.mobile_operator.entity.plan;

import by.alex.mobile_operator.entity.plan.enums.AddOn;
import by.alex.mobile_operator.entity.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder
public abstract class Plan {
    private Integer id;
    private String name;
    private Double subscriptionFee;
    private List<AddOn> addOns;
    private List<User> users;
}
