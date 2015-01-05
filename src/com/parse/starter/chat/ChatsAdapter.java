package com.parse.starter.chat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.R;
import com.parse.starter.contacts.ContactsAdapter.ContactHolder;

public class ChatsAdapter extends ArrayAdapter<ParseObject> {
	
	public static class ChatHolder {
		ParseObject chat;
		ImageView photo;
		TextView name;
		TextView content;
		TextView date;
	}
	
	private Context context;
	private int layoutResourceId;
	private List<ParseObject> chats;
	private ChatHolder holder;
	
	public ChatsAdapter(Context context, int layoutResourceId,
			List<ParseObject> chats) {
		super(context, layoutResourceId, chats);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.chats = chats;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View row = convertView;
		
		
		LayoutInflater inflater = LayoutInflater.from(getContext());
		row = inflater.inflate(layoutResourceId, parent, false);
				
		holder = new ChatHolder();
		holder.chat = chats.get(position);
		holder.photo = (ImageView)row.findViewById(R.id.iv_chats_photo);
		holder.name = (TextView)row.findViewById(R.id.tv_chats_contact);
		holder.content = (TextView)row.findViewById(R.id.tv_chats_content);
		holder.date = (TextView)row.findViewById(R.id.tv_chats_date);
		
		row.setTag(holder);
		setupItem(holder);
		return row;
	}
	
	private String setupTxtName(ParseUser contact) {
		String nickname = contact.getString("nickname");
		if (nickname != null && !nickname.equals(""))
			return nickname;
		else
			return contact.getUsername();
	}
	
	private void setupItem(final ChatHolder holder) {
		final ParseUser currentUser = ParseUser.getCurrentUser();
		holder.chat.getParseUser("user1")
		.fetchIfNeededInBackground(new GetCallback<ParseUser>() {

			@Override
			public void done(ParseUser user1, ParseException e) {
				if (e == null) {
					if (user1.equals(currentUser)) {
						holder.chat.getParseUser("user2")
						.fetchIfNeededInBackground(new GetCallback<ParseUser>() {

							@Override
							public void done(ParseUser user2, ParseException e) {
								if (e == null)
									holder.name.setText(setupTxtName(user2));
							}
							
						});
					} else {
						holder.name.setText(setupTxtName(user1));
					}
				}
					
			}
			
		});
		
		// get last message
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Message");
		query.whereEqualTo("chat", holder.chat);
		query.orderByDescending("createdAt");
		query.getFirstInBackground(new GetCallback<ParseObject>() {

			@Override
			public void done(ParseObject message, ParseException e) {
				if (e == null) {
					holder.content.setText(message.getString("text"));
					DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					holder.date.setText(df.format(message.getCreatedAt()));
				}					
			}
			
		});
	}

}
