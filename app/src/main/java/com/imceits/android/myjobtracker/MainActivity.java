package com.imceits.android.myjobtracker;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
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
    GoogleSignInAccount account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mViewModel = new ViewModelProvider(this).get(JobsViewModel.class);
        // removing back navigation arrow from toolbar
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.signin).build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
     //   NavigationUI.setupActionBarWithNavController(this, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            // hide default toolbar
            if (destination.getId() == R.id.jobs) {
                toolbar.setVisibility(View.GONE);
            }else {
                toolbar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            mViewModel.updateCurrentUserPhotoUri(account.getPhotoUrl());
            mViewModel.updateCurrentUserName(account.getDisplayName());
        }else {
            // go to sign in fragment if account is not sign in
            navController.navigate(R.id.signin);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

}
