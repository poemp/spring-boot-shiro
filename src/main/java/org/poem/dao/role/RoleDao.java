package org.poem.dao.role;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.poem.dao.base.impl.BaseDaoImpl;
import org.poem.jooq.tables.TSysRole;
import org.poem.jooq.tables.records.TSysRoleRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/** @author poem */
@Repository
public class RoleDao extends BaseDaoImpl<TSysRoleRecord, Long> {


    @Autowired
    private DSLContext dslContext;
  public RoleDao() {
    super(TSysRole.T_SYS_ROLE);
  }

  @Override
  public Configuration getConfiguration() {
    return dslContext.configuration();
  }
}
