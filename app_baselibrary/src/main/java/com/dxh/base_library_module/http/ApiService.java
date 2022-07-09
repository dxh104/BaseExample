package com.dxh.base_library_module.http;


import com.dxh.base_library_module.Constant;
import com.dxh.base_library_module.bean.CalendarInfo;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by XHD on 2019/03/27
 */
public interface ApiService {
    @FormUrlEncoded
    @POST()
    Observable<String> commonPostRequest(@Url String urlPath, @FieldMap Map<String, Object> map);

    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST()
    Observable<String> commonPostRequest(@Url String urlPath, @Body RequestBody body);

    @GET()
    Observable<String> commonGetRequest(@Url String urlPath);

    /**
     * 日历详述
     */
    @GET(Constant.UrlOrigin.CALENDAR_DETAILS)
    Observable<CalendarInfo> calendarDetails(@Query("client") String client, @Query("timestamp") String timestamp, @Query("token") String token);

}
