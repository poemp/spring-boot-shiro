package org.poem.dao.role;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Table;
import org.poem.dao.base.impl.BaseDaoImpl;
import org.poem.jooq.tables.TSysRole;
import org.poem.jooq.tables.TSysUserRole;
import org.poem.jooq.tables.records.TSysRoleRecord;
import org.poem.jooq.tables.records.TSysUserRoleRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/** @author poem */
@Repository
public class SysRoleDao extends BaseDaoImpl<TSysRoleRecord, Long> {

  @Autowired private DSLContext dslContext;

  public SysRoleDao() {
    super(TSysRole.T_SYS_ROLE);
  }

  @Override
  public Configuration getConfiguration() {
    return dslContext.configuration();
  }

  /**
   * 查询人员的角色
   * @param userId 用户
   * @return
   */
  public Record[] getSysRoleRecord(Long userId) {
    return  dslContext
        .select()
        .from(TSysRole.T_SYS_ROLE)
        .join(TSysUserRole.T_SYS_USER_ROLE)
        .on(TSysRole.T_SYS_ROLE.ID.eq(TSysUserRole.T_SYS_USER_ROLE.ROLE_ID))
        .where(TSysUserRole.T_SYS_USER_ROLE.USER_ID.eq(userId))
        .fetchArray();
  }
}
