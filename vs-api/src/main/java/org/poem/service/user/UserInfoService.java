package org.poem.service.user;


import org.poem.vo.UserInfoVO;

/**
 * @author poem
 */
public interface UserInfoService {

    /**
     * 根据名字查询用户信息
     * @param userName
     * @return
     */
    UserInfoVO findByUsername(String userName);
}
