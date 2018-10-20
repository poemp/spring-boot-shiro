package org.poem.vo;

import java.util.List;

/** @author poem */
public class UserInfoVO {

  /** 用户名称 */
  private String name;

  /** 账号 */
  private String userName;

  /** 密码 */
  private String password;

  /** 加密密码的盐 */
  private String salt;

  /** 用户状态,0:创建未认证（比如没有激活，没有输入验证码等等） */
  private String state;

  /**
   * 是否锁住
   */
  private Boolean locked;

  private List<SysRoleVO> sysRoles;

  public Boolean getLocked() {
    return locked;
  }

  public void setLocked(Boolean locked) {
    this.locked = locked;
  }

  public List<SysRoleVO> getSysRoles() {
    return sysRoles;
  }

  public void setSysRoles(List<SysRoleVO> sysRoles) {
    this.sysRoles = sysRoles;
  }

  public String getCredentialsSalt() {
    return this.userName + this.state;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  @Override
  public String toString() {
    return "UserInfoVO{" +
            "name='" + name + '\'' +
            ", userName='" + userName + '\'' +
            ", password='" + password + '\'' +
            ", salt='" + salt + '\'' +
            ", state=" + state +
            ", sysRoles=" + sysRoles +
            '}';
  }
}
