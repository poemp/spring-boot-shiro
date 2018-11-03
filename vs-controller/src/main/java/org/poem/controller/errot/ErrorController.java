package org.poem.controller.errot;

import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.HttpStatus;
import org.poem.annotation.ShiroOauthodIgnore;
import org.poem.result.ResultVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author poem
 */
@RestController
@ShiroOauthodIgnore
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    /**
     *
     * @return
     */
    @Override
    public String getErrorPath() {
        return "/error";
    }

    /**
     * 错误了
     * @param request
     * @return
     */
    @RequestMapping("/error")
    public ResultVO<String> handleError(HttpServletRequest request){
        //获取statusCode:401,404,500
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if(statusCode == HttpStatus.SC_UNAUTHORIZED){
            return new ResultVO<>(HttpStatus.SC_UNAUTHORIZED,"没有权限","");
        }else if(statusCode == HttpStatus.SC_NOT_FOUND){
            return new ResultVO<>( HttpStatus.SC_NOT_FOUND,"没有找到","");
        }else if(statusCode == HttpStatus.SC_FORBIDDEN){
            return new ResultVO<>(HttpStatus.SC_FORBIDDEN,"访问错误","");
        }else{
            return new ResultVO<>(500,"服务器错误","");
        }
    }
}
