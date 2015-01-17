package com.sysmind.home;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.csipsimple.R;

public class UserInGroupConf extends Activity {
	static final String KEY_USERS_TYPE = "users";
	static final String KEY_USERS_ROW = "row";
	String uri1=LoginPage.uri,loginUser;
	
	//String uri=uri1+"usersingroupUsers?userGroupName=Project5&userName=a";
	String uri="usersingroupUsers?";
	String uri2="conferenceRequestGroupSysmind?";
	//userGroupName=Project5&userName=a";
	String[] arr={"l1","l2","l3"};
	ArrayAdapter<String> a;
	ListAdapter adapter;
	Boolean ok = false;
	String xml,groupType,finalUrl,xml1;
	Document doc;
	NodeList nl;
	Button addGroupBackButton,addGroupLogoutButton,addallusers;
	Intent in;
	ListView li;
	ArrayAdapter<String> add_user;
	ArrayList<UserInGroupConfSampleBean> list = new ArrayList<UserInGroupConfSampleBean>();
	EditText inputSearch;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_in_group_conf);
		addGroupBackButton=(Button)findViewById(R.id.addGroupConf_backbtn);
		addGroupLogoutButton=(Button)findViewById(R.id.addGroupConf_logout_btn);
		addallusers=(Button)findViewById(R.id.addallusers);
		//ListView li=(ListView)findViewById(R.id.listViewAdduser);
//		in = getIntent();
//		groupType= in.getStringExtra("GroupName");
		
		groupType= LoginPage.sh_Pref.getString("GroupName", null);
		loginUser= LoginPage.sh_Pref.getString("Username", null);
		/*a=new ArrayAdapter<String>(getApplicationContext(),R.layout.adduser_listview,arr);
		li.setAdapter(a);*/
		TextView grpTextview = (TextView)findViewById(R.id.addGroupConf_textview);

		grpTextview.setText(groupType);
//		finalUrl=uri1+uri+"userGroupName="+groupType+"&"+"userName="+LoginPage.userNameString;
		finalUrl=uri1+uri;
		//Toast.makeText(getApplicationContext(), "ADD GROUP CONF="+finalUrl, Toast.LENGTH_LONG).show();
		
		ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();
		XMLParser parser = new XMLParser();
		
		parser.params.add(new BasicNameValuePair("userGroupName", groupType));
		parser.params.add(new BasicNameValuePair("userName", loginUser));
//		Toast.makeText(getApplicationContext(), "login user name===== "+loginUser, Toast.LENGTH_LONG).show();
		
		xml = parser.getXmlFromUrl(finalUrl);
		//Toast.makeText(getApplicationContext(), "Welcum to user="+xml, Toast.LENGTH_LONG).show();
		li=(ListView)findViewById(R.id.listViewaddGroupConf);
	//	inputSearch = (EditText) findViewById(R.id.inputSearch);
		doc = parser.getDomElement(xml);
		nl = doc.getElementsByTagName(KEY_USERS_ROW);

		
		LoginPage.toEdit.putBoolean("IS_LOGIN", true);
		LoginPage.toEdit.putString("selectedMenu", "addGroup"); 
		
		LoginPage.toEdit.commit(); 
		
		
		for (int i = 0; i < nl.getLength(); i++) {
			//	Toast.makeText(getApplicationContext(), "Welcum to user="+nl, Toast.LENGTH_LONG).show();	
			Element e = (Element) nl.item(i);
			UserInGroupConfSampleBean s = new UserInGroupConfSampleBean();
			//s.setId(parser.getValue(e, KEY_DEPARTMENT_ROW));
			s.setValue(parser.getValue(e, KEY_USERS_TYPE));
			HashMap<String, String> map = new HashMap<String, String>();

			menuItems.add(map);
			list.add(s);
			
		}
		if(list.size()!=0){
		adapter = new SimpleAdapter(this, menuItems,R.layout.user_in_group_conf_listview,
				new String[] { KEY_USERS_TYPE}, new int[] {
				R.id.addGroupConf_button,
			//	R.id.adduser_conf_button
		});
		adapter=new UserInGroupConfCustAdapter(UserInGroupConf.this,list);

		li.setAdapter(adapter);
		}else{
			 AlertDialog.Builder alertboxDuplicate = new
					 AlertDialog.Builder(UserInGroupConf.this);
					 //
				//	 alertboxDuplicate.setMessage("Username not matched with this email address");
					 alertboxDuplicate.setMessage("NO USER EXISTS");
					 alertboxDuplicate.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
					 public void onClick(DialogInterface arg0, int arg1) {
//					 ok=false;
							in = new Intent(UserInGroupConf.this, UserGroup.class);
							startActivity(in);
					 }
					 });
					
					 alertboxDuplicate.show();
		
		}
		addGroupBackButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				in=new Intent(getApplicationContext(),com.sysmind.home.ConferenceCall.class);
				startActivity(in);
			}
		});

		addallusers.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Toast.makeText(getApplicationContext(), "WELCOME :D", Toast.LENGTH_LONG).show();
				String allUserUrl = uri1 + uri2;
 				XMLParser parser = new XMLParser();
				parser.params.add(new BasicNameValuePair("confbridgeExtension", LoginPage.myExtension));
				parser.params.add(new BasicNameValuePair("selectedGroup", groupType));			
				parser.params.add(new BasicNameValuePair("userName",loginUser));
				
			    xml1 = parser.getXmlFromUrl(allUserUrl);
			}
		});
		
		
		addGroupLogoutButton.setOnClickListener(new View.OnClickListener() {

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
	    	intent = new Intent(UserInGroupConf.this, UserGroup.class);
	        startActivity(intent);
	    
	}
	//	}

}
