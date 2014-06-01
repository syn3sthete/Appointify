package com.busy.doctor;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity {

	EditText txtFirstName,txtLastName,txtEmail,txtPhone;
	Button btnRegister;
	
	String fName="",lName="",address="",city="",email="",gender="",mName="",mobile="",pincode="";
	int age = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		txtFirstName = (EditText)findViewById(R.id.txtFirstName);
		txtLastName = (EditText)findViewById(R.id.txtLastName);
		txtEmail = (EditText)findViewById(R.id.txtEmail);
		txtPhone = (EditText)findViewById(R.id.txtPhone);
		btnRegister = (Button)findViewById(R.id.btnRegister);
		
		Intent i = getIntent();
		fName = i.getExtras().getString("firstName");
		lName = i.getExtras().getString("lastName");
		gender = "";
		age = i.getExtras().getInt("age");
		city = i.getExtras().getString("city");
		email = i.getExtras().getString("email");
		
		txtFirstName.setText(fName);
		txtLastName.setText(lName);
		txtEmail.setText(email);
		
		btnRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				try {
					JSONObject object = new JSONObject();
					object.put("FirstName", fName);
					object.put("Address", address);
					object.put("Age", age);
					object.put("City", city);
					object.put("Email", email);
					object.put("Gender", gender);
					object.put("LastName", lName);
					object.put("MiddleName", mName);
					object.put("Mobile", txtPhone.getText().toString());
					object.put("PinCode", pincode);
					
					final List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(0);
					
					Log.d("Request", object.toString());
					
					PostWithObject.getWebData("http://quality.sirf99.com/api/CMS/Register", nameValuePair, 
							object, registerHandler, Register.this);
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
		});
	}
	
	public Handler registerHandler = new Handler() {
		
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
							
					     int data=main.getInt("Data");

					     Intent i=new Intent(getBaseContext(), HomeScreenActivity.class);
						 finish();
						 startActivity(i);
						
					 }
						 else {
							String message=main.getString("Message");
							Utils.showErrorAlert(message, Register.this, "Register Failed!");
						}
					}
					catch (JSONException e) {
//						Utils.showAlert(e.getMessage(), Main_Login.this,"Json Error");
						Utils.showError(Register.this);
						
//						showToast(e.getMessage());
					}
					catch (Throwable e) {
						// TODO: handle exception
						Utils.showErrorAlert(e.getMessage(), Register.this,"Throwable " +
								"Error");
					}
					
					break;
				}
				//progressDialog.dismiss();
			}
		};	
	
}
