package com.parse.starter;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignupActivity extends Activity {
	
	private EditText etUsername;
	private EditText etPassword;
	private EditText etEmail;
	private TextView tvError;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		
		etUsername = (EditText)findViewById(R.id.et_signup_username);
		etPassword = (EditText)findViewById(R.id.et_signup_password);
		etEmail = (EditText)findViewById(R.id.et_signup_email);
		tvError = (TextView)findViewById(R.id.tv_signup_error);
		Button btnCreateAccount = (Button)findViewById(R.id.btn_create_account);
		
		tvError.setVisibility(View.INVISIBLE);
		
		btnCreateAccount.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ParseUser user = new ParseUser();
				user.setUsername(etUsername.getText().toString());
				user.setPassword(etPassword.getText().toString());
				user.setEmail(etEmail.getText().toString());				
				user.put("status", "Hey there! I am using MsgUp.");
				 
				user.signUpInBackground(new SignUpCallback() {
				  public void done(ParseException e) {
				    if (e == null) {
				      // Hooray! Let them use the app now.
				    	Intent intent = new Intent(getApplicationContext(), ChatsActivity.class);
						startActivity(intent);
						finish();
				    } else {
				      // Sign up didn't succeed. Look at the ParseException
				      // to figure out what went wrong
				    	tvError.setVisibility(View.VISIBLE);
				    }
				  }
				});
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.signup, menu);
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
