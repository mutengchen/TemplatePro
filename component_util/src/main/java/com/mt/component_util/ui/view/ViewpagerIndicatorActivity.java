package com.mt.component_util.ui.view;

import android.app.Activity;

import com.mt.component_util.R;

import java.util.ArrayList;

/**
 *获取imageview 对应的url列表
 * 设置viewpager,然后进行
 */
public class ViewpagerIndicatorActivity extends Activity {
//    List<PhotoView> photoViews = new ArrayList<>();
//    ViewPager imageViewPager;
//    PageIndicatorView pageIndicatorView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        ButterKnife.bind(this);
//        getIntentData();
//
//    }
//    private String TAG = PicShowActivity.class.getSimpleName();
//    private void getIntentData(){
//        //获取图片数据，byte[]
//        List<String> image_url = getIntent().getStringArrayListExtra("pic_url");
//        int current_index = getIntent().getIntExtra("current_index",0);
//        //创建对应的photoview
//        for(String temp:image_url){
//            Log.d(TAG, "getIntentData: "+image_url);
//            PhotoView photoView = new PhotoView(this);
//            photoViews.add(photoView);
//            photoView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    finish();
//                }
//            });
//            //通过glide加载本地图片
//            Glide.with(this).load(temp).into(photoView);
//        }
//        imageViewPager.setAdapter(new CommonViewAdapter<>(photoViews));
//        imageViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                pageIndicatorView.setSelected(position);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//        //加载想要放大的图片
//        imageViewPager.setCurrentItem(current_index);
//    }
//
//    @Override
//    public int setLayoutID() {
//        return R.layout.pic_show_layout;
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//    }
//
//    @Override
//    public void loadData() {
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//    }

}