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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.csipsimple.R;

public class EnterCodePage extends Activity{
	
	EditText enterCodeEditText;
	Button enterCodeButton;
	String enterCodeEditTextString,UserName,fnlurl;
	Intent intent;
	String uri1="enterCodeSysmind?";
	String uri;
	Boolean ok=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
//	    WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.enter_code_page);
		
		intent = getIntent();
        UserName = intent.getStringExtra("Username");
        fnlurl = intent.getStringExtra("FinalUrl");
        
      //  Toast.makeText(getApplicationContext(), ""+UserName, Toast.LENGTH_LONG).show();
		
        enterCodeEditText=(EditText)findViewById(R.id.enter_cpg_editText);
        enterCodeButton=(Button)findViewById(R.id.enter_cpg_button);
		
		enterCodeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
			    enterCodeEditTextString=enterCodeEditText.getText().toString();
//				Toast.makeText(getApplicationContext(), "code is********"+enterCodeEditTextString, Toast.LENGTH_LONG).show();
				uri=LoginPage.uri;
				String finalUri= uri+ uri1;	
				XMLParser parser = new XMLParser();
				
				parser.params.add(new BasicNameValuePair("userName", UserName));
				parser.params.add(new BasicNameValuePair("verificationCode", enterCodeEditTextString));
				String xml = parser.getXmlFromUrl(finalUri);
			//	Toast.makeText(getApplicationContext(), "finalUri is*********"+finalUri, Toast.LENGTH_LONG).show();	
			//	Toast.makeText(getApplicationContext(), "xml is*********"+xml, Toast.LENGTH_LONG).show();
				if(xml.startsWith("CORRECT")){
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EnterCodePage.this);

					alertDialogBuilder.setMessage("YOU ARE REGISTERED SUCCESSFULLY");
					// set positive button: Yes message
					alertDialogBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									// go to a new activity of the app
									intent= new Intent(getApplicationContext(),Option_Page.class);
									intent.putExtra("UName",UserName);
									intent.putExtra("FunalUrl",fnlurl);
									startActivity(intent);
								}
							});
					// set negative button: No message
//					alertDialogBuilder.setNegativeButton("Cancel",
//							new DialogInterface.OnClickListener() {
//								public void onClick(
//										DialogInterface dialog, int id) {
//									// cancel the alert box and put a
//									// Toast to the user
//									dialog.cancel();
////									Toast.makeText(getApplicationContext(),"You chose a negative answer",Toast.LENGTH_LONG).show();
//								}
//							});

					AlertDialog alertDialog = alertDialogBuilder
							.create();
					alertDialog.show();
				
				}
				else{
					
					
					AlertDialog.Builder alertboxDuplicat = new AlertDialog.Builder(EnterCodePage.this);
//					alertboxDuplicate.setMessage("Username not matched with this email address");
					alertboxDuplicat.setMessage("INCORRECT code");
					alertboxDuplicat.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
							ok=false;
						}
					});

					alertboxDuplicat.show();
				}		
				
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

}
