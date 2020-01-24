package com.imceits.android.myjobtracker.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.Gson;
import com.imceits.android.myjobtracker.data.Jobs;
import com.imceits.android.myjobtracker.databinding.FragmentProfileBinding;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding dataBinding;

    private Jobs job;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String jobs = sharedPreferences.getString("job", "");
        job = new Gson().fromJson(jobs, Jobs.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        dataBinding = FragmentProfileBinding.inflate(inflater, container, false);
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        JobsViewModel mViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(JobsViewModel.class);
        dataBinding.setJob(job);
        dataBinding.setViewmodel(mViewModel);
        dataBinding.executePendingBindings();
    }

}
