package com.parse.starter.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.starter.NewStatusActivity;
import com.parse.starter.R;
import com.parse.starter.R.id;
import com.parse.starter.R.layout;
import com.parse.starter.R.menu;

import android.R.color;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class StatusActivity extends Activity {
	
	private TextView tvStatus;
	private ListView lvStatus;
	private ImageButton ibEdit;
	private ArrayAdapter<String> listAdapter ;  
	private String currentStatus;
	private ParseUser currentUser = ParseUser.getCurrentUser();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_status);
		tvStatus = (TextView)findViewById(R.id.tv_status_current);
		lvStatus = (ListView)findViewById(R.id.lv_status);
		ibEdit = (ImageButton)findViewById(R.id.ib_status_edit);	
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Status");
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null) {
					 ArrayList<String> statusList = new ArrayList<String>();
					 for (ParseObject status : objects) {		
						 statusList.add(status.getString("name"));
						 listAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.item_status, statusList);
					 }
					 lvStatus.setAdapter( listAdapter );
					 onListViewItemClick();
				}
			}
			
		});
		
		ibEdit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), NewStatusActivity.class);
				startActivity(intent);
			}
		});
	       
	}
	
	private void onListViewItemClick() {
		lvStatus.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				final String status = parent.getItemAtPosition(position).toString();
				ParseUser currentUser = ParseUser.getCurrentUser();
				currentUser.put("status", status);
				currentUser.saveInBackground(new SaveCallback() {

					@Override
					public void done(ParseException e) {
						if (e == null)
							tvStatus.setText(status);
					}
					
				});
				
			}
			
		});
	}
	
	@Override
	public void onResume() {
		super.onResume();
		currentStatus = currentUser.getString("status");
		if (currentStatus != null)
			tvStatus.setText(currentStatus);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.status, menu);
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
