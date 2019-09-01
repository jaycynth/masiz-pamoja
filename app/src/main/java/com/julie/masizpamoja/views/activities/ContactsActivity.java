package com.julie.masizpamoja.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

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

public class ContactsActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Contacts");


        helpDeskViewModel = ViewModelProviders.of(this).get(HelpDeskViewModel.class);

        accessToken = SharedPreferencesManager.getInstance(this).getToken();

        startProgressBar();
        helpDeskViewModel.allContacts("Bearer "+ accessToken);

        helpDeskViewModel.getAllContactListResponse().observe(this, allContactListState -> {

            if(allContactListState.getAllContactList() !=null){
                handleAllContacts(allContactListState.getAllContactList());
            }

            if(allContactListState.getErrorThrowable() != null){
                handleErrorThrowable(allContactListState.getErrorThrowable());
            }

            if(allContactListState.getMessage() != null){
                handleError(allContactListState.getMessage());
            }
        });
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

        allContactListAdapter = new AllContactListAdapter(contactLists, this);
        contactsRv.setAdapter(allContactListAdapter);
        layoutManager = new LinearLayoutManager(this);
        contactsRv.setLayoutManager(layoutManager);
        contactsRv.setNestedScrollingEnabled(false);
    }

    private void handleErrorThrowable(Throwable errorThrowable) {
        stopProgressBar();
        if (errorThrowable instanceof IOException) {
            Toast.makeText(this, "network failure", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleError(String message) {
        stopProgressBar();
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
