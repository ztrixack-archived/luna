package com.ztrixack.database;

import java.io.File;

import com.ztrixack.utils.FileUtils;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

class DatabaseContext extends ContextWrapper {

	private static final String DEBUG_CONTEXT = "DatabaseContext";

	public DatabaseContext(Context base) {
		super(base);
	}

	@Override
	public File getDatabasePath(String name) {
		File sdcard = Environment.getExternalStorageDirectory();
		String dbfolder = sdcard.getAbsolutePath() + File.separator
				+ "databases" + File.separator;
		String dbfile = dbfolder + name;
		if (!dbfile.endsWith(".db")) {
			dbfile += ".db";
		}

		if (!FileUtils.checkIfFolderExists(dbfolder)) {
			FileUtils.createFolder(dbfolder);
		}

		if (!FileUtils.checkIfFileExistAndNotEmpty(dbfile)) {
			FileUtils.createFile(dbfile);
		}

		return new File(dbfile);
	}

	@Override
	public SQLiteDatabase openOrCreateDatabase(String name, int mode,
			SQLiteDatabase.CursorFactory factory) {
		SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(
				getDatabasePath(name), null);
		// SQLiteDatabase result = super.openOrCreateDatabase(name, mode,
		// factory);
		if (Log.isLoggable(DEBUG_CONTEXT, Log.WARN)) {
			Log.w(DEBUG_CONTEXT, "openOrCreateDatabase(" + name + ",,) = "
					+ result.getPath());
		}
		return result;
	}
}