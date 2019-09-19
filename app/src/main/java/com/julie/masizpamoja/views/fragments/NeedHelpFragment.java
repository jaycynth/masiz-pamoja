package com.julie.masizpamoja.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.julie.masizpamoja.R;
import com.julie.masizpamoja.adapters.AllContactListAdapter;
import com.julie.masizpamoja.models.AllContactList;
import com.julie.masizpamoja.models.ContactList;
import com.julie.masizpamoja.utils.SharedPreferencesManager;
import com.julie.masizpamoja.viewmodels.HelpDeskViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NeedHelpFragment extends DialogFragment {

    @BindView(R.id.contactsRv)
    RecyclerView contactsRv;

    AllContactListAdapter allContactListAdapter;

    private static RecyclerView.LayoutManager layoutManager;

    private List<ContactList> contactLists = new ArrayList<>();


    HelpDeskViewModel helpDeskViewModel;

    @BindView(R.id.holder_layout)
    LinearLayout holderLayout;

    @BindView(R.id.spin_kit)
    ProgressBar circularProgressBar;

    String accessToken;


    public static NeedHelpFragment newInstance() {
        return new NeedHelpFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.FilterDialogTheme);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts_dialog, container, false);
        ButterKnife.bind(this, view);


        helpDeskViewModel = ViewModelProviders.of(this).get(HelpDeskViewModel.class);

        accessToken = SharedPreferencesManager.getInstance(getActivity()).getToken();

        startProgressBar();
        helpDeskViewModel.allContacts("Bearer " + accessToken);

        helpDeskViewModel.getAllContactListResponse().observe(this, allContactListState -> {

            if (allContactListState.getAllContactList() != null) {
                handleAllContacts(allContactListState.getAllContactList());
            }

            if (allContactListState.getErrorThrowable() != null) {
                handleErrorThrowable(allContactListState.getErrorThrowable());
            }

            if (allContactListState.getMessage() != null) {
                handleError(allContactListState.getMessage());
            }
        });

        return view;

    }



    private void startProgressBar() {
        circularProgressBar.setVisibility(View.VISIBLE);
        holderLayout.setAlpha(0.0f);

    }

    private void stopProgressBar() {
        circularProgressBar.setVisibility(View.GONE);
        holderLayout.setAlpha(1);
    }

    private void handleAllContacts(AllContactList allContactList) {

        stopProgressBar();
        boolean status = allContactList.getStatus();
        if(status){
            contactLists = allContactList.getContactList();
            initView(contactLists);
        }

    }

    private void initView(List<ContactList> contactLists) {

        allContactListAdapter = new AllContactListAdapter(contactLists, getActivity());
        contactsRv.setAdapter(allContactListAdapter);
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        contactsRv.setLayoutManager(layoutManager);
        contactsRv.setNestedScrollingEnabled(false);
    }

    private void handleErrorThrowable(Throwable errorThrowable) {
        stopProgressBar();
        if (errorThrowable instanceof IOException) {
            Toast.makeText(getActivity(), "network failure", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleError(String message) {
        stopProgressBar();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

    }

}
