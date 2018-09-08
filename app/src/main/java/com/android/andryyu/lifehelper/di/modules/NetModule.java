package com.android.andryyu.lifehelper.di.modules;

import com.android.andryyu.lifehelper.BaseApplication;
import com.android.andryyu.lifehelper.BuildConfig;
import com.android.andryyu.lifehelper.http.api.ApiService;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by yufei on 2017/3/13.
 */
@Module
public class NetModule {

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        //做网络缓存
        File httpCacheDirectory = new File(BaseApplication.getContext().getExternalCacheDir(), "cache_zhiyixing");

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        OkHttpClient okhttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .cache(new Cache(httpCacheDirectory, 1024*1024*10))
                .addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(new CacheInterceptor())
                .build();
        return okhttpClient;
    }

    //网络缓存(如果服务器不支持的话，手动添加Cache-Control和max-age=)
    public class CacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            Response response1 = response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    //cache for 2 days
                    .header("Cache-Control", "max-age=" + 3600 * 24 * 2)
                    .build();
            return response1;
        }
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient okhttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okhttpClient)
                .baseUrl("http://static.owspace.com/")
                //增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                //增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    public ApiService provideApiService(Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }
}
