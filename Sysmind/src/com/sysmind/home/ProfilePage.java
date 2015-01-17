package com.sysmind.home;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore.Images.ImageColumns;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.csipsimple.R;

public class ProfilePage extends Activity {

	String fileImageSrc = "", fileResumeSrc = "";
	byte[] data;
	byte[] resumeByteArray;
	Bitmap bitmap;
	
	//private static final String TAG = "FileChooserExampleActivity";
    //private static final int REQUEST_CODE = 6384;

    String uri;
    String uri1="insertUserProfileSysmind?";
	
    String profdepartment = "", proflocation = "", profprofession = "";
    String encodedResumeString = "", imgString = "";
    String imageType = "", resumeType = "";
    
	Button profile_backbtn,prof_image_btn,prof_resume_btn,prof_btn_submit,profile_logout_btn;
	Spinner spin_department,spin_location,spin_profession;
	EditText profilePageEmail,prof_txt_resume;
	ImageView prof_imageview;
	protected int idIntentPickID=123,IdIntentResumeID=1234;
	ArrayAdapter<String> department;
	ArrayAdapter<String> location;
	ArrayAdapter<String> professsion;
	Intent in;
	String UserName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
//	    WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.profile);
		
		String[] depart=LoginPage.departmentArray;
		String[] locat=LoginPage.locationArray;
		String[] prof=LoginPage.professionArray;
		
		department = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,depart);
		department.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		location = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,locat);
		location.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		professsion = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,prof);
	    professsion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//Toast.makeText(getApplicationContext(), "department="+department, Toast.LENGTH_LONG).show();
		
	    uri = LoginPage.uri;
	    
		in = getIntent();
		UserName = in.getStringExtra("UName");
		
		//Toast.makeText(getApplicationContext(), "S3="+s3, Toast.LENGTH_LONG).show();
		
		spin_department = (Spinner)findViewById(R.id.spin_department);		
		spin_department.setAdapter(department);
		
		spin_location = (Spinner)findViewById(R.id.spin_location);
		spin_location.setAdapter(location);
		
		spin_profession = (Spinner)findViewById(R.id.spin_profession);
		spin_profession.setAdapter(professsion);
		prof_txt_resume = (EditText)findViewById(R.id.prof_txt_resume);
		//profilePageEmail=(EditText)findViewById(R.id.prof_txt_email);
		prof_imageview = (ImageView)findViewById(R.id.prof_imageview);
		profile_backbtn = (Button)findViewById(R.id.profile_backbtn);
		prof_image_btn = (Button)findViewById(R.id.prof_image_btn);
		prof_resume_btn = (Button)findViewById(R.id.prof_resume_btn);
		prof_btn_submit = (Button)findViewById(R.id.prof_btn_submit);
		profile_logout_btn=(Button)findViewById(R.id.profile_logout_btn);
		
		profile_backbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				in=new Intent(getApplicationContext(),com.sysmind.home.Option_Page.class);
				startActivity(in);
			}
		});
		
		prof_image_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				in = new Intent(Intent.ACTION_PICK);
				in.setType("image/*");
				startActivityForResult(in, idIntentPickID);
			}
		});
		prof_resume_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.addCategory(Intent.CATEGORY_OPENABLE);
				intent.setType("application/*");

				Intent i = Intent.createChooser(intent, "File");
				startActivityForResult(i, IdIntentResumeID);
			}
		});
		
		profile_logout_btn.setOnClickListener(new View.OnClickListener() {
 			
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
		prof_btn_submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				addProfile();
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

	private void addProfile() {
		//Bitmap Icon = BitmapFactory.decodeResource(getResources(), R.drawable.addbtm);
		profdepartment = String.valueOf((String)spin_department.getItemAtPosition((int)spin_department.getSelectedItemId()));
		proflocation = String.valueOf((String)spin_location.getItemAtPosition((int)spin_location.getSelectedItemId()));
		profprofession = String.valueOf((String)spin_profession.getItemAtPosition((int)spin_profession.getSelectedItemId()));
		
		if(bitmap != null) {
			if(fileImageSrc.lastIndexOf(".") > -1) {
				imageType = fileImageSrc.substring(fileImageSrc.lastIndexOf(".")+1);
			}
			//Toast.makeText(getApplicationContext(), "imageType="+imageType, Toast.LENGTH_LONG).show();
			data = insert(bitmap);
			byte [] dataimg = data;
			byte [] imgByte = Base64.encode(dataimg, Base64.DEFAULT);
			imgString = new String(imgByte);
		}
		
		if(resumeByteArray != null) {
			byte [] encoded = Base64.encode(resumeByteArray, Base64.DEFAULT);
			encodedResumeString = new String(encoded);
			//Toast.makeText(getApplicationContext(), "profTxtResume.getText()="+prof_txt_resume.getText(), Toast.LENGTH_LONG).show();
			
			String resumePathAndName = prof_txt_resume.getText().toString();
			if(resumePathAndName.lastIndexOf(".") > -1) {
				resumeType = resumePathAndName.substring(resumePathAndName.lastIndexOf(".")+1);
			}
		}
		
	    if (profdepartment.length()>0 && proflocation.length()>0 && profprofession.length()>0) {
	    	//String finalUri= uri + uri1 + "userName=" + UserName + "&department=" +profdepartment+ "&location=" + proflocation + "&profession=" +profprofession +  "&email=" + profemail + "&imageData=" + imgString + "&resumeData=" + b ;
			String finalUri = uri + uri1;
			String FinalUrl = finalUri.replaceAll(" ", "%20");
			
	    	XMLParser parser = new XMLParser();
	    	parser.params.add(new BasicNameValuePair("userName", UserName));
			parser.params.add(new BasicNameValuePair("department", profdepartment));
			parser.params.add(new BasicNameValuePair("location", proflocation));
			parser.params.add(new BasicNameValuePair("profession", profprofession));
			//parser.params.add(new BasicNameValuePair("email", profemail));
			parser.params.add(new BasicNameValuePair("imageData", imgString));
			parser.params.add(new BasicNameValuePair("imageType", imageType));
			parser.params.add(new BasicNameValuePair("resumeData", encodedResumeString));
			parser.params.add(new BasicNameValuePair("resumeType", resumeType));
			
			String xml = parser.getXmlFromUrl(FinalUrl);
			//Toast.makeText(getApplicationContext(), "imgString="+imgString, Toast.LENGTH_LONG).show();
			//Toast.makeText(getApplicationContext(), ""+xml, Toast.LENGTH_LONG).show();
			in = new Intent(getApplicationContext(),com.sysmind.home.Option_Page.class);
			
			startActivity(in);
	    	
		} else {
//			Toast.makeText(getApplicationContext(), "Not Submitted", Toast.LENGTH_LONG).show();
		}
	}
	
	protected void showChooser() {

		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("application/pdf");

		Intent i = Intent.createChooser(intent, "File");
		startActivityForResult(i, IdIntentResumeID);

	}

	@Override
	public void onActivityResult(int requestCode, int resultcode, Intent intent) {
		super.onActivityResult(requestCode, resultcode, intent);
		
		if (requestCode == IdIntentResumeID) {
			if (null != intent) {

				Cursor curs = this.getContentResolver().query(intent.getData(),
						null, null, null, null);
				curs.moveToFirst();
				int idx = curs.getColumnIndexOrThrow(ImageColumns.DATA);
				fileResumeSrc = curs.getString(idx);
				
				try {
					File file = new File(fileResumeSrc);
					resumeByteArray = loadResumeFile(file);
					prof_txt_resume.setText(fileResumeSrc);
				} catch (FileNotFoundException e) {
					System.out.println("File Not Found.");
					e.printStackTrace();
				} catch (IOException e1) {
					System.out.println("Error Reading The File.");
					e1.printStackTrace();
				}
			}
		}

		if(requestCode == idIntentPickID) {
			Cursor curs = this.getContentResolver().query(intent.getData(),
					null, null, null, null);
			curs.moveToFirst();
			int idx = curs.getColumnIndexOrThrow(ImageColumns.DATA);
			fileImageSrc = curs.getString(idx);
			//Toast.makeText(getApplicationContext(), "fileImageSrc="+fileImageSrc, Toast.LENGTH_LONG).show();
			
			bitmap = decodeFile(new File(fileImageSrc));
		    prof_imageview.setImageBitmap(bitmap);
			insert(bitmap);

			try {
				AssetManager as = this.getAssets();
				BufferedInputStream bis = new BufferedInputStream(as.open("memb.png"));
				bitmap = BitmapFactory.decodeStream(bis);
				prof_imageview.setImageBitmap(bitmap);

			} catch (IOException e) {

			}
		}

	}
	
	private static byte[] loadResumeFile(File file) throws IOException {
	    InputStream is = new FileInputStream(file);

	    long length = file.length();
	    if (length > Integer.MAX_VALUE) {
	        // File is too large
	    }
	    
	    byte[] bytes = new byte[(int)length];
	    
	    int offset = 0;
	    int numRead = 0;
	    while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	        offset += numRead;
	    }

	    if (offset < bytes.length) {
	    	is.close();
	        throw new IOException("Could not completely read file "+file.getName());
	    }

	    is.close();
	    
	    return bytes;
	}
	
	private Bitmap decodeFile(File f) {
        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f),null,o);
            
            //Find the correct scale value. It should be the power of 2.
			final int REQUIRED_SIZE = 80;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
                if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale*=2;
            }
            
            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
            
        } catch (FileNotFoundException e) {}
        return null;
    }
	
	private byte[] insert(Bitmap bmap) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
		data = outputStream.toByteArray();
        
		return data;
	}

}