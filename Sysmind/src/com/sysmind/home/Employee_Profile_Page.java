package com.sysmind.home;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.csipsimple.R;

public class Employee_Profile_Page extends Activity {

	String uri1 = "detailsUsers?";
	String uri;
	String userName, userExt,selectedMenuType;
	
	static final String KEY_ServerResponse = "Server-Response";
	static final String KEY_query = "query";
	static final String KEY_Extension = "extension";
	static final String KEY_ROW = "row";
	static final String KEY_IMAGEDATA = "imageData";
	static final String KEY_RESUMEDATA = "resumeData";
	static final String KEY_RESUMETYPE = "resumeType";

	static final String KEY_DEPARTMENT = "department";
	static final String KEY_LOCATIONDATA = "location";
	static final String KEY_PROFESSION = "profession";
	static final String KEY_EMAIL = "email";

	Button empPrflButton1, empPrflButton2, empPrflAddGroupButton, empPrflButton4, empPrflShowResumeButton, empl_profpg_backbtn, empl_profpg_logout_btn;
	Intent in;
	TextView empPrflTextview;
	ListView emplProfileListview;
	ImageView empl_profpg_empimageview;
	ArrayAdapter<String> EmployeeProfPageAdapter;

	ArrayList<String> list = new ArrayList<String>();
	
	FileCache fileCache;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.employee_profile_page);
		emplProfileListview=(ListView)findViewById(R.id.empl_profdetail_listview);
		empl_profpg_empimageview=(ImageView)findViewById(R.id.empl_profpg_empimageview);
		empPrflButton1=(Button)findViewById(R.id.empl_profpg_connect);		
		empl_profpg_logout_btn=(Button)findViewById(R.id.empl_profpg_logout_btn);
		empPrflAddGroupButton=(Button)findViewById(R.id.empl_profpg_addgroup);
		empPrflButton4=(Button)findViewById(R.id.empl_profpg_email);
		empPrflShowResumeButton=(Button)findViewById(R.id.empl_profpg_resume);
	//	empl_profpg_backbtn=(Button)findViewById(R.id.empl_profpg_backbtn);
		
		selectedMenuType= LoginPage.sh_Pref.getString("selectedMenu", null);
		
		Toast.makeText(getApplicationContext(), "selectedMenuType***"+selectedMenuType,Toast.LENGTH_LONG).show();
		
		uri=LoginPage.uri;
//		in=getIntent();
//		userName = in.getStringExtra("UserName");
//		userExt = in.getStringExtra("UserExtension");
		if(selectedMenuType.equalsIgnoreCase("department")){
			userName= LoginPage.sh_Pref.getString("UserName", null);
			userExt= LoginPage.sh_Pref.getString("UserExtension", null);
		}else if(selectedMenuType.equalsIgnoreCase("location")){
			userName= LoginPage.sh_Pref.getString("UserName", null);
			userExt= LoginPage.sh_Pref.getString("UserExtension", null);
		}else if(selectedMenuType.equalsIgnoreCase("profession")){
			userName= LoginPage.sh_Pref.getString("UserName", null);
			userExt= LoginPage.sh_Pref.getString("UserExtension", null);
		}else if(selectedMenuType.equalsIgnoreCase("adduser")){
			
			userName= LoginPage.sh_Pref.getString("UserName", null);
//			userExt= LoginPage.sh_Pref.getString("UserExtension", null);
			
//			Toast.makeText(getApplicationContext(), "add user extension is**"+userExt,Toast.LENGTH_LONG).show();	
		
		}else if(selectedMenuType.equalsIgnoreCase("mygroupUser")){
			userName= LoginPage.sh_Pref.getString("UserName", null);
			userExt= LoginPage.sh_Pref.getString("UserExtension", null);
		}
		//Toast.makeText(getApplicationContext(), ""+userName,Toast.LENGTH_LONG).show();
		
		
		empPrflTextview = (TextView)findViewById(R.id.empl_profpg_textview);

		empPrflTextview.setText(userName);

		//String finalUri= uri+ uri1 + "userName="+ userName;
		String finalUri = uri+ uri1;
		String FinalUrl = finalUri.replaceAll(" ", "%20");
		XMLParser parser = new XMLParser();
		
		parser.params.add(new BasicNameValuePair("userName", userName));
	
		String xml = parser.getXmlFromUrl(FinalUrl);
		ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();
		//Toast.makeText(getApplicationContext(), ""+xml,Toast.LENGTH_LONG).show();

		Document doc = parser.getDomElement(xml); // getting DOM element
		HashMap<String, String> map = new HashMap<String, String>();
		NodeList nl = doc.getElementsByTagName(KEY_ServerResponse);

		Element e = (Element) nl.item(0);
		DepSampleBean s = new DepSampleBean();
		
		// adding each child node to HashMap key => value
		map.put(KEY_query, parser.getValue(e, KEY_query));
		map.put(KEY_PROFESSION, parser.getValue(e, KEY_PROFESSION));
		map.put(KEY_LOCATIONDATA, parser.getValue(e, KEY_LOCATIONDATA));
		
		String KEY1 = parser.getValue(e, KEY_query);
		String KEY2 = parser.getValue(e, KEY_Extension);
		String KEY3 = parser.getValue(e, KEY_IMAGEDATA);
		String KEY4 = parser.getValue(e, KEY_EMAIL);
		//String KEY5 = parser.getValue(e, KEY_RESUMEDATA);
		//String KEY6 = parser.getValue(e, KEY_RESUMETYPE);
		
		empPrflButton4.setText(KEY4);

		//Toast.makeText(getApplicationContext(), "KEY3="+KEY3, Toast.LENGTH_LONG).show();
		
		try {
			byte[] decodedByte = Base64.decode(KEY3, Base64.DEFAULT);

			//Toast.makeText(getApplicationContext(), "DecodedByte="+decodedByte, Toast.LENGTH_LONG).show();
			Bitmap Byte = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);

			//Toast.makeText(getApplicationContext(), "BMP="+Byte, Toast.LENGTH_LONG).show();
			empl_profpg_empimageview.setImageBitmap(Byte);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		/*Toast.makeText(getApplicationContext(), "ResumeData KEY5="+KEY5, Toast.LENGTH_LONG).show();
		Toast.makeText(getApplicationContext(), "ResumeNAME="+userName + "." + KEY6, Toast.LENGTH_LONG).show();
		
		// Save Resume encoded data on Phone and Get the resume Path.
		if(KEY5 != null && !KEY5.equals("") && KEY6 != null && !KEY6.equals("")) {
			resumePath = saveResumeAndGetPath(KEY5, userName + "." + KEY6);
		}
		Toast.makeText(getApplicationContext(), "resumePath="+resumePath, Toast.LENGTH_LONG).show();*/

		menuItems.add(map);
		ListAdapter adapter = new SimpleAdapter(this, menuItems,
				R.layout.empl_profpg_customized_layout,
				new String[] { KEY_PROFESSION, KEY_LOCATIONDATA}, new int[] {
				R.id.emp_prof_details_textView1, R.id.emp_prof_details_textView2});

		emplProfileListview.setAdapter(adapter);

		empPrflButton1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Toast.makeText(getApplicationContext(), "dept connect USERS***"+userName, Toast.LENGTH_LONG).show();
				//Toast.makeText(getApplicationContext(), "dept connect EXTEN***"+userExt, Toast.LENGTH_LONG).show();
			
				in = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+userExt));
				startActivity(in);
				
			}
		});

		empPrflAddGroupButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				in=new Intent(getApplicationContext(), com.sysmind.home.MyGroup.class);
				in.putExtra("UserName", userName);
				startActivity(in);
			}
		});
		//
		empl_profpg_logout_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				in=new Intent(getApplicationContext(),com.sysmind.home.LoginPage.class);
				startActivity(in);
//				SharedPreferences sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE); 
// 				Editor toEdit = sh_Pref.edit();
				LoginPage.toEdit.clear();
				LoginPage.toEdit.commit();
				finish();
			}
		});
		
//		empl_profpg_backbtn.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				in=new Intent(getApplicationContext(),com.sysmind.home.SysmindDepartmentPage.class);
//				startActivity(in);
//				
//			}
//		});
//

		empPrflShowResumeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				String resumePath = fetchResumeFromServer();
				
				if(resumePath != null && !resumePath.equals("")) {
					File resumeFile = new File(resumePath); 
		            if(resumeFile.exists()) {
		                Uri path = Uri.fromFile(resumeFile); 
		                Intent resumeIntent = new Intent(Intent.ACTION_VIEW);
		                resumeIntent.setDataAndType(path, "application/*");
		                resumeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	
						try {
							startActivity(resumeIntent);
						} catch (ActivityNotFoundException e) {
							Toast.makeText(getApplicationContext(), "No Application available to view this file.", Toast.LENGTH_LONG).show();
						}
		            } else {
		            	Toast.makeText(getApplicationContext(), "Resume Not Available.", Toast.LENGTH_LONG).show();
		            }
				} else {
	            	Toast.makeText(getApplicationContext(), "Resume Not Found.", Toast.LENGTH_LONG).show();
	            }
			}
		});
	}
	
	private String fetchResumeFromServer() {
		String resumePath = "";
		String finalUri= uri + "resumeUsers";
		String FinalUrl=finalUri.replaceAll(" ", "%20");
		XMLParser parser = new XMLParser();
		
		parser.params.add(new BasicNameValuePair("userName", userName));
	
		String xml = parser.getXmlFromUrl(FinalUrl);
		//ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();
		//Toast.makeText(getApplicationContext(), ""+xml,Toast.LENGTH_LONG).show();

		Document doc = parser.getDomElement(xml); // getting DOM element
		//HashMap<String, String> map = new HashMap<String, String>();
		NodeList nl = doc.getElementsByTagName(KEY_ServerResponse);

		Element element = (Element) nl.item(0);
		
		String resumeData = parser.getValue(element, KEY_RESUMEDATA);
		String resumeType = parser.getValue(element, KEY_RESUMETYPE);
		
		//Toast.makeText(getApplicationContext(), "ResumeData="+resumeData, Toast.LENGTH_LONG).show();
		//Toast.makeText(getApplicationContext(), "ResumeNAME="+userName + "." + resumeType, Toast.LENGTH_LONG).show();
		
		// Save Resume encoded data on Phone and Get the resume Path.
		if(resumeData != null && !resumeData.equals("") && resumeType != null && !resumeType.equals("")) {
			resumePath = saveResumeAndGetPath(resumeData, userName + "." + resumeType);
		}
		//Toast.makeText(getApplicationContext(), "resumePath="+resumePath, Toast.LENGTH_LONG).show();
		
		return resumePath;
	}
	
	private String saveResumeAndGetPath(String data, String name) {
		//Toast.makeText(getApplicationContext(), data+"<---insise saveResumeAndGetPath--->"+name, Toast.LENGTH_LONG).show();
		fileCache = new FileCache(this.getApplicationContext());
		fileCache.clear();
		
		byte[] fileAsBytes = Base64.decode(data, 0);
		File filePath = fileCache.getFile(name);
		
		//Toast.makeText(getApplicationContext(), "filePath: " + filePath, Toast.LENGTH_LONG).show();
		FileOutputStream os;
		try {
			os = new FileOutputStream(filePath, true);
	    	os.write(fileAsBytes);
	    	os.close();
	    	return filePath.getAbsolutePath();
	    } catch (FileNotFoundException e) {
			e.printStackTrace();
			return "failed";
		} catch(Exception e){
			e.printStackTrace();
			return "failed";
		}
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
	    if(selectedMenuType.equalsIgnoreCase("department")){
	    	Intent intent = new Intent(Intent.ACTION_MAIN);
	    	intent = new Intent(Employee_Profile_Page.this, DepartmentType.class);
	        startActivity(intent);
	    }else if(selectedMenuType.equalsIgnoreCase("location")){
	    	Intent intent = new Intent(Intent.ACTION_MAIN);
	    	intent = new Intent(Employee_Profile_Page.this, LocationType.class);
	        startActivity(intent);
	    	
	    }else if(selectedMenuType.equalsIgnoreCase("profession")){
	    	Intent intent = new Intent(Intent.ACTION_MAIN);
	    	intent = new Intent(Employee_Profile_Page.this, ProfessionTypes.class);
	        startActivity(intent);
	    	
	    }else if(selectedMenuType.equalsIgnoreCase("adduser")){
	    	Intent intent = new Intent(Intent.ACTION_MAIN);
	    	intent = new Intent(Employee_Profile_Page.this, Adduser.class);
	        startActivity(intent);
	    }else if(selectedMenuType.equalsIgnoreCase("mygroupUser")){
	    	Intent intent = new Intent(Intent.ACTION_MAIN);
	    	intent = new Intent(Employee_Profile_Page.this, UsersInGroupMain.class);
	        startActivity(intent);
	    }
	    
	    else{
	    	Intent intent = new Intent(Intent.ACTION_MAIN);
	    	intent = new Intent(Employee_Profile_Page.this, Option_Page.class);
	        startActivity(intent);
	    }
	}

}
