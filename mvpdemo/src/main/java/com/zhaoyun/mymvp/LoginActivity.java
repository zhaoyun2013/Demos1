package com.zhaoyun.mymvp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.zhaoyun.mymvp.presenter.LoginPresenter;
import com.zhaoyun.mymvp.view.ILoginView;

public class LoginActivity extends Activity implements ILoginView{

    private EditText etUsername,etPassword;
    private Button btnLogin,btnClear;
    private ProgressBar progressBar;
    private LoginPresenter loginPresenter = new LoginPresenter(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.login();
            }
        });
        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.clear();
            }
        });
    }


    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public String getUsername() {
        return etUsername.getText().toString();
    }

    @Override
    public String getPassword() {
        return etPassword.getText().toString();
    }

    @Override
    public void clearUsername() {
        etUsername.setText("");
    }

    @Override
    public void clearPassword() {
        etPassword.setText("");
    }

    @Override
    public void loginSuccess() {
        Toast.makeText(this,"login success.",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,MainActivity.class));
    }

    @Override
    public void loginFailed() {
        Toast.makeText(this,"login failed.",Toast.LENGTH_SHORT).show();
    }
}
