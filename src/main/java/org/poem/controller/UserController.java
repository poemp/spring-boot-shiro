package org.poem.controller;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @author poem */
@RestController
@RequestMapping(value = "/user")
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);
  /**
   * @param request
   * @param map
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/login",method = RequestMethod.POST)
  public String login(HttpServletRequest request, Map<String, Object> map) throws Exception {
    logger.info("HomeController.login()");
    // 登录失败从request中获取shiro处理的异常信息。
    // shiroLoginFailure:就是shiro异常类的全类名.
    String exception = (String) request.getAttribute("shiroLoginFailure");
    logger.info("exception=" + exception);
    String msg = "";
    if (exception != null) {
      if (UnknownAccountException.class.getName().equals(exception)) {
        logger.info("UnknownAccountException -- > 账号不存在：");
        msg = "UnknownAccountException -- > 账号不存在：";
      } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
        logger.info("IncorrectCredentialsException -- > 密码不正确：");
        msg = "IncorrectCredentialsException -- > 密码不正确：";
      } else if ("kaptchaValidateFailed".equals(exception)) {
        logger.info("kaptchaValidateFailed -- > 验证码错误");
        msg = "kaptchaValidateFailed -- > 验证码错误";
      } else {
        msg = "else >> " + exception;
        logger.info("else -- >" + exception);
      }
    }
    map.put("msg", msg);
    // 此方法不处理登录成功,由shiro进行处理
    return "/login";
  }

  @RequestMapping("/403")
  public String unauthorizedRole() {
    logger.info("------没有权限-------");
    return "403";
  }
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
