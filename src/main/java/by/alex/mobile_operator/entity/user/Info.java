package by.alex.mobile_operator.entity.user;

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
    private String passportNo;
    private String password;
    private String name;
    private String surname;
    private LocalDate birthday;
}
