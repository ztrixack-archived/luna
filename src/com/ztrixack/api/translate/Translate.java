package com.ztrixack.api.translate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import android.util.Log;

public class Translate {

	private static final String URL = "http://dict.longdo.com/mobile.php?search=";
	private static final String URL_SPEECH = "http://translate.google.com/translate_tts?tl=en&q=";

	public static String translate(String text) throws IOException {
		// fetch
		URL url = new URL(URL + URLEncoder.encode(text, "UTF-8"));
		URLConnection urlConnection = url.openConnection();
		urlConnection.setRequestProperty("User-Agent", "Something Else");
		BufferedReader br = new BufferedReader(new InputStreamReader(
				urlConnection.getInputStream()));
		String result = br.readLine();
		br.close();
		Log.d("", result);
		return result;
	}
}