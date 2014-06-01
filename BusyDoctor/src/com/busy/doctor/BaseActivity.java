package com.busy.doctor;

import myPackage.DemoLibrary.SlidingMenu;
import myPackage.DemoLibrary.app.SlidingFragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

public class BaseActivity extends SlidingFragmentActivity {

	private int mTitleRes;
	protected SettingFragment mFrag;

	public BaseActivity(int titleRes) {
		mTitleRes = titleRes;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//setTitle(mTitleRes);
		

		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		if (savedInstanceState == null) {
			FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
			mFrag = new SettingFragment();
			t.replace(R.id.menu_frame, mFrag);
			t.commit();
		} 
		
		else {
			mFrag = (SettingFragment)this.getSupportFragmentManager().findFragmentById(R.id.menu_frame);
		}

		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.20f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case android.R.id.home:
//			toggle();
//			return true;
//		case R.id.github:
//			Util.goToGitHub(this);
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getSupportMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
}
