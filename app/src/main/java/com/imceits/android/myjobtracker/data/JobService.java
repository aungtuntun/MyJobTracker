package com.imceits.android.myjobtracker.data;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;

public interface JobService {

    @GET("bins/8d195.json")
    Call<List<Jobs>> getJobs();
}
