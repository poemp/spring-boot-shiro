package org.poem.base.impl;


import org.jooq.*;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;
import org.poem.base.BaseDao;


import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


public abstract class BaseDaoImpl<R extends UpdatableRecord<R>, ID extends Number> implements BaseDao<R, ID> {

    private Table<R> table;


    public BaseDaoImpl(Table<R> table) {
        this.table = table;
    }

    @PostConstruct
    public abstract Configuration getConfiguration();

    /**
     * @param object
     * @throws DataAccessException
     */
    @Override
    public void insert(R object) throws DataAccessException {
        DSL.using(getConfiguration()).insertInto(object.getTable()).set(object).execute();
    }

    /**
     * @param objects
     * @throws DataAccessException
     */
    @Override
    public void insert(Collection<R> objects) throws DataAccessException {
        DSL.using(getConfiguration()).batchInsert(objects).execute();

    }

    /**
     * @param object
     * @throws DataAccessException
     */
    @Override
    public void update(R object) throws DataAccessException {
        DSL.using(getConfiguration()).executeUpdate(object);
    }


    /**
     * @param object
     * @throws DataAccessException
     */
    @Override
    public void delete(R object) throws DataAccessException {
        DSL.using(getConfiguration()).executeDelete(object);
    }

    /**
     * @param objects
     * @throws DataAccessException
     */
    @Override
    public void delete(Collection<R> objects) throws DataAccessException {
        DSL.using(getConfiguration()).batchDelete(objects).execute();
    }

    @Override
    public void deleteById(Collection<ID> ids) throws DataAccessException {
        Field<ID> primaryField = getPrimaryKey();
        if (primaryField != null && ids.size() > 0) {
            DSL.using(getConfiguration()).delete(this.table).where(primaryField.in(ids)).execute();
        }
    }

    /**
     * 删除数据
     *
     * @param id
     * @throws DataAccessException
     */
    @Override
    public void deleteById(ID id) throws DataAccessException {
        Field<ID> primaryKey = getPrimaryKey();
        if (primaryKey != null && null != id) {
            DSL.using(getConfiguration()).delete(this.table).where(primaryKey.eq(id)).execute();
        }
    }

    @Override
    public R findById(ID id) throws DataAccessException {
        Field<ID> primaryField = getPrimaryKey();
        if (primaryField != null) {
            return DSL.using(getConfiguration()).selectFrom(this.table).where(primaryField.eq(id)).fetchAny();
        }
        return null;
    }

    @Override
    public <Z> List<R> fetch(Field<Z> field, Z... values) throws DataAccessException {
        return DSL.using(getConfiguration()).selectFrom(this.table).where(new Condition[]{field.in(values)}).fetch();
    }

    public List<R> findByIds(ID... ids) throws DataAccessException {
        Field<ID> primaryField = getPrimaryKey();
        if (primaryField != null) {
            return DSL.using(getConfiguration()).selectFrom(this.table).where(primaryField.in(ids)).fetch();
        }
        return new ArrayList<>();
    }

    @Override
    public List<R> findByIdList(Collection<ID> ids) throws DataAccessException {
        Field<ID> primaryField = getPrimaryKey();
        if (primaryField != null) {
            return DSL.using(getConfiguration()).selectFrom(this.table).where(primaryField.in(ids)).fetch();
        }
        return new ArrayList<>();
    }

    @Override
    public <Z> List<R> fetch(Field<Z> field, Collection<Z> values) throws DataAccessException {
        return DSL.using(getConfiguration()).selectFrom(this.table).where(new Condition[]{field.in(values)}).fetch();
    }

    @Override
    public <Z> R fetchOne(Field<Z> field, Z value) throws DataAccessException {
        return DSL.using(getConfiguration()).selectFrom(this.table).where(new Condition[]{field.eq(value)}).fetchOne();
    }

    @Override
    public <Z> Optional<R> fetchOptional(Field<Z> field, Z value) throws DataAccessException {
        return Optional.ofNullable(this.fetchOne(field, value));
    }



    private Field<ID> getPrimaryKey() {
        UniqueKey key = this.table.getPrimaryKey();
        if (key == null || key.getFields() == null || key.getFields().isEmpty()) {
            return null;
        }
        List<TableField<R, ID>> fields = key.getFields();
        return fields.get(0);
    }

    @Override
    public R[] findByConditions(List<Condition> conditions, Collection<SortField<?>> sortFields) {
        return DSL.using(getConfiguration()).selectFrom(this.table).where(conditions).orderBy(sortFields).fetchArray();
    }

    @Override
    public R[] findByConditions(List<Condition> conditions) {
        return DSL.using(getConfiguration()).selectFrom(this.table).where(conditions).fetchArray();
    }

    @Override
    public R[] findByCondition(Condition condition) {
        return DSL.using(getConfiguration()).selectFrom(this.table).where(condition).fetchArray();
    }

    @Override
    public R[] findByCondition(Condition condition, Collection<SortField<?>> sortFields) {
        return DSL.using(getConfiguration()).selectFrom(this.table).where(condition).orderBy(sortFields).fetchArray();
    }

    @Override
    public void deleteByConditions(Condition... conditions) {
        DSL.using(getConfiguration()).deleteFrom(this.table).where(conditions).execute();
    }

    @Override
    public void deleteByConditions(Collection<Condition> conditions) {
        DSL.using(getConfiguration()).deleteFrom(this.table).where(conditions).execute();
    }

    @Override
    public List<R> findAll(){
       return DSL.using(getConfiguration()).selectFrom(this.table).fetch();
    }
}
