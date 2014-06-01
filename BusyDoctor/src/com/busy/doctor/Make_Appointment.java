package com.busy.doctor;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class Make_Appointment extends Activity{

	EditText txtFirstName,txtLastName,txtEmail,txtPhone;
	Button btnFixAppointment;
	LinearLayout ll_availableSlots;
	SharedPreferences sharedPref;
	private static Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.make_appointment);
		Make_Appointment.context = getApplicationContext();
		
		sharedPref = getSharedPreferences("appointify", MODE_PRIVATE);
		
		txtFirstName = (EditText)findViewById(R.id.txtFirstName);
		txtLastName = (EditText)findViewById(R.id.txtLastName);
		txtEmail = (EditText)findViewById(R.id.txtEmail);
		txtPhone = (EditText)findViewById(R.id.txtPhone);
		btnFixAppointment = (Button)findViewById(R.id.btnFixAppointment);
		ll_availableSlots = (LinearLayout)findViewById(R.id.ll_availableSlots);
		
		
//		Intent i = getIntent();
//		txtFirstName.setText(i.getExtras().getString("firstName"));
//		txtLastName.setText(i.getExtras().getString("lastName"));
		
//		Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
//		String possibleEmail="";
//		Account[] accounts = AccountManager.get(getBaseContext()).getAccounts();
		
//		Account account = accounts[0];
//		possibleEmail = account.name;
		
//		for (Account account : accounts) {
//		    if (emailPattern.matcher(account.name).matches()) {
//		        possibleEmail = account.name;
//		    }
//		}
		
//		txtEmail.setText(possibleEmail);
		
		btnFixAppointment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				try {
					final List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(0);
					
					JSONObject object = new JSONObject();
					object.put("AppointmentDate", "06-01-2014");
					object.put("BookedSlotID", sharedPref.getInt("appointmentSlotId", 0)+"");
					object.put("DoctorID", "400");
					object.put("FirstName", sharedPref.getString("firstName", ""));
					object.put("LastName", sharedPref.getString("lastName", ""));
					object.put("MiddleName", "");
					object.put("PatientID", sharedPref.getInt("patientId", 0)+"");
					object.put("Places", sharedPref.getString("city", ""));
					
					PostWithObject.getWebData("http://quality.sirf99.com/api/CMS/MakeAppointment", 
							nameValuePair, object , appointmentHandler , Make_Appointment.this);
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
		});
		
		try {
			final List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(0);
			
			JSONObject object = new JSONObject();
			object.put("DoctorID", 400);
			object.put("RequestedDate", "06-01-2014");
			
			PostWithObject.getWebData("http://quality.sirf99.com/api/CMS/GetAvailableTimeSlot", 
					nameValuePair, object , internetHandler , Make_Appointment.this);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	public static Context getAppContext() {
        return Make_Appointment.context;
    }
	
	
public Handler appointmentHandler = new Handler() {
		
		int memberId;
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case 1:
					Toast.makeText(getBaseContext(), "COnnection problem", Toast.LENGTH_SHORT).show();
					break;
					
				case 3:
					Toast.makeText(getBaseContext(), "Some error occured", Toast.LENGTH_SHORT).show();
					break;
					
				case 2:
				
					try {
						
						 JSONObject main=new JSONObject(msg.getData().getString("text"));
						 boolean success= main.getBoolean("Succeeded");
						 
						 if (success) {
						
							 JSONObject data = main.getJSONObject("Data");
							 int patientId = data.getInt("PatientID");
							 String appointmentDate = data.getString("AppointmentDate");
							 int appointmentNo = data.getInt("AppointmentNo");
							 int bookingSlotId = data.getInt("BookedSlotID");
							 int doctorId = data.getInt("DoctorID");
							 String fName = data.getString("FirstName");
							 String lName = data.getString("LastName");
							 String message = data.getString("Message");
							 
							 SharedPreferences.Editor editor = sharedPref.edit();
							 editor.putString("appointmentNo", appointmentNo+"");
							 editor.commit();
							 
							 Intent i = new Intent(context, AppointmentBookedActivity.class);
							 startActivity(i);
							 
					 }
						 else {
							String message=main.getString("Message");
							Utils.showErrorAlert(message, Make_Appointment.this, "Register Failed!");
						}
					}
					catch (JSONException e) {
//						Utils.showAlert(e.getMessage(), Main_Login.this,"Json Error");
						Utils.showError(Make_Appointment.this);
						
//						showToast(e.getMessage());
					}
					catch (Throwable e) {
						// TODO: handle exception
						Utils.showErrorAlert(e.getMessage(), Make_Appointment.this,"Throwable " +
								"Error");
					}
					
					break;
				}
				//progressDialog.dismiss();
			}
		};
	
	public Handler internetHandler = new Handler() {
		
		int memberId;
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case 1:
					Toast.makeText(getBaseContext(), "COnnection problem", Toast.LENGTH_SHORT).show();
					break;
					
				case 3:
					Toast.makeText(getBaseContext(), "Some error occured", Toast.LENGTH_SHORT).show();
					break;
					
				case 2:
				
					try {
						
						 JSONObject main=new JSONObject(msg.getData().getString("text"));
						 boolean success= main.getBoolean("Succeeded");
						 
						 if (success) {
							
					     JSONArray data= main.getJSONArray("Data");
					     
					     final RadioButton[] rb = new RadioButton[data.length()];
					     RadioGroup rg = new RadioGroup(Make_Appointment.getAppContext()); 
					     rg.setOrientation(RadioGroup.VERTICAL);
					     final int [] availableSlotID = new int[data.length()];
					     
					     for (int i = 0; i < data.length(); i++) {
							
					    	 JSONObject object = data.getJSONObject(i);
					    	 
					    	 rb[i]  = new RadioButton(Make_Appointment.this);
					    	 availableSlotID[i] = object.getInt("AvailableSlotID");
					    	 
					    	 if (!object.getBoolean("IsAvailable")) {
					    		 rb[i].setEnabled(false);
					    	    }
					    	 
					    	 rb[i].setText(object.getString("StartTime")+" To "+object.getString("EndTime"));
						    	 rg.addView(rb[i]); //the RadioButtons are added to the radioGroup instead of the layout
					    	    
					     }
					    
					     final SharedPreferences.Editor editor = sharedPref.edit();
					     
					     rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
							
							@Override
							public void onCheckedChanged(RadioGroup rGroup, int checkedId) {
								RadioButton checkedRadioButton = (RadioButton)rGroup.findViewById(checkedId);
						        
						        boolean isChecked = checkedRadioButton.isChecked();
						        int position = 0;
						        
						        if (isChecked)
						        {
						        	for (int j = 0; j < rb.length; j++) {
										
						        		RadioButton radioButton  = rb[j];
						        		if (radioButton==checkedRadioButton) {
											position = j;
											break;
										}
						        		
									}
						        	
						            editor.putInt("appointmentSlotId", availableSlotID[position]);
						            editor.putString("time", checkedRadioButton.getText().toString());
						            editor.commit();
						            
						        }
								
							}
						});
					     
					     ll_availableSlots.addView(rg);//you add the whole RadioGroup to the layout
					     
					 }
						 else {
							String message=main.getString("Message");
							Utils.showErrorAlert(message, Make_Appointment.this, "Register Failed!");
						}
					}
					catch (JSONException e) {
//						Utils.showAlert(e.getMessage(), Main_Login.this,"Json Error");
						Utils.showError(Make_Appointment.this);
						
//						showToast(e.getMessage());
					}
					catch (Throwable e) {
						// TODO: handle exception
						Utils.showErrorAlert(e.getMessage(), Make_Appointment.this,"Throwable " +
								"Error");
					}
					
					break;
				}
				//progressDialog.dismiss();
			}
		};
		
}
