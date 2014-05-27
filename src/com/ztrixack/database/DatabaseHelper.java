package com.ztrixack.database;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.ztrixack.database.model.Emotion;
import com.ztrixack.database.model.Word;

public class DatabaseHelper extends SQLiteOpenHelper {

	public static final File DATABASE_FILE_PATH = Environment
			.getExternalStorageDirectory();

	// Logcat emotion
	private static final String LOG = DatabaseHelper.class.getName();

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "DatabaseManager";

	// Table Names
	private static final String TABLE_WORD = "words";
	private static final String TABLE_EMOTION = "emotions";
	private static final String TABLE_WORD_EMOTION = "word_emotions";

	// Common column names
	private static final String KEY_ID = "id";
	private static final String KEY_CREATED_AT = "created_at";

	// NOTES Table - column nmaes
	private static final String KEY_WORD = "word";
	private static final String KEY_STATUS = "status";

	// EMOTIONS Table - column names
	private static final String KEY_EMOTION_NAME = "emotion_name";

	// NOTE_EMOTIONS Table - column names
	private static final String KEY_WORD_ID = "word_id";
	private static final String KEY_EMOTION_ID = "emotion_id";

	// Table Create Statements
	// Word table create statement
	private static final String CREATE_TABLE_WORD = "CREATE TABLE "
			+ TABLE_WORD + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_WORD
			+ " TEXT," + KEY_STATUS + " INTEGER," + KEY_CREATED_AT
			+ " DATETIME" + ")";

	// Emotion table create statement
	private static final String CREATE_TABLE_EMOTION = "CREATE TABLE "
			+ TABLE_EMOTION + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_EMOTION_NAME + " TEXT," + KEY_CREATED_AT + " DATETIME" + ")";

	// word_emotion table create statement
	private static final String CREATE_TABLE_WORD_EMOTION = "CREATE TABLE "
			+ TABLE_WORD_EMOTION + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_WORD_ID + " INTEGER," + KEY_EMOTION_ID + " INTEGER,"
			+ KEY_CREATED_AT + " DATETIME" + ")";

	private static final String DATAPATH = "/database/";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// creating required tables
		db.execSQL(CREATE_TABLE_WORD);
		db.execSQL(CREATE_TABLE_EMOTION);
		db.execSQL(CREATE_TABLE_WORD_EMOTION);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORD);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMOTION);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORD_EMOTION);

		// create new tables
		onCreate(db);
	}

	// ------------------------ "words" table methods ----------------//

	/**
	 * Creating a word
	 */
	public long createToDo(Word word, long[] emotion_ids) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_WORD, word.getWord());
		values.put(KEY_STATUS, word.getStatus());
		values.put(KEY_CREATED_AT, getDateTime());

		// insert row
		long word_id = db.insert(TABLE_WORD, null, values);

		// insert emotion_ids
		for (long emotion_id : emotion_ids) {
			createWordEmotion(word_id, emotion_id);
		}

		return word_id;
	}

	/**
	 * get single word
	 */
	public Word getWord(long word_id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_WORD + " WHERE "
				+ KEY_ID + " = " + word_id;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();

		Word td = new Word();
		td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
		td.setWord((c.getString(c.getColumnIndex(KEY_WORD))));
		td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

		return td;
	}

	/**
	 * getting all words
	 * */
	public List<Word> getAllToDos() {
		List<Word> words = new ArrayList<Word>();
		String selectQuery = "SELECT  * FROM " + TABLE_WORD;

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				Word td = new Word();
				td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
				td.setWord((c.getString(c.getColumnIndex(KEY_WORD))));
				td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

				// adding to word list
				words.add(td);
			} while (c.moveToNext());
		}

		return words;
	}

	/**
	 * getting all words under single emotion
	 * */
	public List<Word> getAllToDosByEmotion(String emotion_name) {
		List<Word> words = new ArrayList<Word>();

		String selectQuery = "SELECT  * FROM " + TABLE_WORD + " td, "
				+ TABLE_EMOTION + " tg, " + TABLE_WORD_EMOTION
				+ " tt WHERE tg." + KEY_EMOTION_NAME + " = '" + emotion_name
				+ "'" + " AND tg." + KEY_ID + " = " + "tt." + KEY_EMOTION_ID
				+ " AND td." + KEY_ID + " = " + "tt." + KEY_WORD_ID;

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				Word td = new Word();
				td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
				td.setWord((c.getString(c.getColumnIndex(KEY_WORD))));
				td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

				// adding to word list
				words.add(td);
			} while (c.moveToNext());
		}

		return words;
	}

	/**
	 * getting word count
	 */
	public int getToDoCount() {
		String countQuery = "SELECT  * FROM " + TABLE_WORD;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		int count = cursor.getCount();
		cursor.close();

		// return count
		return count;
	}

	/**
	 * Updating a word
	 */
	public int updateToDo(Word word) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_WORD, word.getWord());
		values.put(KEY_STATUS, word.getStatus());

		// updating row
		return db.update(TABLE_WORD, values, KEY_ID + " = ?",
				new String[] { String.valueOf(word.getId()) });
	}

	/**
	 * Deleting a word
	 */
	public void deleteToDo(long tado_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_WORD, KEY_ID + " = ?",
				new String[] { String.valueOf(tado_id) });
	}

	// ------------------------ "emotions" table methods ----------------//

	/**
	 * Creating emotion
	 */
	public long createEmotion(Emotion emotion) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_EMOTION_NAME, emotion.getEmotion());
		values.put(KEY_CREATED_AT, getDateTime());

		// insert row
		long emotion_id = db.insert(TABLE_EMOTION, null, values);

		return emotion_id;
	}

	/**
	 * getting all emotions
	 * */
	public List<Emotion> getAllEmotions() {
		List<Emotion> emotions = new ArrayList<Emotion>();
		String selectQuery = "SELECT  * FROM " + TABLE_EMOTION;

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				Emotion t = new Emotion();
				t.setId(c.getInt((c.getColumnIndex(KEY_ID))));
				t.setEmotion(c.getString(c.getColumnIndex(KEY_EMOTION_NAME)));

				// adding to emotions list
				emotions.add(t);
			} while (c.moveToNext());
		}
		return emotions;
	}

	/**
	 * Updating a emotion
	 */
	public int updateEmotion(Emotion emotion) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_EMOTION_NAME, emotion.getEmotion());

		// updating row
		return db.update(TABLE_EMOTION, values, KEY_ID + " = ?",
				new String[] { String.valueOf(emotion.getId()) });
	}

	/**
	 * Deleting a emotion
	 */
	public void deleteEmotion(Emotion emotion,
			boolean should_delete_all_emotion_words) {
		SQLiteDatabase db = this.getWritableDatabase();

		// before deleting emotion
		// check if words under this emotion should also be deleted
		if (should_delete_all_emotion_words) {
			// get all words under this emotion
			List<Word> allEmotionToDos = getAllToDosByEmotion(emotion
					.getEmotion());

			// delete all words
			for (Word word : allEmotionToDos) {
				// delete word
				deleteToDo(word.getId());
			}
		}

		// now delete the emotion
		db.delete(TABLE_EMOTION, KEY_ID + " = ?",
				new String[] { String.valueOf(emotion.getId()) });
	}

	// ------------------------ "word_emotions" table methods ----------------//

	/**
	 * Creating word_emotion
	 */
	public long createWordEmotion(long word_id, long emotion_id) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_WORD_ID, word_id);
		values.put(KEY_EMOTION_ID, emotion_id);
		values.put(KEY_CREATED_AT, getDateTime());

		long id = db.insert(TABLE_WORD_EMOTION, null, values);

		return id;
	}

	/**
	 * Updating a word emotion
	 */
	public int updateNoteEmotion(long id, long emotion_id) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_EMOTION_ID, emotion_id);

		// updating row
		return db.update(TABLE_WORD, values, KEY_ID + " = ?",
				new String[] { String.valueOf(id) });
	}

	/**
	 * Deleting a word emotion
	 */
	public void deleteToDoEmotion(long id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_WORD, KEY_ID + " = ?",
				new String[] { String.valueOf(id) });
	}

	// closing database
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen()) {
			db.close();
		}
	}

	/**
	 * get datetime
	 * */
	private String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}

	public SQLiteDatabase getReadableDatabase() {
		SQLiteDatabase db = SQLiteDatabase.openDatabase(DATABASE_FILE_PATH
				+ DATAPATH + DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY);
		return db;
	}

	public SQLiteDatabase getWritableDatabase() {
		SQLiteDatabase db = SQLiteDatabase
				.openDatabase(DATABASE_FILE_PATH + DATAPATH + DATABASE_NAME,
						null, SQLiteDatabase.OPEN_READWRITE);
		return db;
	}
}