package com.zhaoyun.mymvp.presenter;

import android.os.Handler;

import com.zhaoyun.mymvp.business.LoginBusiness;
import com.zhaoyun.mymvp.model.User;
import com.zhaoyun.mymvp.view.ILoginView;

/**
 * Created by zhaoyun on 17-3-1.
 */

public class LoginPresenter {
    private LoginBusiness loginBusiness;
    private ILoginView view;
    private Handler handler;

    private LoginPresenter(){}

    public LoginPresenter(ILoginView iLoginView){
        this.view = iLoginView;
        this.loginBusiness = new LoginBusiness();
        this.handler = new Handler();
    }

    public void login(){
        view.showLoading();
        loginBusiness.login(view.getUsername(), view.getPassword(), new LoginBusiness.OnLoginListener() {
            @Override
            public void loginSuccess(User user) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.hideLoading();
                        view.loginSuccess();
                    }
                });
            }

            @Override
            public void loginFailed() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.hideLoading();
                        view.loginFailed();
                    }
                });
            }
        });
    }

    public void clear() {
        view.clearPassword();
        view.clearUsername();
    }
}
