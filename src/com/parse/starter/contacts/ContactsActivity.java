/*
 * Add contacts
 */

package com.parse.starter.contacts;

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ParseException;
import com.parse.starter.R;
import com.parse.starter.R.id;
import com.parse.starter.R.layout;
import com.parse.starter.R.menu;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactsActivity extends Activity {
	
	private ImageView ivSearch;
	private EditText etSearch;
	private TextView tvMy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);
		
		etSearch = (EditText)findViewById(R.id.et_contacts_search);
		ivSearch = (ImageView)findViewById(R.id.iv_contacts_search);
		tvMy = (TextView)findViewById(R.id.tv_contacts_my);
		
		tvMy.setText("My username: " + ParseUser.getCurrentUser().getUsername());
		
		ivSearch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ParseQuery<ParseUser> query = ParseUser.getQuery();
				query.whereContains("username", etSearch.getText().toString());
				query.whereNotEqualTo("objectId", ParseUser.getCurrentUser().getObjectId());
				
				query.findInBackground(new FindCallback<ParseUser>() {
				  public void done(List<ParseUser> objects, ParseException e) {
				    if (e == null) {
				    	Fragment newFrg = new ContactsFragment().newInstance(getApplicationContext(), objects);
			        	 Fragment frg = getFragmentManager().findFragmentByTag("fragment_contacts");
			        	 FragmentTransaction ft = getFragmentManager().beginTransaction();
			        	 if (frg != null)
							 ft.remove(frg);
			        	 ft.add(R.id.container_contacts, newFrg, "fragment_contacts").commit();
				    } else {
				        // Something went wrong.
				    }
				  }
				});
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contacts, menu);
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
