package com.greak.data.database;

import android.support.annotation.Nullable;

import com.greak.data.models.Account;

import lombok.Getter;
import lombok.Setter;

public class UserInstance {

    private static UserInstance instance;

    @Getter
    @Setter
    @Nullable
    public Account account;

    @Getter
    @Setter
    public boolean freshLogin;

    public static UserInstance getInstance() {
        if (instance == null) {
            instance = new UserInstance();
        }
        return instance;
    }

    public boolean isLogged() {
        return account != null;
    }

    public void logout() {
        account = null;
    }
}