package com.android.andryyu.lifehelper.data;

import android.content.Context;

import com.android.andryyu.lifehelper.data.account.GitHubAccount;
import com.andryyu.helper.sub.github.Event;
import com.andryyu.helper.sub.payload.EventFormatter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Quinn on 10/12/15.
 */
public class RetrofitUtil {

    private final static String TAG = "RetrofitUtil";

    private volatile static Retrofit jsonInstance;

    public static String token;


    // Returns singleton class instance
    public static Retrofit getJsonRetrofitInstance(final Context context) {
        if (jsonInstance == null) {
            synchronized (Retrofit.class) {
                if (jsonInstance == null) {
                    OkHttpClient client = new OkHttpClient.Builder()
                            .addInterceptor(new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {

                                    Request request = chain.request();
                                    GitHubAccount gitHubAccount = GitHubAccount.getInstance(context);
                                    token = gitHubAccount.getAuthToken();
                                    request = request.newBuilder()
                                            .removeHeader("User-Agent")
                                            .addHeader("Authorization", "Token " + token)
                                            .addHeader("User-Agent", "Leaking/1.0")
                                            //.addHeader("Accept", "application/vnd.github.beta+json")
                                            .addHeader("Accept", "application/vnd.github.v3.raw")
                                            .build();

                                    return chain.proceed(request);
                                }
                            }).build();

                    Gson gson = null;
                    GsonBuilder builder = new GsonBuilder();
                    builder.registerTypeAdapter(Event.class, new EventFormatter());
                    gson = builder.create();
                    jsonInstance = new Retrofit.Builder()
                            .baseUrl(Constants.BASE_URL)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .client(client)
                            .build();

                }
            }
        }
        return jsonInstance;
    }
}
