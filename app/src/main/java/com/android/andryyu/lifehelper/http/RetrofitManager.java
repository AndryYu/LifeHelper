package com.android.andryyu.lifehelper.http;

import com.android.andryyu.lifehelper.BaseApplication;
import com.android.andryyu.lifehelper.utils.FileUtil;
import com.android.andryyu.lifehelper.utils.NetUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by yufei on 2017/3/13.
 */

public class RetrofitManager {
    private static RetrofitManager instance;
    private static Retrofit retrofit;
    private static Gson mGson;


    private RetrofitManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://andryyu.cn/")
                .client(httpClient())
                .addConverterFactory(GsonConverterFactory.create(gson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static void reset() {
        instance = null;
    }

    public static RetrofitManager getInstance() {
        if (instance == null) {
            synchronized (RetrofitManager.class) {
                instance = new RetrofitManager();
            }
        }
        return instance;
    }


    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }

    private static OkHttpClient httpClient() {
        File cacheFile = new File(FileUtil.getAppCacheDir(BaseApplication.getContext()), "/HttpCache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetUtil.isNetworkReachable(BaseApplication.getContext())) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetUtil.isNetworkReachable(BaseApplication.getContext())) {
                    int maxAge = 0;
                    // 有网络时 设置缓存超时时间0个小时
                    response.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
                } else {
                    // 无网络时，设置超时为1周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .build();
                }
                return response;
            }
        };
        return new OkHttpClient.Builder()
                .cache(cache).addInterceptor(cacheInterceptor)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }

    public static Gson gson() {
        if (mGson == null) {
            synchronized (RetrofitManager.class) {
                mGson = new GsonBuilder().setLenient().create();
            }
        }
        return mGson;
    }
}
