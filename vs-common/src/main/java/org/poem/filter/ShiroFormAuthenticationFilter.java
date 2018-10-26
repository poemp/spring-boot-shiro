package org.poem.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.poem.vo.UserInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 当用户不先注销登陆而直接再次登陆时不会跳转到新登录用户的问题
 * @author poem
 */
public class ShiroFormAuthenticationFilter extends FormAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(ShiroFormAuthenticationFilter.class);

  /**
   *
   * @param request
   * @param response
   * @param mappedValue
   * @return
   */
  @Override
  public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
    if (isLoginRequest(request, response)) {
      if (isLoginSubmission(request, response)) {
        // 本次用户登陆账号
        String account = this.getUsername(request);
        Subject subject = this.getSubject(request, response);
        // 之前登陆的用户
        UserInfoVO user = (UserInfoVO) subject.getPrincipal();
        // 如果两次登陆的用户不一样，则先退出之前登陆的用户
        if (account != null && user != null && !account.equals(user.getUserName())) {
            logger.info(user + " 已登陆。");
          subject.logout();
        }
      }
    }

    return super.isAccessAllowed(request, response, mappedValue);
  }
}
