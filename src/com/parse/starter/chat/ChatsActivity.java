package com.parse.starter.chat;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.R;
import com.parse.starter.contacts.UserContactsActivity;
import com.parse.starter.group.GroupActivity;
import com.parse.starter.settings.UserActivity;
import com.parse.starter.status.StatusActivity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ChatsActivity extends Activity {
	
	private TextView tvHint;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chats);
		tvHint = (TextView)findViewById(R.id.tv_chats);
		
		ParseUser currentUser = ParseUser.getCurrentUser();
		ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Chat");
		query1.whereEqualTo("user1", currentUser);
		ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Chat");
		query2.whereEqualTo("user2", currentUser);
		List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
		queries.add(query1);
		queries.add(query2);
		ParseQuery<ParseObject> mainQuery = ParseQuery.or(queries);
		mainQuery.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> chats, ParseException e) {
				if (e==null) {
					Fragment newFrg = new ChatsFragment().newInstance(getApplicationContext(), chats);
		        	Fragment frg = getFragmentManager().findFragmentByTag("fragment_chats");
		        	FragmentTransaction ft = getFragmentManager().beginTransaction();
		        	if (frg != null)
						ft.remove(frg);
		        	ft.add(R.id.container_chats, newFrg, "fragment_chats").commit();
		        	if (chats == null || chats.size() == 0)
		        		tvHint.setText("Select a contact to start a chat");
		        	else
		        		tvHint.setText("Tap and hold on a chat for more options");
				}
					
			}
			
		});
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
			intent.putExtra("if_chat", true);
			startActivity(intent);
			return true;
		} else if (id == R.id.action_new_group) {
			Intent intent = new Intent(getApplicationContext(), GroupActivity.class);
			startActivity(intent);
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
