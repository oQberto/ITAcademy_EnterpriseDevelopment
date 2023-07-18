package by.alex.mobile_operator.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BasePlan extends Plan {
    private Integer includedMinutes;
    private Integer includedSMS;
}
