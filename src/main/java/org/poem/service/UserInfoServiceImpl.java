package org.poem.service;

import org.jooq.Record;
import org.poem.api.UserInfoService;
import org.poem.dao.permission.SysPermissionDao;
import org.poem.dao.role.SysRoleDao;
import org.poem.dao.user.UserDao;
import org.poem.jooq.tables.TSysPermission;
import org.poem.jooq.tables.TSysRole;
import org.poem.jooq.tables.records.TUserInfoRecord;
import org.poem.vo.SysPermissionVO;
import org.poem.vo.SysRoleVO;
import org.poem.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/** @author poem */
@Service
public class UserInfoServiceImpl implements UserInfoService {

  @Autowired private UserDao userDao;

  @Autowired private SysRoleDao sysRoleDao;

  @Autowired private SysPermissionDao sysPermissionDao;

  /**
   * 查询用户
   *
   * @param userName
   * @return
   */
  @Override
  public UserInfoVO findByUsername(String userName) {
    TUserInfoRecord tUserInfoRecord = this.userDao.findByUsername(userName);
    if (tUserInfoRecord == null) {
      return null;
    }
    UserInfoVO userInfoVO = new UserInfoVO();
    userInfoVO.setName(tUserInfoRecord.getName());
    userInfoVO.setUserName(tUserInfoRecord.getUsername());
    userInfoVO.setPassword(tUserInfoRecord.getPassword());
    userInfoVO.setSalt(tUserInfoRecord.getSalt());
    userInfoVO.setState(tUserInfoRecord.getState());
    userInfoVO.setLocked("1".equals(tUserInfoRecord.getState()));
    userInfoVO.setSysRoles(sysRoles(tUserInfoRecord.getId()));
    return userInfoVO;
  }

  /**
   * @param userId
   * @return
   */
  private List<SysRoleVO> sysRoles(Long userId) {
    Record[] records = this.sysRoleDao.getSysRoleRecord(userId);
    List<SysRoleVO> sysRoleVOS = new ArrayList<>();
    for (Record record : records) {
      SysRoleVO sysRoleVO = new SysRoleVO();
      sysRoleVO.setId(record.get(TSysRole.T_SYS_ROLE.ID));
      sysRoleVO.setRole(record.get(TSysRole.T_SYS_ROLE.ROLE));
      sysRoleVO.setAvailable(record.get(TSysRole.T_SYS_ROLE.AVAILABLE) == 1);
      sysRoleVO.setDescription(record.get(TSysRole.T_SYS_ROLE.DESCRIPTION));
      sysRoleVO.setSysPermissions(this.sysPermissionVO(record.get(TSysRole.T_SYS_ROLE.ID)));
      sysRoleVOS.add(sysRoleVO);
    }
    return sysRoleVOS;
  }

  /**
   * @param roleId
   * @return
   */
  private List<SysPermissionVO> sysPermissionVO(Long roleId) {
    Record[] records = this.sysPermissionDao.getBySysRoleId(roleId);
    List<SysPermissionVO> sysPermissionVOS = new ArrayList<>();
    for (Record record : records) {
      SysPermissionVO sysPermissionVO = new SysPermissionVO();
      sysPermissionVO.setId(record.get(TSysPermission.T_SYS_PERMISSION.ID));
      sysPermissionVO.setName(record.get(TSysPermission.T_SYS_PERMISSION.NAME));
      sysPermissionVO.setPermission(record.get(TSysPermission.T_SYS_PERMISSION.PERMISSION));
      sysPermissionVO.setAvailable(record.get(TSysPermission.T_SYS_PERMISSION.AVAILABLE));
      sysPermissionVO.setResourceType(record.get(TSysPermission.T_SYS_PERMISSION.RESOURCE_TYPE));
      sysPermissionVOS.add(sysPermissionVO);
    }
    return sysPermissionVOS;
  }
}
