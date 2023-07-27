package by.alex.mobile_operator.dao;

import java.util.List;
import java.util.Optional;

public interface DefaultDao<E, K> {
    List<E> getAll();

    Optional<E> getById(K id);

    boolean delete(K id);

    boolean update(E entity);

    E save(E entity);
}
