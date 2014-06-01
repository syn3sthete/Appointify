package com.busy.doctor;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Actionbar extends RelativeLayout{

	LayoutInflater inflater;
	TextView txtTitle;
	
	public Actionbar(Context context) {
		super(context);
		initialize(context);
	}
	
	public Actionbar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize(context);
	}

	private void initialize(Context context) {
		// TODO Auto-generated method stub
		
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.actionbar, this);
		
		txtTitle = (TextView)findViewById(R.id.txtTitle);
		
	}
	
	public void setTitle(String cityName) {
		txtTitle.setText(cityName);
	}
}
