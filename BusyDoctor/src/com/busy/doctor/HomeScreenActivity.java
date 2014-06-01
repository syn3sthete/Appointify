package com.busy.doctor;

import myPackage.DemoLibrary.SlidingMenu;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeScreenActivity extends BaseActivity {

	SliderMenu fragment;
	SlidingMenu sm;
	
	public HomeScreenActivity() {
		super(R.string.left_and_right);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_home_screen);
		
		fragment = new SliderMenu();

		sm = getSlidingMenu();

		sm.setMode(SlidingMenu.LEFT);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		setContentView(R.layout.activity_home_screen);
		sm.setSecondaryMenu(R.layout.menu_frame);

		sm.setSecondaryShadowDrawable(R.drawable.shadowright);
		
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, fragment)
				.commit();
		
	}
	
	public void requestAppointment(View v) {
		Intent request = new Intent(getBaseContext(), Make_Appointment.class);
		startActivity(request);
	}
	
	
}
