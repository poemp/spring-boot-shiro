package org.poem.controller;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author poem
 */
@RestController
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
//    /**
//     *
//     * @param req
//     * @param model
//     * @return
//     */
//    @RequestMapping(value = "/login")
//    public String showLoginForm(HttpServletRequest req, Model model) {
//        String exceptionClassName = (String)req.getAttribute("shiroLoginFailure");
//        String error = null;
//        if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
//            error = "用户名/密码错误";
//        } else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
//            error = "用户名/密码错误";
//        } else if(exceptionClassName != null) {
//            error = "其他错误：" + exceptionClassName;
//        }
//        model.addAttribute("error", error);
//        logger.info("error:  "+error);
//
//        // 此方法不处理登陆成功（认证成功），shiro认证成功会自动跳转到上一个请求路径
//        // 登陆失败还到login页面
//        return "user/login";
//    }
}
