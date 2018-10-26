package org.poem;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.poem.jwt.JwtHelper;
import org.poem.vo.UserInfoVO;
import org.poem.vo.login.LoginSuccessVo;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author poem
 */
public class RequestUtil {

    /**
     * 获取useId
     * @param request
     * @return
     */
    public static Long getUserId(HttpServletRequest request){
        LoginSuccessVo loginSuccessVo = getLoginSuccessVo(request);
        if(loginSuccessVo == null){
            return null;
        }
        return loginSuccessVo.getUserId();
    }



    /**
     * 获取Authorization
     * @param request
     * @return
     */
    public static String getAuthorization(HttpServletRequest request){
        String token=request.getHeader("Authorization");
        if(StringUtils.isEmpty(token)){
            token=request.getParameter("Authorization");
        }
        if(StringUtils.isEmpty(token)){
            return null;
        }
        return token;
    }


    /**
     * 获取LoginSuccessVo
     * @param request
     * @return
     */
    public static LoginSuccessVo getLoginSuccessVo(HttpServletRequest request){
        String authorization = getAuthorization(request);
        if(StringUtils.isEmpty(authorization)){
            return null;
        }
        authorization = authorization.replaceAll("Banner ", "");
        try {
            Map<String, Object> map = JwtHelper.extractInfo(authorization);
            if (map == null){
                return null;
            }
            String token = (String) map.get(Constant.JWT_CLAIM_KEY);
            UserInfoVO userInfoVO = JSONObject.parseObject(token,UserInfoVO.class);
            LoginSuccessVo loginSuccessVo = new LoginSuccessVo();
            loginSuccessVo.setAuthorization(token);
            loginSuccessVo.setName(userInfoVO.getName());
            loginSuccessVo.setUserId(userInfoVO.getUserId());
            loginSuccessVo.setUserAccount(userInfoVO.getUserName());
            return  loginSuccessVo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
