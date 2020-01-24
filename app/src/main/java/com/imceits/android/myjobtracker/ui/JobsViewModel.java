package com.imceits.android.myjobtracker.ui;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.imceits.android.myjobtracker.data.JobRepository;
import com.imceits.android.myjobtracker.data.Jobs;

import java.util.List;
import java.util.Objects;

public class JobsViewModel extends ViewModel {

    private final MutableLiveData<Uri> currentUserPhotoUri = new MutableLiveData<>();
    private final MutableLiveData<String> currentUserName = new MutableLiveData<>();
    private final LiveData<List<Jobs>> jobList;
    private final JobRepository jobRepository;

    public JobsViewModel() {
        this.jobRepository = new JobRepository();
        jobList = jobRepository.getJobs();
    }

    public LiveData<Uri> getCurrentUserPhotoUri() {
        return currentUserPhotoUri;
    }

    public LiveData<String> getCurrentUserName() {
        return currentUserName;
    }

    public void updateCurrentUserPhotoUri(Uri uri) {
        if (Objects.equals(uri, currentUserPhotoUri.getValue()))
            return;
        currentUserPhotoUri.setValue(uri);
    }

    public void updateCurrentUserName(String name) {
        if (Objects.equals(name, currentUserName.getValue()))
            return;
        currentUserName.setValue(name);
    }

    public LiveData<List<Jobs>> getJobList() {
        return jobList;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        jobRepository.onCleared();
    }
}
