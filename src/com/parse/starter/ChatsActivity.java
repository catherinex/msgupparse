package com.parse.starter;

import com.parse.starter.settings.UserActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ChatsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chats);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu;  this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chats, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_new_chat) {
			Intent intent = new Intent(getApplicationContext(), ContactsActivity.class);
			startActivity(intent);
			return true;
		} else if (id == R.id.action_new_contact) {
			Intent intent = new Intent(getApplicationContext(), ContactsActivity.class);
			startActivity(intent);
			return true;
		} else if (id == R.id.action_settings) {
			Intent intent = new Intent(getApplicationContext(), UserActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
