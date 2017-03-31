package com.zhaoyun.mymvp.view;

import android.util.SparseArray;

import com.zhaoyun.mymvp.model.News;

/**
 * Created by zhaoyun on 17-3-1.
 */

public interface IMainView {
    void addData(SparseArray<News> datas);
    void hideLoading();
    void showLoading();
    void uploadSuccess(String msg);
    void uploadFailed(String msg);
}
