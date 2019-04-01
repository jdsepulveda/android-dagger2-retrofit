package com.example.moviesapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.moviesapp.R;
import com.example.moviesapp.base.App;
import com.example.moviesapp.model.UserDataManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @Inject
    UserDataManager userDataManager;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tvUserName)
    AutoCompleteTextView tvUserName;
    @BindView(R.id.tvPassword)
    TextInputEditText tvPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.constraintLayoutLogin)
    ConstraintLayout constraintLayoutLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        App.getUserComponent(this).inject(this);

        tvPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick(R.id.btnLogin)
    public void onViewClicked() {
        attemptLogin();
    }

    private void attemptLogin() {
        tvUserName.setError(null);
        tvPassword.setError(null);

        String userName = tvUserName.getText().toString();
        String password = tvPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            tvPassword.setError(getString(R.string.error_invalid_password));
            focusView = tvPassword;
            cancel = true;
        }

        // Check for a valid user name.
        if (TextUtils.isEmpty(userName)) {
            tvUserName.setError(getString(R.string.error_field_required));
            focusView = tvUserName;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            if (userDataManager.checkUser(userName, password)) {
                Intent intent = new Intent(LoginActivity.this, MoviesActivity.class);
                startActivity(intent);
                finish();
            } else {
                Snackbar.make(constraintLayoutLogin, R.string.user_not_registered, Snackbar.LENGTH_SHORT).show();
            }
            //showProgress(true);
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
}