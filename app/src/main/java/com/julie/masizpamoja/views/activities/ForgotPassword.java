package com.julie.masizpamoja.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.julie.masizpamoja.R;
import com.julie.masizpamoja.viewmodels.ForgotPasswordViewModel;

import java.io.IOException;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgotPassword extends AppCompatActivity {

    @BindView(R.id.emailEdit)
    TextInputEditText emailEdit;

    @BindView(R.id.submit)
    CircularProgressButton submit;


    ForgotPasswordViewModel forgotPasswordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Forgot Password");

        forgotPasswordViewModel = ViewModelProviders.of(this).get(ForgotPasswordViewModel.class);

        forgotPasswordViewModel.getForgotPasswordResponse().observe(this, forgotPasswordState -> {
            if(forgotPasswordState.getForgotPassword() != null){
                handleForgotPassword(forgotPasswordState.getForgotPassword());
            }

            if(forgotPasswordState.getErrorThrowable() != null){
                handleErrorThrowable(forgotPasswordState.getErrorThrowable());
            }

            if(forgotPasswordState.getMessage() != null){
                handleError(forgotPasswordState.getMessage());
            }
        });

        submit.setOnClickListener(v->{
            String email = emailEdit.getText().toString().trim();
            if(TextUtils.isEmpty(email)){
                Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show();
            }else{
                submit.startMorphAnimation();
                forgotPasswordViewModel.forgotPassword(email);
            }
        });


    }

    private void handleForgotPassword(com.julie.masizpamoja.models.ForgotPassword forgotPassword) {
        submit.startMorphRevertAnimation();
        Toast.makeText(this, forgotPassword.getStatus(), Toast.LENGTH_SHORT).show();
    }

    private void handleErrorThrowable(Throwable errorThrowable) {
        submit.startMorphRevertAnimation();
        if(errorThrowable instanceof IOException){
            Toast.makeText(this, "You are currently offline", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, getString(R.string.error_occurred), Toast.LENGTH_SHORT).show();
        }

    }

    private void handleError(String message) {
        submit.startMorphRevertAnimation();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            super.onBackPressed();
        }


        return super.onOptionsItemSelected(item);
    }

}
