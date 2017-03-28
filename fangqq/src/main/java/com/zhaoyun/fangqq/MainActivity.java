package com.zhaoyun.fangqq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.zhaoyun.fangqq.drag.DragLayout;
import com.zhaoyun.fangqq.drag.MyLinearLayout;

public class MainActivity extends Activity {

    private DragLayout mDragLayout;
    private String TAG = MainActivity.class.getSimpleName();
    private ListView mMainList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDragLayout = (DragLayout) findViewById(R.id.dl);
        MyLinearLayout mLinearLayout = (MyLinearLayout) findViewById(R.id.mll);
        mLinearLayout.setDraglayout(mDragLayout);

        mMainList = (ListView)findViewById(R.id.lv_main);
        mMainList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Cheeses.NAMES));

        mDragLayout.setDragStatusListener(new DragLayout.OnDragStatusChangeListener() {

            @Override
            public void onOpen() {
//                Utils.showToast(MainActivity.this, "onOpen");
                // 左面板ListView随机设置一个条目
//                Random random = new Random();
//
//                int nextInt = random.nextInt(50);
//                mLeftList.smoothScrollToPosition(nextInt);

            }

            @Override
            public void onDraging(float percent) {
//                Log.d(TAG, "onDraging: " + percent);// 0 -> 1
//                // 更新图标的透明度
//                // 1.0 -> 0.0
//                ViewHelper.setAlpha(mHeaderImage, 1 - percent);
            }

            @Override
            public void onClose() {
//                Utils.showToast(MainActivity.this, "onClose");
//                // 让图标晃动
////				mHeaderImage.setTranslationX(translationX)
//                ObjectAnimator mAnim = ObjectAnimator.ofFloat(mHeaderImage, "translationX", 15.0f);
//                mAnim.setInterpolator(new CycleInterpolator(4));
//                mAnim.setDuration(500);
//                mAnim.start();
            }
        });

        ImageView header = (ImageView) findViewById(R.id.iv_header);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,DrawerActivity.class));
            }
        });
    }


}
