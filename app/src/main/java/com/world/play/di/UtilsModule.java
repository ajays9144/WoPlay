package com.world.play.di;

import android.app.Application;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.world.play.network.ApiCallInterface;
import com.world.play.repository.StoriesRepository;
import com.world.play.utils.AppConstants;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class UtilsModule
{
    /**
     * Provide gson gson.
     *
     * @return the gson
     */
    @Provides
    @Singleton
    public Gson provideGson() {
        GsonBuilder builder =
                new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return builder.setLenient().create();
    }

    /**
     * Provide retrofit retrofit.
     *
     * @param gson         the gson
     * @param okHttpClient the ok http client
     * @return the retrofit
     */
    @Provides
    @Singleton
    public Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    /**
     * Gets api call interface.
     *
     * @param retrofit the retrofit
     * @return the api call interface
     */
    @Provides
    @Singleton
    ApiCallInterface getApiCallInterface(Retrofit retrofit) {
        return retrofit.create(ApiCallInterface.class);
    }

    /**
     * Gets request header.
     *
     * @return the request header
     */
    @Provides
    @Singleton
    public OkHttpClient getRequestHeader() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder().build();
            return chain.proceed(request);
        }).connectTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS);
        return httpClient.build();
    }

    @Provides
    @Singleton
    StoriesRepository providerShowBookRepository(Application application, ApiCallInterface apiCallInterface) {
        return new StoriesRepository(application.getApplicationContext(), apiCallInterface);
    }
}
