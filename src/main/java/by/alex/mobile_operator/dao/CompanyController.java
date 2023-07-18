package by.alex.mobile_operator.dao;

import java.util.List;
import java.util.Optional;

public interface CompanyController<E, K> {
    List<E> getAll();

    Optional<E> getById(K id);

    void delete(K id);

    void update(E entity);

    E save(E entity);
}
