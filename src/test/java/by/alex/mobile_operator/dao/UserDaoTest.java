package by.alex.mobile_operator.dao;

import by.alex.mobile_operator.entity.user.Info;
import by.alex.mobile_operator.entity.user.User;
import by.alex.mobile_operator.entity.user.enums.Role;
import by.alex.mobile_operator.entity.user.enums.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class UserDaoTest {
    private final UserDao userDao = UserDao.getInstance();

    @Test
    void getAll() {
        User user1 = userDao.save(getUser(1, "name1", "123"));
        User user2 = userDao.save(getUser(2, "name2", "123"));
        User user3 = userDao.save(getUser(3, "name3", "123"));

        List<User> actualResult = userDao.getAll();
        assertThat(actualResult).hasSize(3);

        List<Integer> usersIds = actualResult.stream()
                .map(User::getId)
                .toList();
        assertThat(usersIds).contains(user1.getId(), user2.getId(), user3.getId());
    }

    @Test
    void getByIdIfUserExists() {
        User user = userDao.save(getUser(1, "name1", "123"));

        Optional<User> actualResult = userDao.getById(1);
        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(user);
    }

    @Test
    void getByIdIfUserNotExists() {
        User user = userDao.save(getUser(1, "name", "123"));

        Optional<User> actualResult = userDao.getById(5);
        assertThat(actualResult).isEmpty();
    }

    @Test
    void deleteIfUserExist() {
        User user = userDao.save(getUser(1, "name", "123"));

        boolean actualResult = userDao.delete(1);
        assertThat(actualResult).isTrue();
        assertThat(userDao.getAll()).hasSize(0);
    }

    @Test
    void deleteIfUserNotExists() {
        User user = userDao.save(getUser(1, "name", "123"));

        boolean actualResult = userDao.delete(2);
        assertThat(actualResult).isFalse();
        assertThat(userDao.getAll()).hasSize(1);
    }

    @Test
    void updateIsUserExists() {
        User user = userDao.save(getUser(1, "name", "123"));
        user.getInfo().setName("Alex");

        boolean actualResult = userDao.update(user);
        assertThat(actualResult).isTrue();
        assertThat(userDao.getById(1).get()).isEqualTo(user);
    }

    @Test
    void updateIfUserNotExists() {
        User user = getUser(1, "name", "123");
        user.getInfo().setName("Max");

        boolean actualResult = userDao.update(user);
        assertThat(actualResult).isFalse();
    }

    @Test
    void saveIfUserNotExists() {
        User user = getUser(1, "name", "123");

        User actualResult = userDao.save(user);
        assertThat(actualResult).isNotNull();
    }

    @Test
    void saveIfUserExists() {
        User user = userDao.save(getUser(1, "name", "123"));

        User actualResult = userDao.save(user);
        assertThat(actualResult).isNull();
    }

    @Test
    void findByUsernameAndPasswordIfUserExists() {
        User user = userDao.save(getUser(1, "name", "123"));

        Optional<User> actualResult = userDao.findByUsernameAndPassword("name", "123");
        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(user);
    }

    @Test
    void findByUsernameAndPasswordIfPasswordIsWrong() {
        User user = userDao.save(getUser(1, "name", "123"));

        Optional<User> actualResult = userDao.findByUsernameAndPassword("name", "dummy");
        assertThat(actualResult).isEmpty();
    }

    @Test
    void findByUsernameAndPasswordIfUsernameIsWrong() {
        User user = userDao.save(getUser(1, "dummy", "123"));

        Optional<User> actualResult = userDao.findByUsernameAndPassword("name", "123");
        assertThat(actualResult).isEmpty();
    }


    @Test
    void findByUsernameAndPasswordIfUserNotExists() {
        User user = userDao.save(getUser(1, "name", "123"));

        Optional<User> actualResult = userDao.findByUsernameAndPassword("dummy", "dummy");
        assertThat(actualResult).isEmpty();
    }

    private User getUser(Integer id, String name, String password) {
        return User.builder()
                .id(id)
                .role(Role.CUSTOMER)
                .info(getInfo(id, name, password))
                .status(Status.ACTIVE)
                .build();
    }

    @AfterEach
    void cleanData() {
        userDao.getAll().clear();
    }

    private Info getInfo(Integer id, String name, String password) {
        return Info.builder()
                .id(id)
                .passportNo("MP123")
                .password(password)
                .name(name)
                .surname(name + "_surname")
                .birthday(LocalDate.of(2000, 12, 25))
                .build();
    }
}