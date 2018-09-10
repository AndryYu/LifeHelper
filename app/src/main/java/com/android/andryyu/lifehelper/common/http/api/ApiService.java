package com.android.andryyu.lifehelper.common.http.api;

import com.android.andryyu.lifehelper.common.data.Constants;
import com.android.andryyu.lifehelper.entity.douban.HotMovieBean;
import com.android.andryyu.lifehelper.entity.douban.MovieDetailBean;
import com.android.andryyu.lifehelper.entity.news.NewsDetailInfo;
import com.android.andryyu.lifehelper.entity.news.SpecialInfo;
import com.android.andryyu.lifehelper.entity.zhihu.Daily;
import com.android.andryyu.lifehelper.entity.dandu.DetailEntity;
import com.android.andryyu.lifehelper.entity.HomePicEntity;
import com.android.andryyu.lifehelper.entity.dandu.Item;
import com.android.andryyu.lifehelper.entity.dandu.Result;
import com.android.andryyu.lifehelper.entity.zhihu.Story;
import com.android.andryyu.lifehelper.entity.WeatherAPI;
import com.android.andryyu.lifehelper.entity.news.NewsInfo;
import com.andryyu.helper.sub.github.Empty;
import com.andryyu.helper.sub.github.GithubToken;
import com.andryyu.helper.sub.github.User;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;



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
    @GET("http://static.owspace.com/")
    Observable<Result.Data<List<Item>>> getList(@Query("c") String c, @Query("a") String a, @Query("p") int page, @Query("model") int model, @Query("page_id") String pageId, @Query("create_time") String createTime, @Query("client") String client, @Query("version") String version, @Query("time") long time, @Query("device_id") String deviceId, @Query("show_sdv") int show_sdv);
    /**
     * http://static.owspace.com/?c=api&a=getPost&post_id=292296&show_sdv=1
     * <p>详情页</p>
     *
     * @param c
     * @param a
     * @param post_id
     * @param show_sdv
     * @return
     */
    @GET("http://static.owspace.com/")
    Observable<Result.Data<DetailEntity>> getDetail(@Query("c") String c, @Query("a") String a, @Query("post_id") String post_id, @Query("show_sdv") int show_sdv);

    //github相关接口
    @POST("https://api.github.com/authorizations")
    Observable<Response<GithubToken>> createToken(@Body GithubToken token, @Header("Authorization") String authorization) ;
    @GET("https://api.github.com/authorizations")
    Observable<Response<List<GithubToken>>> listToken(@Header("Authorization") String authorization) ;
    @DELETE("https://api.github.com/authorizations/{id}")
    Observable<Response<Empty>> removeToken(@Header("Authorization") String authorization, @Path("id") String id) ;
    @GET("https://api.github.com/user")
    Observable<Response<User>> authUser();


    //获取新闻列表
    @Headers(Constants.CACHE_CONTROL_NETWORK)
    @GET("http://c.m.163.com/nc/article/{type}/{id}/{startPage}-20.html")
    Observable<Map<String, List<NewsInfo>>> getNewsList(@Path("type") String type, @Path("id") String id, @Path("startPage") int startPage);
    //获取专题
    @Headers(Constants.CACHE_CONTROL_NETWORK)
    @GET("http://c.3g.163.com/nc/special/{specialId}.html")
    Observable<Map<String, SpecialInfo>> getSpecial(@Path("specialId") String specialIde);
    //获取新闻详情
    @Headers(Constants.AVOID_HTTP403_FORBIDDEN)
    @GET("http://c.3g.163.com/nc/article/{newsId}/full.html")
    Observable<Map<String, NewsDetailInfo>> getNewsDetail(@Path("newsId") String newsId);

    // 豆瓣热映电影，每日更新
    @GET("https://api.douban.com/v2/movie/in_theaters")
    Observable<HotMovieBean> fetchHotMovie();
    //获取电影详情
    @GET("https://api.douban.com/v2/movie/subject/{id}")
    Observable<MovieDetailBean> fetchMovieDetail(@Path("id") String id);
}
