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
	private int mPointWidth;//Բ���ľ���
	
	private View viewRedPoint;//С���
	
	private Button btnStart;//��ʼ���鰴ť

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
				//��ת��ҳ��
				
			}
		});
		
		initViews();
		vpGuide.setAdapter(new GuideAdapter());
		
		vpGuide.setOnPageChangeListener(new GuidePageListener());
		
	}
	
	//��ʼ��viewpager
	private void initViews(){
		mImageViewList = new ArrayList<ImageView>();
		
		//��ʼ������ҳ������ҳ��
		for(int i=0;i<mImageIds.length;i++){
			ImageView image = new ImageView(this);
			image.setBackgroundResource(mImageIds[i]);
			mImageViewList.add(image);
		}
		
		//��ʼ������ҳ��СԲ��
		for(int i=0;i<mImageIds.length;i++){
			View point = new View(this);
			point.setBackgroundResource(R.drawable.shape_point_gray);//��������ҳĬ��Բ��
			
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(9,9);
			if(i>0){
				params.leftMargin = 10;//����Բ����
				
			}
			point.setLayoutParams(params);//��������ҳԲ��Ĵ�С
			
			llPointGroup.addView(point);//��Բ����Ӹ����Բ���
		}
		
		//��ȡ��ͼ��,��layout�����¼����м���
		llPointGroup.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			//��layoutִ�н�����ص��˷���
			public void onGlobalLayout() {
				System.out.println("layout ����");
				llPointGroup.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				mPointWidth = llPointGroup.getChildAt(1).getLeft() - llPointGroup.getChildAt(0).getLeft();
				System.out.println("Բ����룺" + mPointWidth);
			}
		});
	}
	
//viewpager��������
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
		 * ����positionλ�õĽ���
		 */
	    @Override
	    public void destroyItem(View container, int position, Object object) {
	    	((ViewPager) container).removeView((View)object);
	    }
	}
	
	class GuidePageListener implements OnPageChangeListener{

		//����״̬�����仯
		public void onPageScrollStateChanged(int state) {
			
		}

		//�����¼�
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			System.out.println("��ǰλ�ã�" + position + ";�ٷֱȣ�" + positionOffset+ ";�ƶ����룺" + positionOffsetPixels);
			int len = (int) (mPointWidth * positionOffset) + position * mPointWidth;
			//��ȡ��ǰ���Ĳ��ֲ���
			RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) viewRedPoint.getLayoutParams();
			params.leftMargin = len;//������߾�
			
			viewRedPoint.setLayoutParams(params);//���¸�С������ò��ֲ���
			
		}

		//ĳ��ҳ�汻ѡ��
		public void onPageSelected(int position) {
			if(position  == mImageIds.length-1){//���һ��ҳ��
				btnStart.setVisibility(View.VISIBLE);//��ʾ��ʼ����İ�ť
			}else{
				btnStart.setVisibility(View.INVISIBLE);
			}
		}
		
		
	}
}
