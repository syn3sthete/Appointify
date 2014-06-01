package com.busy.doctor;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

public class MainActivity extends Activity implements OnClickListener,ConnectionCallbacks, OnConnectionFailedListener{

	// Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;
    private ConnectionResult mConnectionResult;
    private boolean mSignInClicked;
    private boolean mIntentInProgress;
    private static final int RC_SIGN_IN = 0;
    
    private SignInButton btnSignIn;
    String email = "";
    
    Person currentPerson;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
		
		// Initializing google plus api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API, null)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
        
        btnSignIn.setOnClickListener(this);
        
	}

	protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }
 
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
    
	/**
	 * Sign-in into google
	 * */
	private void signInWithGplus() {
	    if (!mGoogleApiClient.isConnecting()) {
	        mSignInClicked = true;
	        resolveSignInError();
	    }
	}
	 
	/**
	 * Method to resolve any signin errors
	 * */
	private void resolveSignInError() {
	    if (mConnectionResult.hasResolution()) {
	        try {
	            mIntentInProgress = true;
	            mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
	        } catch (SendIntentException e) {
	            mIntentInProgress = false;
	            mGoogleApiClient.connect();
	        }
	    }
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub
		if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }
 
        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;
 
            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub
		mGoogleApiClient.connect();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		switch (arg0.getId()) {
		case R.id.btn_sign_in:
			// Signin button clicked
			signInWithGplus();
			break;
//		case R.id.btn_sign_out:
//			// Signout button clicked
//			signOutFromGplus();
//			break;
//		case R.id.btn_revoke_access:
//			// Revoke access button clicked
//			revokeGplusAccess();
//			break;
		}
		
	}
	
	@Override
    protected void onActivityResult(int requestCode, int responseCode,
            Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            if (responseCode != RESULT_OK) {
                mSignInClicked = false;
            }
 
            mIntentInProgress = false;
 
            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }
 
	/**
	 * Fetching user's information name, email, profile pic
	 * */
	private void getProfileInformation() {
		try {
			if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
				currentPerson = Plus.PeopleApi
						.getCurrentPerson(mGoogleApiClient);
				String personName = currentPerson.getDisplayName();
				String personPhotoUrl = currentPerson.getImage().getUrl();
				String personGooglePlusProfile = currentPerson.getUrl();
				email = Plus.AccountApi.getAccountName(mGoogleApiClient);

				Log.d("GOOGLE PROFILE", currentPerson.toString());
				Log.d("Email", email);
				
				final List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(0);
				
				try {
					
					JSONObject object = new JSONObject();
					object.put("Email", email);
					
					PostWithObject.getWebData("http://quality.sirf99.com/api/CMS/ValidateUser", 
							nameValuePair, object, validateHandler, MainActivity.this);
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				
//				Log.e(TAG, "Name: " + personName + ", plusProfile: "
//						+ personGooglePlusProfile + ", email: " + email
//						+ ", Image: " + personPhotoUrl);

//				txtName.setText(personName);
//				txtEmail.setText(email);

				// by default the profile url gives 50x50 px image only
				// we can replace the value with whatever dimension we want by
				// replacing sz=X
//				personPhotoUrl = personPhotoUrl.substring(0,
//						personPhotoUrl.length() - 2)
//						+ PROFILE_PIC_SIZE;

//				new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);

			} else {
				Toast.makeText(getApplicationContext(),
						"Person information is null", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    @Override
    public void onConnected(Bundle arg0) {
        mSignInClicked = false;
        Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
 
        // Get user's information
        getProfileInformation();
 
        // Update the UI after signin
//        updateUI(true);
 
    }
	
public Handler validateHandler = new Handler() {
		
		int memberId;
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case 1:
					Toast.makeText(getBaseContext(), "Connection problem", Toast.LENGTH_SHORT).show();
					break;
					
				case 3:
					Toast.makeText(getBaseContext(), "Some error occured", Toast.LENGTH_SHORT).show();
					break;
					
				case 2:
				
					try {
						
						 JSONObject main=new JSONObject(msg.getData().getString("text"));
						 boolean success= main.getBoolean("Succeeded");
						 int data = 0;
						 if (success) {
							
					     data=main.getInt("Data");
					     
					     if (data>0) {
					    	 Intent i = new Intent(getBaseContext(), HomeScreenActivity.class);
						     startActivity(i);
						     finish();
						}
					     else {
							 Intent i = new Intent(getBaseContext(), Register.class);
								i.putExtra("firstName", currentPerson.getName().getGivenName());
								i.putExtra("lastName", currentPerson.getName().getFamilyName());
								i.putExtra("gender", currentPerson.getGender());
								i.putExtra("age", currentPerson.getAgeRange().getMin());
								i.putExtra("city", currentPerson.getPlacesLived().get(0).getValue());
								i.putExtra("email", email);
								startActivity(i);
								finish();
						}
					 }
						 else {
							 Intent i = new Intent(getBaseContext(), Register.class);
								i.putExtra("firstName", currentPerson.getName().getGivenName());
								i.putExtra("lastName", currentPerson.getName().getFamilyName());
								i.putExtra("gender", currentPerson.getGender());
								i.putExtra("age", currentPerson.getAgeRange().getMin());
								i.putExtra("city", currentPerson.getPlacesLived().get(0).getValue());
								i.putExtra("email", email);
								startActivity(i);
								finish();
						}
						 
						 
						 SharedPreferences prefs = getSharedPreferences("appointify", MODE_PRIVATE);
						 SharedPreferences.Editor editor = prefs.edit();
						 
						 editor.putString("firstName", currentPerson.getName().getGivenName());
						 editor.putString("lastName", currentPerson.getName().getFamilyName());
						 editor.putString("gender", currentPerson.getGender()+"");
						 editor.putString("age", currentPerson.getAgeRange().getMin()+"");
						 editor.putString("city", currentPerson.getPlacesLived().get(0).getValue());
						 editor.putString("email", email);
						 editor.putInt("patientId", data);
						 editor.commit();
						 
					}
					catch (JSONException e) {
//						Utils.showAlert(e.getMessage(), Main_Login.this,"Json Error");
						Utils.showError(MainActivity.this);
						
//						showToast(e.getMessage());
					}
					catch (Throwable e) {
						// TODO: handle exception
						Utils.showErrorAlert(e.getMessage(), MainActivity.this,"Throwable " +
								"Error");
					}
					
					break;
				}
				//progressDialog.dismiss();
			}
		};
    
}
