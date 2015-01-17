
package com.sysmind.home;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import com.csipsimple.R;

public class OptionLoginPage extends Activity{
	
     public static	String [] departmentArray;
	 static final String KEY_DEPARTMENT = "department";
	 String uri;
	 String uri1="listDepartments";
     String URL;
     
     
     public static	String [] locationArray;
     static final String KEY_LOCATION = "locations";
     String uri2="llistLocations";
     String URL1;
	
	 
	 public static	String [] professionArray;
	 static final String KEY_PROFESSION = "professions";
	 String uri3="plistProfessions";
	 String URL2;
	 
	 
	Button user_profile_icon_btn,home_button_icon_btn;
	Intent in;
	String UserName;
	String xml,xml1,xml2;
	Document doc,doc1,doc2;
	NodeList nl,nl1,nl2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	    WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.option_page_layout);
		
		
	
		uri=LoginPage.uri;
		XMLParser parser = new XMLParser();
		URL=uri+uri1;
		xml = parser.getXmlFromUrl(URL);
		
		
		
		URL1=uri+uri2;
		xml1 = parser.getXmlFromUrl(URL1);
		
		URL2=uri+uri3;
		xml2 = parser.getXmlFromUrl(URL2);
		
		
		doc = parser.getDomElement(xml);
		NodeList nl = doc.getElementsByTagName(KEY_DEPARTMENT);
        departmentArray = new String[nl.getLength()];
        for (int i = 0; i < nl.getLength(); i++) {
		departmentArray[i] = nl.item(i).getTextContent();
				}
        
        doc1 = parser.getDomElement(xml1);
	    nl1 = doc1.getElementsByTagName(KEY_LOCATION);
	    locationArray = new String[nl1.getLength()];
        for (int i = 0; i < nl1.getLength(); i++) {
		locationArray[i] = nl1.item(i).getTextContent();
				}
        
        doc2 = parser.getDomElement(xml2);
	    nl2 = doc2.getElementsByTagName(KEY_PROFESSION);
	    professionArray = new String[nl2.getLength()];
        for (int i = 0; i < nl2.getLength(); i++) {
        professionArray[i] = nl2.item(i).getTextContent();
				}
        
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
		in = getIntent();
        UserName = in.getStringExtra("UName");
        
        //Toast.makeText(getApplicationContext(), ""+ UserName, Toast.LENGTH_LONG).show();
		
		user_profile_icon_btn=(Button)findViewById(R.id.user_profile_icon_btn);
		home_button_icon_btn=(Button)findViewById(R.id.home_button_icon_btn);
		
		
		user_profile_icon_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				in=new Intent(getApplicationContext(),com.sysmind.home.ProfilePage.class);
				
				in.putExtra("UName",UserName);
				startActivity(in);
				
				
				
				
			}
		});
		
		
		home_button_icon_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				in=new Intent(getApplicationContext(),com.sysmind.home.SysmindHomePage.class);
				startActivity(in);
				
				
			}
		});
	
	}

}
