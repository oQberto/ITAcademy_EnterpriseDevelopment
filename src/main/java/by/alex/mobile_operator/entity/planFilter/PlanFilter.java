package by.alex.mobile_operator.entity.planFilter;

import by.alex.mobile_operator.entity.plan.enums.PlanType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlanFilter {
    private Double subscriptionFeeFrom;
    private Double subscriptionFeeTo;
    private PlanType planType;
}
