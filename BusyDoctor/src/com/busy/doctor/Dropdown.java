package com.busy.doctor;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Dropdown extends LinearLayout{

	private Context context;
	private LayoutInflater layoutInflater;
	private LinearLayout itemsContainer;
	private TextView txtDropDown;
	
	public Dropdown(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initialize(context);
	}

	public Dropdown(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize(context);
	}
	
	private void initialize(Context context) {
		this.context = context;
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.custom_dropdown, this);
		itemsContainer = (LinearLayout) findViewById(R.id.items_container);
		txtDropDown=(TextView)findViewById(R.id.txtDropdown);
		
	}
	
	public void setLayoutParams(int width, int height) {
		// TODO Auto-generated method stub
		itemsContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	}
	
	public void setHint(String hint) {
		txtDropDown.setHint(hint);
	}
	
	public void onClick() {
		Toast.makeText(context, "Dropdown Clicked", Toast.LENGTH_SHORT).show();
	}
}
