package com.sysmind.home;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.csipsimple.R;

public class SysmindHomePage extends Activity {

	ListView sysmind_home_page_listview;
	String[] homemenu;
	ArrayAdapter<String> homeMenuArrayAdapter;
	Intent in;
	// Button sysmind_homepg_button;
	String uri = LoginPage.uri;
	String uri1 = "conferenceRequestSysmind?";
	String xml, FinalUrl;
	String ext1, ext2;
	Button sysmind_homepg_backbtn, sysmind_homepg_logout_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		 Toast.makeText(getApplicationContext(),"home page",Toast.LENGTH_LONG).show();
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.sysmind_homeppage_layout);
		in = getIntent();
		FinalUrl = in.getStringExtra("fnalurl");
//		 Toast.makeText(getApplicationContext(), "KEY1"+FinalUrl, Toast.LENGTH_LONG).show();
		homemenu = getResources().getStringArray(R.array.Home_Menu);

		homeMenuArrayAdapter = new ArrayAdapter<String>(this,
				R.layout.sysmind_profession_single_item, homemenu);

		sysmind_home_page_listview = (ListView) findViewById(R.id.sysmind_home_page_listview);

		// sysmind_homepg_button = (Button)
		// findViewById(R.id.sysmind_homepg_button);

		sysmind_homepg_backbtn = (Button) findViewById(R.id.homepg_backbtn);
		sysmind_homepg_logout_btn = (Button) findViewById(R.id.homepg_logout_btn);

		sysmind_home_page_listview.setAdapter(homeMenuArrayAdapter);

		sysmind_home_page_listview
				.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						if (position == 0) {
							// Toast.makeText(getApplicationContext(),
							// "Welcum to user=" + position,
							// Toast.LENGTH_LONG).show();
							in = new Intent(getApplicationContext(),com.sysmind.home.SysmindDepartmentPage.class);
//							in.putExtra("Position", position);
							 
							startActivity(in);
						}
						if (position == 1) {
							in = new Intent(getApplicationContext(),com.sysmind.home.SysmindLocationPage.class);
							startActivity(in);
						}
						if (position == 2) {
							in = new Intent(getApplicationContext(),com.sysmind.home.SysmindProfessionPage.class);
							startActivity(in);
						}

						if (position == 3) {
							// uri = LoginPage.uri;
							// String finalUrl = uri + uri1 +
							// "confbridgeExtension="+LoginPage.myExtension+"&joinExtension="+LoginPage.myExtension;
							String finalUrl = uri + uri1;
							XMLParser parser = new XMLParser();
							parser.params.add(new BasicNameValuePair("confbridgeExtension",LoginPage.myExtension));
							parser.params.add(new BasicNameValuePair("joinExtension", LoginPage.myExtension));
							xml = parser.getXmlFromUrl(finalUrl);
							in = new Intent(getApplicationContext(),com.sysmind.home.ConferenceCall.class);
							in.putExtra("FunalUrl", finalUrl);
							startActivity(in);
						}

						if (position == 4) {
							in = new Intent(getApplicationContext(),com.sysmind.home.MyGroupHomePage.class);
							startActivity(in);
//							Intent in = new Intent(getApplicationContext(),
//									com.csipsimple.ui.prefs.PrefsFast.class);
//							startActivity(in);
						}

						if (position == 5) {
							in = new Intent(getApplicationContext(),com.sysmind.home.SettingHomePage.class);
//							in.putExtra("FunalUrl", FinalUrl);
							startActivity(in);
						}
					}
				});

		// sysmind_homepg_button.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// in = new Intent(getApplicationContext(),
		// com.sysmind.home.LoginPage.class);
		// // in.addCategory(Intent.CATEGORY_HOME);
		// // in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// // in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
		// Intent.FLAG_ACTIVITY_NEW_TASK);
		// // in.putExtra("EXIT", true);
		// in.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);//;(Intent.FLAG_ACTIVITY_NO_HISTORY);
		// startActivity(in);
		// finish();
		// }
		// });
		//
		sysmind_homepg_backbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				in = new Intent(getApplicationContext(),com.sysmind.home.Option_Page.class);
				startActivity(in);
			}
		});

		sysmind_homepg_logout_btn
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

//						Toast.makeText(getApplicationContext(),"LOGOUT BUTTON", Toast.LENGTH_LONG).show();
						LoginPage.toEdit.clear();
						LoginPage.toEdit.commit();

						in = new Intent(getApplicationContext(),com.sysmind.home.LoginPage.class);
						startActivity(in);
						finish();
					}
				});

	}

	protected void onPause() {
		super.onPause();
//		 Toast.makeText(getApplicationContext(),"pause method()****", Toast.LENGTH_SHORT).show();
//		SharedPreferences sh_Pref = getSharedPreferences("Login Credentials",
//				MODE_PRIVATE);
//		Editor toEdit = sh_Pref.edit();
		 LoginPage.toEdit.putString("lastActivity", getClass().getName());
		 LoginPage.toEdit.commit();
		// finish();
	}
	
	public void onBackPressed()
	{
	        Intent intent = new Intent(Intent.ACTION_MAIN);
	    	intent = new Intent(SysmindHomePage.this, Option_Page.class);
	        startActivity(intent);
	    
	}
	
//	 public void exit(View view){
//	      moveTaskToBack(true); 
//	      SysmindHomePage.this.finish();
//	   }
	// public void onDestroy() {
	// // TODO Auto-generated method stub
	// super.onDestroy();
	// System.gc();
	// }
	// @Override
	// protected void onRestart() {
	// // TODO Auto-generated method stub
	// super.onRestart();
	// SharedPreferences sh_Pref = getSharedPreferences("Login Credentials",
	// MODE_PRIVATE);
	// Editor toEdit = sh_Pref.edit();
	// toEdit.putString("lastActivity", getClass().getName());
	// toEdit.commit();
	// }
	// @Override
	// public void onBackPressed()
	// {
	// finish(); //finishes the current activity and doesnt save in stock
	// Intent i = new Intent(SysmindHomePage.this, LoginPage.class);
	// in.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
	// startActivity(i);
	// }
}
