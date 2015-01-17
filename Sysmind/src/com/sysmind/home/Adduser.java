package com.sysmind.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.csipsimple.R;

public class Adduser extends Activity {
	static final String KEY_USERS_TYPE = "users";
	static final String KEY_USERS_ROW = "row";
	String uri1=LoginPage.uri;
	String uri=uri1+"ualllistUsers";
	String[] arr={"l1","l2","l3"};
	ArrayAdapter<String> a;
	ListAdapter adapter;
	String xml,loginUser;
	Document doc;
	NodeList nl;
	Button addUserBackButton,addUserLogoutButton;
	Intent in;
	ListView li;
	ArrayAdapter<String> add_user;
	ArrayList<AdduserSampleBean> list = new ArrayList<AdduserSampleBean>();
	EditText inputSearch;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_user);
		addUserBackButton=(Button)findViewById(R.id.adduser_backbtn);
		addUserLogoutButton=(Button)findViewById(R.id.adduser_logout_btn);
		loginUser= LoginPage.sh_Pref.getString("Username", null);
		ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();
		XMLParser parser = new XMLParser();
		parser.params.add(new BasicNameValuePair("userName",loginUser));
		xml = parser.getXmlFromUrl(uri);
//		Toast.makeText(getApplicationContext(), "loginUser=== "+loginUser, Toast.LENGTH_LONG).show();
		li=(ListView)findViewById(R.id.listViewAdduser);
	//	inputSearch = (EditText) findViewById(R.id.inputSearch);
		doc = parser.getDomElement(xml);
		nl = doc.getElementsByTagName(KEY_USERS_ROW);
		
		
		LoginPage.toEdit.putBoolean("IS_LOGIN", true);
		LoginPage.toEdit.putString("selectedMenu", "adduser"); 
		
		LoginPage.toEdit.commit(); 
		
		
		for (int i = 0; i < nl.getLength(); i++) {
			//	Toast.makeText(getApplicationContext(), "Welcum to user="+nl, Toast.LENGTH_LONG).show();	
			Element e = (Element) nl.item(i);
			AdduserSampleBean s = new AdduserSampleBean();
			//s.setId(parser.getValue(e, KEY_DEPARTMENT_ROW));
			s.setValue(parser.getValue(e, KEY_USERS_TYPE));
			HashMap<String, String> map = new HashMap<String, String>();

			menuItems.add(map);
			list.add(s);

			// Toast.makeText(getApplicationContext(), "Welcum to list="+list, Toast.LENGTH_LONG).show();
		}

		adapter = new SimpleAdapter(this, menuItems,R.layout.adduser_listview,
				new String[] { KEY_USERS_TYPE}, new int[] {
				R.id.adduser_button,
			//	R.id.adduser_conf_button
		});
		adapter=new AdduserCustomerAdapter(Adduser.this,list);

		li.setAdapter(adapter);

		addUserBackButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				in=new Intent(getApplicationContext(),com.sysmind.home.ConferenceCall.class);
				startActivity(in);
			}
		});

		addUserLogoutButton.setOnClickListener(new View.OnClickListener() {

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
	    	intent = new Intent(Adduser.this, ConferenceCall.class);
	        startActivity(intent);
	    
	}


}
