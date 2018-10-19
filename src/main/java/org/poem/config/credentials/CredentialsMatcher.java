package org.poem.config.credentials;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author poem */
public class CredentialsMatcher extends SimpleCredentialsMatcher {

  private static final Logger logger = LoggerFactory.getLogger(CredentialsMatcher.class);
  /**
   * @param token
   * @param info
   * @return
   */
  @Override
  public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
    logger.info("doCredentialsMatch");
    UsernamePasswordToken utoken = (UsernamePasswordToken) token;
    // 获得用户输入的密码:(可以采用加盐(salt)的方式去检验)
    String inPassword = new String(utoken.getPassword());
    logger.info("获得用户输入的密码:" + inPassword);
    // 获得数据库中的密码
    String dbPassword = (String) info.getCredentials();
    logger.info("获得数据库中的密码:" + dbPassword);
    // 进行密码的比对
    return this.equals(inPassword, dbPassword);
  }
}
