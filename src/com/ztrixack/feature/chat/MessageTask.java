package com.ztrixack.feature.chat;

import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ListView;

public class MessageTask {
	/** Called when the activity is first created. */
	private Activity mActivity;
	private ListView mView;
	private ArrayList<Message> messages;
	private AwesomeAdapter adapter;
	private static String sender = "Luna";

	public MessageTask(Activity activity, ListView view) {
		mActivity = activity;
		mView = view;
		messages = new ArrayList<Message>();

		messages.add(new Message("Hi", true));

		adapter = new AwesomeAdapter(mActivity, messages);
		mView.setAdapter(adapter);
	}

	public void sendMessage(String newMessage) {
		if (newMessage.length() > 0) {
			addNewMessage(new Message(newMessage, false));
			// new SendMessage().execute();
		}
	}

	public void receiveMessage(String newMessage) {
		if (newMessage.length() > 0) {
			addNewMessage(new Message(newMessage, true));
			// new SendMessage().execute();
		}
	}

	void addNewMessage(final Message msg) {
		mActivity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				messages.add(msg);
				adapter.notifyDataSetChanged();
				mView.setSelection(messages.size() - 1);
			}
		});

	}
}