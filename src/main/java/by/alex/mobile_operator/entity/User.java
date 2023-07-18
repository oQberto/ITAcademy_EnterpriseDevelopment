package by.alex.mobile_operator.entity;

import by.alex.mobile_operator.enums.Role;
import by.alex.mobile_operator.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Integer id;
    private Role role;
    private Info info;
    private Plan plan;
    private Status status;
}
