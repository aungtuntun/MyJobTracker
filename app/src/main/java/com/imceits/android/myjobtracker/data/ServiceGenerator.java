package com.imceits.android.myjobtracker.data;

import com.imceits.android.myjobtracker.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class ServiceGenerator {

    private static final String BASE_URL = "https://api.myjson.com/";

    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create());

    private  static Retrofit retrofit = builder.build();

    private static  OkHttpClient getClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(BuildConfig.DEBUG? HttpLoggingInterceptor.Level.BODY:HttpLoggingInterceptor.Level.NONE);
        return  new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS).build();
    }

    static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
