package com.julie.masizpamoja.views.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.julie.masizpamoja.MainActivity;
import com.julie.masizpamoja.R;
import com.julie.masizpamoja.models.Login;
import com.julie.masizpamoja.models.LoginError;
import com.julie.masizpamoja.utils.SharedPreferencesManager;
import com.julie.masizpamoja.viewmodels.LoginViewModel;

import java.io.IOException;
import java.util.Objects;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.register)
    TextView register;

    @BindView(R.id.forgot_password)
    TextView forgotPassword;

    @BindView(R.id.emailLayout)
    TextInputLayout emailLayout;
    @BindView(R.id.emailEdit)
    TextInputEditText emailEdit;

    @BindView(R.id.passwordLayout)
    TextInputLayout passwordLayout;
    @BindView(R.id.passwordEdit)
    TextInputEditText passwordEdit;

    @BindView(R.id.signInBtn)
    CircularProgressButton signInBtn;

    @BindView(R.id.terms_and_conditions)
    CheckBox termsAndConditions;

    @BindView(R.id.terms_and_conditions_text)
    TextView termsAndConditionsText;

    LoginViewModel loginViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        loginViewModel.getLoginResponse().observe(this, loginState -> {
            if (loginState.getAllLogins() != null) {
                handleLoginResponse(loginState.getAllLogins());
            }
            if (loginState.getMessage() != null) {
                handleNetworkResponse(loginState.getMessage());
            }
            if (loginState.getErrorThrowable() != null) {
                handleError(loginState.getErrorThrowable());
            }
            if (loginState.getLoginError() != null) {
                handleLoginError(loginState.getLoginError());
            }

        });


        register.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        forgotPassword.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, ForgotPassword.class));

        });

        signInBtn.setOnClickListener(v -> {
            String email = emailEdit.getText().toString().trim();
            String password = passwordEdit.getText().toString().trim();

            if (termsAndConditions.isChecked()) {
                Login(email, password);
                hideKeyboard();
            } else {
                Toast.makeText(this, "Please agree to the terms and conditions first", Toast.LENGTH_SHORT).show();
            }

        });

    }


    private void hideKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        termsAndConditionsText.setOnClickListener(v -> {
            //Toast.makeText(this, "terms and conditions", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this,TermsAndConditions.class));
        });
    }


    private void handleLoginError(LoginError loginError) {
        signInBtn.startMorphRevertAnimation();
        Toast.makeText(getApplicationContext(), loginError.getMessage(), Toast.LENGTH_SHORT).show();
    }


    private void handleError(Throwable errorThrowable) {
        signInBtn.startMorphRevertAnimation();
        if (errorThrowable instanceof IOException) {
            Toast.makeText(this, "Network Failure", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, getString(R.string.error_occurred), Toast.LENGTH_SHORT).show();
        }
    }

    private void handleNetworkResponse(String message) {
        signInBtn.startMorphRevertAnimation();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    private void handleLoginResponse(Login allLogins) {
        signInBtn.startMorphRevertAnimation();
        boolean status = allLogins.getStatus();
        if (status) {
            SharedPreferencesManager.getInstance(this).saveToken(allLogins.getAccessToken());
            SharedPreferencesManager.getInstance(this).saveNames(allLogins.getUser().getName());
            SharedPreferencesManager.getInstance(this).saveUserImage(allLogins.getUser().getProfile());
            SharedPreferencesManager.getInstance(this).saveUserId(String.valueOf(allLogins.getUser().getId()));


            Intent homeIntent = new Intent(LoginActivity.this, MainActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(homeIntent);
            finish();
        }
    }

    private void Login(String email, String password) {
        if (!validateInputs(email, password)) {
            return;
        }
        SharedPreferencesManager.getInstance(this).saveEmail(email);
        loginViewModel.userLogin(email, password);
        signInBtn.startMorphAnimation();
    }

    private boolean validateInputs(String email, String password) {
        boolean valid = true;
        if (TextUtils.isEmpty(email)) {
            emailLayout.setError(getString(R.string.enter_email_error));
            emailEdit.requestFocus();
            valid = false;
        } else {
            emailLayout.setError(null);
        }
        if (TextUtils.isEmpty(password)) {
            passwordLayout.setError(getString(R.string.enter_password_error));
            passwordEdit.requestFocus();
            valid = false;
        } else {
            passwordLayout.setError(null);
        }
        if (!isValidEmail(email)) {
            emailLayout.setError(getString(R.string.invalid_email_address_error));
            emailEdit.requestFocus();

            valid = false;
        } else {
            emailLayout.setError(null);
        }
        return valid;
    }

    private boolean isValidEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }
}
