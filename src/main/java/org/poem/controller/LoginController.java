package org.poem.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.poem.vo.UserInfoVO;
import org.poem.vo.result.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/** @author poem */
@RestController
public class LoginController {

  private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
  /**
   * @param req
   * @param model
   * @return
   */
  //    @RequestMapping(value = "/login")
  public String showLoginForm(HttpServletRequest req, Model model) {
    String exceptionClassName = (String) req.getAttribute("shiroLoginFailure");
    String error = null;
    if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
      error = "用户名/密码错误";
    } else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
      error = "用户名/密码错误";
    } else if (exceptionClassName != null) {
      error = "其他错误：" + exceptionClassName;
    }
    model.addAttribute("error", error);
    logger.info("error:  " + error);

    // 此方法不处理登陆成功（认证成功），shiro认证成功会自动跳转到上一个请求路径
    // 登陆失败还到login页面
    return "user/login";
  }

  /**
   * @param username
   * @param password
   * @return
   */
  @RequestMapping(value = "/unauth")
  public ResultVO<UserInfoVO> unauth(String username, String password) {
    Subject subject = SecurityUtils.getSubject();
    UsernamePasswordToken token = new UsernamePasswordToken(username, password);
    try {
      subject.login(token);
    } catch (AuthenticationException ex) {
      if (ex instanceof ConcurrentAccessException) {
        logger.error("并发访问异常（多个用户同时登录时抛出）");
        return new ResultVO<>(1, null, "并发访问异常（多个用户同时登录时抛出）");
      } else if (ex instanceof UnknownAccountException) {
        logger.error("账号不存在");
        return new ResultVO<>(1, null, "账号不存在");
      } else if (ex instanceof ExcessiveAttemptsException) {
        logger.error("认证次数超过限制");
        return new ResultVO<>(1, null, "认证次数超过限制");
      } else if (ex instanceof DisabledAccountException) {
        if (ex instanceof LockedAccountException) {
          logger.error("账号被锁定");
          return new ResultVO<>(1, null, "账号被锁定");
        } else {
          logger.error("禁用的账号");
          return new ResultVO<>(1, null, "禁用的账号");
        }
      } else if (ex instanceof UnsupportedTokenException) {
        logger.error("使用了不支持的Token");
        return new ResultVO<>(1, null, "使用了不支持的Token");
      } else if (ex instanceof IncorrectCredentialsException) {
        logger.error("密码错误");
        return new ResultVO<>(1, null, "密码错误");
      }
    }
    UserInfoVO userInfoVO = (UserInfoVO) SecurityUtils.getSubject().getPrincipal();
    return new ResultVO<>(0, userInfoVO, "login success");
  }
}
