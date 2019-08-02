package com.julie.masizpamoja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;
import com.julie.masizpamoja.views.activities.LoginActivity;
import com.julie.masizpamoja.views.fragments.Home;
import com.julie.masizpamoja.views.fragments.Profile;
import com.julie.masizpamoja.views.fragments.Settings;
import com.julie.masizpamoja.views.fragments.Support;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;

    ActionBarDrawerToggle mToggle;

    @BindView(R.id.nav_view)
    NavigationView nav_View;

    FragmentManager fragmentManager;

    @BindView(R.id.viewLayout)
    FrameLayout viewLayout;

    Home home;
    Profile profile;
    Settings settings;
    Support support;

    Fragment fragment = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nav_View.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();

        // Declare fragments here
        home = new Home();
        profile = new Profile();
        settings = new Settings();
        support = new Support();

        //load home fragment
        fragment = home;
        getSupportFragmentManager().beginTransaction().replace(R.id.viewLayout, fragment)
                .commitAllowingStateLoss();

        mToggle = new ActionBarDrawerToggle(this,mDrawer,R.string.open,R.string.close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
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
            case R.id.action_settings:
                fragment = settings;
                setTitle(menuItem.getTitle());
                break;
            case R.id.action_share:
                shareLink();
            case R.id.action_support:
                fragment = support;
                setTitle(menuItem.getTitle());
                break;
            case R.id.action_logout:
               logOut();
                break;
            default:
                break;
                

        }
        fragmentManager.beginTransaction().replace(R.id.viewLayout, fragment).commit();
        mDrawer.closeDrawers();
        return true;

    }

    private void shareLink() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String shareMessage= "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }

    private void logOut() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
}
