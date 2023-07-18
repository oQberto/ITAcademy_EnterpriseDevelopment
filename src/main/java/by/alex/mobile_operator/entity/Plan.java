package by.alex.mobile_operator.entity;

import by.alex.mobile_operator.enums.AddOn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public abstract class Plan {
    private Integer id;
    private String name;
    private BigDecimal basePrice;
    private AddOn addOn;
}
