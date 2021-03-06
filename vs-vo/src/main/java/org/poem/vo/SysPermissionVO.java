package org.poem.vo;

/** @author poem */
public class SysPermissionVO {

  /** //主键. */
  private Long id;
  /** 名称. */
  private String name;

  /** 资源类型，[menu|button] */
  private String resourceType;

  /** 资源路径. */
  private String url;

  /** 权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view */
  private String permission;

  /** 父编号 */
  private Long parentId;

  /** 父编号列表 */
  private String parentIds;

  /** 是否可用 */
  private String available;



  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getResourceType() {
    return resourceType;
  }

  public void setResourceType(String resourceType) {
    this.resourceType = resourceType;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getPermission() {
    return permission;
  }

  public void setPermission(String permission) {
    this.permission = permission;
  }

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public String getParentIds() {
    return parentIds;
  }

  public void setParentIds(String parentIds) {
    this.parentIds = parentIds;
  }

  public String getAvailable() {
    return available;
  }

  public void setAvailable(String available) {
    this.available = available;
  }


  @Override
  public String toString() {
    return "SysPermissionVO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", resourceType='" + resourceType + '\'' +
            ", url='" + url + '\'' +
            ", permission='" + permission + '\'' +
            ", parentId=" + parentId +
            ", parentIds='" + parentIds + '\'' +
            ", available=" + available +
            '}';
  }
}
