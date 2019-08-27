package com.julie.masizpamoja.views.fragments;


import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.julie.masizpamoja.R;
import com.julie.masizpamoja.utils.SharedPreferencesManager;
import com.julie.masizpamoja.viewmodels.SupportViewModel;

import java.io.IOException;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;


public class Support extends Fragment {

    @BindView(R.id.title_info)
    EditText titleInfo;

    @BindView(R.id.textArea_information)
    EditText textAreaInformtion;

    @BindView(R.id.submit)
    CircularProgressButton submit;

    private SupportViewModel supportViewModel;

    private String accessToken, email;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_support, container, false);
        ButterKnife.bind(this, view);

        supportViewModel = ViewModelProviders.of(this).get(SupportViewModel.class);

        accessToken = SharedPreferencesManager.getInstance(getActivity()).getToken();
        email = SharedPreferencesManager.getInstance(getActivity()).getEmail();


        supportViewModel.getSupportResponse().observe(this, supportState -> {
            if (supportState.getSupport() != null) {
                handleSupport(supportState.getSupport());
            }
            if (supportState.getErrorThrowable() != null) {
                handleErrorThrowable(supportState.getErrorThrowable());
            }
            if (supportState.getMessage() != null) {
                handleError(supportState.getMessage());
            }
        });


        submit.setOnClickListener(v -> {
            String subject = titleInfo.getText().toString().trim();
            String message = textAreaInformtion.getText().toString().trim();

            if (TextUtils.isEmpty(subject)) {
                Toast.makeText(getActivity(), "Enter a subject before you proceed", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(message)) {
                Toast.makeText(getActivity(), "Enter Message before proceeding", Toast.LENGTH_SHORT).show();
            } else {
                submit.startMorphAnimation();
                supportViewModel.support(subject, email, message, "Bearer " + accessToken);
            }
        });


        return view;
    }


    private void handleSupport(com.julie.masizpamoja.models.Support support) {
        submit.startMorphRevertAnimation();
        boolean status = support.getStatus();
        if (status) {
            Toast.makeText(getActivity(), support.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void handleErrorThrowable(Throwable errorThrowable) {
        submit.startMorphRevertAnimation();

        if (errorThrowable instanceof IOException) {
            Toast.makeText(getActivity(), "You are currently offline", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("SupportFragment", "conversion error " + errorThrowable.getMessage());
        }

    }

    private void handleError(String message) {
        submit.startMorphRevertAnimation();

        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}
