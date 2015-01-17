package com.sysmind.home;

import com.csipsimple.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DepartmentConnection extends Activity {

	Button connect,addGroup;
	Intent intent;
	String userTypeString;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.department_connection);
		connect=(Button)findViewById(R.id.deptConnect);
		addGroup=(Button)findViewById(R.id.deptAddgroup);
		
		intent = getIntent();
		userTypeString= intent.getStringExtra("User_type");
		
		
	connect.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
//			Toast.makeText(getApplicationContext(), "hieeeeee conn", Toast.LENGTH_LONG).show();
			intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:19650256575"));
			startActivity(intent);
		}
	});	
		
		
	addGroup.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
//			Toast.makeText(getApplicationContext(), "hieeeeee groupp", Toast.LENGTH_LONG).show();
			intent=new Intent(getApplicationContext(),com.sysmind.home.MyGroup.class);
			intent.putExtra("User_type", userTypeString);
			
		startActivity(intent);
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
}
