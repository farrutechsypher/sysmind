package com.sysmind.home;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

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
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.csipsimple.R;

public class SysmindDepartmentPage extends Activity {
	
	
//    String[] departmentArray;
//	String KEY_DEPARTMENT = "department";
//	String uri;
//	String uri1 = "listDepartments";
//	String URL,xml;
//	Document doc;
	
	
	

	ListView sysmindDepartmentListview;
	String[] depart;
	ArrayAdapter<String> Department;
	Button sysmindDpmntLogoutBtn, sysmindDpmntBackBtn;
	Intent intent;
//	SharedPreferences sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE); 
//		Editor toEdit = sh_Pref.edit();
	static String position;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		Toast.makeText(getApplicationContext(), "welcome to sysmind department", Toast.LENGTH_LONG).show();
		setContentView(R.layout.sysmind_department_layout);
		
//		uri = LoginPage.uri;
//		XMLParser parser = new XMLParser();
//		URL = uri + uri1;
//		xml = parser.getXmlFromUrl(URL);
//		
//		doc = parser.getDomElement(xml);
//		NodeList nl = doc.getElementsByTagName(KEY_DEPARTMENT);
//		departmentArray = new String[nl.getLength()];
//		for (int i = 0; i < nl.getLength(); i++) {
//			departmentArray[i] = nl.item(i).getTextContent();
//		}
		
//		Toast.makeText(getApplicationContext(), "Welcum TO DEPT**URL is "+URL, Toast.LENGTH_LONG).show();
//		Toast.makeText(getApplicationContext(), "Welcum to user=", Toast.LENGTH_LONG).show();
		sysmindDepartmentListview = (ListView) findViewById(R.id.sysmind_department_pg_listview);

		sysmindDpmntLogoutBtn = (Button) findViewById(R.id.sysmind_dpg_logout_btn);

		sysmindDpmntBackBtn = (Button) findViewById(R.id.sysmind_dpg_backbtn);

		depart = LoginPage.departmentArray;
//		Toast.makeText(getApplicationContext(), "depart value is"+depart, Toast.LENGTH_LONG).show();
		Department = new ArrayAdapter<String>(getApplicationContext(), R.layout.sysmind_department_single_item, depart);
		sysmindDepartmentListview.setAdapter(Department);

		sysmindDepartmentListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

				String department_type = (String) sysmindDepartmentListview.getItemAtPosition(position);
				intent = new Intent(SysmindDepartmentPage.this, DepartmentType.class);
//				intent.putExtra("Department_type", department_type);
		
				
				LoginPage.toEdit.putBoolean("IS_LOGIN", true);
				LoginPage.toEdit.putString("Department_type", department_type); 
			
				LoginPage.toEdit.commit(); 
				
				startActivity(intent);
			}
		});

		sysmindDpmntLogoutBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				intent = new Intent(getApplicationContext(), com.sysmind.home.LoginPage.class);
				startActivity(intent);
			
				LoginPage.toEdit.clear();
				LoginPage.toEdit.commit();
				finish();
			}
		});

		sysmindDpmntBackBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				intent = new Intent(getApplicationContext(), com.sysmind.home.SysmindHomePage.class);
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
	
	public void onBackPressed()
	{
	    
	        Intent intent = new Intent(Intent.ACTION_MAIN);
	    	intent = new Intent(SysmindDepartmentPage.this, SysmindHomePage.class);
	        startActivity(intent);
	    
	}
	
//	 public void exit(View view){
//	      moveTaskToBack(true); 
//	      SysmindDepartmentPage.this.finish();
//	   }
}
