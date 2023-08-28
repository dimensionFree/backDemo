package com.kkk.config.filter;

import com.kkk.userManage.entity.User;

public class LoginUserContext {
    private final static ThreadLocal<User> USER_THREAD_LOCAL = new ThreadLocal<>();

    private LoginUserContext() {
    }

    public static void setLoginUser(User user) {
        USER_THREAD_LOCAL.set(user);
    }

    public static User getUser() {
        return USER_THREAD_LOCAL.get();
    }

    public static void remove() {
        USER_THREAD_LOCAL.remove();
    }
}
