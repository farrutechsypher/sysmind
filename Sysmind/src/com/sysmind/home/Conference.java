package com.sysmind.home;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.csipsimple.R;

public class Conference extends Activity {
	Intent in;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.confernce_layout_1);
		
		Button b1=(Button)findViewById(R.id.conf_backbtn);
		Button b2=(Button)findViewById(R.id.conf_logout_btn);
		Button b3=(Button)findViewById(R.id.adduser);
		
		b1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				in=new Intent(Conference.this,com.sysmind.home.UsersInMyGroup.class);
				startActivity(in);
				
			}
		});

		b2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				in=new Intent(Conference.this,com.sysmind.home.LoginPage.class);
				startActivity(in);
		//		SharedPreferences sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE); 
		//			Editor toEdit = sh_Pref.edit();
				LoginPage.toEdit.clear();
				LoginPage.toEdit.commit();
				
			}
		});
		
		b3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				in=new Intent(Conference.this,com.sysmind.home.Adduser.class);
				startActivity(in);
				
			}
		});
	}
	
}
