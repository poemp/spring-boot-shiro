package org.poem.errot;

import org.poem.result.ResultVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author poem
 */
@RestController
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
        if(statusCode == 401){
            return new ResultVO<>(401,"没有权限","");
        }else if(statusCode == 404){
            return new ResultVO<>(404,"没有找到","");
        }else if(statusCode == 403){
            return new ResultVO<>(403,"访问错误","");
        }else{
            return new ResultVO<>(500,"服务器错误","");
        }
    }
}
