package com.parse.starter.contacts;

import java.util.List;

import com.parse.ParseUser;
import com.parse.starter.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactsAdapter extends ArrayAdapter<ParseUser> {
	
	private Context context;
	private int layoutResourceId;
	private List<ParseUser> contacts;
	private ContactHolder holder;
	
	public static class ContactHolder {
		ParseUser contact;
		ImageView photo;
		TextView name;
		TextView status;
	}

	public ContactsAdapter(Context context, int layoutResourceId,
			List<ParseUser> contacts) {
		super(context, layoutResourceId, contacts);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.contacts = contacts;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View row = convertView;
		
		
		LayoutInflater inflater = LayoutInflater.from(getContext());
		row = inflater.inflate(layoutResourceId, parent, false);
				
		holder = new ContactHolder();
		holder.contact = contacts.get(position);
		holder.photo = (ImageView)row.findViewById(R.id.iv_contact_photo);
		holder.name = (TextView)row.findViewById(R.id.tv_contact_name);
		holder.status = (TextView)row.findViewById(R.id.tv_contact_status);
		
		row.setTag(holder);
		setupItem(holder);
		return row;
	}
	
	private void setupItem(final ContactHolder holder) {
		holder.name.setText(holder.contact.getUsername());
		
	}

}
