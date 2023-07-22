package by.alex.mobile_operator.entity.plan;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class InternetPlan extends Plan {
    private Integer internetTraffic;
}