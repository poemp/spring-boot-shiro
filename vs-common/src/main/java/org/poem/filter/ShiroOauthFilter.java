package org.poem.filter;

import com.alibaba.fastjson.JSON;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.poem.RequestUtil;
import org.poem.filter.token.OAuthToken;
import org.poem.result.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自己实现请求的拦截
 * @author poem
 */
public class ShiroOauthFilter extends AuthenticatingFilter {

    private static final Logger logger = LoggerFactory.getLogger(ShiroOauthFilter.class);



    /**
     * 创建token
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token
        String token = RequestUtil.getAuthorization((HttpServletRequest) request);

        if(StringUtils.isBlank(token)){
            return null;
        }
        return new OAuthToken(token);
    }

    /**
     * 权限拦截
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token，如果token不存在，直接返回401
        String token = RequestUtil.getAuthorization((HttpServletRequest) request);
        if(StringUtils.isBlank(token)){
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setContentType("application/json;charset=utf-8");
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Allow-Origin", "*");

            String json = JSON.toJSONString(new ResultVO<>(HttpStatus.SC_UNAUTHORIZED, "invalid token"));
            httpResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
            httpResponse.getWriter().print(json);
            return false;
        }

        return executeLogin(request, response);
    }

    /**
     * 登陆失败
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        try {
            //处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            ResultVO<String> r = new ResultVO<String>(HttpStatus.SC_UNAUTHORIZED,null, throwable.getMessage());

            String json = JSON.toJSONString(r);
            httpResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
            httpResponse.getWriter().print(json);
        } catch (IOException e1) {
            logger.error(e1.getMessage(),e);
        }

        return false;
    }

    /**
     * 拦截请求
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //获取请求token，如果token不存在，直接返回401
        String token = RequestUtil.getAuthorization((HttpServletRequest) request);
        if(StringUtils.isBlank(token)){
            return false;
        }
        return super.isAccessAllowed(request, response, mappedValue) ||
                (!isLoginRequest(request, response) && isPermissive(mappedValue));
    }
}
