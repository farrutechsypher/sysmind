
package com.sysmind.home;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.csipsimple.R;


public class UserInGroupMainCustomAdapter extends BaseAdapter   implements OnClickListener {
	private Activity activity;
    private ArrayList<UserInGroupMainSampleBean> data;
    private static LayoutInflater inflater=null;
    public Resources res;
    String uri=LoginPage.uri;
   	String uri1 = "removeUsers?";
   	String uriSwitch="switchGroupUsers?";
   	String xml;
    Intent in;
    String[] mygroupArray;
	Document doc;
	String uri2 = "usersgroupUsers";
	String loginUser,Url;
	ArrayAdapter<String> mygroups;
	
    int i=0;
    public UserInGroupMainCustomAdapter(Activity list, ArrayList<UserInGroupMainSampleBean> d) {
        
        /********** Take passed values **********/
         activity = list;
         data=d;
     //    res = resLocal;
      
         /***********  Layout inflator to call external xml layout () ***********/
          inflater = ( LayoutInflater )activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      
 }
	@Override
	public int getCount() {

        if(data.size()<=0)
            return 1;
        return data.size();
	}

	@Override
	public Object getItem(int arg0) {
        return arg0;

	}

	@Override
	public long getItemId(int arg0) {
	     return arg0;
	}
	  public static class ViewHolder{
          
		  Button bgroup,b2;
		  TextView tv;
      }

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		View vi = arg1;
		 ViewHolder holder;
       
         
        if(arg1==null){
             
            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.user_in_group_main_singleitem, null);
            holder = new ViewHolder();
             
            /****** View Holder Object to contain tabitem.xml file elements ******/
          holder.tv=(TextView) vi.findViewById(R.id.useringrpmaintyp_username);
          holder.bgroup=(Button) vi.findViewById(R.id.removeswitchbtn);
          holder. b2=(Button) vi.findViewById(R.id.connectmainbtn);
             
           /************  Set holder with LayoutInflater ************/
           vi.setTag( holder );
        }
        else 
            holder=(ViewHolder)vi.getTag();
         
        if(data.size()<=0)
        {
         //  Toast.makeText(activity, "no data", 1000).show();
             
        }
        else
        {
            /***** Get each Model object from Arraylist ********/
            
             
            /************  Set Model values in Holder elements ***********/

             holder.tv.setText((data.get(position).getUser()));
           
              
             /******** Set Item Click Listner for LayoutInflater for each row *******/
             holder.bgroup.setOnClickListener(new OnItemClickListener(position));
             holder.b2.setOnClickListener(new OnItemClickListener(position));
             holder.tv.setOnClickListener(new OnItemClickListener(position));
           
        }
       
        return vi;
    }
		
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	 private class OnItemClickListener  implements OnClickListener{           
         private int mPosition;
          
         OnItemClickListener(int position){
              mPosition = position;
         }

         
         
		@Override
		public void onClick(final View v) {
			// TODO Auto-generated method stub
			 
			
			switch(v.getId())
			{
			case R.id.removeswitchbtn:
			
			    //Creating the instance of PopupMenu  
	        	
	            PopupMenu popup = new PopupMenu(activity.getApplicationContext(), v);  
	            //Inflating the Popup using xml file  
	           popup.getMenuInflater().inflate(R.menu.poupup_menu, popup.getMenu());  
//	           Toast.makeText(activity.getApplicationContext(),"R.menu.poupup_menu Clicked : "+R.menu.poupup_menu+"  "+popup.getMenu(),Toast.LENGTH_SHORT).show();     
	            
	            //registering popup with OnMenuItemClickListener  
	            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {  
	            public boolean onMenuItemClick(MenuItem item) {  
	            	 
	            String option= (String) item.getTitle();
	            Toast.makeText(activity.getApplicationContext(),"You Clicked : " + item.getTitle()+ "  "+option,Toast.LENGTH_SHORT).show();  
	         
	            final String userGroupTypeString= LoginPage.sh_Pref.getString("Group_name", null);
	          	
	              if(option.equalsIgnoreCase("Remove")){
	           
	            		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);

						alertDialogBuilder.setMessage("Are you sure you want to remove "+data.get(mPosition).getUser().toUpperCase()+" from "+userGroupTypeString.toUpperCase());
						alertDialogBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
										
			            	  Toast.makeText(activity.getApplicationContext()," Welcome to Remove of popup menu "+userGroupTypeString,Toast.LENGTH_SHORT).show();  
//			            	  Toast.makeText(activity.getApplicationContext(), "userGroup is*** "+userGroupTypeString+" username is**** "+data.get(mPosition).getUser() , Toast.LENGTH_LONG).show();  
						          	
			            	  String finalUrl = uri + uri1;	
			 				  XMLParser parser = new XMLParser();
							  parser.params.add(new BasicNameValuePair("userName", data.get(mPosition).getUser()));
						   	  parser.params.add(new BasicNameValuePair("userGroupName", userGroupTypeString));
							
							  xml = parser.getXmlFromUrl(finalUrl);
							  Toast.makeText(activity.getApplicationContext(), "Welcum to user=" + finalUrl,Toast.LENGTH_LONG).show();
							  Toast.makeText(activity.getApplicationContext(), "Welcum to positionuserr=" + xml,Toast.LENGTH_LONG).show();
//										 
							  in = new Intent(activity.getApplicationContext(), com.sysmind.home.UsersInGroupMain.class);
							  activity.startActivity(in);
									
									}
								});
						
						alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
							  public void onClick(DialogInterface dialog, int id) {
							  dialog.cancel();
									}
								});

						AlertDialog alertDialog = alertDialogBuilder.create();
						alertDialog.show();
						
	              
	              }else if(option.equalsIgnoreCase("Switch")){
//		              String userGroupTypeString= LoginPage.sh_Pref.getString("Group_name", null);
//	            	  Toast.makeText(activity.getApplicationContext()," Welcome to Switch of pop up menu " ,Toast.LENGTH_SHORT).show();  
//	            	  Toast.makeText(activity.getApplicationContext(), "user group is ***"+userGroupTypeString+" username is **** "+data.get(mPosition).getUser(), Toast.LENGTH_LONG).show();
	              
//	            	  in = new Intent(activity.getApplicationContext(), com.sysmind.home.UsersInGroupMainSwitch.class);
//					  activity.startActivity(in);
//	            		in=new Intent(activity.getApplicationContext(),com.sysmind.home.MyGroup.class);
	    				
//	    				LoginPage.toEdit.putBoolean("IS_LOGIN", true);
//	    				LoginPage.toEdit.putString("UserName",  data.get(mPosition).getUser()); 
	    			
//	    				LoginPage.toEdit.commit(); 
	    				
//	    				activity.startActivity(in);
	            		
	            		loginUser= LoginPage.sh_Pref.getString("Username", null);	
	            		Url = uri + uri2;
	            		XMLParser parser = new XMLParser();
	            		parser.params.add(new BasicNameValuePair("userName",loginUser));
//	            		 Toast.makeText(getApplicationContext(),"mygroup Url** "+Url+"  "+LoginPage.userNameString+" loginUser "+loginUser, Toast.LENGTH_LONG).show();
	            		String xml = parser.getXmlFromUrl(Url);
	            		doc = parser.getDomElement(xml);
	            		 PopupMenu popup1 = new PopupMenu(activity.getApplicationContext(), v); 
	            		 
	            		 
	            		NodeList nl = doc.getElementsByTagName(MyGroup.KEY_GROUP);
	            		mygroupArray = new String[nl.getLength()];
	            		for (int i = 0; i < nl.getLength(); i++) {
	            			mygroupArray[i] = nl.item(i).getTextContent();
//	            			 Toast.makeText(activity.getApplicationContext(),"mygroup Url** "+mygroupArray[i], Toast.LENGTH_LONG).show();            			
	            		     popup1.getMenu().add(mygroupArray[i] );
	            		}
	            		mygroups = new ArrayAdapter<String>(activity.getApplicationContext(),
	            				R.menu.popup_group, mygroupArray);
	            	
//	            		 Toast.makeText(activity.getApplicationContext()," "+mygroups.getItem(mPosition),Toast.LENGTH_SHORT).show();      		
	            		
	            	 
	     	            //Inflating the Popup using xml file  
	     	           popup1.getMenuInflater().inflate(R.menu.popup_group, popup1.getMenu());  
//	     	          popup1.getMenu().add("tryyy");
	            		
	     	          popup1.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {  
	 		             public boolean onMenuItemClick(MenuItem item) {  
	 		            	 
	 		            	 final String optionGroup= (String) item.getTitle();
//	 		              Toast.makeText(popupOnButtonClick.this,"You Clicked : " + item.getTitle()+ "  "+option,Toast.LENGTH_SHORT).show();  
	 		           if(!optionGroup.equals("")){
//	 		        	   Toast.makeText(activity.getApplication()," Group is selected",Toast.LENGTH_SHORT).show(); 	   
	 		         
	 		         		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);

							alertDialogBuilder.setMessage("Are you sure you want to switch "+data.get(mPosition).getUser().toUpperCase()+" from "+userGroupTypeString.toUpperCase()+" to "+optionGroup.toUpperCase());
							alertDialogBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
											
							          	
				            	  String finalUrl = uri + uriSwitch;	
				 				  XMLParser parser = new XMLParser();
								  parser.params.add(new BasicNameValuePair("userName", data.get(mPosition).getUser()));
							   	  parser.params.add(new BasicNameValuePair("userGroupName", userGroupTypeString));
								  parser.params.add(new BasicNameValuePair("newUserGroupName", optionGroup));
								String  xmlSwitch = parser.getXmlFromUrl(finalUrl);
								  Toast.makeText(activity.getApplicationContext(), "url of switch is=" + finalUrl,Toast.LENGTH_LONG).show();
								  Toast.makeText(activity.getApplicationContext(), "xmlSwitch******  " + xmlSwitch,Toast.LENGTH_LONG).show();
				
							if(xmlSwitch.startsWith("INSERTED")){
								  
								in = new Intent(activity.getApplicationContext(), com.sysmind.home.UsersInGroupMain.class);
								  activity.startActivity(in);	
								
							}else if(xmlSwitch.startsWith("EXISTS")){
								AlertDialog.Builder alertExists = new AlertDialog.Builder(activity);

								alertExists.setMessage(data.get(mPosition).getUser().toUpperCase()+" already exists in "+optionGroup.toUpperCase());
								alertExists.setPositiveButton("OK",new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
												
											
											}
										});
								
								

								AlertDialog alertDialog = alertExists.create();
								alertDialog.show();
								
							}
									 
										
										}
									});
							
							alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
								  public void onClick(DialogInterface dialog, int id) {
								  dialog.cancel();
										}
									});

							AlertDialog alertDialog = alertDialogBuilder.create();
							alertDialog.show();
							       
	 		           
	 		           
	 		           }
	 		            	 
	 		            	 return true;  
	 		             }  
	 		            });  
	 		  
	 		            popup1.show();//showing popup menu  
	            		
	            		
	              }
	              
	              
	              return true;  
	             }  
	            });  
	  
	            popup.show();//showing popup menu  
				break;
				
				
			case R.id.connectmainbtn:
				Toast.makeText(activity.getApplicationContext(), "Welcome to connect**", Toast.LENGTH_LONG).show();
				Toast.makeText(activity.getApplicationContext(), "userin group main  connect EXTEN***"+data.get(mPosition).getExtension(), Toast.LENGTH_LONG).show();
//			String temp="http://192.168.1.128:8084/SysmindServices/extensionUsers?userName=tina";
//				
				in=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+data.get(mPosition).getExtension()));
				activity.startActivity(in);
				
//				in=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+data.get(mPosition).getExtension()));
//				activity.startActivity(in);
				break;
				
			case R.id.useringrpmaintyp_username:
			
			//	Toast.makeText(activity.getApplicationContext(), "dept connect EXTEN***"+data.get(mPosition).getExtension(), Toast.LENGTH_LONG).show();
				TextView txt = (TextView)v.findViewById(R.id.useringrpmaintyp_username);
				String txt1 = txt.getText().toString();
			//	Toast.makeText(activity.getApplicationContext(), "dept connect USERS***"+txt1, Toast.LENGTH_LONG).show();			
			//	Toast.makeText(activity,""+txt1, 1000).show();
				in=new Intent(activity.getApplicationContext(),com.sysmind.home.Employee_Profile_Page.class);
//				in.putExtra("UserName", txt1);
//				in.putExtra("UserExtension", data.get(mPosition).getExtension());
				
				LoginPage.toEdit.putBoolean("IS_LOGIN", true);
				LoginPage.toEdit.putString("UserName", txt1); 
				LoginPage.toEdit.putString("UserExtension", data.get(mPosition).getExtension()); 
			
				LoginPage.toEdit.commit(); 
				
				activity.startActivity(in);
				break;
			
			}
			
		}
	 }

}
