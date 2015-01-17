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
public class SysmindProfessionPage extends Activity{
	
	 ListView sysmind_profession_pg_listview;
	 String[] prof;
	 ArrayAdapter< String> Profession;
	 Button sysmind_ppg_logout_btn,sysmind_ppg_backbtn;
	 Intent in;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
//	    WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    setContentView(R.layout.sysmind_professsion_layout);
	    
	    sysmind_profession_pg_listview=(ListView)findViewById(R.id. sysmind_profession_pg_listview);
	    
	    sysmind_ppg_logout_btn=(Button)findViewById(R.id.sysmind_ppg_logout_btn);
	    sysmind_ppg_backbtn=(Button)findViewById(R.id.sysmind_ppg_backbtn);
		
    prof=LoginPage.professionArray; 
    Profession=new ArrayAdapter<String>(getApplicationContext(),R.layout.sysmind_profession_single_item,prof);
	sysmind_profession_pg_listview.setAdapter(Profession);;
	    
	sysmind_profession_pg_listview.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			
			String  profession_type= (String)sysmind_profession_pg_listview.getItemAtPosition(position);
		    in=new Intent(SysmindProfessionPage.this,ProfessionTypes.class);
		    
		    LoginPage.toEdit.putBoolean("IS_LOGIN", true);
			LoginPage.toEdit.putString("Profession_type", profession_type); 
		
			LoginPage.toEdit.commit(); 
		
//		    in.putExtra("Profession_type",profession_type);
		    startActivity(in);
		
				
		}
	});
	
	    sysmind_ppg_logout_btn.setOnClickListener(new View.OnClickListener() {
			
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
	    
	    sysmind_ppg_backbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
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
	    	intent = new Intent(SysmindProfessionPage.this, SysmindHomePage.class);
	        startActivity(intent);
	    
	}

}
