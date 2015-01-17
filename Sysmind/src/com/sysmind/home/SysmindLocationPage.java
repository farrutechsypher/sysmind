package com.sysmind.home;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.csipsimple.R;

public class SysmindLocationPage extends Activity {
	ListView sysmind_location_lpg_listview;
	String[] locat;
    ArrayAdapter< String> Location;
	Button sysmindLocationLogoutBtn,sysmindLocationBackBtn;
	Intent in;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
//	    WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    setContentView(R.layout.sysmind_location_layout);
	    
	    sysmind_location_lpg_listview=(ListView)findViewById(R.id.sysmind_location_lpg_listview);
	    sysmindLocationLogoutBtn=(Button)findViewById(R.id.sysmind_lpg_logout_btn);
	    sysmindLocationBackBtn=(Button)findViewById(R.id.sysmind_lpg_backbtn);
		
	    locat=LoginPage.locationArray;
	    Location=new ArrayAdapter<String>(getApplicationContext(),R.layout.sysmind_location_single_item,locat);
	    sysmind_location_lpg_listview.setAdapter(Location);
	    
	    
		sysmind_location_lpg_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				
				String  location_type= (String)sysmind_location_lpg_listview.getItemAtPosition(position);
			    in=new Intent(SysmindLocationPage.this,LocationType.class);
//			    in.putExtra("Location_type",location_type);
			    
			    LoginPage.toEdit.putBoolean("IS_LOGIN", true);
				LoginPage.toEdit.putString("Location_type", location_type); 
			
				LoginPage.toEdit.commit(); 
				
			    startActivity(in);
					
			}
		});
		
		sysmindLocationLogoutBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				in=new Intent(getApplicationContext(),com.sysmind.home.LoginPage.class);
				startActivity(in);
				
//				SharedPreferences sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE); 
// 				Editor toEdit = sh_Pref.edit();
				LoginPage.toEdit.clear();
				LoginPage.toEdit.commit();
				finish();
			}
		});
		
		sysmindLocationBackBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				in=new Intent(getApplicationContext(),com.sysmind.home.SysmindHomePage.class);
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
	    	intent = new Intent(SysmindLocationPage.this, SysmindHomePage.class);
	        startActivity(intent);
	    
	}

}
