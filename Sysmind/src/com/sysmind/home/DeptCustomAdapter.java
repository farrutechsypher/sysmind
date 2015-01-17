package com.sysmind.home;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.csipsimple.R;

public class DeptCustomAdapter extends BaseAdapter   implements OnClickListener {
	private Activity activity;
    private ArrayList<DepSampleBean> data;
    private static LayoutInflater inflater=null;
    public Resources res;
    
    Intent in;
    int i=0;
    public DeptCustomAdapter(Activity list, ArrayList<DepSampleBean> d) {
        
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
            vi = inflater.inflate(R.layout.departmenttype_singleitem, null);
            holder = new ViewHolder();
             
            /****** View Holder Object to contain tabitem.xml file elements ******/
          holder.tv=(TextView) vi.findViewById(R.id.departmenttyp_username);
          holder.bgroup=(Button) vi.findViewById(R.id.addGroupbtn);
          holder. b2=(Button) vi.findViewById(R.id.connectbtn);
             
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
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			
			switch(v.getId())
			{
			case R.id.addGroupbtn:
				
				in=new Intent(activity.getApplicationContext(),com.sysmind.home.MyGroup.class);
//				in.putExtra("UserName", data.get(mPosition).getUser());
//				activity.startActivity(in);
				
				LoginPage.toEdit.putBoolean("IS_LOGIN", true);
				LoginPage.toEdit.putString("UserName",  data.get(mPosition).getUser()); 
//				LoginPage.toEdit.putString("UserExtension", data.get(mPosition).getExtension()); 
			
				LoginPage.toEdit.commit(); 
				
				activity.startActivity(in);
				break;
			case R.id.connectbtn:
			//	Toast.makeText(activity.getApplicationContext(), "dept connect USERS***"+data.get(mPosition).getUser(), Toast.LENGTH_LONG).show();
			//	Toast.makeText(activity.getApplicationContext(), "dept connect EXTEN***"+data.get(mPosition).getExtension(), Toast.LENGTH_LONG).show();
//			String temp="http://192.168.1.128:8084/SysmindServices/extensionUsers?userName=tina";
//				
				in=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+data.get(mPosition).getExtension()));
				activity.startActivity(in);
				break;
				
			case R.id.departmenttyp_username:
			
			//	Toast.makeText(activity.getApplicationContext(), "dept connect EXTEN***"+data.get(mPosition).getExtension(), Toast.LENGTH_LONG).show();
				TextView txt = (TextView)v.findViewById(R.id.departmenttyp_username);
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
