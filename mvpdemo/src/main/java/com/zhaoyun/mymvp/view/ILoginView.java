package com.zhaoyun.mymvp.view;

/**
 * Created by zhaoyun on 17-3-1.
 */

public interface ILoginView {
    void showLoading();
    void hideLoading();
    String getUsername();
    String getPassword();
    void clearUsername();
    void clearPassword();
    void loginSuccess();
    void loginFailed();

}
