package com.zhaoyun.mymvp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhaoyun.mymvp.model.News;
import com.zhaoyun.mymvp.presenter.NewsPresenter;
import com.zhaoyun.mymvp.view.IMainView;
import com.zhaoyun.mymvp.volley.VolleyUtil;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements IMainView{

    private ListView mList;
    private NewsAdapter mAdapter;
    private SparseArray<News> mData;
    private NewsPresenter newsPresenter = new NewsPresenter(this);

    private Button btnUpload;
    private TextView tvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        tvText = (TextView) findViewById(R.id.tvText);
        mList = (ListView) findViewById(R.id.list);
        mAdapter = new NewsAdapter();
        mList.setAdapter(mAdapter);
        newsPresenter.loadData();


    }

    public void uploadFile(View v){
        tvText.setText("");
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 0:
                if(resultCode == RESULT_OK){
                    File file = new File(data.getData().getPath());
                    newsPresenter.uploadFile(file);
                }
                break;
        }
    }

    class NewsAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mData==null?0:mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_news,null);
            TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            TextView tvDetail = (TextView) convertView.findViewById(R.id.tvDetail);
            tvTitle.setText(mData.get(position).getTitle());
            tvDetail.setText(mData.get(position).getDetail());
            return convertView;
        }
    }

    @Override
    public void addData(SparseArray<News> datas) {
        if(mData == null){
            mData = new SparseArray<News>();
        }
        mData = datas;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void hideLoading() {
        Toast.makeText(this,"hide loading...",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        tvText.setText("加载中...");
    }

    @Override
    public void uploadSuccess(String msg) {
        tvText.setText("上传成功。"+msg);
    }

    @Override
    public void uploadFailed(String msg) {
        tvText.setText("上传失败。"+msg);
    }
}
