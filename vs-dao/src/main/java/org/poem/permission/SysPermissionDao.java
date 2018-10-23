package org.poem.permission;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Table;
import org.poem.base.impl.BaseDaoImpl;
import org.poem.jooq.tables.TSysPermission;
import org.poem.jooq.tables.TSysRolePermission;
import org.poem.jooq.tables.records.TSysPermissionRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author poem
 */
@Repository
public class SysPermissionDao extends BaseDaoImpl<TSysPermissionRecord, Long> {

    @Autowired
    private DSLContext dslContext;

    public SysPermissionDao() {
        super(TSysPermission.T_SYS_PERMISSION);
    }


    @Override
    public Configuration getConfiguration() {
        return dslContext.configuration();
    }


    /**
     *
     * @param roleId
     * @return
     */
    public Record[] getBySysRoleId(Long roleId){
        return  dslContext.select()
                .from(TSysPermission.T_SYS_PERMISSION)
                .join(TSysRolePermission.T_SYS_ROLE_PERMISSION)
                .on(TSysRolePermission.T_SYS_ROLE_PERMISSION.PERMISSION_ID.eq(TSysPermission.T_SYS_PERMISSION.ID))
                .where(TSysRolePermission.T_SYS_ROLE_PERMISSION.ROLE_ID.eq(roleId))
                .fetchArray();
    }
}
