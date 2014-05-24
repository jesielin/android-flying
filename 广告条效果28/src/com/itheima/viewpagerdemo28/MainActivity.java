package com.itheima.viewpagerdemo28;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ViewPager viewPager;

	private LinearLayout pointGroup;

	private TextView iamgeDesc;

	// 图片资源ID
	private final int[] imageIds = { R.drawable.a, R.drawable.b, R.drawable.c,
			R.drawable.d, R.drawable.e };

    //图片标题集合
	private final String[] imageDescriptions = {
			"巩俐不低俗，我就不能低俗",
			"扑树又回来啦！再唱经典老歌引万人大合唱",
			"揭秘北京电影如何升级",
			"乐视网TV版大派送",
			"热血屌丝的反杀"
	};
	
	
	private ArrayList<ImageView> imageList;

	/**
	 * 上一个页面的位置
	 */
	protected int lastPosition;
	
	/**
	 * 是否开启自动滚动
	 */
	private boolean isRoll;
	
	/**
	 * 自动滚动实现方式
	 * 1.Timer定时器
	 * 2.子线程while true
	 * 3.ClockManager
	 * 4.handler
	 */
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(isRoll){
				viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
				handler.sendEmptyMessageDelayed(0, 1000);
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		viewPager = (ViewPager) findViewById(R.id.viewpager);
		pointGroup = (LinearLayout) findViewById(R.id.point_group);
		iamgeDesc = (TextView) findViewById(R.id.image_desc);
		iamgeDesc.setText(imageDescriptions[0]);
		
		
		imageList = new ArrayList<ImageView>();
		for (int i = 0; i <imageIds.length; i++) {
			
			
			//初始化图片资源
			ImageView image = new ImageView(this);
			image.setBackgroundResource(imageIds[i]);
			imageList.add(image);
			
			//添加指示点
			ImageView point =new ImageView(this);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			
			params.rightMargin = 20;
			point.setLayoutParams(params);
			
			point.setBackgroundResource(R.drawable.point_bg);
			if(i==0){
				point.setEnabled(true);
			}else{
				point.setEnabled(false);
			}
			pointGroup.addView(point);
		}
		
		 viewPager.setAdapter(new MyPagerAdapter());
		 viewPager.setCurrentItem(Integer.MAX_VALUE/2-Integer.MAX_VALUE/2%imageIds.length);
		 handler.sendEmptyMessageDelayed(0, 1000);
		 viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			

			@Override
			/**
			 * 页面切换后调用 
			 * position  新的页面位置
			 */
			public void onPageSelected(int position) {
				
				//设置文字描述内容
				iamgeDesc.setText(imageDescriptions[position%imageIds.length]);
				
				//改变指示点的状态
				//把当前点enbale 为true 
				pointGroup.getChildAt(position%imageIds.length).setEnabled(true);
				//把上一个点设为false
				pointGroup.getChildAt(lastPosition%imageIds.length).setEnabled(false);
				lastPosition = position;
				
			}
			
			@Override
			/**
			 * 页面正在滑动的时候，回调
			 */
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
			}
			
			@Override
			/**
			 * 当页面状态发生变化的时候，回调
			 */
			public void onPageScrollStateChanged(int state) {
				
			}
		});

	}
	
	@Override
	protected void onStart() {
		isRoll = true;
		super.onStart();
	}
	
	@Override
	protected void onDestroy() {
		isRoll = false;
		super.onDestroy();
	}
	

	private class MyPagerAdapter extends PagerAdapter {

		@Override
		/**
		 * 获得页面的总数
		 */
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		@Override
		/**
		 * 获得相应位置上的view
		 * container  view的容器，其实就是viewpager自身
		 * position 	相应的位置
		 */
		public Object instantiateItem(ViewGroup container, int position) {
			// 给 container 添加内容
			container.addView(imageList.get(position%imageList.size()));
			return imageList.get(position%imageList.size());
		}

		@Override
		/**
		 * 判断 view和object的对应关系 
		 */
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		/**
		 * 销毁对应位置上的object
		 */
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
			object = null;
		}

	}

}
