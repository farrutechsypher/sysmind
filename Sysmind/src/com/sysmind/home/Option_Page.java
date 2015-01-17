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
import android.widget.Button;
import android.widget.Toast;

import com.csipsimple.R;

public class Option_Page extends Activity {

//	public static String[] departmentArray;
//	static final String KEY_DEPARTMENT = "department";
	String uri;
//	String uri1 = "listDepartments";
//	String URL;

//	public static String[] locationArray;
//	static final String KEY_LOCATION = "locations";
//	String uri2 = "llistLocations";
//	String URL1;

//	public static String[] professionArray;
//	static final String KEY_PROFESSION = "professions";
//	String uri3 = "plistProfessions";
//	String URL2;

	Button userProfileButton, homeButton,option_logout_btn;
	Intent in;
	String UserName, FinalUrl;
	String xml, xml1, xml2;
	Document doc, doc1, doc2;
	NodeList nl, nl1, nl2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.option_page_layout);

		option_logout_btn=(Button)findViewById(R.id.option_logout_btn);
		uri = LoginPage.uri;
		XMLParser parser = new XMLParser();
		//URL = uri + uri1;
		//xml = parser.getXmlFromUrl(URL);

//		URL1 = uri + uri2;
//		xml1 = parser.getXmlFromUrl(URL1);

//		URL2 = uri + uri3;
//		xml2 = parser.getXmlFromUrl(URL2);

//		doc = parser.getDomElement(xml);
//		NodeList nl = doc.getElementsByTagName(KEY_DEPARTMENT);
//		departmentArray = new String[nl.getLength()];
//		for (int i = 0; i < nl.getLength(); i++) {
//			departmentArray[i] = nl.item(i).getTextContent();
//		}

//		doc1 = parser.getDomElement(xml1);
//		nl1 = doc1.getElementsByTagName(KEY_LOCATION);
//		locationArray = new String[nl1.getLength()];
//		for (int i = 0; i < nl1.getLength(); i++) {
//			locationArray[i] = nl1.item(i).getTextContent();
//		}

//		doc2 = parser.getDomElement(xml2);
//		nl2 = doc2.getElementsByTagName(KEY_PROFESSION);
//		professionArray = new String[nl2.getLength()];
//		for (int i = 0; i < nl2.getLength(); i++) {
//			professionArray[i] = nl2.item(i).getTextContent();
//		}

		in = getIntent();
		UserName = in.getStringExtra("UName");
		FinalUrl = in.getStringExtra("FunalUrl");
//		Toast.makeText(getApplicationContext(), "" + UserName, Toast.LENGTH_LONG).show();
		// XMLParser parser = new XMLParser();
		// String xml = parser.getXmlFromUrl(FinalUrl); // getting XML

		// Toast.makeText(getApplicationContext(), ""+xml,
		// Toast.LENGTH_LONG).show();
		// if( xml.equals("INSERTED") || xml.startsWith("<?xml") )
		// {

		userProfileButton = (Button) findViewById(R.id.user_profile_icon_btn);
		homeButton = (Button) findViewById(R.id.home_button_icon_btn);

		userProfileButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				in = new Intent(getApplicationContext(),com.sysmind.home.ProfilePage.class);
				in.putExtra("UName", UserName);
				startActivity(in);
			}
		});

		homeButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				in = new Intent(getApplicationContext(),com.sysmind.home.SysmindHomePage.class);
				in.putExtra("fnalurl", FinalUrl);
				startActivity(in);

			}
		});
		
		option_logout_btn.setOnClickListener(new View.OnClickListener() {
 			
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
		
	}
	
	protected void onPause() {
	    super.onPause();

	    LoginPage.toEdit.putString("lastActivity", getClass().getName());
	    LoginPage.toEdit.commit();
	}
	
	public void onBackPressed()
	{
	    
		 Intent intent = new Intent(Intent.ACTION_MAIN);
	        intent.addCategory(Intent.CATEGORY_HOME);
	        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        startActivity(intent);
	    
	}
}
