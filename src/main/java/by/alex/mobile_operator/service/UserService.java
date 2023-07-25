package by.alex.mobile_operator.service;

import by.alex.mobile_operator.dao.UserDao;
import by.alex.mobile_operator.entity.user.User;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserService {
    private static final UserService INSTANCE = new UserService();
    private final UserDao userDao = UserDao.getInstance();

    public boolean updateUser(User user) {
        return userDao.update(user);
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
