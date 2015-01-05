/*
 * Select contact
 */

package com.parse.starter.contacts;

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.starter.ContactsActivity;
import com.parse.starter.R;
import com.parse.starter.R.id;
import com.parse.starter.R.layout;
import com.parse.starter.R.menu;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class UserContactsActivity extends Activity {
	
	private Button btnAdd;
	private List<ParseUser> contacts;
	private String subtitle = "0 contact";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_contacts);
		
		int isVisible = getIntent().getIntExtra("is_visible", View.INVISIBLE);
		final boolean ifChat = getIntent().getBooleanExtra("if_chat", false);
		String title = getIntent().getStringExtra("title");
		
		final ActionBar actionBar = getActionBar();
		actionBar.setTitle(title);
				
		final ParseUser currentUser = ParseUser.getCurrentUser();
		ParseRelation<ParseUser> relation = currentUser.getRelation("contacts");
		ParseQuery<ParseUser> query = relation.getQuery();
		query.findInBackground(new FindCallback<ParseUser>() {

			@Override
			public void done(List<ParseUser> objects, ParseException e) {
				if (e == null) {
					contacts = objects;
					
					ParseQuery<ParseUser> query = ParseUser.getQuery();
					query.whereEqualTo("contacts", currentUser);
					query.findInBackground(new FindCallback<ParseUser>() {

						@Override
						public void done(List<ParseUser> users,
								ParseException e) {
							if (e == null) {
								if (users != null && users.size() > 0) {
									for (ParseUser user : users)
										contacts.add(user);
								}
								
								if (contacts != null && contacts.size() > 0) {
									if (contacts.size() == 1)
										subtitle = "1 contact";
									else
										subtitle = Integer.toString(contacts.size()) + " contacts";
								}
								actionBar.setSubtitle(subtitle);
								
								// load fragment
								Fragment newFrg = new ContactsFragment().newInstance(getApplicationContext(), contacts, ifChat);
					        	Fragment frg = getFragmentManager().findFragmentByTag("fragment_user_contacts");
					        	FragmentTransaction ft = getFragmentManager().beginTransaction();
					        	if (frg != null)
									ft.remove(frg);
					        	ft.add(R.id.container_user_contacts, newFrg, "fragment_user_contacts").commit();
							}
						}
						
					});
					
				}
					
			}
			
		});
		
		
		
		
		
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
