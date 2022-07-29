package com.dxh.base_library_module.base.mvp;

import android.content.Context;

/*
 * Create by XHD on 2022/07/29
 * Description:数据层基类
 */
public abstract class BaseMvpModel {
    public Context mContext;

    protected void attach(Context context) {
        if (mContext == null) {
            mContext = context;
        }
        onCreate();
    }

    protected void detach() {
        onDestroy();
        if (mContext != null) {
            mContext = null;
        }
    }

    protected abstract void onCreate();

    protected abstract void onDestroy();
}
