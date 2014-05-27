package com.ztrixack.domain;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.ztrixack.auth.google.AuthenGoogleAccount;
import com.ztrixack.database.DatabaseHelper;
import com.ztrixack.database.model.Emotion;
import com.ztrixack.database.model.Word;
import com.ztrixack.luna.R;

public class LoginActivity extends ActionBarActivity {

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private static AuthenGoogleAccount authenGPlus;
	DatabaseHelper db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

		authenGPlus = new AuthenGoogleAccount(this);

		deTest();
	}

	private void deTest() {
		db = new DatabaseHelper(getApplicationContext());
		// Creating tags
		Emotion tag1 = new Emotion("Shopping");
		Emotion tag2 = new Emotion("Important");
		Emotion tag3 = new Emotion("Watchlist");
		Emotion tag4 = new Emotion("Androidhive");

		// Inserting tags in db
		long tag1_id = db.createEmotion(tag1);
		long tag2_id = db.createEmotion(tag2);
		long tag3_id = db.createEmotion(tag3);
		long tag4_id = db.createEmotion(tag4);

		Log.d("Emotion Count", "Emotion Count: " + db.getAllEmotions().size());

		// Creating ToDos
		Word todo1 = new Word("iPhone 5S", 0);
		Word todo2 = new Word("Galaxy Note II", 0);
		Word todo3 = new Word("Whiteboard", 0);

		Word todo4 = new Word("Riddick", 0);
		Word todo5 = new Word("Prisoners", 0);
		Word todo6 = new Word("The Croods", 0);
		Word todo7 = new Word("Insidious: Chapter 2", 0);

		Word todo8 = new Word("Don't forget to call MOM", 0);
		Word todo9 = new Word("Collect money from John", 0);

		Word todo10 = new Word("Post new Article", 0);
		Word todo11 = new Word("Take database backup", 0);

		// Inserting todos in db
		// Inserting todos under "Shopping" Emotion
		long todo1_id = db.createToDo(todo1, new long[] { tag1_id });
		long todo2_id = db.createToDo(todo2, new long[] { tag1_id });
		long todo3_id = db.createToDo(todo3, new long[] { tag1_id });

		// Inserting todos under "Watchlist" Emotion
		long todo4_id = db.createToDo(todo4, new long[] { tag3_id });
		long todo5_id = db.createToDo(todo5, new long[] { tag3_id });
		long todo6_id = db.createToDo(todo6, new long[] { tag3_id });
		long todo7_id = db.createToDo(todo7, new long[] { tag3_id });

		// Inserting todos under "Important" Emotion
		long todo8_id = db.createToDo(todo8, new long[] { tag2_id });
		long todo9_id = db.createToDo(todo9, new long[] { tag2_id });

		// Inserting todos under "Androidhive" Emotion
		long todo10_id = db.createToDo(todo10, new long[] { tag4_id });
		long todo11_id = db.createToDo(todo11, new long[] { tag4_id });

		Log.e("Word Count", "Word count: " + db.getToDoCount());

		// "Post new Article" - assigning this under "Important" Emotion
		// Now this will have - "Androidhive" and "Important" Emotions
		db.createWordEmotion(todo10_id, tag2_id);

		// Getting all tag names
		Log.d("Get Emotions", "Getting All Emotions");

		List<Emotion> allEmotions = db.getAllEmotions();
		for (Emotion tag : allEmotions) {
			Log.d("Emotion Name", tag.getEmotion());
		}

		// Getting all Words
		Log.d("Get Words", "Getting All ToDos");

		List<Word> allToDos = db.getAllToDos();
		for (Word todo : allToDos) {
			Log.d("ToDo", todo.getWord());
		}

		// Getting todos under "Watchlist" tag name
		Log.d("ToDo", "Get todos under single Emotion name");

		List<Word> tagsWatchList = db.getAllToDosByEmotion(tag3.getEmotion());
		for (Word todo : tagsWatchList) {
			Log.d("ToDo Watchlist", todo.getWord());
		}

		// Deleting a ToDo
		Log.d("Delete ToDo", "Deleting a Word");
		Log.d("Emotion Count",
				"Emotion Count Before Deleting: " + db.getToDoCount());

		db.deleteToDo(todo8_id);

		Log.d("Emotion Count",
				"Emotion Count After Deleting: " + db.getToDoCount());

		// Deleting all Words under "Shopping" tag
		Log.d("Emotion Count",
				"Emotion Count Before Deleting 'Shopping' Words: "
						+ db.getToDoCount());

		db.deleteEmotion(tag1, true);

		Log.d("Emotion Count",
				"Emotion Count After Deleting 'Shopping' Words: "
						+ db.getToDoCount());

		// Updating tag name
		tag3.setEmotion("Movies to watch");
		db.updateEmotion(tag3);

		// Don't forget to close database connection
		db.closeDB();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

	@Override
	public void onStart() {
		super.onStart();
		authenGPlus.onStart();
	}

	@Override
	public void onStop() {
		super.onStop();
		authenGPlus.onStop();
	}

	@Override
	protected void onActivityResult(int requestCode, int responseCode,
			Intent intent) {
		if (requestCode == AuthenGoogleAccount.REQUEST_CODE_SIGN_IN) {
			if (responseCode != RESULT_OK) {
				authenGPlus.setSignInClicked(false);
			}

			authenGPlus.setIntentInProgress(false);
			authenGPlus.connect();

		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements
			OnClickListener {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_login,
					container, false);
			rootView.findViewById(R.id.button_signin_google)
					.setOnClickListener(this);

			return rootView;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.button_signin_google:
				// Signin button clicked
				authenGPlus.signInWithGplus();
				break;
			case R.id.button_signin_facebook:
				// Signout button clicked
				// signOutFromGplus();
				break;
			case R.id.button_signin:
				// Revoke access button clicked
				// revokeGplusAccess();
				break;
			case R.id.button_signup:
				// Revoke access button clicked
				// revokeGplusAccess();
				break;
			}
		}
	}

}
