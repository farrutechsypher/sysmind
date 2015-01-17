
package com.sysmind.home;

import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.csipsimple.R;

public class UsersInGroupMainSwitch extends Activity {

	Document doc;

	String uri, Url;
//	String uri1 = "glistGroups";
	String uri1 = "usersgroupUsers";
	
	ListView myGroupListview;
	ImageView empProfilepageImage;
	Button backButton,mygroupLogout;
	public static String[] mygroupArray;
	static final String KEY_GROUP = "group";
	ArrayAdapter<String> mygroups;
	Intent in;
	String userTypeString, selectedMenuType,loginUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.mygroup);

		uri = uri = LoginPage.uri;

//		in = getIntent();
//		userTypeString = in.getStringExtra("UserName");
		userTypeString= LoginPage.sh_Pref.getString("UserName", null);
		loginUser= LoginPage.sh_Pref.getString("Username", null);
//		Toast.makeText(getApplicationContext(),"Welcum to my grp ** "+userTypeString, Toast.LENGTH_LONG).show();
		myGroupListview = (ListView) findViewById(R.id.add_grp_listView);
		empProfilepageImage = (ImageView) findViewById(R.id.empl_profpg_imageView);
		backButton = (Button) findViewById(R.id.empl_profpg_backbtn);
		mygroupLogout=(Button)findViewById(R.id.mygroupLogout);
		
		
//		selectedMenuType = LoginPage.sh_Pref.getString("selectedMenu", null);

		//Toast.makeText(getApplicationContext(),"selectedMenuType***" + selectedMenuType, Toast.LENGTH_LONG).show();

		backButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// Toast.makeText(getApplicationContext(), "button",
				// Toast.LENGTH_LONG).show();

//				if (selectedMenuType.equalsIgnoreCase("department")) {
					Intent intent = new Intent(Intent.ACTION_MAIN);
					intent = new Intent(UsersInGroupMainSwitch.this, UsersInGroupMain.class);
					startActivity(intent);

//				} 
//				else if (selectedMenuType.equalsIgnoreCase("location")) {
//
//					Intent intent = new Intent(Intent.ACTION_MAIN);
//					intent = new Intent(MyGroup.this, LocationType.class);
//					startActivity(intent);
//
//				} else if (selectedMenuType.equalsIgnoreCase("profession")) {
//
//					Intent intent = new Intent(Intent.ACTION_MAIN);
//					intent = new Intent(MyGroup.this, ProfessionTypes.class);
//					startActivity(intent);
//
//				} else {
//					Intent intent = new Intent(Intent.ACTION_MAIN);
//					intent = new Intent(MyGroup.this, Option_Page.class);
//					startActivity(intent);
//
//				}
			}
		});
		
		mygroupLogout.setOnClickListener(new View.OnClickListener() {
 			
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

		empProfilepageImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				in = new Intent(getApplicationContext(),com.sysmind.home.AddGroupName.class);
//				in.putExtra("UsrName", userTypeString);
				LoginPage.toEdit.putBoolean("IS_LOGIN", true);
				LoginPage.toEdit.putString("UsrName", userTypeString); 
			
			
				LoginPage.toEdit.commit(); 
				
				startActivity(in);
//				 Toast.makeText(getApplicationContext(),"mygroup** "+userTypeString, Toast.LENGTH_LONG).show();

			}
		});

		Url = uri + uri1;
		XMLParser parser = new XMLParser();
		parser.params.add(new BasicNameValuePair("userName",loginUser));
//		 Toast.makeText(getApplicationContext(),"mygroup Url** "+Url+"  "+LoginPage.userNameString+" loginUser "+loginUser, Toast.LENGTH_LONG).show();
		String xml = parser.getXmlFromUrl(Url);
		doc = parser.getDomElement(xml);

		NodeList nl = doc.getElementsByTagName(KEY_GROUP);
		mygroupArray = new String[nl.getLength()];
		for (int i = 0; i < nl.getLength(); i++) {
			mygroupArray[i] = nl.item(i).getTextContent();
		}
		mygroups = new ArrayAdapter<String>(getApplicationContext(),
				R.layout.mygroup_singleitem, mygroupArray);
		// Toast.makeText(getApplicationContext(), ""+xml,
		// Toast.LENGTH_LONG).show();

		myGroupListview.setAdapter(mygroups);
//		myGroupListview.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1,
//					int position, long arg3) {
//
//				String groupname = (String) myGroupListview
//						.getItemAtPosition(position);
//				// String
//				// urlAdd=uri+"addUserTOGroupUsers?AdduserGroup="+groupname+"&AdduserName="+userTypeString;
//				String urlAdd = uri + "addUserTOGroupUsers?";
//
//				XMLParser parser = new XMLParser();
//
//				parser.params.add(new BasicNameValuePair("AdduserGroup",groupname));
//				parser.params.add(new BasicNameValuePair("AdduserName",userTypeString));
//
//				String serverResponse = parser.getXmlFromUrl(urlAdd); // getting
//																		// XML
//				// Toast.makeText(getApplicationContext(), ""+serverResponse,
//				// Toast.LENGTH_LONG).show();
//				if (serverResponse.startsWith("INSERTED")) {
//
//					AlertDialog.Builder alertboxDuplicate = new AlertDialog.Builder(com.sysmind.home.MyGroup.this);
//					alertboxDuplicate.setMessage(userTypeString+" Added Successfully in "+groupname);
//					alertboxDuplicate.setPositiveButton("Ok",
//							new DialogInterface.OnClickListener() {
//
//								public void onClick(DialogInterface arg0,
//										int arg1) {
//									Boolean ok = false;
//
//								}
//							});
//
//					alertboxDuplicate.show();
//
//				} else {
//					AlertDialog.Builder alertboxDuplicate = new AlertDialog.Builder(
//							com.sysmind.home.MyGroup.this);
//					alertboxDuplicate
////							.setMessage("Group Name Already Exists for the Selected User");
//					.setMessage(userTypeString+" Already Exists in "+groupname);
//					alertboxDuplicate.setPositiveButton("Ok",
//							new DialogInterface.OnClickListener() {
//								public void onClick(DialogInterface arg0,
//										int arg1) {
//									Boolean ok = false;
//
//								}
//							});
//
//					alertboxDuplicate.show();
//				}
//
//			}
//		});

	}

	protected void onPause() {
		super.onPause();

		LoginPage.toEdit.putString("lastActivity", getClass().getName());
		LoginPage.toEdit.commit();
	}

	public void onBackPressed() {
		if (selectedMenuType.equalsIgnoreCase("department")) {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent = new Intent(UsersInGroupMainSwitch.this, DepartmentType.class);
			startActivity(intent);

		} 
	

	}

}
