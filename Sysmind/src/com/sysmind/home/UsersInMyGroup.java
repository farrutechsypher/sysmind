package com.sysmind.home;

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
import com.csipsimple.R;

public class UsersInMyGroup extends Activity{
	String uri1="usersingroupUsers?";
	ListView useringrp_listView;
	//ImageView usergrp_imageView;
	Document doc;
	Button userInGrpBackBtn,userInGrpLogoutBtn;
	String finalUrl;
	 public static	String [] usergroupArray;
	 static final String KEY_USERS = "users";
	 ArrayAdapter< String> myusers;
	 ArrayAdapter< String> myusers1;
	 String groupType,xml1;
	 EditText inputSearchGroup;
	Intent in;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.group_screen);
		userInGrpBackBtn=(Button)findViewById(R.id.grpscrn_backbtn);
		userInGrpLogoutBtn=(Button)findViewById(R.id.grpscrn_logout_btn);
		in = getIntent();
		groupType= in.getStringExtra("GroupName");
		String uri=LoginPage.uri;
		finalUrl=uri+uri1+"userName="+LoginPage.userNameString+"&"+"userGroupName="+groupType;
		//Toast.makeText(getApplicationContext(), "Welcum to user*******="+finalUrl, Toast.LENGTH_LONG).show();
		String FinalUrl=finalUrl.replaceAll(" ", "%20");
		
	
		
		useringrp_listView=(ListView)findViewById(R.id.sysmind_grpscrn_listview);
		inputSearchGroup = (EditText) findViewById(R.id.inputSearchGroup);
//		usergrp_imageView=(ImageView)findViewById(R.id.usergrp_imageView);
//		
//		usergrp_imageView.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				in=new Intent(getApplicationContext(),com.sysmind.home.AddGroupName.class);
//				startActivity(in);
//			}
//		});
		
		
		XMLParser parser = new XMLParser();
		String xml = parser.getXmlFromUrl(FinalUrl);
		//Toast.makeText(getApplicationContext(), "Welcum to user="+xml, Toast.LENGTH_LONG).show();
		doc = parser.getDomElement(xml);
		
		NodeList nl = doc.getElementsByTagName(KEY_USERS);
		usergroupArray = new String[nl.getLength()];
        for (int i = 0; i < nl.getLength(); i++) {
        	usergroupArray[i] = nl.item(i).getTextContent();
			
        }
        
//        myusers=new ArrayAdapter<String>(getApplicationContext(),
//        		R.layout.useringrp_singlelist,
//        		R.id.listTextViewinGrop,
//        		usergroupArray);
        
        myusers1=new ArrayAdapter<String>(getApplicationContext(),
        		R.layout.useringrp_singlelist,
        		R.id.textView1inGrop,
        		usergroupArray);
	
//        useringrp_listView.setAdapter(myusers);
        useringrp_listView.setAdapter(myusers1);
    	
    	useringrp_listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
			//	TextView t1=(TextView)findViewById(R.id.textView1inGrop);	
				String  user= (String)useringrp_listView.getItemAtPosition(position);
				//String txt1u = ((Button) findViewById(R.id.textView1inGrop)).getText().toString() ;
				//Toast.makeText(getApplicationContext(), "GROUP user=" + user, Toast.LENGTH_LONG).show();
				
				String finalUrl11 = "http://192.168.1.92:8084/SysmindServices/confrenceRequestSysmind?" + "confbridgeExtension="+LoginPage.myExtension+"&joinUser="+user;
//				String  groupname= (String)userGroupListView.getItemAtPosition(position);
//				in=new Intent(UserGroup.this,UsersInMyGroup.class);
//				in.putExtra("GroupName",groupname);
//				startActivity(in);
			}
		});
    	
    	inputSearchGroup.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				UsersInMyGroup.this.myusers1.getFilter().filter(cs);
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                    int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
	        public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
    	

        userInGrpBackBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				in=new Intent(getApplicationContext(),com.sysmind.home.UserGroup.class);
				startActivity(in);
			}
		});
        
        userInGrpLogoutBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				in=new Intent(getApplicationContext(),com.sysmind.home.LoginPage.class);
				startActivity(in);
				SharedPreferences sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE); 
 				Editor toEdit = sh_Pref.edit();
 				toEdit.clear();
 				toEdit.commit();
				finish();
			}
		});
	}

	protected void onPause() {
	    super.onPause();
	    LoginPage.toEdit.putString("lastActivity", getClass().getName());
	    LoginPage.toEdit.commit();
	}
	
}
