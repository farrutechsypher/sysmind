package com.sysmind.home;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.csipsimple.R;

public class AddGroupName extends Activity {

	String uri;
	String uri1 = "addetailsGroups?";
	Intent in;

	EditText add_grp_name_editText1;
	Button add_grp_name_button1;

	String addGrpName, Url, FinalUrl,userString,loginUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.add_group_name);

		// uri=uri=LoginPage.uri;
		in = getIntent();
//		userString = in.getStringExtra("UsrName");
		userString= LoginPage.sh_Pref.getString("UsrName", null);
		loginUser= LoginPage.sh_Pref.getString("Username", null);
//		 Toast.makeText(getApplicationContext(),"Welcum to add grp ** "+userString, Toast.LENGTH_LONG).show();
		add_grp_name_button1 = (Button) findViewById(R.id.add_grp_name_button1);
		Button backButton = (Button) findViewById(R.id.empl_profpg_backbtn);
		Button group_logout_btn = (Button) findViewById(R.id.group_logout_btn);
		
		group_logout_btn.setOnClickListener(new View.OnClickListener() {

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
		backButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				in = new Intent(getApplicationContext(),
						com.sysmind.home.MyGroup.class);
			
//				in.putExtra("UserName", userString);
				LoginPage.toEdit.putBoolean("IS_LOGIN", true);
				LoginPage.toEdit.putString("UserName", userString); 
			
			
				LoginPage.toEdit.commit(); 
				startActivity(in);
//				Toast.makeText(getApplicationContext(),"bk btn to add grp ** "+userString, Toast.LENGTH_LONG).show();
			}
		});
		add_grp_name_button1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				add_grp_name_editText1 = (EditText) findViewById(R.id.add_grp_name_editText1);
				addGrpName = add_grp_name_editText1.getText().toString();

				if (addGrpName != null && addGrpName.length() > 0) {
					uri = LoginPage.uri;

					// Url=uri+uri1+"groupName="+addGrpName;
					Url = uri + uri1;
					FinalUrl = Url.replaceAll(" ", "%20");
					// Toast.makeText(getApplicationContext(),
					// "GROUPEEEE"+FinalUrl, Toast.LENGTH_LONG).show();
					XMLParser parser = new XMLParser();
					parser.params.add(new BasicNameValuePair("groupName",
							addGrpName));
					parser.params.add(new BasicNameValuePair("userName",
							loginUser));
					
					String xml = parser.getXmlFromUrl(FinalUrl);
					
					if (xml.startsWith("INSERTED")) {

						in = new Intent(getApplicationContext(),
								com.sysmind.home.MyGroup.class);
						in.putExtra("UserName", userString);
						startActivity(in);
						return;
					} else {

						AlertDialog.Builder alertboxDuplicate = new AlertDialog.Builder(
								com.sysmind.home.AddGroupName.this);
						alertboxDuplicate
								.setMessage("Group Name Already Exists");
						alertboxDuplicate.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface arg0,
											int arg1) {

										Boolean ok = false;

									}
								});

						alertboxDuplicate.show();

					}
				} else {

					AlertDialog.Builder alertbox = new AlertDialog.Builder(
							com.sysmind.home.AddGroupName.this);
					alertbox.setMessage("Please Enter the Group Name");
					alertbox.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface arg0,
										int arg1) {

									Boolean ok = false;

								}
							});

					alertbox.show();

				}

			}
		});

	}

	protected void onPause() {
		super.onPause();

//		SharedPreferences sh_Pref = getSharedPreferences("Login Credentials",
//				MODE_PRIVATE);
//		Editor toEdit = sh_Pref.edit();
		LoginPage.toEdit.putString("lastActivity", getClass().getName());
		LoginPage.toEdit.commit();
	}
	
	public void onBackPressed()
	{
	    
	        Intent intent = new Intent(Intent.ACTION_MAIN);
	    	intent = new Intent(AddGroupName.this, MyGroup.class);
	    	
			in.putExtra("UserName", userString);
	        startActivity(intent);
	    
	}

}
