package com.ztrixack.domain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.ztrixack.auth.google.AuthenGoogleAccount;
import com.ztrixack.luna.R;

public class LoginActivity extends ActionBarActivity {

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private static AuthenGoogleAccount authenGPlus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

		authenGPlus = new AuthenGoogleAccount(this);
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
