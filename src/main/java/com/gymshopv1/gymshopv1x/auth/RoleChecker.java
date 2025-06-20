package com.gymshopv1.gymshopv1x.auth;

import com.gymshopv1.gymshopv1x.entity.User;

public class RoleChecker {
    public static boolean isAdmin(User user) {
        return user != null && Boolean.TRUE.equals(user.getAdmin());
    }

    public static boolean isUser(User user) {
        return user != null && !Boolean.TRUE.equals(user.getAdmin());
    }
}
