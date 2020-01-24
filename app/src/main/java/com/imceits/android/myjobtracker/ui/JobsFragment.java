package com.imceits.android.myjobtracker.ui;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.imceits.android.myjobtracker.R;
import com.imceits.android.myjobtracker.databinding.FragmentJobsBinding;

import java.util.Objects;

public class JobsFragment extends Fragment implements View.OnClickListener {

    private JobsViewModel mViewModel;
    private GoogleSignInClient signInClient;
    private FragmentJobsBinding dataBinding;
    private JobAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        dataBinding = FragmentJobsBinding.inflate(inflater, container, false);
        dataBinding.appbar.imgAccount.setOnClickListener(this);
        dataBinding.appbar.imgExit.setOnClickListener(this);
        dataBinding.jobList.setHasFixedSize(true);
        dataBinding.jobList.setNestedScrollingEnabled(false);
        dataBinding.refreshLayout.setOnRefreshListener(() -> {
            dataBinding.refreshLayout.setRefreshing(false);
            getJobList();
        });
        dataBinding.jobList.setItemAnimator(new DefaultItemAnimator());
        adapter = new JobAdapter();
        dataBinding.jobList.setAdapter(adapter);
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(JobsViewModel.class);
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
                .build();
        signInClient = GoogleSignIn.getClient(requireContext(), options);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(requireContext());
        if (account != null) {
            updateProfile(account);
        }
        dataBinding.setViewModel(mViewModel);
       getJobList();
    }

    private void getJobList() {
        mViewModel.getJobList().observe(this, jobs -> adapter.setDataList(jobs));
    }

    private void updateProfile(GoogleSignInAccount account) {
        String name = account.getDisplayName();
        Uri personPhoto = account.getPhotoUrl();
        mViewModel.updateCurrentUserName(name);
        mViewModel.updateCurrentUserPhotoUri(personPhoto);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_account:
                Navigation.findNavController(v).navigate(R.id.action_jobs_to_profile);
                break;
            case R.id.img_exit:
                signOut(v);
                break;
        }
    }

    private void signOut(View view) {
        signInClient.signOut().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                signInClient.revokeAccess().addOnCompleteListener(task1 ->
                        Navigation.findNavController(view).navigate(R.id.action_jobs_to_signin));
            }
        });
    }
}
