package com.sysmind.home;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.csipsimple.R;

public class SipPhoneSetting extends ListActivity {

	// static final String URL =
	// "http://192.168.1.18:8082/SysmindServices/insertUserRegistrationSysmind?userName=sohit&password=2234&phoneNumber=1287367845678&email=abc@ixyz.com";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);

//		Toast.makeText(getApplicationContext(), ""+ LoginPage.menuItems , Toast.LENGTH_LONG).show();
//		Toast.makeText(getApplicationContext(), ""+ LoginPage.menuItems.get(0) , Toast.LENGTH_LONG).show();
//		Toast.makeText(getApplicationContext(), "login page value"+LoginPage.menuItems, Toast.LENGTH_LONG).show();
		
		
		ListAdapter adapter = new SimpleAdapter(this, LoginPage.menuItems,
				R.layout.list_item, new String[] { LoginPage.KEY_query, LoginPage.KEY_Extension,
					LoginPage.KEY_ExtPass, LoginPage.KEY_AsteriskIP }, new int[] { R.id.name,
						R.id.desciption, R.id.cost, R.id.costd });
		
		setListAdapter(adapter);

		ListView listView = getListView();

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// getting values from selected ListItem
				Intent in = new Intent(getApplicationContext(),
						com.csipsimple.ui.account.AccountsEditList.class);
//				Intent in = new Intent(getApplicationContext(),
//						com.csipsimple.ui.prefs.PrefsFast.class);
			
				startActivity(in);
				
			}
		});
	}
	protected void onPause() {
		super.onPause();
		 LoginPage.toEdit.putString("lastActivity", getClass().getName());
		 LoginPage.toEdit.commit();
	}
	
	public void onBackPressed()
	{
        Intent intent = new Intent(Intent.ACTION_MAIN);
    	intent = new Intent(SipPhoneSetting.this, SettingHomePage.class);
        startActivity(intent);
	    
	}
}
