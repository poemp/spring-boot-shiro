package org.poem;

import java.util.List;


/**
 * @author poem
 */
public class SysRoleVO {

  /** 编号 */
  private Long id;

  /** 角色标识程序中判断使用,如"admin",这个是唯一的: */
  private String role;

  /** 角色描述,UI界面显示使用 */
  private String description;

  /** 是否可用,如果不可用将不会添加给用户 */
  private Boolean available = Boolean.FALSE;

  private List<SysPermissionVO> sysPermissions;

  public List<SysPermissionVO> getSysPermissions() {
    return sysPermissions;
  }

  public void setSysPermissions(List<SysPermissionVO> sysPermissions) {
    this.sysPermissions = sysPermissions;
  }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "SysRoleVO{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", description='" + description + '\'' +
                ", available=" + available +
                ", sysPermissions=" + sysPermissions +
                '}';
    }
}
