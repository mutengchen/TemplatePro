package com.mt.component_util.ui.view;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

/**
 * activity: tablayout+fragment显示app的主要布局框架
 */
public class TabLayoutActivity extends Activity {
//    TabLayout layout;
//    public void setViewPager(MViewPager viewPager){
//        main = LayoutInflater.from(getContext()).inflate(R.layout.tab_item_layout,null,false);
//        task = LayoutInflater.from(getContext()).inflate(R.layout.tab_item_layout,null,false);
//        notifi= LayoutInflater.from(getContext()).inflate(R.layout.tab_item_layout,null,false);
//        profile= LayoutInflater.from(getContext()).inflate(R.layout.tab_item_layout,null,false);
//        ((TextView)main.findViewById(R.id.tab_item_name)).setText("主页");
//        ((TextView)task.findViewById(R.id.tab_item_name)).setText("任务");
//        ((TextView)notifi.findViewById(R.id.tab_item_name)).setText("通知");
//        ((TextView)profile.findViewById(R.id.tab_item_name)).setText("我的");
//
//        main.findViewById(R.id.tab_img).setBackground(getResources().getDrawable(R.drawable.hom_btn_style));
//        task.findViewById(R.id.tab_img).setBackground(getResources().getDrawable(R.drawable.task_btn_style));
//        notifi.findViewById(R.id.tab_img).setBackground(getResources().getDrawable(R.drawable.notification_btn_style));
//        profile.findViewById(R.id.tab_img).setBackground(getResources().getDrawable(R.drawable.profile_btn_style));
//        layout.addTab(layout.newTab());
//        layout.addTab(layout.newTab());
//        layout.addTab(layout.newTab());
//        layout.addTab(layout.newTab());
//        layout.setupWithViewPager(viewPager);
//        layout.getTabAt(0).setCustomView(main);
//        layout.getTabAt(1).setCustomView(task);
//        layout.getTabAt(2).setCustomView(notifi);
//        layout.getTabAt(3).setCustomView(profile);
//        viewPager.setCurrentItem(0);
//
//        layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                Log.d(TAG, "bottom onTabSelected: ");
//                if(tab.getPosition()==2){
//                    MainActivity.notify_new_num = 0;
//                    initPoint(TYPE_NOTIFI,MainActivity.notify_new_num);
//                }
//
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//                Log.d(TAG, "bottom onTabReselected: ");
//                if(tab.getPosition()==2){
//                    MainActivity.notify_new_num = 0;
//                    initPoint(TYPE_NOTIFI,MainActivity.notify_new_num);
//                }
//            }
//        });
//    }
}
