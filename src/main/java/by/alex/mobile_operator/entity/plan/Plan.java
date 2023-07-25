package by.alex.mobile_operator.entity.plan;

import by.alex.mobile_operator.entity.plan.enums.AddOn;
import by.alex.mobile_operator.entity.plan.enums.PlanType;
import by.alex.mobile_operator.entity.user.User;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor(force = true)
@ToString(exclude = {"users", "addOns"})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = InternetPlan.class, name = "internetPlan"),
        @JsonSubTypes.Type(value = PhonePlan.class, name = "phonePlan"),
        @JsonSubTypes.Type(value = TVPlan.class, name = "tvPlan")
})
public abstract class Plan {
    private final Integer id;
    private String name;
    private Double subscriptionFee;
    private PlanType planType;
    private List<AddOn> addOns;
    private List<User> users;
}
