package org.poem.service.user;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.poem.RequestUtil;
import org.poem.result.ResultVO;
import org.poem.vo.UserInfoVO;
import org.poem.vo.login.LoginSuccessVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/** @author poem */
@RestController
@RequestMapping(value = "/v1/user")
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);


  /**
   * 用户查询.
   *
   * @return
   */
  @RequestMapping("/userList")
  @RequiresPermissions("userInfo:view") // 权限管理;
  public ResultVO<LoginSuccessVo> userInfo(HttpServletRequest request) {
    LoginSuccessVo loginSuccessVo = RequestUtil.getLoginSuccessVo(request);
    if(loginSuccessVo != null){
      loginSuccessVo.setUserInfoVO(JSONObject.parseObject(loginSuccessVo.getAuthorization(), UserInfoVO.class));
      loginSuccessVo.setAuthorization(null);
    }
    return new ResultVO<>(0,loginSuccessVo);
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
