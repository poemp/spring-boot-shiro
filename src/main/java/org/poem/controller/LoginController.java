package org.poem.controller;

import com.jcraft.jsch.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.jooq.Result;
import org.poem.vo.UserInfoVO;
import org.poem.vo.result.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
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
   * @param username
   * @param password
   * @return
   */
  @RequestMapping(value = "/unauth")
  public ResultVO<UserInfoVO> unauth(String username, String password) {
    if (StringUtils.isEmpty(username) && StringUtils.isEmpty(password)){
      return new ResultVO<>(-1, null,"重新登陆。");
    }
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
        return new ResultVO<>(2, null, "账号不存在");
      } else if (ex instanceof ExcessiveAttemptsException) {
        logger.error("认证次数超过限制");
        return new ResultVO<>(3, null, "认证次数超过限制");
      } else if (ex instanceof DisabledAccountException) {
        if (ex instanceof LockedAccountException) {
          logger.error("账号被锁定");
          return new ResultVO<>(4, null, "账号被锁定");
        } else {
          logger.error("禁用的账号");
          return new ResultVO<>(5, null, "禁用的账号");
        }
      } else if (ex instanceof UnsupportedTokenException) {
        logger.error("使用了不支持的Token");
        return new ResultVO<>(6, null, "使用了不支持的Token");
      } else if (ex instanceof IncorrectCredentialsException) {
        logger.error("密码错误");
        return new ResultVO<>(7, null, "密码错误");
      }
    }
    UserInfoVO userInfoVO = (UserInfoVO) SecurityUtils.getSubject().getPrincipal();
    return new ResultVO<>(0, userInfoVO, "login success");
  }

}
