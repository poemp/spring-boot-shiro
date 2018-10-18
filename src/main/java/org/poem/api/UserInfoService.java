package org.poem.api;

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
    public UserInfoVO findByUsername(String userName);
}
