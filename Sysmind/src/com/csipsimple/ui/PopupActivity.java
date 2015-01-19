package com.csipsimple.ui;

import com.csipsimple.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class PopupActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_popup_layout);
		
		String msg = getIntent().getStringExtra("msg");
		
		findViewById(R.id.ok_btn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setResult(RESULT_OK);
				finish();
				
			}
		});
		
		((TextView)findViewById(R.id.msg_txtv)).setText(msg);
	}

}
