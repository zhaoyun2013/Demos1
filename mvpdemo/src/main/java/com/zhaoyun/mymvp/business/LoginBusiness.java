package com.zhaoyun.mymvp.business;

import android.content.res.Resources;

import com.zhaoyun.mymvp.model.User;

/**
 * Created by zhaoyun on 17-3-1.
 */

public class LoginBusiness {
    public void login(final String username, final String password, final OnLoginListener onLoginListener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if("zhao".equals(username) && "123".equals(password)){
                    User user = new User();
                    user.setPassword(password);
                    user.setUsername(username);
                    onLoginListener.loginSuccess(user);
                }else{
                    onLoginListener.loginFailed();
                }
            }
        }).start();
    }


    public interface OnLoginListener{
        void loginSuccess(User user);
        void loginFailed();
    }
}
