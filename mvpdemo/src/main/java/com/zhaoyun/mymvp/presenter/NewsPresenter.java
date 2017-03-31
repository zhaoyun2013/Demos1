package com.zhaoyun.mymvp.presenter;

import android.util.SparseArray;

import com.zhaoyun.mymvp.business.NewsBusiness;
import com.zhaoyun.mymvp.model.News;
import com.zhaoyun.mymvp.view.IMainView;

import java.io.File;
import java.util.List;

/**
 * Created by zhaoyun on 17-3-1.
 */

public class NewsPresenter{
    private NewsBusiness newsBusiness;
    private IMainView view;
    public NewsPresenter(IMainView view){
        this.view = view;
        newsBusiness = new NewsBusiness();
    }

    public void loadData(){
        view.showLoading();
        newsBusiness.getList(new NewsBusiness.OnLoadNewsListener() {
            @Override
            public void onSuccess(SparseArray<News> list) {
                view.hideLoading();
                view.addData(list);
            }

            @Override
            public void onFailure(String str) {
                view.hideLoading();
            }
        });
    }

    public void  uploadFile(File file){
        newsBusiness.uploadFile(file, new NewsBusiness.OnUploadListener() {
            @Override
            public void onSuccess(String str) {
                view.uploadSuccess(str);
            }

            @Override
            public void onFailure(String str) {
                view.uploadFailed(str);
            }
        });
    }
}
