package com.imceits.android.myjobtracker.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobRepository {

    private final JobService jobService;
    public JobRepository() {
        this.jobService = ServiceGenerator.createService(JobService.class);
    }

    public LiveData<List<Jobs>> getJobs() {
        MutableLiveData<List<Jobs>> jobsList = new MutableLiveData<>();
        Call<List<Jobs>> call = jobService.getJobs();
        call.enqueue(new Callback<List<Jobs>>() {
            @Override
            public void onResponse(@NotNull Call<List<Jobs>> call, @NotNull Response<List<Jobs>> response) {
                jobsList.postValue(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<List<Jobs>> call, @NotNull Throwable t) {
                Log.d("Repository","Job fetch failed  = " + t.getMessage());
            }
        });
        return jobsList;
    }


    public void onCleared() {
    }

}
