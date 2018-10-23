package org.poem.user;


import org.poem.UserInfoVO;

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
