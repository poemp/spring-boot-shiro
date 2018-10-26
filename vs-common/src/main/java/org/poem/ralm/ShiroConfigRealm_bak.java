package org.poem.ralm;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.poem.Constant;
import org.poem.filter.token.OAuthToken;
import org.poem.jwt.JwtHelper;
import org.poem.user.UserInfoService;
import org.poem.vo.SysPermissionVO;
import org.poem.vo.SysRoleVO;
import org.poem.vo.UserInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author poem
 */
public class ShiroConfigRealm_bak extends AuthorizingRealm {

    @Autowired
    private UserInfoService userInfoService;

    /** */
    private static final Logger logger = LoggerFactory.getLogger(ShiroConfigRealm_bak.class);


    /**
     * 是否支持
     *
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuthToken;
    }

    /**
     * 认证.登录
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("权限配置-->ShiroConfigRealm.doGetAuthorizationInfo()");
        // 获取session中的用户
        UserInfoVO user = (UserInfoVO) principals.fromRealm(this.getClass().getName()).iterator().next();
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
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        OAuthToken oAuthToken = (OAuthToken) token;
        String tokenss = (String) oAuthToken.getCredentials();


        UserInfoVO userInfoVO = null;
        try {
            Map<String, Object> map = JwtHelper.extractInfo(tokenss);
            String s = (String) map.get(Constant.JWT_CLAIM_KEY);
            userInfoVO = JSONObject.parseObject(s, UserInfoVO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("----->>userInfoVO=" + userInfoVO);
        //会抛出账号不存在的异常
        if (userInfoVO == null) {
            return null;
        }
        //锁住了
        if (userInfoVO.getLocked() != null && userInfoVO.getLocked()) {
            throw new LockedAccountException("locked this account");
        }
        SimpleAuthenticationInfo authenticationInfo =
                new SimpleAuthenticationInfo(
                        userInfoVO,
                        userInfoVO.getPassword(),
                        ByteSource.Util.bytes(userInfoVO.getCredentialsSalt()),
                        getName());
        // 放入shiro.调用CredentialsMatcher检验密码
        //会抛出密码错误
        return authenticationInfo;
    }
}
