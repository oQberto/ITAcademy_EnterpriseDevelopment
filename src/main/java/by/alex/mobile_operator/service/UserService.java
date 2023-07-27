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

    /**
     * The variable works as a "SERIAL"
     */
    private Integer userId = 1;

    public boolean updateUser(User user) {
        return userDao.update(user);
    }

    public Optional<User> login(String username, String password) {
        return userDao.findByUsernameAndPassword(username, password);
    }

    public boolean saveUser(User user) {
        user.setId(userId++);
        return userDao.save(user) != null;
    }

    public String showInfo(User user) {
        if (userDao.getById(user.getId()).isPresent()) {
            return user.getInfo().toString() + user.getPlan();
        } else {
            return "There isn't a user";
        }
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
