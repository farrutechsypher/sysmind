

package com.sysmind.home;

import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.csipsimple.R;

public class MyGroupHomePage extends Activity{

	
	
	Document doc;

	String Url,loginUser;
	//	String uri = "http://192.168.1.92:8084/SysmindServices/";
	//	String uri1="usersgroupUsers?userName=b";
	String uri=LoginPage.uri;
	//	String uri1="usersgroupUsers?userName="+LoginPage.userNameString;
	String uri1="usersgroupUsers?";
	ListView userGroupListView;
	ImageView userGroupImageView;

	public static	String [] usergroupArray;
	static final String KEY_GROUP = "group";
	ArrayAdapter< String> mygroups;
	Intent in;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.my_groups);
		Button add_user_bk=(Button)findViewById(R.id.usergrp_backbtn);
		Button mygrp_btn = (Button) findViewById(R.id.mygrp_btn);
		loginUser= LoginPage.sh_Pref.getString("Username", null);
		//Button add_user_logout=(Button)findViewById(R.id.grpscrn_logout_btn);
		//	uri=LoginPage.uri;

		userGroupListView=(ListView)findViewById(R.id.usergrp_listView);
		//userGroupImageView=(ImageView)findViewById(R.id.usergrp_imageView);

//		userGroupImageView.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//
//				in=new Intent(getApplicationContext(),com.sysmind.home.AddGroupName.class);
//				startActivity(in);
//
//			}
//		});

		Url=uri+uri1;
		XMLParser parser = new XMLParser();
		
		parser.params.add(new BasicNameValuePair("userName",loginUser));
		String xml = parser.getXmlFromUrl(Url);
		//Toast.makeText(getApplicationContext(), "Welcum to user="+xml, Toast.LENGTH_LONG).show();
		doc = parser.getDomElement(xml);

		NodeList nl = doc.getElementsByTagName(KEY_GROUP);
		usergroupArray = new String[nl.getLength()];
		for (int i = 0; i < nl.getLength(); i++) {
			usergroupArray[i] = nl.item(i).getTextContent();

		}

		mygroups=new ArrayAdapter<String>(getApplicationContext(),R.layout.usergrp_singlelist,usergroupArray);
		//	Toast.makeText(getApplicationContext(), ""+xml, Toast.LENGTH_LONG).show();

		userGroupListView.setAdapter(mygroups);

		userGroupListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

				String  mygroupname= (String)userGroupListView.getItemAtPosition(position);
				Toast.makeText(getApplicationContext(), "Welcome to group***  "+mygroupname, Toast.LENGTH_LONG).show();;
		
				in = new Intent(MyGroupHomePage.this, UsersInGroupMain.class);
//				intent.putExtra("Department_type", department_type);
		
				
				LoginPage.toEdit.putBoolean("IS_LOGIN", true);
				LoginPage.toEdit.putString("Group_name", mygroupname); 
			
				LoginPage.toEdit.commit(); 
				startActivity(in);
				
				
			}
		});
		
		
//		userGroupListView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
//					long arg3) {
//
//				String  groupname= (String)userGroupListView.getItemAtPosition(position);
//				in=new Intent(UserGroup.this,UsersInMyGroup.class);
//				in.putExtra("GroupName",groupname);
//				startActivity(in);
//			}
//		});
		mygrp_btn.setOnClickListener(new View.OnClickListener() {
 			
 			@Override
 			public void onClick(View v) {
 				// TODO Auto-generated method stub
 				in=new Intent(getApplicationContext(),com.sysmind.home.LoginPage.class);
 				startActivity(in);
 				
 				LoginPage.toEdit.clear();
 				LoginPage.toEdit.commit();
 				finish();
 			}
 		});   


		add_user_bk.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				in=new Intent(getApplicationContext(),com.sysmind.home.SysmindHomePage.class);
				startActivity(in);
			}
		});

		//        add_user_logout.setOnClickListener(new View.OnClickListener() {
		//			
		//			@Override
		//			public void onClick(View v) {
		//				// TODO Auto-generated method stub
		//				in=new Intent(getApplicationContext(),com.sysmind.home.LoginPage.class);
		//				startActivity(in);
		//			}
		//		});
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
	    	intent = new Intent(MyGroupHomePage.this, SysmindHomePage.class);
	        startActivity(intent);
	    
	}

}
