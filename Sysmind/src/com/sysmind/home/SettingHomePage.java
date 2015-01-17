package com.sysmind.home;

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

public class SettingHomePage extends Activity {

	ListView sysmind_home_page_listview;
	String[] homemenu;
	ArrayAdapter<String> settinghomeMenuArrayAdapter;
	Intent in;
	// Button sysmind_homepg_button;
	String uri = LoginPage.uri;
	String uri1 = "confrenceRequestSysmind?";
	String xml, FinalUrl;
	String ext1, ext2;
	Button sysmind_homepg_backbtn, sysmind_homepg_logout_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_homepage);
		// in = getIntent();
		// FinalUrl = in.getStringExtra("fnalurl");
		// Toast.makeText(getApplicationContext(), "KEY1"+FinalUrl,
		// Toast.LENGTH_LONG).show();
		homemenu = getResources().getStringArray(R.array.Setting_Home_Menu);

		settinghomeMenuArrayAdapter = new ArrayAdapter<String>(this,
				R.layout.sysmind_profession_single_item, homemenu);

		sysmind_home_page_listview = (ListView) findViewById(R.id.setting_home_page_listview);
		// sysmind_homepg_button = (Button)
		// findViewById(R.id.sysmind_homepg_button);

		sysmind_homepg_backbtn = (Button) findViewById(R.id.setting_homepg_backbtn);
		sysmind_homepg_logout_btn = (Button) findViewById(R.id.setting_homepg_logout_btn);

		sysmind_home_page_listview.setAdapter(settinghomeMenuArrayAdapter);

		sysmind_home_page_listview
				.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						if (position == 0) {
							in = new Intent(getApplicationContext(),com.sysmind.home.SipPhoneSetting.class);
							startActivity(in);
						}
						if (position == 1) {
							in = new Intent(getApplicationContext(),com.csipsimple.ui.prefs.PrefsFast.class);
							startActivity(in);
						}
					}
				});

		sysmind_homepg_backbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				in = new Intent(getApplicationContext(),com.sysmind.home.SysmindHomePage.class);
				startActivity(in);
			}
		});

		sysmind_homepg_logout_btn
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						//Toast.makeText(getApplicationContext(),"LOGOUT BUTTON", Toast.LENGTH_LONG).show();
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
		LoginPage.toEdit.putString("lastActivity", getClass().getName());
		LoginPage.toEdit.commit();
	}

	public void onBackPressed() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent = new Intent(SettingHomePage.this, SysmindHomePage.class);
		startActivity(intent);

	}
}
