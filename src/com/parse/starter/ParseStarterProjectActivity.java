package com.parse.starter;

/*
 * log in -> ChatsActivity
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseUser;
import com.parse.ParseException;

public class ParseStarterProjectActivity extends Activity {
	
	private TextView tvError;
	private EditText etUsername;
	private EditText etPassword;
	
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ParseAnalytics.trackAppOpened(getIntent());
		
		tvError = (TextView)findViewById(R.id.tv_error);
		etUsername = (EditText)findViewById(R.id.et_username);
		etPassword = (EditText)findViewById(R.id.et_password);
		
		Button btnLogin = (Button)findViewById(R.id.btn_login);
		Button btnSignup = (Button)findViewById(R.id.btn_signup);
		
		tvError.setVisibility(View.INVISIBLE);
		
		// log in
				btnLogin.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						ParseUser.logInInBackground(etUsername.getText().toString(), etPassword.getText().toString(), new LogInCallback() {
							  public void done(ParseUser user, ParseException e) {
							    if (user != null) {
							      // Hooray! The user is logged in.
							    	Intent intent = new Intent(getApplicationContext(), ChatsActivity.class);
									startActivity(intent);
									finish();
							    } else {
							      // Signup failed. Look at the ParseException to see what happened.
							    	tvError.setVisibility(View.VISIBLE);
							    	tvError.setText("Username or Password is INVALID!");
							    }
							  }
						});
					}
				});
				
				// sign up
				btnSignup.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
						startActivity(intent);
					}
				});
	}
}
