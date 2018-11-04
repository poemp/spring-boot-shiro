package org.poem.service.user;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.poem.base.impl.BaseDaoImpl;
import org.poem.jooq.tables.TUserInfo;
import org.poem.jooq.tables.records.TUserInfoRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/** @author poem */
@Repository
public class UserDao extends BaseDaoImpl<TUserInfoRecord, Long> {

  @Autowired private DSLContext dslContext;


  public UserDao() {
    super(TUserInfo.T_USER_INFO);
  }

  @Override
  public Configuration getConfiguration() {
    return dslContext.configuration();
  }

  /**
   * 获取数据
   * @param userName
   * @return
   */
  public TUserInfoRecord findByUsername(String userName) {
    return dslContext
        .selectFrom(TUserInfo.T_USER_INFO)
        .where(TUserInfo.T_USER_INFO.USERNAME.eq(userName))
        .fetchAny();
  }
}
