package com.parse.starter.settings;

import com.parse.starter.GlobalData;
import com.parse.starter.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class NameActivity extends Activity {
	
	private EditText etName;
	private Button btnCancel, btnOk;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_name);
		
		String name = GlobalData.currentUser.getString("nickname");
		
		etName = (EditText)findViewById(R.id.et_name);
		btnCancel = (Button)findViewById(R.id.btn_name_cancel);
		btnOk = (Button)findViewById(R.id.btn_name_ok);
		
		if (name != null)
			etName.setText(name);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.name, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
