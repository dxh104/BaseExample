package com.dxh.base_library_module.http;

import android.util.Log;

import com.dxh.base_library_module.base.BaseResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * 直接调用
 */
public class ApiMethods {
    private static final String TAG = "ApiMethods";

    public static ApiMethods newInstance() {
        ApiMethods apiMethods = new ApiMethods();
        return apiMethods;
    }

    /**
     * 封装线程管理和订阅的过程
     */
    private static void apisubscribe(Observable observable, Observer observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 下载文件
     *
     * @param body
     * @param path 路径
     * @return 是否下载成功
     */
    private boolean writeResponseBodyToDisk(ResponseBody body, String path) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(path);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    //表单post 响应 字符串传true/实体类传false  map不能为null
    public <T extends Object> void postRequestMap(Observer<T> observer, String url, Map<String, Object> map, boolean isJsonOrBean) {
        apisubscribe(ApiHelper.getInstance(isJsonOrBean).getRetrofitService().commonPostRequest(url, map), observer);
    }

    //json post 响应bean  body不能为null
    public <T extends Object> void postRequestBean(Observer<T> observer, String url, RequestBody body) {
        apisubscribe(ApiHelper.getInstance(false).getRetrofitService().commonPostRequest(url, body), observer);
    }

    //json post 响应json  body不能为null
    public void postRequestString(Observer<String> observer, String url, RequestBody body) {
        apisubscribe(ApiHelper.getInstance(true).getRetrofitService().commonPostRequest(url, body), observer);
    }

    //url get 响应 字符串(传true)/实体类(传false)
    public <T extends Object> void getRequest(Observer<T> observer, String url, boolean isJsonOrBean) {
        apisubscribe(ApiHelper.getInstance(isJsonOrBean).getRetrofitService().commonGetRequest(url), observer);
    }

}
