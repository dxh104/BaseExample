package com.dxh.base_library_module.manager;


import com.dxh.base_library_module.bean.CalendarInfo;
import com.dxh.base_library_module.http.ApiHelper;
import com.dxh.base_library_module.http.ApiService;

import io.reactivex.Observable;

/**
 * Created by XHD on 2019/03/27
 */
public class DataManager {
    private static DataManager dataManager;
    private ApiService apiService;

    public static DataManager getInstance() {
        return dataManager == null ? dataManager = new DataManager() : dataManager;
    }

    private DataManager() {
        apiService = ApiHelper.getInstance().getRetrofitService();
    }

    /**
     * 日历详述
     */
    public Observable<CalendarInfo> calendarDetails(String client, String timestamp, String token) {
        return apiService.calendarDetails(client, timestamp, token);
    }


}
