package com.julie.masizpamoja.views.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.julie.masizpamoja.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Support extends Fragment {

    @BindView(R.id.title_info)
    EditText titleInfo;

    @BindView(R.id.textArea_information)
    EditText textAreaInformtion;

    @BindView(R.id.submit)
    Button submit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_support, container, false);
        ButterKnife.bind(this, view);


        return view;
    }

}
