package com.parse.starter;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import github.ankushsachdeva.emojicon.EmojiconEditText;
import github.ankushsachdeva.emojicon.EmojiconsPopup;
import github.ankushsachdeva.emojicon.EmojiconGridView.OnEmojiconClickedListener;
import github.ankushsachdeva.emojicon.EmojiconsPopup.OnEmojiconBackspaceClickedListener;
import github.ankushsachdeva.emojicon.EmojiconsPopup.OnSoftKeyboardOpenCloseListener;
import github.ankushsachdeva.emojicon.emoji.Emojicon;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class NewStatusActivity extends Activity {
	
	private EmojiconEditText etStatus;
	private Button btnCancel, btnOk;
	private ImageButton ibIcon;
	private EmojiconsPopup popup;
	private RelativeLayout rlInput;
	private ParseUser currentUser = ParseUser.getCurrentUser();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_status);
		etStatus = (EmojiconEditText)findViewById(R.id.et_newstatus);
		btnCancel = (Button)findViewById(R.id.btn_newstatus_cancel);
		btnOk = (Button)findViewById(R.id.btn_newstatus_ok);
		ibIcon = (ImageButton)findViewById(R.id.ib_newstatus_icon);
		rlInput = (RelativeLayout)findViewById(R.id.rl_newstatus_input);

		String status = currentUser.getString("status");
		if ( status != null && !status.equals(""))
			etStatus.setText(status);
		
		ibIcon.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (popup == null) {
					popup = new EmojiconsPopup(rlInput, getApplicationContext());
					popup.setSizeForSoftKeyboard();
					setPopupWindowEvent();
				}
				if (!popup.isShowing())
					popup.showAsDropDown(etStatus);				
				 else 
					popup.dismiss();
			}
		});
		
		btnCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		btnOk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				currentUser.put("status", etStatus.getText().toString());
				currentUser.saveInBackground(new SaveCallback() {

					@Override
					public void done(ParseException e) {
						if (e == null)
							finish();
					}
					
				});
			}
		});
	}
	
	private void setPopupWindowEvent() {
		
		popup.setOnEmojiconClickedListener(new OnEmojiconClickedListener() {

					@Override
		            public void onEmojiconClicked(Emojicon emojicon) {
		                etStatus.append(emojicon.getEmoji());
		            }
		        });
		
		popup.setOnEmojiconBackspaceClickedListener(new OnEmojiconBackspaceClickedListener() {

		@Override
	    public void onEmojiconBackspaceClicked(View v) {
	        KeyEvent event = new KeyEvent(
	                 0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
	        etStatus.dispatchKeyEvent(event);
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
}
