package com.imceits.android.myjobtracker;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.imceits.android.myjobtracker.ui.JobsViewModel;

public class MainActivity extends AppCompatActivity  {

    AppBarConfiguration appBarConfiguration;
    NavController navController;
    JobsViewModel mViewModel;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appBarConfiguration = new AppBarConfiguration.Builder().build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
     //   NavigationUI.setupActionBarWithNavController(this, navController);
        NavigationUI.setupWithNavController(toolbar, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.jobs) {
                toolbar.setVisibility(View.GONE);
            }else {
                toolbar.setVisibility(View.VISIBLE);
            }
        });
        mViewModel = ViewModelProviders.of(this).get(JobsViewModel.class);

    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            mViewModel.updateCurrentUserPhotoUri(account.getPhotoUrl());
            mViewModel.updateCurrentUserName(account.getDisplayName());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

}
