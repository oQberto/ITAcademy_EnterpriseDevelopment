package by.alex.mobile_operator.dao;

import by.alex.mobile_operator.entity.user.User;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserDao implements CompanyController<User, Integer> {
    private static final UserDao INSTANCE = new UserDao();
    /**
     * The list works sa a database
     */
    private final List<User> users = new ArrayList<>();

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public Optional<User> getById(Integer id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean delete(Integer id) {
        var user = getById(id);
        return user.map(users::remove).orElse(false);
    }

    @Override
    public boolean update(User entity) {
        var userId = getById(entity.getId())
                .map(User::getId)
                .orElse(null);

        if (entity.getId().equals(userId)) {
            delete(entity.getId());
            save(entity);
            return true;
        }

        return false;
    }

    @Override
    public User save(User entity) {
        var user = getById(entity.getId());

        if (user.isEmpty()) {
            users.add(entity);
            return entity;
        }

        return null;
    }

    public Optional<User> findByUsernameAndPassword(String username, String password) {
        return users.stream()
                .filter(user -> user.getInfo().getName().equals(username)
                        && user.getInfo().getPassword().equals(password))
                .findFirst();
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}
