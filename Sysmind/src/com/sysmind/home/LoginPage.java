package com.sysmind.home;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.csipsimple.R;


public class LoginPage extends Activity {
	
	EditText userNameText, passwordText;
	Button loginButton, registerButton;
	int backButtonCount;
	//public Static String userNameString;
	String passwordString;
	String serverResponse="";
	Intent intent;
	public static SharedPreferences sh_Pref;
//	SharedPreferences pref;
	public static Editor toEdit;
	String name, pass;
//	public static boolean isQuit = false;
	//uri = "http://14.140.220.106:8556/SysmindServices/"
	//http://dial.for-more.biz:12680/SysmindServices
//	public static String uri = "http://192.168.1.12:8090/SysmindServices/";
	
//	public static String uri = "http://dial.for-more.biz:12680/SysmindServices/";
	
	public static String uri = "http://192.168.1.14:8080//SysmindServices/";
	
//	public static String uri = "http://172.16.0.12:8090//SysmindServices/";
	String urlAction = "loginSysmind?";
	public static ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

	//static final String KEY_ServerResponse = "Server-Response";
	//static final String KEY_query = "query";
	//static final String KEY_Extension = "Extension";
	//static final String KEY_ExtPass = "Ext-Pass";
	//static final String KEY_AsteriskIP = "Asterisk-IP";
	Class<?> activityClass;
	static final String KEY_ServerResponse = "Server-Response";
	static final String KEY_query = "query";
	public static String KEY_Extension = "Extension";
	public static final String KEY_ExtPass = "Ext-Pass";
	public static final String KEY_AsteriskIP = "Asterisk-IP";
	static String myExtension, userNameString, myip;
	
	public static String[] locationArray;
	static final String KEY_LOCATION = "locations";
	String uri2 = "llistLocations";
	String URL1,xml1;
	Document doc, doc1, doc2;
	NodeList nl, nl1, nl2;
	
	public static String[] departmentArray;
	static final String KEY_DEPARTMENT = "department";

	String uri1 = "listDepartments";
	String URL,xml;
	XMLParser parser;
	
	public static String[] professionArray;
	static final String KEY_PROFESSION = "professions";
	String uri3 = "plistProfessions";
	String URL2,xml2;
	ProgressDialog progressDialog;
	  private int progressStatus=0;
	  private static int myProgress;
//	  private ProgressDialog progressDialog;
	  private Handler myHandler=new Handler();
//	final AlertDialog.Builder alertbox = new AlertDialog.Builder(LoginPage.this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		if (getIntent().getBooleanExtra("EXIT", false)) {
//		    finish();  
//		}
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);


		setContentView(R.layout.login);
		loginButton = (Button) findViewById(R.id.logpg_btn);
		registerButton = (Button) findViewById(R.id.logpg_reg_btn);
		userNameText = (EditText) findViewById(R.id.logpg_username);
		passwordText = (EditText) findViewById(R.id.logpg_password);
		
		sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);
		toEdit = sh_Pref.edit();

//		registerButton.setEnabled(false);
//		registerButton.setBackgroundColor(Color.TRANSPARENT);s
		
		registerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				intent = new Intent(getApplicationContext(), com.sysmind.home.RegisterPage.class);
				startActivity(intent);
			}
		});
		

		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				// TODO Auto-generated method stub
				userNameString = userNameText.getText().toString();
				passwordString = passwordText.getText().toString();
				
									
	
				if (userNameString != null && userNameString.length() > 0 && passwordString != null && passwordString.length() > 0) {
					// String finalUrl = uri + urlAction + "userName=" +
					// userNameString + "&password=" + passwordString;
					String finalUrl = uri + urlAction;
					parser = new XMLParser();
					parser.params.add(new BasicNameValuePair("userName",userNameString));
					parser.params.add(new BasicNameValuePair("password",passwordString));
					
					GetXMLTask task = new GetXMLTask();
					task.execute(new String [] {finalUrl});
					
//					serverResponse = parser.getXmlFromUrl(finalUrl); // getting XML
	
	//				Toast.makeText(getApplicationContext(),"serverResponse  " + serverResponse,Toast.LENGTH_LONG).show();
					/*toEdit.putBoolean("IS_LOGIN", true);
					toEdit.putString("ServerLoginResponse", serverResponse);
					// toEdit.putString("Password", passwordString);
					toEdit.commit();
	
					if (serverResponse != null && serverResponse.startsWith("<?xml")) {
						// Toast.makeText(getApplicationContext(),
						// "successs"+serverResponse, Toast.LENGTH_LONG).show();
	
						setSharedPrefernces();
						populateLoginResponse();
						populateDeptLocProfValues();
	
						intent = new Intent(getApplicationContext(),com.sysmind.home.Option_Page.class);
						intent.putExtra("FunalUrl", serverResponse);
						intent.putExtra("UName", userNameString);
						startActivity(intent);
						finish();
					}
	
					else if (serverResponse != null && serverResponse.startsWith("notverified")) {
						AlertDialog.Builder alert = new AlertDialog.Builder(LoginPage.this);
						alert.setMessage("YOU ARE NOT VERIFIED");
						alert.setNegativeButton("Ok",new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface arg0,int arg1) {
	//																Intent in = new Intent(LoginPage.this,	com.sysmind.home.LoginPage.class);
	//																startActivity(in);
	//																userNameText.setSelected(true);
										userNameText.setText("");
										passwordText.setText("");
										userNameText.requestFocus();
									}
								});
						alert.show();
					} else {
						Toast.makeText(getApplicationContext(),"UNNSUCCCEESSSSS", Toast.LENGTH_LONG).show();
						// Toast.makeText(getApplicationContext(), "KEY3",
						// Toast.LENGTH_LONG).show();
						AlertDialog.Builder alert = new AlertDialog.Builder(LoginPage.this);
						// AlertDialog dialog = alert.create();
						alert.setMessage("Wrong username or password.");
						alert.setNegativeButton("Ok",new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface arg0,int arg1) {
										// Intent in = new Intent(LoginPage.this, com.sysmind.home.LoginPage.class);
										// startActivity(in);
										userNameText.setText("");
										passwordText.setText("");
										userNameText.requestFocus();
									}
								});
						alert.show();
					}*/
				} else {
				
					AlertDialog.Builder alert = new AlertDialog.Builder(LoginPage.this);
					// AlertDialog dialog = alert.create();
					alert.setMessage("Username or password can not be blank.");
	
					alert.setNegativeButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface arg0,int arg1) {
	//															Intent in = new Intent(LoginPage.this,com.sysmind.home.LoginPage.class);
	//															startActivity(in);
									userNameText.setSelected(true);
									passwordText.setText("");
									userNameText.setText("");
								}
							});
					alert.show();
				}                
			   

			}
		});
//		finish();
		
	}
	
	private class GetXMLTask extends AsyncTask<String, Void, String> {
		
		@Override
		protected void onPreExecute() {

			 progressDialog =ProgressDialog.show(LoginPage.this,"","Loading...", true,false);
			// progressDialog.setMax(50);

		}

		@Override
		protected String doInBackground(String... urls) {
			
			for(String url : urls) {
//				output = getOutputFromUrl(url);
				serverResponse = parser.getXmlFromUrl(url); // getting XML
			}
			
			return serverResponse;
		}
		
		@Override
		protected void onPostExecute(String output) {
			if (progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
			
			toEdit.putBoolean("IS_LOGIN", true);
			toEdit.putString("ServerLoginResponse", serverResponse);
			// toEdit.putString("Password", passwordString);
			toEdit.commit();

			if (serverResponse != null && serverResponse.startsWith("<?xml")) {
				// Toast.makeText(getApplicationContext(),
				// "successs"+serverResponse, Toast.LENGTH_LONG).show();

				setSharedPrefernces();
				populateLoginResponse();
				populateDeptLocProfValues();

				intent = new Intent(getApplicationContext(),com.sysmind.home.Option_Page.class);
				intent.putExtra("FunalUrl", serverResponse);
				intent.putExtra("UName", userNameString);
				startActivity(intent);
				finish();
			}

			else if (serverResponse != null && serverResponse.startsWith("notverified")) {
				AlertDialog.Builder alert = new AlertDialog.Builder(LoginPage.this);
				alert.setMessage("YOU ARE NOT VERIFIED");
				alert.setNegativeButton("Ok",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0,int arg1) {
								userNameText.setText("");
								passwordText.setText("");
								userNameText.requestFocus();
							}
						});
				alert.show();
			}else if (serverResponse != null && serverResponse.equalsIgnoreCase("error")) {
				AlertDialog.Builder alert = new AlertDialog.Builder(LoginPage.this);
				alert.setMessage("CONNECTION ISSUE");
				alert.setNegativeButton("Ok",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0,int arg1) {
//																Intent in = new Intent(LoginPage.this,	com.sysmind.home.LoginPage.class);
//																startActivity(in);
//																userNameText.setSelected(true);
								userNameText.setText("");
								passwordText.setText("");
								userNameText.requestFocus();
							}
						});
				alert.show();
			} else {
//				Toast.makeText(getApplicationContext(),"UNNSUCCCEESSSSS", Toast.LENGTH_LONG).show();
				AlertDialog.Builder alert = new AlertDialog.Builder(LoginPage.this);
				// AlertDialog dialog = alert.create();
				alert.setMessage("Wrong username or password.");
				alert.setNegativeButton("Ok",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0,int arg1) {
								// Intent in = new Intent(LoginPage.this, com.sysmind.home.LoginPage.class);
								// startActivity(in);
								userNameText.setText("");
								passwordText.setText("");
								userNameText.requestFocus();
							}
						});
				alert.show();
			}
		}
	}
	
	public void setSharedPrefernces() { 
		
		toEdit.putBoolean("IS_LOGIN", true);
		toEdit.putString("Username", userNameString); 
		toEdit.putString("Password", passwordString); 
		toEdit.commit(); 
	}

//	@Override
//	protected void onRestart() {
//	    // TODO Auto-generated method stub
//	    super.onRestart();
//	    if(LoginPage.isQuit)
//	        finish();
//	}
	
	public void onBackPressed()
	{
	    if(backButtonCount >= 1)
	    {
	        Intent intent = new Intent(Intent.ACTION_MAIN);
	        intent.addCategory(Intent.CATEGORY_HOME);
	        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        startActivity(intent);
	    }
	    else
	    {
	        Toast.makeText(this, "Press BACK button again to exit.", Toast.LENGTH_SHORT).show();
	        backButtonCount++;
	    }
	}
	
//	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		name = sh_Pref.getString("Username", null);
		pass = sh_Pref.getString("Password", null);
//		Toast.makeText(getApplicationContext(), "inside onResume method***"+name+"---"+pass, 20).show();
		if(name!=null && name.length()>0 && pass!=null && pass.length()>0){
			
			try {
				serverResponse = sh_Pref.getString("ServerLoginResponse", null);
				if (serverResponse!=null && serverResponse.startsWith("<?xml")) {
					populateLoginResponse();
				}
				populateDeptLocProfValues();
//	            SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	            activityClass = Class.forName(sh_Pref.getString("lastActivity", Activity.class.getName()));
	            
//	            Toast.makeText(getApplicationContext(), "last visited page saved is***"+activityClass, 20).show();
	            
			} catch(ClassNotFoundException ex) {
	        	//Toast.makeText(getApplicationContext(), "No last visited page found***", 20).show();
	            activityClass = Activity.class;
			}

	        startActivity(new Intent(this, activityClass));
		}
	}
	private void populateLoginResponse(){
		XMLParser parser = new XMLParser();
		Document doc = parser.getDomElement(serverResponse); // getting DOM element
		HashMap<String, String> map = new HashMap<String, String>();
		NodeList nl = doc.getElementsByTagName(KEY_ServerResponse);

//		 Toast.makeText(getApplicationContext(), "KEY1", Toast.LENGTH_LONG).show();
		// looping through all item nodes <item>
		for (int i = 0; i < nl.getLength(); i++) {
			// creating new HashMap

			Element e = (Element) nl.item(i);
			// adding each child node to HashMap key => value
			map.put(KEY_query, parser.getValue(e, KEY_query));
			String KEY1 = parser.getValue(e, KEY_query);

			myExtension=parser.getValue(e, KEY_Extension);
			myip=parser.getValue(e, KEY_AsteriskIP);
			map.put(KEY_Extension, parser.getValue(e, KEY_Extension));
			map.put(KEY_ExtPass, parser.getValue(e, KEY_ExtPass));
			map.put(KEY_AsteriskIP, parser.getValue(e, KEY_AsteriskIP));
			
			// adding HashTable to ArrayList
			menuItems.add(map);
		}
		
	}
	private void populateDeptLocProfValues() {
		
		XMLParser parser = new XMLParser();
		URL1 = uri + uri2;
		xml1 = parser.getXmlFromUrl(URL1);
		
		doc1 = parser.getDomElement(xml1);
		nl1 = doc1.getElementsByTagName(KEY_LOCATION);
		locationArray = new String[nl1.getLength()];
		for (int i = 0; i < nl1.getLength(); i++) {
			locationArray[i] = nl1.item(i).getTextContent();
		}
		
		URL2 = uri + uri3;
		xml2 = parser.getXmlFromUrl(URL2);
		
		doc2 = parser.getDomElement(xml2);
		nl2 = doc2.getElementsByTagName(KEY_PROFESSION);
		professionArray = new String[nl2.getLength()];
		for (int i = 0; i < nl2.getLength(); i++) {
			professionArray[i] = nl2.item(i).getTextContent();
		}
		
		URL = uri + uri1;
		xml = parser.getXmlFromUrl(URL);
		
		doc = parser.getDomElement(xml);
	    nl = doc.getElementsByTagName(KEY_DEPARTMENT);
		departmentArray = new String[nl.getLength()];
		for (int i = 0; i < nl.getLength(); i++) {
			departmentArray[i] = nl.item(i).getTextContent();
		}
		
	}
	
}
