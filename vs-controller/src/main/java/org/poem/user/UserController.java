package org.poem.user;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** @author poem */
@RestController
@RequestMapping(value = "/user")
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);


  /**
   * 用户查询.
   *
   * @return
   */
  @RequestMapping("/userList")
  @RequiresPermissions("userInfo:view") // 权限管理;
  public String userInfo() {
    return "userInfo";
  }

  /**
   * 用户添加;
   *
   * @return
   */
  @RequestMapping("/userAdd")
  @RequiresPermissions("userInfo:add") // 权限管理;
  public String userInfoAdd() {
    return "userInfoAdd";
  }

  /**
   * 用户删除;
   *
   * @return
   */
  @RequestMapping("/userDel")
  @RequiresPermissions("userInfo:del") // 权限管理;
  public String userDel() {
    return "userInfoDel";
  }
}
