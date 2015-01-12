/*
 * Enter your name
 */

package com.parse.starter.settings;

import github.ankushsachdeva.emojicon.EmojiconEditText;
import github.ankushsachdeva.emojicon.EmojiconGridView.OnEmojiconClickedListener;
import github.ankushsachdeva.emojicon.EmojiconsPopup;
import github.ankushsachdeva.emojicon.EmojiconsPopup.OnEmojiconBackspaceClickedListener;
import github.ankushsachdeva.emojicon.EmojiconsPopup.OnSoftKeyboardOpenCloseListener;
import github.ankushsachdeva.emojicon.emoji.Emojicon;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.starter.GlobalData;
import com.parse.starter.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class NameActivity extends Activity {
	
	private LinearLayout ll;
	private EmojiconEditText etName;
	private Button btnCancel, btnOk;
	private EmojiconsPopup popup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_name);
		
		ll = (LinearLayout)findViewById(R.id.ll_name);
		etName = new EmojiconEditText(this);
		etName.setLayoutParams(new LinearLayout.LayoutParams(
				500,
                LinearLayout.LayoutParams.WRAP_CONTENT));
		//etName.setText("I \ue32d emojicon");
		etName.setEmojiconSize(30);
		ll.addView(etName);
		
		ImageButton btnIcon = new ImageButton(this);
		btnIcon.setLayoutParams(new LinearLayout.LayoutParams(
                80,
                80));
		btnIcon.setImageDrawable(getResources().getDrawable(R.drawable.smiley_happy));
		btnIcon.setScaleType(ScaleType.CENTER_CROP);
		ll.addView(btnIcon);
		
		String name = ParseUser.getCurrentUser().getString("nickname");
		
		btnIcon.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (popup == null) {
					popup = new EmojiconsPopup(ll, getApplicationContext());
					popup.setSizeForSoftKeyboard();
					setPopupWindowEvent();
				}
				if (!popup.isShowing())
					popup.showAsDropDown(etName);				
				 else 
					popup.dismiss();
			}
		});

		btnCancel = (Button)findViewById(R.id.btn_name_cancel);
		btnOk = (Button)findViewById(R.id.btn_name_ok);
		
		if (name != null)
			etName.setText(name);
		

		
		
		
		btnCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onClickCancelButton();
			}
		});
		
		btnOk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onClickOkButton();
			}
		});
	}
	
	private void setPopupWindowEvent() {
		// popup icon window
		//Set on emojicon click listener
		popup.setOnEmojiconClickedListener(new OnEmojiconClickedListener() {

					@Override
		            public void onEmojiconClicked(Emojicon emojicon) {
		                etName.append(emojicon.getEmoji());
		            }
		        });
		
		popup.setOnEmojiconBackspaceClickedListener(new OnEmojiconBackspaceClickedListener() {

		@Override
	    public void onEmojiconBackspaceClicked(View v) {
	        KeyEvent event = new KeyEvent(
	                 0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
	        etName.dispatchKeyEvent(event);
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
	
	private void onClickCancelButton() {
		finish();
	}
	
	private void onClickOkButton() {
		ParseUser user = ParseUser.getCurrentUser();
		user.put("nickname", etName.getText().toString());
		user.saveInBackground(new SaveCallback() {

			@Override
			public void done(ParseException e) {
				if (e == null)
					finish();
			}
			
		});
	}
}
