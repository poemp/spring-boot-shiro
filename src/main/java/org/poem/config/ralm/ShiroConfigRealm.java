package org.poem.config.ralm;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.poem.api.UserInfoService;
import org.poem.vo.SysPermissionVO;
import org.poem.vo.SysRoleVO;
import org.poem.vo.UserInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** @author poem */
public class ShiroConfigRealm extends AuthorizingRealm {

  @Autowired private UserInfoService userInfoService;
  /** */
  private static final Logger logger = LoggerFactory.getLogger(ShiroConfigRealm.class);
  /**
   * 认证.登录
   *
   * @param principals
   * @return
   */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    logger.info("权限配置-->ShiroConfigRealm.doGetAuthorizationInfo()");
    UserInfoVO user = (UserInfoVO) principals.fromRealm(this.getClass().getName()).iterator().next();//获取session中的用户
    List<SysRoleVO> roleid = user.getSysRoles();
    logger.info("roleid=" + roleid);

    SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
    UserInfoVO userInfoVO = (UserInfoVO) principals.getPrimaryPrincipal();
    for (SysRoleVO role : userInfoVO.getSysRoles()) {
      authorizationInfo.addRole(role.getRole());
      for (SysPermissionVO p : role.getSysPermissions()) {
        authorizationInfo.addStringPermission(p.getPermission());
      }
    }
    return authorizationInfo;
  }

  /**
   * 授权
   *
   * @param token
   * @return
   * @throws AuthenticationException
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)  throws AuthenticationException {
    logger.info("ShiroConfigRealm.doGetAuthenticationInfo()");
    UsernamePasswordToken utoken = (UsernamePasswordToken) token;//获取用户输入的token
    // 获取用户的输入的账号.
    String username = (String) token.getPrincipal();
    logger.info("username=" + utoken.getUsername());
    logger.info(("password=" + utoken.getPassword());
    logger.info(token.getCredentials() + "");


      //处理session
      SessionsSecurityManager securityManager = (SessionsSecurityManager) SecurityUtils.getSecurityManager();
      DefaultSessionManager sessionManager = (DefaultSessionManager) securityManager.getSessionManager();
      Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();//获取当前已登录的用户session列表
      for (Session session : sessions) {
          //清除该用户以前登录时保存的session
          //如果和当前session是同一个session，则不剔除
          if (SecurityUtils.getSubject().getSession().getId().equals(session.getId())){
              break;
          }
          UserInfoVO user = (UserInfoVO) (session.getAttribute("user"));
          if (user != null) {
              String userName = user.getUserName();
              if (username.equals(userName)) {
                  logger.info(username + "已登录，剔除中...");
                  sessionManager.getSessionDAO().delete(session);
              }
          }
      }

    // 通过username从数据库中查找 User对象，如果找到，没找到.
    // 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
    UserInfoVO userInfoVO = userInfoService.findByUsername(username);
    logger.info("----->>userInfoVO=" + userInfoVO);
    if (userInfoVO == null) {
      return null;
    }
    SimpleAuthenticationInfo authenticationInfo =
        new SimpleAuthenticationInfo(
            userInfoVO,
            userInfoVO.getPassword(),
            ByteSource.Util.bytes(userInfoVO.getCredentialsSalt()),
            getName());
    return authenticationInfo;
  }
}
