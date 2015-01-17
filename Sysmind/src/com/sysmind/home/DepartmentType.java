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
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.csipsimple.R;

public class DepartmentType extends Activity /*implements android.widget.AdapterView.OnItemClickListener*/ {

	public static String [] department_typeArray;
	static final String KEY_DEPARTMENT_TYPE = "users";
	static final String KEY_EXTENSION_TYPE = "extension";
	static final String KEY_DEPARTMENT_ROW = "row";
	String urlAction="ulistUsers?";
	Boolean ok = false;
	String uri;
	String URL;
	TextView contaqpgTextview;
	Intent intent;
	String departmentTypeString;
	String serverResponse,FinalUrl,finalUrl,loginUser;
//	SharedPreferences sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);
//    Editor toEdit = sh_Pref.edit();
	Document doc;
	NodeList nl;

	Button DptmentTypeBckButton,DptmentTypeLogoutButton,addGroupButton,connectButton;
	ArrayAdapter<String> departmentType;
	ListView lv;
	ArrayList<DepSampleBean> list = new ArrayList<DepSampleBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.departmenttype);

		addGroupButton=(Button) findViewById(R.id.addGroupbtn);
		connectButton=(Button) findViewById(R.id.connectbtn);

		DptmentTypeBckButton=(Button)findViewById(R.id.contaqpg_backbtn);
		DptmentTypeLogoutButton=(Button)findViewById(R.id.contaqpg_logout_btn);
//		intent = getIntent();
//		departmentTypeString= intent.getStringExtra("Department_type");
		
		departmentTypeString= LoginPage.sh_Pref.getString("Department_type", null);
		loginUser= LoginPage.sh_Pref.getString("Username", null);
		
		LoginPage.toEdit.putBoolean("IS_LOGIN", true);
		LoginPage.toEdit.putString("selectedMenu", "department"); 
		
		LoginPage.toEdit.commit(); 
			
		
		uri=LoginPage.uri;
		URL=uri+urlAction;

		//finalUrl=URL+"deptName="+departmentTypeString;
		finalUrl=URL;
	//	Toast.makeText(getApplicationContext(), "Welcum to user*******="+finalUrl, Toast.LENGTH_LONG).show();
		String FinalUrl=finalUrl.replaceAll(" ", "%20");
		//Toast.makeText(getApplicationContext(), "Welcum to user((((((="+FinalUrl, Toast.LENGTH_LONG).show();
		ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();
		XMLParser parser = new XMLParser();
		
		parser.params.add(new BasicNameValuePair("deptName", departmentTypeString));
		parser.params.add(new BasicNameValuePair("userName", loginUser));
		
		serverResponse = parser.getXmlFromUrl(FinalUrl);
		lv=(ListView)findViewById(R.id.sysmind_contaqpg_listview);
		contaqpgTextview=(TextView)findViewById(R.id.contaqpg_textview);
		contaqpgTextview.setText(departmentTypeString);

		lv=(ListView)findViewById(R.id.sysmind_contaqpg_listview);

		doc = parser.getDomElement(serverResponse);
		nl = doc.getElementsByTagName(KEY_DEPARTMENT_ROW);

		for (int i = 0; i < nl.getLength(); i++) {

			Element e = (Element) nl.item(i);
			DepSampleBean s = new DepSampleBean();
			//s.setId(parser.getValue(e, KEY_DEPARTMENT_ROW));
			s.setUser(parser.getValue(e, KEY_DEPARTMENT_TYPE));
			s.setExtension(parser.getValue(e, KEY_EXTENSION_TYPE));
			HashMap<String, String> map = new HashMap<String, String>();

			menuItems.add(map);
			list.add(s);
		}
	//	Toast.makeText(getApplicationContext(), "LIST SIZE*****="+list.size(), Toast.LENGTH_LONG).show();
		
		if(list.size()!=0) {
			ListAdapter adapter = new SimpleAdapter(this, menuItems,
					R.layout.departmenttype_singleitem,
					new String[] { KEY_DEPARTMENT_TYPE}, new int[] {
					R.id.departmenttyp_username});
	
			adapter=new DeptCustomAdapter(DepartmentType.this,list);
	
			lv.setAdapter(adapter);
		} else {
//			Toast.makeText(getApplicationContext(), "LIST IS EMPTY*****=", Toast.LENGTH_LONG).show();		
			AlertDialog.Builder alertboxDuplicate = new AlertDialog.Builder(DepartmentType.this);
			 //
		//	alertboxDuplicate.setMessage("Username not matched with this email address");
			alertboxDuplicate.setMessage("NO USER EXISTS");
			alertboxDuplicate.setNegativeButton("Ok", new
			DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
//					ok=false;
					intent=new Intent(getApplicationContext(),com.sysmind.home.SysmindDepartmentPage.class);
					startActivity(intent);
				}
			});
			
			alertboxDuplicate.show();
		
		}
		DptmentTypeBckButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				intent=new Intent(getApplicationContext(),com.sysmind.home.SysmindDepartmentPage.class);
				startActivity(intent);

			}
		});

		DptmentTypeLogoutButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				setLoggingOut(true);
				intent=new Intent(getApplicationContext(),com.sysmind.home.LoginPage.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
	    
	    LoginPage.toEdit.putString("lastActivity", getClass().getName());
	    LoginPage.toEdit.commit();
	}

	public void onBackPressed()
	{
        Intent intent = new Intent(Intent.ACTION_MAIN);
    	intent = new Intent(DepartmentType.this, SysmindDepartmentPage.class);
        startActivity(intent);
	    
	}
}


