
package com.sysmind.home;

import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.csipsimple.R;

public class DepartmentUsers extends Activity{
	String uri1="allListUsers?";
	ListView departmentUserListView;
	//ImageView usergrp_imageView;
	Document doc;
	Button departmentUserBackBtn,departmentUserLogoutBtn;
	String finalUrl;
	 public static	String [] depUserArray;
	 static final String KEY_USERS = "users";
	 ArrayAdapter< String> myusers;
	 ArrayAdapter< String> myusers1;
	 String departmentTypeString,xml1,loginUser;
	 EditText inputSearchdepartmentUser;
	Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.department_user);
		departmentUserBackBtn=(Button)findViewById(R.id.department_user_backbtn);
		departmentUserLogoutBtn=(Button)findViewById(R.id.department_user_logout_btn);
		intent = getIntent();
		departmentTypeString= intent.getStringExtra("Department_type");
		loginUser= LoginPage.sh_Pref.getString("Username", null);
		String uri="http://192.168.1.217:8084/SysmindServices/";
	//	finalUrl=uri+uri1+"deptName="+departmentTypeString+"&"+"userName="+LoginPage.userNameString;
	
		finalUrl=uri+uri1;
		//Toast.makeText(getApplicationContext(), "Welcum to user*******="+finalUrl, Toast.LENGTH_LONG).show();
		String FinalUrl=finalUrl.replaceAll(" ", "%20");
		//Toast.makeText(getApplicationContext(), "Welcum to user((((((="+FinalUrl, Toast.LENGTH_LONG).show();
		
		departmentUserListView=(ListView)findViewById(R.id.department_user_listview);
	//	inputSearchdepartmentUser = (EditText) findViewById(R.id.inputSearchGroup);
		
		
		XMLParser parser = new XMLParser();
		
		parser.params.add(new BasicNameValuePair("deptName", departmentTypeString));
		parser.params.add(new BasicNameValuePair("userName", loginUser));
	
		String xml = parser.getXmlFromUrl(FinalUrl);
		//Toast.makeText(getApplicationContext(), "Welcum to user="+xml, Toast.LENGTH_LONG).show();
		doc = parser.getDomElement(xml);
		
		NodeList nl = doc.getElementsByTagName(KEY_USERS);
		depUserArray = new String[nl.getLength()];
        for (int i = 0; i < nl.getLength(); i++) {
        	depUserArray[i] = nl.item(i).getTextContent();
			
        }
//        
        myusers1=new ArrayAdapter<String>(getApplicationContext(),R.layout.department_user_singlelist,
        		R.id.departmentUser_listTextView,depUserArray);
//	
        departmentUserListView.setAdapter(myusers1);
//    	
        departmentUserListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				String user_type = (String) departmentUserListView.getItemAtPosition(position);
				intent = new Intent(DepartmentUsers.this, DepartmentConnection.class);
				intent.putExtra("User_type", user_type);
				//intent.putExtra("Departmnt_type", departmentTypeString);
				startActivity(intent);		
				
			}
		});
//    	
        departmentUserBackBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				intent=new Intent(getApplicationContext(),com.sysmind.home.SysmindDepartmentPage.class);
				startActivity(intent);
			}
		});
//        
        departmentUserLogoutBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				intent=new Intent(getApplicationContext(),com.sysmind.home.LoginPage.class);
//				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				intent.putExtra("EXIT", true);
				startActivity(intent);
//				SharedPreferences sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE); 
// 				Editor toEdit = sh_Pref.edit();
				LoginPage.toEdit.clear();
				LoginPage.toEdit.commit();
				
				finish();
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
