package com.busy.doctor;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AppointmentBookedActivity extends Activity {

	TextView txtAppointmentStatus,txtAppointmentNumber;
	SharedPreferences prefs;
	Button btnOk,btnReminder;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_appointment_booked);
		
		prefs = getSharedPreferences("appointify", MODE_PRIVATE);
		
		btnOk = (Button)findViewById(R.id.okay_button);
		btnReminder = (Button)findViewById(R.id.reminder_button);
		
		btnReminder.setOnClickListener(new CreateEvent());
		
		txtAppointmentStatus = (TextView)findViewById(R.id.appointment_confirm);
		txtAppointmentStatus.setText("Your Appointment for "+prefs.getString("date", "")+" "+prefs.getString("time", "")+"with Dr. Talreja has been confirmed.");
		
		txtAppointmentNumber = (TextView)findViewById(R.id.appointment_number);
		txtAppointmentNumber.setText("Appointment No. "+prefs.getString("appointmentNo", ""));
		
	}
	
	/*************** Added READ_CALENDAR permission in manifest ***************/
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_create_reminder);
//		
//		createEvent = (Button) findViewById(R.id.remindButton);
//		createEvent.setOnClickListener(new CreateEvent());
//	}
	
	private class CreateEvent implements OnClickListener {

		private final long EVENT_INTERVAL = 900000; 
		private final String EVENT_TITLE = "Doctor's Appointment";
		private final String EVENT_DESCRIPTION = "Dr. Vinod Talreja's appointment today";
		
		private Date currentDate, endDate;
//		private long startMillis, endMillis;
		private Calendar beginTime, endTime;
		
		@Override
		public void onClick(View v) {
			constructEventDetails();
			createEventIntent();
		}

		private void createEventIntent() {
			// TODO Auto-generated method stub
			Intent createEvent = new Intent(Intent.ACTION_INSERT)
	         .setType("vnd.android.cursor.item/event")
	         .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
	         .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
	         .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY , false) // just included for completeness
	         .putExtra(Events.TITLE, EVENT_TITLE)
	         .putExtra(Events.DESCRIPTION, EVENT_DESCRIPTION)
	         .putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY)
	         .putExtra(Events.ACCESS_LEVEL, Events.ACCESS_PRIVATE);
			startActivity(createEvent);
		}

		private void constructEventDetails() {
			currentDate = new Date();
			beginTime = Calendar.getInstance();
			
			endDate = new Date(currentDate.getTime() + EVENT_INTERVAL);
			endTime = Calendar.getInstance();
			endTime.setTime(endDate);
		}
		
	}
}
