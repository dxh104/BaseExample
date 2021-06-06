package com.dxh.example;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dxh.base_library_module.base.BaseActivity;
import com.dxh.base_library_module.bean.CalendarInfo;
import com.dxh.base_library_module.http.ApiUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.observers.DefaultObserver;

public class MainActivity extends BaseActivity {
    @BindView(R.id.tv_data)
    TextView tvData;
    @BindView(R.id.btn_getData)
    Button btnGetData;

    @OnClick({R.id.tv_data, R.id.btn_getData})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_data:
                break;
            case R.id.btn_getData:
                showLoadingDialog();//加载等待框
                //处理获取的数据
                ApiUtil.calendarDetails(mProvider, "ceshi", "1462377600", "CD78D9012F1C063E54C640EA27952F80", new DefaultObserver<CalendarInfo>() {
                    @Override
                    public void onNext(CalendarInfo calendarInfo) {
                        showCalendarInfo(calendarInfo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        requestOnError(e);
                    }

                    @Override
                    public void onComplete() {
                        hideLoadingDialog();
                    }
                });
                break;
        }
    }

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        View actionBar = setActionBar("首页");
    }


    @Override
    protected void initData() {

    }

    @Override
    protected boolean isSetScreenOrientation() {
        return true;
    }

    @Override
    protected boolean isVerticalScreen() {
        return true;
    }

    @Override
    protected boolean isHideTitleBar() {
        return true;
    }

    @Override
    protected boolean isSetBackgroundColorAndStatusBarColor() {
        return true;
    }

    //展示日历信息
    private void showCalendarInfo(CalendarInfo calendarInfo) {
        tvData.setText(calendarInfo.toString());
    }

}
