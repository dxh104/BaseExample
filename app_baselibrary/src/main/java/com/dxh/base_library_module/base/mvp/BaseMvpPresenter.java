package com.dxh.base_library_module.base.mvp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.dxh.base_library_module.base.IBaseView;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/*
 * Create by XHD on 2022/07/29
 * Description:逻辑层基类
 */
public abstract class BaseMvpPresenter<Model extends BaseMvpModel, IView extends IBaseView> {
    public Model mModel;
    public IView mView;
    public Context mContext;
    private Handler mMainHandler;

    protected void attach(Context context, IView view) {
        if (mContext == null) {
            mContext = context;
        }
        if (mModel == null) {
            mModel = createModel();
            if (mModel != null)
                mModel.attach(context);
        }
        if (mView == null) {
            mView = view;
        }
        onCreate();
    }

    protected void detach() {
        onDestroy();
        if (mModel != null) {
            mModel.detach();
            mModel = null;
        }
        if (mContext != null) {
            mContext = null;
        }
        if (mView != null) {
            mView = null;
        }
        if (mMainHandler != null) {
            mMainHandler.removeCallbacksAndMessages(null);
            mMainHandler = null;
        }
    }

    //子类不需要可以重写
    public Model createModel() {
        Model model = null;
        //返回当前类的父类的Type
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {//如果支持泛型
            Class<Model> clazz = (Class<Model>) ((ParameterizedType) type).getActualTypeArguments()[0];
            try {
                //反射inflate
                model = clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return model;
    }

    public abstract void onCreate();//实例化数据库，网络请求库，管理器，一类操作

    public abstract void onDestroy();//回收对象，取消请求等

    public IView getView() {
        return mView;
    }

    public Context getContext() {
        return mContext;
    }

    public void runMainThread(Runnable action) {
        if (mMainHandler == null) {
            mMainHandler = new Handler(Looper.getMainLooper());
        }
        if (!isRunMainThread()) {
            mMainHandler.post(action);
        } else {
            action.run();
        }
    }

    private boolean isRunMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }
}
