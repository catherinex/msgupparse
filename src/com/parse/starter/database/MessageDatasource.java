package com.parse.starter.database;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class MessageDatasource {
	
	private static String TABLE = "Message";
	private static String COL_CHAT = "chat";
	private static String COL_SENDER = "sender";
	private static String COL_TEXT = "text";
	
	public void createMessage(ParseObject chat, ParseUser sender, String text) {
		ParseObject message = new ParseObject(TABLE);
		message.put(COL_CHAT, chat);
		message.put(COL_SENDER, sender);
		message.put(COL_TEXT, text);
		message.saveInBackground(new SaveCallback() {

			@Override
			public void done(ParseException e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}

}
