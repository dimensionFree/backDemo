package com.kkk.userManage.utils.jwtPojo;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class JwtInfo {

    private String uid;

    public JwtInfo(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
