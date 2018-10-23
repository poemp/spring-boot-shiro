package org.poem.base;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.SortField;
import org.jooq.UpdatableRecord;
import org.jooq.exception.DataAccessException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BaseDao<R extends UpdatableRecord, ID extends Number> {

    /**
     *
     * @param object
     * @throws DataAccessException
     */
    void insert(R object) throws DataAccessException;

    /**
     *
     * @param objects
     * @throws DataAccessException
     */
    void insert(Collection<R> objects) throws DataAccessException;

    /**
     *
     * @param object
     * @throws DataAccessException
     */
    void update(R object) throws DataAccessException;

    /**
     *
     * @param object
     * @throws DataAccessException
     */
    void delete(R object) throws DataAccessException;

    /**
     *
     * @param objects
     * @throws DataAccessException
     */
    void delete(Collection<R> objects) throws DataAccessException;

    /**
     *
     * @param ids
     * @throws DataAccessException
     */
    void deleteById(Collection<ID> ids) throws DataAccessException;


    /**
     * 删除数据
     * @param id
     * @throws DataAccessException
     */
    void deleteById(ID id) throws DataAccessException;


    /**
     *
     * @param conditions
     */
    public void deleteByConditions(Collection<Condition> conditions);
    /**
     *
     * @param id
     * @return
     * @throws DataAccessException
     */
    R findById(ID id) throws DataAccessException;

    /**
     *
     * @param field
     * @param values
     * @param <Z>
     * @return
     * @throws DataAccessException
     */
    <Z> List<R> fetch(Field<Z> field, Z... values) throws DataAccessException;

    /**
     *
     * @param field
     * @param values
     * @param <Z>
     * @return
     * @throws DataAccessException
     */
    <Z> List<R> fetch(Field<Z> field, Collection<Z> values) throws DataAccessException;

    /**
     *
     * @param field
     * @param value
     * @param <Z>
     * @return
     * @throws DataAccessException
     */
    <Z> R fetchOne(Field<Z> field, Z value) throws DataAccessException;



    /**
     *
     * @param conditions
     * @param sortFields
     * @return
     */
    public R[] findByConditions(List<Condition> conditions, Collection<SortField<?>> sortFields);

    /**
     *
     * @param conditions
     * @return
     */
    public R[] findByConditions(List<Condition> conditions);


    /**
     *
     * @param condition
     * @return
     */
    public R[] findByCondition(Condition condition);
    /**
     *
     * @param condition
     * @param sortFields
     * @return
     */
    public R[] findByCondition(Condition condition, Collection<SortField<?>> sortFields);



    /**
     *
     * @param conditions
     */
    public void deleteByConditions(Condition... conditions);
    /**
     *
     * @param ids
     * @return
     * @throws DataAccessException
     */
    public List<R> findByIdList(Collection<ID> ids) throws DataAccessException;


    /**
     *
     * @param field
     * @param value
     * @param <Z>
     * @return
     * @throws DataAccessException
     */
    public <Z> Optional<R> fetchOptional(Field<Z> field, Z value) throws DataAccessException;


    /**
     * 前部数据
     * @return
     * @throws DataAccessException
     */
    public List<R> findAll()  throws DataAccessException ;
}
