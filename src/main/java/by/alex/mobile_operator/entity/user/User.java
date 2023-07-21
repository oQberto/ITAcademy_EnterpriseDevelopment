package by.alex.mobile_operator.entity.user;

import by.alex.mobile_operator.entity.user.enums.Role;
import by.alex.mobile_operator.entity.user.enums.Status;
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
    private Status status;
}
