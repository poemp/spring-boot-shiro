package org.poem.controller.login;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.subject.Subject;
import org.poem.Constant;
import org.poem.annotation.ShiroOauthodIgnore;
import org.poem.jwt.JwtHelper;
import org.poem.controller.user.UserInfoService;
import org.poem.vo.UserInfoVO;
import org.poem.result.ResultVO;
import org.poem.vo.login.LoginSuccessVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author poem
 */
@RestController
@ShiroOauthodIgnore
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserInfoService userInfoService;
    /**
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping(value = "/unauth")
    public ResultVO<LoginSuccessVo> loginIn(String username, String password) {
        if (StringUtils.isBlank(username)){
            return new ResultVO<>(-9999,null,"用户名不能为空");
        }
        if(StringUtils.isBlank(password)){
             return new ResultVO<>(-9999,null,"密码不能为空。");
        }
        UserInfoVO userInfoVO = userInfoService.findByUsername(username);
        if (userInfoVO == null){
            return new ResultVO<>(-9999,null,"用户名不存在。");
        }
        if(userInfoVO.getLocked() != null && userInfoVO.getLocked()){
            return new ResultVO<>(-9999,null,"账号被锁定。");
        }
        Map<String, Object> claims = new HashMap<>(0);
        claims.put(Constant.JWT_CLAIM_KEY, JSON.toJSONString(userInfoVO));
        String token = JwtHelper.createJWT(claims, Constant.JWT_TTL);

        LoginSuccessVo loginSuccessVo = new LoginSuccessVo();
        loginSuccessVo.setAuthorization(token);
        loginSuccessVo.setName(userInfoVO.getName());
        loginSuccessVo.setUserId(userInfoVO.getUserId());
        loginSuccessVo.setUserAccount(userInfoVO.getUserName());
        loginSuccessVo.setUserInfoVO(userInfoVO);
        return new ResultVO<>(0, loginSuccessVo, "login success");
    }
    /**
     * 登陆
     * @param username
     * @param password
     * @return
     */
    public ResultVO<LoginSuccessVo> unauth(String username, String password) {
        if (StringUtils.isEmpty(username) && StringUtils.isEmpty(password)) {
            return new ResultVO<>(-1, null, "重新登陆。");
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        try {
            subject.login(usernamePasswordToken);
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
        return loginSuccess();
    }


    /**
     * 登出
     *
     * @return
     */
    @RequestMapping(value = "/logoutSuccess")
    public ResultVO<LoginSuccessVo> logout() {
        return new ResultVO<>(0, null, "logout success");
    }


    /**
     * 登陆成功返回信息
     *
     * @return
     */
    @GetMapping("/loginSuccess")
    public ResultVO<LoginSuccessVo> loginSuccess() {
        UserInfoVO userInfoVO = (UserInfoVO) SecurityUtils.getSubject().getPrincipal();
        Map<String, Object> claims = new HashMap<>(0);
        claims.put(Constant.JWT_CLAIM_KEY, JSON.toJSONString(userInfoVO));
        String token = JwtHelper.createJWT(claims, Constant.JWT_TTL);
        LoginSuccessVo loginSuccessVo = new LoginSuccessVo();
        loginSuccessVo.setAuthorization(token);
        loginSuccessVo.setName(userInfoVO.getName());
        loginSuccessVo.setUserId(userInfoVO.getUserId());
        loginSuccessVo.setUserAccount(userInfoVO.getUserName());
        return new ResultVO<>(0, loginSuccessVo, "login success");
    }

    /**
     * 登出
     *
     * @return
     */
    @RequestMapping(value = "/resLogout")
    public ResultVO<String> resLogout() {
        return new ResultVO<>(999, null, "resLogout success");
    }

}
