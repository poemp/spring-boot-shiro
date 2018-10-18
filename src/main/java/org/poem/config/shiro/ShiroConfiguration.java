package org.poem.config.shiro;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.poem.config.credentials.CredentialsMatcher;
import org.poem.config.ralm.ShiroConfigRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

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
    ShiroConfigRealm shiroConfigRealm = new ShiroConfigRealm();
    shiroConfigRealm.setCredentialsMatcher(credentialsMatcher());
    return shiroConfigRealm;
  }

  /** @return */
  @Bean
  public SecurityManager securityManager() {
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    securityManager.setRealm(shiroConfigRealm());
    securityManager.setSessionManager(sessionManager());
    // <!-- 用户授权/认证信息Cache, 采用EhCache 缓存 -->
    securityManager.setCacheManager(ehCacheManager());
    // 注入记住我管理器;
    securityManager.setRememberMeManager(rememberMeManager());
    return securityManager;
  }

  /**
   * LifecycleBeanPostProcessor，这是个DestructionAwareBeanPostProcessor的子类，
   * 负责org.apache.shiro.util.Initializable类型bean的生命周期的，初始化和销毁。
   * 主要是AuthorizingRealm类的子类，以及EhCacheManager类。
   *
   * @return
   */
  @Bean(name = "lifecycleBeanPostProcessor")
  public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
    logger.info("ShiroConfiguration.getLifecycleBeanPostProcessor()");
    return new LifecycleBeanPostProcessor();
  }

  /**
   * EhCacheManager，缓存管理，用户登陆成功后，把用户信息和权限信息缓存起来，
   * 然后每次用户请求时，放入用户的session中，如果不设置这个bean，每个请求都会查询一次数据库。
   *
   * @return
   */
  @Bean(name = "ehCacheManager")
  @DependsOn("lifecycleBeanPostProcessor")
  public EhCacheManager ehCacheManager() {
    logger.info("ShiroConfiguration.ehCacheManager()");
    EhCacheManager cacheManager = new EhCacheManager();
    cacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
    return cacheManager;
  }
  /**
   * Sets the underlying delegate {@link SessionManager} instance that will be used to support this implementation's
   * <tt>SessionManager</tt> method calls.
   * <p/>
   * This <tt>SecurityManager</tt> implementation does not provide logic to support the inherited
   * <tt>SessionManager</tt> interface, but instead delegates these calls to an internal
   * <tt>SessionManager</tt> instance.
   * <p/>
   * If a <tt>SessionManager</tt> instance is not set, a default one will be automatically created and
   * initialized appropriately for the the existing runtime environment.
   *
   */
  @Bean(name = "sessionManager")
  public DefaultWebSessionManager sessionManager() {
    DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
    return sessionManager;
  }

  /**
   * cookie管理对象;
   *
   * @return
   */
  @Bean(name = "rememberMeManager")
  public CookieRememberMeManager rememberMeManager() {
    logger.info("ShiroConfiguration.rememberMeManager()");
    CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
    cookieRememberMeManager.setCookie(rememberMeCookie());
    return cookieRememberMeManager;
  }

  /**
   * 本地存储 cookie
   * @return
   */
  @Bean(name = "rememberMeCookie")
  public SimpleCookie rememberMeCookie() {
    logger.info("ShiroConfiguration.rememberMeCookie()");
    // 这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
    SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
    // <!-- 记住我cookie生效时间30天 ,单位秒;-->
    simpleCookie.setMaxAge(259200);
    return simpleCookie;
  }

  /**
   * 自定义密码比较器
   *
   * @return
   */
  @Bean
  public CredentialsMatcher credentialsMatcher() {
    logger.info("credentialsMatcher");
    return new CredentialsMatcher();
  }

  //
  //  /** @return */
  //  @Bean
  //  public ShiroLogoutFilter getShiroLogoutFilter() {
  //    ShiroLogoutFilter shiroLogoutFilter = new ShiroLogoutFilter();
  //    shiroLogoutFilter.setRedirectUrl("/logout");
  //    return shiroLogoutFilter;
  //  }
}
