package by.alex.mobile_operator.entity;

import by.alex.mobile_operator.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Info {
    private Integer id;
    private Long passportNo;
    private String name;
    private String secondName;
    private String surname;
    private LocalDate birthday;
    private Sex sex;
}
