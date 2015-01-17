package com.sysmind.home;

import java.util.Random;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.csipsimple.R;

public class RegisterPage extends Activity {

	EditText RegsterUsernameEditTxt, RegsterPasswordEditTxt,
			RegsterPhoneEditTxt, RegsterEmailEditTxt;
	String RegsterUsernameString, RegsterPasswordString, RegsterPhoneString,
			RegsterEmailString;
	Button registerButton;
	Intent intent;
	Boolean ok = false;
	String uri1 = "insertUserRegistrationSysmind?";
	String uri;
	String finalUri;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.register);

		// Toast.makeText(getApplicationContext(),
		// ""+uri,Toast.LENGTH_LONG).show();

		registerButton = (Button) findViewById(R.id.regpg_btn);

		RegsterUsernameEditTxt = (EditText) findViewById(R.id.regpg_username);
		RegsterPasswordEditTxt = (EditText) findViewById(R.id.regpg_password);
		RegsterPhoneEditTxt = (EditText) findViewById(R.id.regpg_phone);
		RegsterEmailEditTxt = (EditText) findViewById(R.id.regpg_mail);

		registerButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// in=new
				// Intent(RegisterPage.this,com.sysmind.home.EnterCodePage.class);
				// startActivity(in);

				RegsterUsernameString = RegsterUsernameEditTxt.getText()
						.toString();
				RegsterPasswordString = RegsterPasswordEditTxt.getText()
						.toString();
				RegsterPhoneString = RegsterPhoneEditTxt.getText().toString();
				RegsterEmailString = RegsterEmailEditTxt.getText().toString();

				// final String email = regpg_mail.getText().toString().trim();

				String regexStr = "^[0-9]*$";

//				if(RegsterPhoneString.trim().matches(regexStr))
//				{
//					 Toast.makeText(getApplicationContext(),"valid numeric PHONE address", Toast.LENGTH_SHORT).show();
//					 //write code here for success
//				}
//				else{
//				    // write code for failure
//					 Toast.makeText(getApplicationContext(),"invalid numeric PHONE address", Toast.LENGTH_SHORT).show();
//				}	
				
				
				
				
				if (RegsterUsernameString != null&& RegsterUsernameString.length() > 0&& RegsterPasswordString != null
						&& RegsterPasswordString.length() > 0&& RegsterPhoneString.length() > 0&& RegsterEmailString.length() > 0)
				{
//					 for (int i = 0; i < RegsterPhoneString.length(); i++) {
//						 
//				            //If we find a non-digit character we return false.
//				            if (Character.isDigit(RegsterPhoneString.charAt(i)))
//				            	 Toast.makeText(getApplicationContext(),"valid numeric PHONE address", Toast.LENGTH_SHORT).show();
//					 
//					 }
					
					
					if(RegsterPhoneString.trim().matches(regexStr))
						{
					//		 Toast.makeText(getApplicationContext(),"valid numeric PHONE address", Toast.LENGTH_SHORT).show();
							 //write code here for success
								
					
					 if(RegsterPhoneString.length() > 9 && RegsterPhoneString.length() < 12)
					    {
					//	 Toast.makeText(getApplicationContext(),"valid PHONE address", Toast.LENGTH_SHORT).show();       
					   		
					
					
					String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

					// onClick of button perform this simplest code.
					if (RegsterEmailString.matches(emailPattern)) {
//						Toast.makeText(getApplicationContext(),"valid email address", Toast.LENGTH_SHORT).show();

						uri = LoginPage.uri;
						// String finalUri= uri+ uri1 + "userName=" +
						// RegsterUsernameString + "&password="+
						// RegsterPasswordString + "&phoneNumber="+
						// RegsterPhoneString + "&email=" + RegsterEmailString;
						 finalUri = uri + uri1;

						XMLParser parser = new XMLParser();

						parser.params.add(new BasicNameValuePair("userName",RegsterUsernameString));
						parser.params.add(new BasicNameValuePair("password",RegsterPasswordString));
						parser.params.add(new BasicNameValuePair("phoneNumber",RegsterPhoneString));
						parser.params.add(new BasicNameValuePair("email",RegsterEmailString));

//						Toast.makeText(getApplicationContext(),"size of list++++" + parser.params.size(),Toast.LENGTH_LONG).show();
//						Toast.makeText(getApplicationContext(),"userName in list++" + parser.params.get(1),Toast.LENGTH_LONG).show();

						String xml = parser.getXmlFromUrl(finalUri);
//						Toast.makeText(getApplicationContext(), finalUri,Toast.LENGTH_LONG).show();
//						Toast.makeText(getApplicationContext(), xml,Toast.LENGTH_LONG).show();

						if (xml.startsWith("INSERTED")) {
//						

							 AlertDialog.Builder alertboxDuplicate = new
							 AlertDialog.Builder(RegisterPage.this);
							 //
							// alertboxDuplicate.setMessage("Username not matched with this email address");
							 alertboxDuplicate.setMessage("Please enter the code that will be send shortly at your emailId");
							 alertboxDuplicate.setNegativeButton("Ok", new
							 DialogInterface.OnClickListener() {
							 public void onClick(DialogInterface arg0, int
							 arg1) {
								 intent=new
										 Intent(RegisterPage.this,com.sysmind.home.EnterCodePage.class);
										 intent.putExtra("Username",
										 RegsterUsernameString);
										 intent.putExtra("FinalUrl", finalUri);
//										 Toast.makeText(getApplicationContext(), ""+RegsterUsernameString, Toast.LENGTH_LONG).show();
										 startActivity(intent);
							 ok=false;
							 }
							 });
							
							 alertboxDuplicate.show();
							
							

						}
						
						else if (xml.startsWith("case2")) {

							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegisterPage.this);

							// alertDialogBuilder.setTitle(this.getTitle()+
							// " decision");
							alertDialogBuilder.setMessage("Please enter the code sent at your email id");
							// set positive button: Yes message
							alertDialogBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int id) {
											// go to a new activity of the app
											intent= new Intent(getApplicationContext(),EnterCodePage.class);
											intent.putExtra("Username",RegsterUsernameString);
											intent.putExtra("FinalUrl",finalUri);
											startActivity(intent);
										}
									});
							// set negative button: No message
							alertDialogBuilder.setNegativeButton("Cancel",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											// cancel the alert box and put a
											// Toast to the user
											dialog.cancel();
		//	Toast.makeText(getApplicationContext(),	"You chose a negative answer",Toast.LENGTH_LONG).show();
										}
									});
						

							AlertDialog alertDialog = alertDialogBuilder.create();
							// show alert
							alertDialog.show();

							
						} else if (xml.startsWith("case3")) {

							AlertDialog.Builder alertboxDuplicate = new AlertDialog.Builder(
									RegisterPage.this);
							// alertboxDuplicate.setMessage("Username not matched with this email address");
							alertboxDuplicate
									.setMessage("Email already exists with some other user");
							alertboxDuplicate.setNegativeButton("Ok",new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface arg0, int arg1) {
											ok = false;
										}
									});

							alertboxDuplicate.show();
						}

						else if (xml.startsWith("case4")) {

							AlertDialog.Builder alertboxDuplicate = new AlertDialog.Builder(
									RegisterPage.this);
							alertboxDuplicate
									.setMessage("This email is already verified with other user");
							alertboxDuplicate.setNegativeButton("Ok",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface arg0, int arg1) {
											ok = false;
										}
									});

							alertboxDuplicate.show();
						}

						else if (xml.startsWith("case5")) {

							AlertDialog.Builder alertboxDuplicate = new AlertDialog.Builder(
									RegisterPage.this);
							alertboxDuplicate.setMessage("This user with this email is already Registered");
							alertboxDuplicate.setNegativeButton("Ok",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface arg0, int arg1) {
											ok = false;
										}
									});

							alertboxDuplicate.show();
						}

						else if (xml.startsWith("case6")) {

							AlertDialog.Builder alertboxDuplicate = new AlertDialog.Builder(
									RegisterPage.this);
							alertboxDuplicate
									.setMessage("You are not allowed to register with this email address.");
							alertboxDuplicate.setNegativeButton("Ok",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface arg0, int arg1) {
											ok = false;
										}
									});

							alertboxDuplicate.show();
						}

						else if (xml.startsWith("case7")) {

							AlertDialog.Builder alertboxDuplicate = new AlertDialog.Builder(
									RegisterPage.this);
							alertboxDuplicate
									.setMessage("This user already exist");
							alertboxDuplicate.setNegativeButton("Ok",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface arg0, int arg1) {
											ok = false;
										}
									});

							alertboxDuplicate.show();
						} else {

							AlertDialog.Builder alertboxDuplicat = new AlertDialog.Builder(
									RegisterPage.this);
							// alertboxDuplicate.setMessage("Username not matched with this email address");
							alertboxDuplicat
									.setMessage("INCORRECT DETAILS,Please verify the details from one sent at your emailId");
							alertboxDuplicat.setNegativeButton("Ok",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface arg0, int arg1) {
											ok = false;
										}
									});

							alertboxDuplicat.show();
						}
					} else {

						AlertDialog.Builder alertboxEmail = new AlertDialog.Builder(
								RegisterPage.this);
						alertboxEmail.setMessage("Invalid email address");
						alertboxEmail.setNegativeButton("Ok",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface arg0,
											int arg1) {
										ok = false;
									}
								});

						alertboxEmail.show();
					}
					    }
					    else
					    {
					    	//Toast.makeText(getApplicationContext(),"INVALID PHONE address", Toast.LENGTH_SHORT).show();
					   
					    	AlertDialog.Builder alertboxEmail = new AlertDialog.Builder(
									RegisterPage.this);
							alertboxEmail.setMessage("Enter 10 or 11 digit Phone Number");
							alertboxEmail.setNegativeButton("Ok",
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface arg0,
												int arg1) {
											ok = false;
										}
									});

							alertboxEmail.show();		    
					    
					    }
						}else{
							
							AlertDialog.Builder alertbox = new AlertDialog.Builder(
									RegisterPage.this);
							alertbox.setMessage("PHONE NUMBER SHOULD BE IN DIGITS ONLY");
							alertbox.setNegativeButton("Ok",
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface arg0,
												int arg1) {
											ok = false;
										}
									});
							alertbox.show();	
							
							
						}
				} else {
					AlertDialog.Builder alertbox = new AlertDialog.Builder(
							RegisterPage.this);
					alertbox.setMessage("Please Enter Correct Detail");
					alertbox.setNegativeButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface arg0,
										int arg1) {
									ok = false;
								}
							});
					alertbox.show();
				}
			}
		});
	}

	@Override
	public void onBackPressed()
	{
	finish();   //finishes the current activity and doesnt save in stock
	Intent i = new Intent(RegisterPage.this, LoginPage.class);
	i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
	startActivity(i);
	}
	
}
