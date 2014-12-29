/*
 * Select contact
 */

package com.parse.starter.contacts;

import com.parse.starter.ContactsActivity;
import com.parse.starter.R;
import com.parse.starter.R.id;
import com.parse.starter.R.layout;
import com.parse.starter.R.menu;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class UserContactsActivity extends Activity {
	
	private Button btnAdd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		int isVisible = getIntent().getIntExtra("is_visible", View.INVISIBLE);
		String title = getIntent().getStringExtra("title");
		
		setContentView(R.layout.activity_user_contacts);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(title);
		//actionBar.setSubtitle("64 contacts");
		
		btnAdd = (Button)findViewById(R.id.btn_contacts_add);
		
		btnAdd.setVisibility(isVisible);
		
		btnAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onClickAddButton();
			}
		});
	}
	
	private void onClickAddButton() {
		Intent intent = new Intent(getApplicationContext(), ContactsActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_contacts, menu);
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
