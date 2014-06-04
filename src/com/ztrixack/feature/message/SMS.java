package com.ztrixack.feature.message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ztrixack.activity.ReadSMSActivity;
import com.ztrixack.app.LunaController;
import com.ztrixack.utils.Boast;

public class SMS {

	private Activity mActivity;
	private Typeface font;
	private ListView mListView;

	public SMS(final Activity activity, ListView view) {
		mActivity = activity;
		mListView = view;
		// font = Typeface.createFromAsset(getAssets(), "fonts/OpenSans.ttf");
		// headingText = (TextView) findViewById(R.id.heading_tv);
		// headingText.setTypeface(font);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				intent.setClass(activity, ReadSMSActivity.class);
				intent.putExtra("id", String.valueOf(id));
				mActivity.startActivity(intent);
			}
		});
	}

	@SuppressWarnings("deprecation")
	protected void onResume() {
		Cursor c = mActivity.getContentResolver().query(
				LunaController.INBOX_URI, null, null, null, null);
		mActivity.startManagingCursor(c);

//		String[] from = { "address", "date", "body" };
//		int[] to = new int[] { R.id.textview_field };
//		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
//				R.layout.listitemlayout, c, from, to);
//
//		mListView.setAdapter(adapter);
	}

	public class ExportTask extends AsyncTask<Void, Integer, Uri> {
		ProgressDialog pDialog;
		Cursor mCursor;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(mActivity);
			pDialog.setMessage("Exporting to file ...");
			pDialog.setIndeterminate(false);
			pDialog.setMax(100);
			pDialog.setProgress(0);
			pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Uri doInBackground(Void... params) {
			if (Environment.MEDIA_MOUNTED.equals(Environment
					.getExternalStorageState())) {
				FileOutputStream fos = null;
				try {
					File f = new File(
							Environment.getExternalStorageDirectory(),
							"TEMP_FILE");
					fos = new FileOutputStream(f);
					mCursor = ((SimpleCursorAdapter) mListView.getAdapter())
							.getCursor();
					int count = mCursor.getCount(), i = 0;

					StringBuilder sb = new StringBuilder();
					if (mCursor.moveToFirst()) {
						do {
							sb.append(
									mCursor.getString(mCursor
											.getColumnIndex("address")))
									.append("\n");
							sb.append(
									mCursor.getString(mCursor
											.getColumnIndex("body"))).append(
									"\n");
							sb.append("\n");
							publishProgress(++i * 100 / count);
						} while (!isCancelled() && mCursor.moveToNext());
					}
					fos.write(sb.toString().getBytes());
					return Uri.fromFile(f);

				} catch (Exception e) {
				} finally {
					if (fos != null) {
						try {
							fos.close();
						} catch (IOException e) {
						}
					}
				}
			}

			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			pDialog.setProgress(values[0]);
		}

		@Override
		protected void onPostExecute(Uri result) {
			super.onPostExecute(result);
			pDialog.dismiss();

			if (result == null) {
				Boast.showText(mActivity, "Export task failed!");
				return;
			}

			Intent shareIntent = new Intent();
			shareIntent.setAction(Intent.ACTION_SEND);
			shareIntent.setType("text/*");
			shareIntent.putExtra(Intent.EXTRA_STREAM, result);
			mActivity.startActivity(Intent.createChooser(shareIntent,
					"Send file to"));
		}
	}
}
