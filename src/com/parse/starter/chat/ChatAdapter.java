package com.parse.starter.chat;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseUser;
import com.parse.starter.R;
import com.parse.starter.contacts.ContactsAdapter.ContactHolder;

public class ChatAdapter extends ArrayAdapter<String> {
	
	public static class ChatHolder {
		String chat;
		TextView tvChat;
	}
	
	private Context context;
	private int layoutResourceId;
	private List<String> chats;
	private ChatHolder holder;
	

	public ChatAdapter(Context context, int layoutResourceId,
			List<String> chats) {
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
		holder.tvChat = (TextView)row.findViewById(R.id.tv_chat_text);
		
		row.setTag(holder);
		setupItem(holder);
		return row;
	}
	
	private void setupItem(final ChatHolder holder) {
		holder.tvChat.setText(holder.chat);
	}
}
