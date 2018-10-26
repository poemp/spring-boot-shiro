package org.poem.vo.login;

import com.fasterxml.jackson.annotation.JsonFormat;

public class LoginSuccessVo {

    /**
     * 用户id
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private Long userId;

    /**
     * 权限
     */
    private String authorization;

    /**
     * 用户名字
     */
    private String name;

    /**
     * 账号
     */
    private String userAccount;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
}
