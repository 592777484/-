package com.example.xiuxiu;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Relation;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

@SuppressLint("NewApi")
public class GuideActivity extends ActionBarActivity {
	
	private static final int[] mImageIds = new int[]{R.drawable.guide_1,
		R.drawable.guide_2,R.drawable.guide_3};
	private ViewPager vpGuide;
	private ArrayList<ImageView> mImageViewList;
	
	private LinearLayout llPointGroup;
	private int mPointWidth;//圆点间的距离
	
	private View viewRedPoint;//小红点
	
	private Button btnStart;//开始体验按钮

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guide);
		vpGuide = (ViewPager) findViewById(R.id.vp_guide);
		llPointGroup = (LinearLayout) findViewById(R.id.ll_point_group);
		viewRedPoint = findViewById(R.id.view_red_point);
		btnStart = (Button) findViewById(R.id.btn_start);
		
		btnStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//跳转主页面
				
			}
		});
		
		initViews();
		vpGuide.setAdapter(new GuideAdapter());
		
		vpGuide.setOnPageChangeListener(new GuidePageListener());
		
	}
	
	//初始化viewpager
	private void initViews(){
		mImageViewList = new ArrayList<ImageView>();
		
		//初始化引导页的三个页面
		for(int i=0;i<mImageIds.length;i++){
			ImageView image = new ImageView(this);
			image.setBackgroundResource(mImageIds[i]);
			mImageViewList.add(image);
		}
		
		//初始化引导页的小圆点
		for(int i=0;i<mImageIds.length;i++){
			View point = new View(this);
			point.setBackgroundResource(R.drawable.shape_point_gray);//设置引导页默认圆点
			
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(9,9);
			if(i>0){
				params.leftMargin = 10;//设置圆点间隔
				
			}
			point.setLayoutParams(params);//设置引导页圆点的大小
			
			llPointGroup.addView(point);//将圆点添加给线性布局
		}
		
		//获取视图数,对layout结束事件进行监听
		llPointGroup.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			//当layout执行结束后回调此方法
			public void onGlobalLayout() {
				System.out.println("layout 结束");
				llPointGroup.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				mPointWidth = llPointGroup.getChildAt(1).getLeft() - llPointGroup.getChildAt(0).getLeft();
				System.out.println("圆点距离：" + mPointWidth);
			}
		});
	}
	
//viewpager数据适配
	class GuideAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mImageIds.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {

			return arg0 == arg1;
		}
		

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mImageViewList.get(position));
			return mImageViewList.get(position);
		};
		/**
		 * 销毁position位置的界面
		 */
	    @Override
	    public void destroyItem(View container, int position, Object object) {
	    	((ViewPager) container).removeView((View)object);
	    }
	}
	
	class GuidePageListener implements OnPageChangeListener{

		//滑动状态发生变化
		public void onPageScrollStateChanged(int state) {
			
		}

		//滑动事件
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			System.out.println("当前位置：" + position + ";百分比：" + positionOffset+ ";移动距离：" + positionOffsetPixels);
			int len = (int) (mPointWidth * positionOffset) + position * mPointWidth;
			//获取当前红点的布局参数
			RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) viewRedPoint.getLayoutParams();
			params.leftMargin = len;//设置左边距
			
			viewRedPoint.setLayoutParams(params);//重新给小红点设置布局参数
			
		}

		//某个页面被选中
		public void onPageSelected(int position) {
			if(position  == mImageIds.length-1){//最后一个页面
				btnStart.setVisibility(View.VISIBLE);//显示开始体验的按钮
			}else{
				btnStart.setVisibility(View.INVISIBLE);
			}
		}
		
		
	}
}
