package com.parse.starter.contacts;

import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.parse.starter.R;
import com.parse.starter.R.id;
import com.parse.starter.R.layout;
import com.parse.starter.R.menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ContactActivity extends Activity {
	
	private TextView tvNickname, tvUsername, tvStatus;
	private ParseUser contact;
	private Button btnFollow;
	private ParseUser currentUser = ParseUser.getCurrentUser();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_contact);
		tvNickname = (TextView)findViewById(R.id.tv_contact_nickname);
		tvUsername = (TextView)findViewById(R.id.tv_contact_username);
		tvStatus = (TextView)findViewById(R.id.tv_contact_status);
		btnFollow = (Button)findViewById(R.id.btn_contact_follow);
		
		btnFollow.setVisibility(View.INVISIBLE);
		
		String objectId = getIntent().getStringExtra("contact");
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.getInBackground(objectId, new GetCallback<ParseUser>() {

			@Override
			public void done(ParseUser user, ParseException e) {
				if (e == null) {
					contact = user;
					String status = contact.getString("status");
					tvNickname.setText(contact.getString("nickname"));
					tvUsername.setText(contact.getUsername());
					if (status != null)
						tvStatus.setText(status);
					
					// check if this user is already one of contacts
					
					ParseRelation<ParseUser> relation = currentUser.getRelation("contacts");
					ParseQuery<ParseUser> query2 = relation.getQuery();
					query2.findInBackground(new FindCallback<ParseUser>() {

						@Override
						public void done(List<ParseUser> objects, ParseException e) {
							if (objects.size() > 0) {
								for (ParseUser pu : objects) {
									if (pu.getObjectId().equals(contact.getObjectId())) {
										btnFollow.setText("Chat");
										break;
									}
										
								}
								
							} else {
								ParseQuery<ParseUser> query = ParseUser.getQuery();
								query.whereEqualTo("contacts", currentUser);
								query.findInBackground(new FindCallback<ParseUser>() {

									@Override
									public void done(List<ParseUser> objects,
											ParseException e) {
										if (objects.size() > 0) {
											for (ParseUser pu : objects) {
												if (pu.getObjectId().equals(contact.getObjectId())) {
													btnFollow.setText("Chat");
													break;
												}
											}
										}
											
									}
									
								});
							}
							btnFollow.setVisibility(View.VISIBLE);
						}
						
					});
				}
					
			}
		});
		
		
		
		btnFollow.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// add relation to current user
				final ParseUser currentUser = ParseUser.getCurrentUser();
				ParseRelation<ParseUser> relation = currentUser.getRelation("contacts");
				relation.add(contact);
				currentUser.saveInBackground(new SaveCallback() {

					@Override
					public void done(ParseException e) {
						if (e == null) {
							finish();
						}
							
					}
					
				});
				
				
			}
		});
		
		
	}
}
