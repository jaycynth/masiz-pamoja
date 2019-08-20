package com.julie.masizpamoja.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.julie.masizpamoja.MainActivity;
import com.julie.masizpamoja.R;
import com.julie.masizpamoja.models.Errors;
import com.julie.masizpamoja.models.Register;
import com.julie.masizpamoja.models.RegisterUnprocessableEntity;
import com.julie.masizpamoja.utils.SharedPreferencesManager;
import com.julie.masizpamoja.viewmodels.RegisterViewModel;

import java.io.IOException;
import java.util.Objects;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;


public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.sign_in)
    TextView signIn;

    @BindView(R.id.nameLayout)
    TextInputLayout nameLayout;
    @BindView(R.id.nameEdit)
    TextInputEditText nameEdit;

    @BindView(R.id.emailLayout)
    TextInputLayout emailLayout;
    @BindView(R.id.emailEdit)
    TextInputEditText emailEdit;

    @BindView(R.id.passwordLayout)
    TextInputLayout passwordLayout;
    @BindView(R.id.passwordEdit)
    TextInputEditText passwordEdit;

//    @BindView(R.id.signUpBtn)
//    Button signUpBtn;


    RegisterViewModel registerViewModel;

    @BindView(R.id.cirLoginButton)
    CircularProgressButton signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);

        registerViewModel.getRegisterResponse().observe(this, registerState -> {
            if (registerState.getAllNewUsers() != null) {
                handleRegisterResponse(registerState.getAllNewUsers());
            }
            if (registerState.getMessage() != null) {

                handleNetworkResponse(registerState.getMessage());
            }
            if (registerState.getErrorThrowable() != null) {
                handleError(registerState.getErrorThrowable());
            }
            if (registerState.getRegisterUnprocessableEntity() != null) {
                handleUnprocessableEntity(registerState.getRegisterUnprocessableEntity()
                );
            }
        });


        signIn.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });

        signUpBtn.setOnClickListener(v -> {
            String fullNames = nameEdit.getText().toString().trim();
            String email = emailEdit.getText().toString().trim();
            String password = passwordEdit.getText().toString().trim();

            registerUser(email, password, fullNames);
             hideKeyboard();

        });
    }

    private void hideKeyboard(){
        try{
            InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(),0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void handleRegisterResponse(Register allNewUsers) {
        signUpBtn.startMorphRevertAnimation();
        boolean status = allNewUsers.getStatus();
        if (status) {

           // SharedPreferencesManager.getInstance(this).saveToken(allNewUsers.getToken());
            SharedPreferencesManager.getInstance(this).saveNames(allNewUsers.getUser().getName());
            SharedPreferencesManager.getInstance(this).saveEmail(allNewUsers.getUser().getEmail());


            Intent navIntent = new Intent(RegisterActivity.this, MainActivity.class);
            navIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(navIntent);
            finish();

        }

    }

    private void handleNetworkResponse(String message) {
        signUpBtn.startMorphRevertAnimation();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    private void handleError(Throwable errorThrowable) {
        signUpBtn.startMorphRevertAnimation();
        if (errorThrowable instanceof IOException) {
            Toast.makeText(this, "Network Problems", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, getString(R.string.error_occurred), Toast.LENGTH_SHORT).show();
        }
    }

    private void handleUnprocessableEntity(RegisterUnprocessableEntity registerUnprocessableEntity) {
        signUpBtn.startMorphRevertAnimation();

        Toast.makeText(this, registerUnprocessableEntity.getMessage(), Toast.LENGTH_SHORT).show();

    }


    private void registerUser(String email, String password, String fullNames) {
        signUpBtn.startMorphAnimation();

        if (!validateInputs(email, password, fullNames)) {
            return;
        }
        registerViewModel.createUser(email, password, fullNames);


        //progressBar.setVisibility(View.VISIBLE);
    }

    private boolean validateInputs(String email, String password, String fullNames) {
        boolean valid = true;
        if (TextUtils.isEmpty(fullNames)) {
            nameLayout.setError(getString(R.string.enter_name_error));
            nameEdit.requestFocus();
            valid = false;
        } else {
            nameLayout.setError(null);
        }
        if (TextUtils.isEmpty(email)) {
            emailLayout.setError(getString(R.string.enter_email_error));
            emailLayout.requestFocus();
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
