package org.poem.config.ralm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.poem.api.UserInfoService;
import org.poem.vo.SysPermissionVO;
import org.poem.vo.SysRoleVO;
import org.poem.vo.UserInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author poem
 */
public class ShiroConfigRealm extends AuthorizingRealm {


    @Autowired
    private UserInfoService userInfoService ;
    /**
     *
     */
    private static final Logger logger = LoggerFactory.getLogger(ShiroConfigRealm.class);
    /**
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("权限配置-->ShiroConfigRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        UserInfoVO userInfoVO = (UserInfoVO)principals.getPrimaryPrincipal();
        for(SysRoleVO role: userInfoVO.getSysRoles()){
            authorizationInfo.addRole(role.getRole());
            for(SysPermissionVO p:role.getSysPermissions()){
                authorizationInfo.addStringPermission(p.getPermission());
            }
        }
        return authorizationInfo;
    }


    /**
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        logger.info("ShiroConfigRealm.doGetAuthenticationInfo()");
        //获取用户的输入的账号.
        String username = (String)token.getPrincipal();
        logger.info(token.getCredentials()+"");
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        UserInfoVO userInfoVO = userInfoService.findByUsername(username);
        logger.info("----->>userInfoVO="+ userInfoVO);
        if(userInfoVO == null){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfoVO,
                userInfoVO.getPassword(),
                ByteSource.Util.bytes(userInfoVO.getCredentialsSalt()),
                getName()
        );
        return authenticationInfo;
    }
}
