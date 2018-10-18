package org.poem.config.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.poem.config.logout.ShiroLogoutFilter;
import org.poem.config.ralm.ShiroConfigRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/** @author poem */
@Configuration
public class ShiroConfiguration {

  /** 日志 */
  private static final Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);

  /**
   * @param securityManager
   * @return
   */
  @Bean
  public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
    logger.info("shiroconfiguration-shirFilter");
    ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
    shiroFilterFactoryBean.setSecurityManager(securityManager);
    // 拦截器
    Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
    // 配置不会拦截的链接
    filterChainDefinitionMap.put("/static/**", "anon");
    // 配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
    filterChainDefinitionMap.put("/logout", "logout");
    // <!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
    // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
    filterChainDefinitionMap.put("/**", "authc");
    // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
    shiroFilterFactoryBean.setLoginUrl("/login");
    // 登录成功后要跳转的链接
    shiroFilterFactoryBean.setSuccessUrl("/index");

    // 未授权界面;
    shiroFilterFactoryBean.setUnauthorizedUrl("/403");
    shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    return shiroFilterFactoryBean;
  }

  /** @return */
  @Bean
  public ShiroConfigRealm shiroConfigRealm() {
    ShiroConfigRealm myShiroRealm = new ShiroConfigRealm();
    return myShiroRealm;
  }

  /** @return */
  @Bean
  public SecurityManager securityManager() {
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    securityManager.setRealm(shiroConfigRealm());
    return securityManager;
  }

  /** @return */
  @Bean
  public ShiroLogoutFilter getShiroLogoutFilter() {
    ShiroLogoutFilter shiroLogoutFilter = new ShiroLogoutFilter();
    shiroLogoutFilter.setRedirectUrl("/logout");
    return shiroLogoutFilter;
  }
}
