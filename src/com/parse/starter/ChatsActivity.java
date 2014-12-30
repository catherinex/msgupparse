package com.parse.starter;

import com.parse.starter.contacts.UserContactsActivity;
import com.parse.starter.settings.UserActivity;
import com.parse.starter.status.StatusActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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
			Intent intent = new Intent(getApplicationContext(), UserContactsActivity.class);
			intent.putExtra("title", "Select contact");
			startActivity(intent);
			return true;
		} else if (id == R.id.action_contacts) {
			Intent intent = new Intent(getApplicationContext(), UserContactsActivity.class);
			intent.putExtra("is_visible", View.VISIBLE);
			intent.putExtra("title", "Contacts");
			startActivity(intent);
			return true;
		} else if (id == R.id.action_settings) {
			Intent intent = new Intent(getApplicationContext(), UserActivity.class);
			startActivity(intent);
			return true;
		} else if (id == R.id.action_status) {
			Intent intent = new Intent(getApplicationContext(), StatusActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
