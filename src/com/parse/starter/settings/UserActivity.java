/*
 * Profile
 */

package com.parse.starter.settings;

import com.parse.ParseUser;
import com.parse.starter.ContactsActivity;
import com.parse.starter.GlobalData;
import com.parse.starter.R;
import com.parse.starter.chat.ChatsActivity;
import com.parse.starter.contacts.UserContactsActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class UserActivity extends Activity {
	
	private ImageView ivIcon, ivEditIcon, ivEditNickname;
	private EditText etNickname;
	private Button btnStart;
	private static final int SELECT_PHOTO = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		
		int isVisible = getIntent().getIntExtra("is_visible", View.INVISIBLE);
		
		
		
		ivIcon = (ImageView)findViewById(R.id.iv_user_icon);
		ivEditIcon = (ImageView)findViewById(R.id.iv_user_edit_icon);
		ivEditNickname = (ImageView)findViewById(R.id.iv_user_edit_nickname);
		etNickname = (EditText)findViewById(R.id.et_user_nickname);
		btnStart = (Button)findViewById(R.id.btn_start);
		
		// set widgets
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		ivIcon.getLayoutParams().height = width - 16 - 16;		
		btnStart.setVisibility(isVisible);
		
		// widgets actions
		ivEditIcon.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onClickIconEditButton();
			}
		});
		
		ivEditNickname.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onClickNameEditButton();
			}
		});
		
		btnStart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), ChatsActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
	
	private void onClickIconEditButton() {
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, SELECT_PHOTO);    
	}
	
	private void onClickNameEditButton() {
		Intent intent = new Intent(this, NameActivity.class);
		startActivity(intent);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		ParseUser user = ParseUser.getCurrentUser();
		if (user.getString("nickname") != null)
			etNickname.setText(user.getString("nickname"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user, menu);
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
