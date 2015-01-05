package com.parse.starter.chat;

import github.ankushsachdeva.emojicon.EmojiconEditText;
import github.ankushsachdeva.emojicon.EmojiconsPopup;
import github.ankushsachdeva.emojicon.EmojiconGridView.OnEmojiconClickedListener;
import github.ankushsachdeva.emojicon.EmojiconsPopup.OnEmojiconBackspaceClickedListener;
import github.ankushsachdeva.emojicon.EmojiconsPopup.OnSoftKeyboardOpenCloseListener;
import github.ankushsachdeva.emojicon.emoji.Emojicon;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.starter.R;
import com.parse.starter.database.MessageDatasource;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class ChatActivity extends Activity {
	
	private ListView lvChat;
	private ImageButton ibSend, ibIcon;
	private ChatAdapter listAdapter;
	private EmojiconEditText etInput;
	private EmojiconsPopup popup;
	private RelativeLayout rlChat;
	
	private ParseUser currentUser = ParseUser.getCurrentUser();
	private ParseUser contact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		ibIcon = (ImageButton)findViewById(R.id.ib_icon);
		etInput = (EmojiconEditText)findViewById(R.id.et_chat_input);
		rlChat = (RelativeLayout)findViewById(R.id.rl_chat);
		
		final ActionBar actionBar = getActionBar();
		actionBar.setIcon(R.drawable.default_photo);
		
		String objectId = getIntent().getStringExtra("contact");
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.getInBackground(objectId, new GetCallback<ParseUser>() {

			@Override
			public void done(ParseUser user, ParseException e) {
				if (e == null) {
					contact = user;
					String name = user.getString("nickname");
					if (name == null || name.equals(""))
						name = user.getUsername();
					actionBar.setTitle(name);
				}
					
			}
		});
		
		lvChat = (ListView)findViewById(R.id.lv_chat);
		ArrayList<String> chatList = new ArrayList<String>();
		listAdapter = new ChatAdapter(this, R.layout.item_chat, chatList);
		lvChat.setAdapter(listAdapter);
		
		ibSend = (ImageButton)findViewById(R.id.ib_send);
		ibSend.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String content = etInput.getText().toString();
				if (!content.equals("")) {
					// 1 - display message
					listAdapter.add(content);
					// 2 - create a new chat if not exists between these two users
					createNewChatIfNotExists(content);
					// 3 - clear input text box
					etInput.setText("");
				}
					
			}
		});
		
		ibIcon.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (popup == null) {
					popup = new EmojiconsPopup(rlChat, getApplicationContext());
					popup.setSizeForSoftKeyboard();
					setPopupWindowEvent();
				}
				if (!popup.isShowing())
					popup.showAtBottom();			
				 else 
					popup.dismiss();
			}
		});
	}
	
	private void createNewChatIfNotExists(final String content) {
		// check if chat exists
		ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Chat");
		query1.whereEqualTo("user1", currentUser);
		query1.whereEqualTo("user2", contact);
		ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Chat");
		query1.whereEqualTo("user1", contact);
		query1.whereEqualTo("user2", currentUser);
		List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
		queries.add(query1);
		queries.add(query2);
		ParseQuery<ParseObject> mainQuery = ParseQuery.or(queries);
		
		mainQuery.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null) {
					if (objects != null && objects.size() > 0) {
						ParseObject chat = objects.get(0);
						createNewMessage(chat, content);
					} else {
						final ParseObject chat = new ParseObject("Chat");
						chat.put("user1", currentUser);
						chat.put("user2", contact);
						chat.saveInBackground(new SaveCallback() {

							@Override
							public void done(ParseException arg0) {
								createNewMessage(chat, content);
							}
							
						});
					}
				}
			}
			
		});
	}
	
	private void createNewMessage(ParseObject chat, String content) {
		// 4 - create one message which is belongs to this chat
		MessageDatasource messageDatasource = new MessageDatasource();
		messageDatasource.createMessage(chat, currentUser, content);
	}
	
	private void setPopupWindowEvent() {
		
		popup.setOnEmojiconClickedListener(new OnEmojiconClickedListener() {

					@Override
		            public void onEmojiconClicked(Emojicon emojicon) {
		                etInput.append(emojicon.getEmoji());
		            }
		        });
		
		popup.setOnEmojiconBackspaceClickedListener(new OnEmojiconBackspaceClickedListener() {

		@Override
	    public void onEmojiconBackspaceClicked(View v) {
	        KeyEvent event = new KeyEvent(
	                 0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
	        etInput.dispatchKeyEvent(event);
	    }
	});
	
	//Set listener for keyboard open/close
	popup.setOnSoftKeyboardOpenCloseListener(new OnSoftKeyboardOpenCloseListener() {

	            @Override
	            public void onKeyboardOpen(int keyBoardHeight) {
	                if(!popup.isShowing()){
	                    popup.showAtBottom();
	                }
	            }

	            @Override
	            public void onKeyboardClose() {
	                if(popup.isShowing())
	                    popup.dismiss();
	            }
	        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chat, menu);
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
