package com.sysmind.home;

import com.csipsimple.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class ConferenceCall extends Activity {

	// String uri="http://192.168.1.12:8090/SysmindServices/";
//	String uri;
//	String uri1 = "confrenceRequestSysmind?";
//	String xml;
//	String ext1, ext2;
	Intent in;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// setContentView(R.layout.conf_url);
		setContentView(R.layout.confernce_layout_1);
//		  in = getIntent();
//	        String FinalUrl = in.getStringExtra("FunalUrl");
//	        
//// 
//			XMLParser parser = new XMLParser();
//			String xml = parser.getXmlFromUrl(FinalUrl); // getting XML
//			
//			Toast.makeText(getApplicationContext(), ""+xml, Toast.LENGTH_LONG).show();
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);

//		uri = LoginPage.uri;
//		String finalUrl = uri + uri1 + "confbridgeExtension=20015&joinExtension=20015";
//		XMLParser parser = new XMLParser();
//		xml = parser.getXmlFromUrl(finalUrl);
//
//		Toast.makeText(getApplicationContext(), "Welcum to user=" + finalUrl,
//				Toast.LENGTH_LONG).show();

		Button b1 = (Button) findViewById(R.id.conf_backbtn);
		Button b2 = (Button) findViewById(R.id.conf_logout_btn);
		Button b3 = (Button) findViewById(R.id.adduser);
		Button b4 = (Button) findViewById(R.id.addgroup);
		b1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				in = new Intent(ConferenceCall.this,com.sysmind.home.SysmindHomePage.class);
				startActivity(in);

			}
		});

		b2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				in = new Intent(ConferenceCall.this,com.sysmind.home.LoginPage.class);
				startActivity(in);
				LoginPage.toEdit.clear();
				LoginPage.toEdit.commit();
				finish();

			}
		});

		b3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				in = new Intent(ConferenceCall.this,com.sysmind.home.Adduser.class);
				startActivity(in);
//				SharedPreferences sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE); 
// 				Editor toEdit = sh_Pref.edit();
				

			}
		});
		
		b4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				in = new Intent(ConferenceCall.this,com.sysmind.home.UserGroup.class);
				startActivity(in);
			}
		});
	}
	
	protected void onPause() {
	    super.onPause();

//	    SharedPreferences sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);
//	    Editor toEdit = sh_Pref.edit();
	    LoginPage.toEdit.putString("lastActivity", getClass().getName());
	    LoginPage.toEdit.commit();
	}
	
	public void onBackPressed()
	{
	    
	        Intent intent = new Intent(Intent.ACTION_MAIN);
	    	intent = new Intent(ConferenceCall.this, SysmindHomePage.class);
	        startActivity(intent);
	    
	}
}
