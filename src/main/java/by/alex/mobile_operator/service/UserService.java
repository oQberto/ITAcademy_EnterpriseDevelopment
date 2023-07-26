package by.alex.mobile_operator.service;

import by.alex.mobile_operator.dao.UserDao;
import by.alex.mobile_operator.entity.user.User;
import lombok.NoArgsConstructor;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserService {
    private static final UserService INSTANCE = new UserService();
    private final UserDao userDao = UserDao.getInstance();

    public boolean updateUser(User user) {
        return userDao.update(user);
    }

    public Optional<User> login(String username, String password) {
        return userDao.findByUsernameAndPassword(username, password);
    }

    public boolean saveUser(User user) {
        return userDao.save(user) != null;
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
