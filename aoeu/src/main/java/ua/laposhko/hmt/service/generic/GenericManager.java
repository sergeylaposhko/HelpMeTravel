package ua.laposhko.hmt.service.generic;

import java.util.List;

/**
 * Created by Вадим on 02.05.2015.
 */
public interface GenericManager<T> {

    public T findById(int id);

    public List<T> findAll();

    public void save(T entity);

    public void update(T entity);

    public void delete(T entity);

}
