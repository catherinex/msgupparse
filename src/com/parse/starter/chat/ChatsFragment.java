package com.parse.starter.chat;

import java.util.List;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.GlobalData;
import com.parse.starter.R;
import com.parse.starter.contacts.ContactsAdapter;
import com.parse.starter.contacts.ContactsFragment;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class ChatsFragment extends ListFragment {
	
	private Context context;
	private List<ParseObject> chats;
	private ChatsAdapter customAdapter;
	private int currentPosition;
	
	public void setContext(Context context) {
		this.context = context;
	}
	
	public void setChats(List<ParseObject> chats){
		this.chats = chats;
	}
	
	public static ChatsFragment newInstance(Context context, List<ParseObject> chats) {
		ChatsFragment fragment = new ChatsFragment();	    
		fragment.setContext(context);
		fragment.setChats(chats);
		return fragment;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);	   
	    customAdapter = new ChatsAdapter(context, R.layout.item_chats, chats);
	    setListAdapter(customAdapter);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		ParseObject chat = (ParseObject)l.getItemAtPosition(position);
		currentPosition = position;
		Intent intent = new Intent(context, ChatActivity.class);
		intent.putExtra("chat_id", chat.getObjectId());
		startActivityForResult(intent, GlobalData.REQUEST_CHAT);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
		if (resultCode == getActivity().RESULT_OK) {
			String chatId = data.getStringExtra("chat_id");
			customAdapter.remove(chats.get(currentPosition));
			ParseQuery.getQuery("Chat").getInBackground(chatId, new GetCallback<ParseObject>() {

				@Override
				public void done(ParseObject chat, ParseException e) {
					customAdapter.insert(chat, currentPosition);
				}
				
			});
		}
    }


}
