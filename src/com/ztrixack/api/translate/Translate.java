package com.ztrixack.api.translate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import android.util.Log;

public class Translate {

	private static final String URL_TSL_LONGDO = "http://dict.longdo.com/mobile.php?search=WEBTEXT";
	private static final String URL_TSL_GOOGLE = "http://translate.google.com/?&langpair=auto|LANGUAGE_CODE&tbb=1&ie=&hl=LANGUAGE_CODE&text=WEBTEXT";
	private static final String LANGUAGE_CODE = "LANGUAGE_CODE";
	private static final String WEBTEXT = "WEBTEXT";
	private static final String URL_SPEECH_GOOGLE = "http://translate.google.com/translate_tts?tl=LANGUAGE_CODE&q=WEBTEXT";

	public static String translateByGoogle(String language, String text) throws IOException {
		// fetch
		String url_complete = URL_TSL_GOOGLE.replace(LANGUAGE_CODE, language).replace(WEBTEXT, URLEncoder.encode(text, "UTF-8"));
		URL url = new URL(url_complete);
		URLConnection urlConnection = url.openConnection();
		urlConnection.setRequestProperty("User-Agent", "Something Else");
		BufferedReader br = new BufferedReader(new InputStreamReader(
				urlConnection.getInputStream()));
		String result = "";
		try {
		while (true) {
			result += br.readLine();
			Log.i("xxx", result);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		br.close();
		return result;
	}
	
	public static String textToSpeechByGoogle(String language, String text) throws IOException {
		// fetch
		String url_complete = URL_SPEECH_GOOGLE.replace(LANGUAGE_CODE, language).replace(WEBTEXT, URLEncoder.encode(text, "UTF-8"));
		URL url = new URL(url_complete);
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