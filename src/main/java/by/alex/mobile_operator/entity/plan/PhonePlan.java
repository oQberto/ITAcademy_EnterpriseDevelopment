package by.alex.mobile_operator.entity.plan;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PhonePlan extends InternetPlan {
    private Integer includedMinutes;
    private Integer minutesToOtherNetworks;
    private Integer includedSMS;
}
