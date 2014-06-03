package com.ztrixack.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ztrixack.luna.R;
import com.ztrixack.utils.ZLog;

public class HTMLActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {
	
	private static final String TAG = HTMLActivity.class.getSimpleName();

	private static final String URL_Test = "http://api.wolframalpha.com/v2/query?input=10483984923/48*172&appid=K7JEHL-TGRU4G63V9";
			//"https://www.google.co.th/?gws_rd=cr&ei=26SGU_iFKcnDkgWVoIDgAg#cr=countryTH&tbs=ctr:countryTH&q=hello";
	
	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	private static EditText edittext;
	private static TextView textview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_html);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));

	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						PlaceholderFragment.newInstance(position + 1)).commit();
	}

	public Activity getActivity() {
		return this;
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			break;
		case 3:
			mTitle = getString(R.string.title_section3);
			break;
		}
	}

	public void onPushMethod(View view) {
		try {
			final String text = edittext.getText().toString();

			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						URL url;
						if (text == null || text.length() <= 0) {
							url = new URL(URL_Test);
						} else {
							url = new URL(text);
						}
						URLConnection urlConnection = url.openConnection();
						urlConnection
								.setRequestProperty(
										"User-Agent",
										"Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB;     rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13 (.NET CLR 3.5.30729)");
						BufferedReader br = new BufferedReader(
								new InputStreamReader(urlConnection
										.getInputStream()));
						char[] buffer = new char[10240];
						int err = br.read(buffer);
						if (err != -1) {
							String raw = String.copyValueOf(buffer);
							ZLog.i(TAG, raw);
							String[] split = raw.split("\n");
							String input = "";
							for (int i = 0; i < split.length; ++i) {
								if (split[i].indexOf("<plaintext>") != -1) {
									input += "\n = " + split[i].substring(split[i].indexOf(">") + 1, split[i].indexOf("</plaintext>"));
								}
								
							}
							final String result = input.substring(4);
							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									textview.setText(result + "\n\n");
								}
							});
						}
						br.close();
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_html, container,
					false);

			edittext = (EditText) rootView.findViewById(R.id.text_edit);
			// listview = (ListView) rootView.findViewById(R.id.section_label);
			textview = (TextView) rootView.findViewById(R.id.section_label);
			// task = new MessageTask(getActivity(), listview);
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((HTMLActivity) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}
	}

}
