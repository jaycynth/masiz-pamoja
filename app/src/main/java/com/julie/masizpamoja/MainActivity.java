package com.julie.masizpamoja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.julie.masizpamoja.models.Logout;
import com.julie.masizpamoja.utils.SharedPreferencesManager;
import com.julie.masizpamoja.viewmodels.MainViewModel;
import com.julie.masizpamoja.views.activities.LoginActivity;
import com.julie.masizpamoja.views.fragments.Contacts;
import com.julie.masizpamoja.views.fragments.Home;
import com.julie.masizpamoja.views.fragments.Profile;
import com.julie.masizpamoja.views.fragments.Settings;
import com.julie.masizpamoja.views.fragments.Support;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;

    ActionBarDrawerToggle mToggle;

    @BindView(R.id.nav_view)
    NavigationView navView;

    FragmentManager fragmentManager;

    @BindView(R.id.viewLayout)
    FrameLayout viewLayout;

    Home home;
    Profile profile;
    Settings settings;
    Support support;
    Contacts contacts;

    Fragment fragment = null;

    MainViewModel mainViewModel;

    String accessToken;

    boolean doubleBackToExitPressedOnce;


    TextView navEmail;
    TextView navName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (!SharedPreferencesManager.getInstance(this).isLoggedIn()) {
            Intent homeIntent = new Intent(this, LoginActivity.class);
            startActivity(homeIntent);
        }

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        accessToken = SharedPreferencesManager.getInstance(this).getToken();


        mainViewModel.getLogoutResponse().observe(this, logoutState -> {
            if (logoutState.getAllLogouts() != null) {
                handleLogout(logoutState.getAllLogouts());
            }
            if (logoutState.getErrorThrowable() != null) {
                handleError(logoutState.getErrorThrowable());
            }

            if (logoutState.getMessage() != null) {
                handleNetworkResponse(logoutState.getMessage());
            }
        });



        navView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();

        // Declare fragments here
        home = new Home();
        profile = new Profile();
        settings = new Settings();
        support = new Support();
        contacts = new Contacts();

        //load home fragment
        fragment = home;
        getSupportFragmentManager().beginTransaction().replace(R.id.viewLayout, fragment)
                .commitAllowingStateLoss();

        mToggle = new ActionBarDrawerToggle(this, mDrawer, R.string.open, R.string.close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();



//        //creating objects for the views in the navigation header
//        View hView = navView.getHeaderView(0);
//        navName = hView.findViewById(R.id.navName);
//        navEmail = hView.findViewById(R.id.navEmail);


    }

    private void handleNetworkResponse(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void handleError(Throwable errorThrowable) {

        if (errorThrowable instanceof IOException) {
            Toast.makeText(this, "Network Failure", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, getString(R.string.error_occurred), Toast.LENGTH_SHORT).show();
        }

    }

    private void handleLogout(Logout allLogouts) {
        boolean status = allLogouts.getStatus();
        if (status) {
            logOut();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_home:
                fragment = home;
                setTitle(menuItem.getTitle());
                break;
            case R.id.action_profile:
                fragment = profile;
                setTitle(menuItem.getTitle());
                break;

            case R.id.action_support:
                fragment = support;
                setTitle(menuItem.getTitle());
                break;
            case R.id.action_contact:
                fragment = contacts;
                setTitle(menuItem.getTitle());
                break;
            case R.id.action_logout:
                mainViewModel.userLogout("Bearer " + accessToken);
                break;
            default:
                break;


        }
        fragmentManager.beginTransaction().replace(R.id.viewLayout, fragment).commit();
        mDrawer.closeDrawers();
        return true;

    }

    @Override
    protected void onStart() {
        if (!SharedPreferencesManager.getInstance(this).isLoggedIn()) {
            Intent homeIntent = new Intent(this, LoginActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(homeIntent);
            finish();
        }

        super.onStart();
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START) || fragmentManager.getBackStackEntryCount() > 0) {
            mDrawer.closeDrawer(GravityCompat.START);

            fragmentManager.popBackStack();


        } else if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
        } else {
            doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press Back again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);


        }
    }




    private void logOut() {
        SharedPreferencesManager.getInstance(this).clear();

        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }
}
