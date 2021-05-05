package com.dxh.base_library_module.http;


import com.dxh.base_library_module.Constant;
import com.dxh.base_library_module.bean.CalendarInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by XHD on 2019/03/27
 */
public interface ApiService {

    /**
     * 日历详述
     */
    @GET(Constant.UrlOrigin.CALENDAR_DETAILS)
    Observable<CalendarInfo> calendarDetails(@Query("client") String client, @Query("timestamp") String timestamp, @Query("token") String token);


}
