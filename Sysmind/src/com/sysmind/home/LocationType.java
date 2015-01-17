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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.csipsimple.R;

public class LocationType extends Activity /*implements android.widget.AdapterView.OnItemClickListener*/ {
	
	public static String [] department_typeArray;
	static final String KEY_LOCATION_TYPE = "users";
	static final String KEY_EXTENSION_TYPE = "extension";
	static final String KEY_LOCATION_ROW = "row";
	Boolean ok = false;
	String uri1="ulistUsers?";
    String URL;
    String uri,loginUser;
	TextView loctyp_textview;
	Intent in;
	String locationtype;
	String xml,FinalUrl,finalUrl;
	Document doc;
	NodeList nl;
	
	Button sysmind_loctyppg_backbtn,sysmind_loctyp_logout_btn,addGroup,dpGroup;
	ArrayAdapter<String> departmentType;
	ListView lv;
	ArrayList<DepSampleBean> list = new ArrayList<DepSampleBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.locationtype);
		
		addGroup=(Button) findViewById(R.id.loctyp_addgrpbtn);
		dpGroup=(Button) findViewById(R.id.loctyp_connectbtn);
		
		sysmind_loctyppg_backbtn=(Button)findViewById(R.id.sysmind_loctyppg_backbtn);
		sysmind_loctyp_logout_btn=(Button)findViewById(R.id.sysmind_loctyp_logout_btn);
		
//		in = getIntent();
//		locationtype= in.getStringExtra("Location_type");
		
		locationtype= LoginPage.sh_Pref.getString("Location_type", null);
		loginUser= LoginPage.sh_Pref.getString("Username", null);
		LoginPage.toEdit.putBoolean("IS_LOGIN", true);
		LoginPage.toEdit.putString("selectedMenu", "location"); 
		
		LoginPage.toEdit.commit(); 
			
		
		uri=LoginPage.uri;
		URL=uri+uri1;
		
    //    String finalUrl=URL+"locName="+locationtype;
		String finalUrl=URL;
		
		String FinalUrl=finalUrl.replaceAll(" ", "%20");
		
		ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();
		XMLParser parser = new XMLParser();
		parser.params.add(new BasicNameValuePair("locName", locationtype));
		parser.params.add(new BasicNameValuePair("userName", loginUser));
		xml = parser.getXmlFromUrl(FinalUrl);

		loctyp_textview=(TextView)findViewById(R.id.loctyp_textview);
		
		loctyp_textview.setText(locationtype);
		
		lv=(ListView)findViewById(R.id.sysmind_loctyp_listview);
	
		
		doc = parser.getDomElement(xml);
		nl = doc.getElementsByTagName(KEY_LOCATION_ROW);

        for (int i = 0; i < nl.getLength(); i++) {
        	
        	Element e = (Element) nl.item(i);
        	DepSampleBean s = new DepSampleBean();
        	//s.setId(parser.getValue(e, KEY_DEPARTMENT_ROW));
        	s.setUser(parser.getValue(e, KEY_LOCATION_TYPE));
    		s.setExtension(parser.getValue(e, KEY_EXTENSION_TYPE));
            HashMap<String, String> map = new HashMap<String, String>();
				
			menuItems.add(map);
		    list.add(s);
        }
        //
        if(list.size()!=0){
        	 ListAdapter adapter = new SimpleAdapter(this, menuItems,R.layout.locationtype_singleitem,new String[]
		{ KEY_LOCATION_TYPE}, new int[] {
     		R.id.locationtyp_username});
        	 adapter=new LocCustomAdapter(LocationType.this,list);
        	 lv.setAdapter(adapter);
    		}else{
    			//Toast.makeText(getApplicationContext(), "LIST IS EMPTY*****=", Toast.LENGTH_LONG).show();		
    			 AlertDialog.Builder alertboxDuplicate = new
    					 AlertDialog.Builder(LocationType.this);
    				//	 alertboxDuplicate.setMessage("Username not matched with this email address");
    					 alertboxDuplicate.setMessage("NO USER EXISTS");
    					 alertboxDuplicate.setNegativeButton("Ok", new
    					 DialogInterface.OnClickListener() {
    					 public void onClick(DialogInterface arg0, int arg1) {
//    					 ok=false;
    						 in=new Intent(getApplicationContext(),com.sysmind.home.SysmindLocationPage.class);
    		    				startActivity(in);
    					 }
    					 });
    					
    					 alertboxDuplicate.show();
    		}
        
           
            sysmind_loctyppg_backbtn.setOnClickListener(new View.OnClickListener() {
    			
    			@Override
    			public void onClick(View arg0) {
    				// TODO Auto-generated method stub
    				in=new Intent(getApplicationContext(),com.sysmind.home.SysmindLocationPage.class);
    				startActivity(in);
    				
    			}
    		});
            sysmind_loctyp_logout_btn.setOnClickListener(new View.OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				in=new Intent(getApplicationContext(),com.sysmind.home.LoginPage.class);
    				startActivity(in);
    				
//    				SharedPreferences sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE); 
//     				Editor toEdit = sh_Pref.edit();
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
	    	intent = new Intent(LocationType.this, SysmindLocationPage.class);
	        startActivity(intent);
	    
	}
	
	}

	
