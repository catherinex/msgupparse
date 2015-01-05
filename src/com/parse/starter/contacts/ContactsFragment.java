package com.parse.starter.contacts;

import java.util.List;

import com.parse.ParseUser;

import com.parse.starter.R;
import com.parse.starter.chat.ChatActivity;
import com.parse.starter.settings.UserActivity;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class ContactsFragment extends ListFragment {
	private Context context;
	private List<ParseUser> contacts;
	private boolean ifChat = false;
	
	public void setContext(Context context) {
		this.context = context;
	}
	
	public void setTourList(List<ParseUser> contacts){
		this.contacts = contacts;
	}
	
	public void setIfChat(boolean ifChat) {
		this.ifChat = ifChat;
	}
	
	public static ContactsFragment newInstance(Context context, List<ParseUser> contacts) {
		ContactsFragment fragment = new ContactsFragment();	    
		fragment.setContext(context);
		fragment.setTourList(contacts);
		return fragment;
	}
	
	public static ContactsFragment newInstance(Context context, List<ParseUser> contacts
			, boolean ifChat) {
		ContactsFragment fragment = new ContactsFragment();	    
		fragment.setContext(context);
		fragment.setTourList(contacts);
		fragment.setIfChat(ifChat);
		return fragment;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);	   
	    ContactsAdapter customAdapter = new ContactsAdapter(context, R.layout.item_contact, contacts);
	    setListAdapter(customAdapter);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		ParseUser user = (ParseUser)l.getItemAtPosition(position);
		if (ifChat) {
			Intent intent = new Intent(context, ChatActivity.class);
			intent.putExtra("contact", user.getObjectId());
			startActivity(intent);
		} else {
			Intent intent = new Intent(context, ContactActivity.class);
			intent.putExtra("contact", user.getObjectId());
			startActivity(intent);
		}
		
	}
}
