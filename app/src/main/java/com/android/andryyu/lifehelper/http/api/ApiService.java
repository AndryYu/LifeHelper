package com.android.andryyu.lifehelper.http.api;

import com.android.andryyu.lifehelper.data.entity.Daily;
import com.android.andryyu.lifehelper.data.entity.HomePicEntity;
import com.android.andryyu.lifehelper.data.entity.Item;
import com.android.andryyu.lifehelper.data.entity.Result;
import com.android.andryyu.lifehelper.data.entity.Story;
import com.android.andryyu.lifehelper.data.entity.WeatherAPI;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by yufei on 2017/3/13.
 */

public interface ApiService {

    /** 查询当前位置的天气 */
    @GET("https://api.heweather.com/x3/weather")
    Observable<WeatherAPI> mWeatherAPI(@Query("city") String city, @Query("key") String key);

    /**  获取OpenEyes每日精选 */
    @GET
    Observable<HomePicEntity>  OpenEyesVideo(@Url String url);

    /**  获取知乎日报*/
    @GET("http://news-at.zhihu.com/api/4/news/latest")
    Observable<Daily> getLatest();

    @GET("http://news-at.zhihu.com/api/4/stories/before/{datetime}?client=0")
    Observable<Daily> getBefore(@Path("datetime") int datetime);

    @GET("http://news-at.zhihu.com/api/4/story/{story_id}")
    Observable<Story> getStory(@Path("story_id") int storyId);

    /**
     * <p>单读应用列表</p>
     * <p>http://static.owspace.com/?c=api&a=getList&p=1&model=1&page_id=0&create_time=0&client=android&version=1.3.0&time=1467867330&device_id=866963027059338&show_sdv=1</p>
     *
     * @param c
     * @param a
     * @param page
     * @param model(0:首页，1：文字，2：声音，3：影像，4：单向历)
     * @param pageId
     * @param time
     * @param deviceId
     * @param show_sdv
     * @return
     */
    @GET("/")
    Observable<Result.Data<List<Item>>> getList(@Query("c") String c, @Query("a") String a, @Query("p") int page, @Query("model") int model, @Query("page_id") String pageId, @Query("create_time") String createTime, @Query("client") String client, @Query("version") String version, @Query("time") long time, @Query("device_id") String deviceId, @Query("show_sdv") int show_sdv);

}
