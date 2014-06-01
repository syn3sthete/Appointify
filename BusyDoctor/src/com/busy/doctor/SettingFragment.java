 package com.busy.doctor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class SettingFragment extends Fragment {
	
	
	
	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	      Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.setting_fragment, container, false);
	    
	    return view;
	  }

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	
		Button b=(Button)getView().findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
			
			Toast.makeText(getActivity(), "Click ",Toast.LENGTH_LONG).show();
				
				
				
			}
		});
		
		
		
	}
	
	
	

}
