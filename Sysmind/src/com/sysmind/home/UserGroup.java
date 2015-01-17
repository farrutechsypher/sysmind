
package com.sysmind.home;

import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.csipsimple.R;

public class UserGroup extends Activity{

	Document doc;

	String FinalUrl,loginUser;
	//	String uri = "http://192.168.1.92:8084/SysmindServices/";
	//	String uri1="usersgroupUsers?userName=b";

	//String uri="http://192.168.1.92:8084/SysmindServices/usersgroupUsers?userName="+LoginPage.userNameString;
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

		setContentView(R.layout.user_group);
		Button add_user_bk=(Button)findViewById(R.id.usergrp_backbtn);
		loginUser= LoginPage.sh_Pref.getString("Username", null);
		//Button add_user_logout=(Button)findViewById(R.id.grpscrn_logout_btn);
		//	uri=LoginPage.uri;

		userGroupListView=(ListView)findViewById(R.id.usergrp_listView);
		Button grp_user_logout_btn=(Button)findViewById(R.id.grp_user_logout_btn);

		grp_user_logout_btn.setOnClickListener(new View.OnClickListener() {

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

		FinalUrl=uri+uri1;
		XMLParser parser = new XMLParser();
		
		parser.params.add(new BasicNameValuePair("userName", loginUser));
		
		String xml = parser.getXmlFromUrl(FinalUrl);
		//Toast.makeText(getApplicationContext(), "Welcum to conference ="+xml, Toast.LENGTH_LONG).show();
		doc = parser.getDomElement(xml);
	//	Toast.makeText(getApplicationContext(), "Welcum to Doc ="+doc, Toast.LENGTH_LONG).show();
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
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {

				String  groupname= (String)userGroupListView.getItemAtPosition(position);
			//	in=new Intent(UserGroup.this,UsersInMyGroup.class);
				in=new Intent(UserGroup.this,UserInGroupConf.class);
				
				LoginPage.toEdit.putBoolean("IS_LOGIN", true);
				LoginPage.toEdit.putString("GroupName", groupname); 
			
				LoginPage.toEdit.commit(); 
//				in.putExtra("GroupName",groupname);
				startActivity(in);
			}
		});

		add_user_bk.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				in=new Intent(getApplicationContext(),com.sysmind.home.ConferenceCall.class);
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
	    	intent = new Intent(UserGroup.this, ConferenceCall.class);
	        startActivity(intent);
	    
	}

}
