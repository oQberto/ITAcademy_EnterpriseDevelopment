package by.alex.mobile_operator.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PremiumPlan extends BasePlan {
    private Integer minutesToOtherNetworks;
    private Integer InternetTraffic;
}
