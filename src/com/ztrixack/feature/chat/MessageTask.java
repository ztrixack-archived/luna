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
			new SendMessage().execute();
		}
	}

	public void receiveMessage(String newMessage) {
		if (newMessage.length() > 0) {
			addNewMessage(new Message(newMessage, true));
			new SendMessage().execute();
		}
	}

	private class SendMessage extends AsyncTask<Void, String, String> {
		@Override
		protected String doInBackground(Void... params) {
			try {
				Thread.sleep(2000); // simulate a network call
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			this.publishProgress(String.format("%s started writing", sender));
			try {
				Thread.sleep(2000); // simulate a network call
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.publishProgress(String.format("%s has entered text", sender));
			try {
				Thread.sleep(3000);// simulate a network call
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			return "Oho";

		}

		@Override
		public void onProgressUpdate(String... v) {
			// check wether we have already added a status message
			if (messages.get(messages.size() - 1).isStatusMessage) {
				messages.get(messages.size() - 1).setMessage(v[0]);
				// update the status for that
				adapter.notifyDataSetChanged();
				mView.setSelection(messages.size() - 1);
			} else {
				addNewMessage(new Message(true, v[0]));
				// add new message, if there is no existing status message
			}
		}

		@Override
		protected void onPostExecute(String text) {
			if (messages.get(messages.size() - 1).isStatusMessage)
			// check if there is any status message, now remove it.
			{
				messages.remove(messages.size() - 1);
			}

			addNewMessage(new Message(text, false));
			// add the orignal message from server.
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