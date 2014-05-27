package com.ztrixack.feature.translate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import android.util.Log;

public class Translate {

	private static final String URL_TSL_LONGDO = "http://dict.longdo.com/mobile.php?search=WEBTEXT";
	private static final String URL_TSL_GOOGLE = "http://translate.google.com.tw/translate_a/t?client=t&hl=en&sl=auto&tl=LANGUAGE_CODE&ie=UTF-8&oe=UTF-8&multires=1&oc=1&otf=2&ssel=0&tsel=0&sc=1&q=WEBTEXT";
	private static final String LANGUAGE_CODE = "LANGUAGE_CODE";
	private static final String WEBTEXT = "WEBTEXT";
	private static final String URL_SPEECH_GOOGLE = "http://translate.google.com/translate_tts?tl=LANGUAGE_CODE&q=WEBTEXT";
	private static final String USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB;     rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13 (.NET CLR 3.5.30729)";

	public static String translateByGoogle(String language, String text)
			throws IOException {
		// fetch
		URL url = new URL(URL_TSL_GOOGLE.replace(LANGUAGE_CODE, language)
				.replace(WEBTEXT, URLEncoder.encode(text, "UTF-8")));
		URLConnection urlConnection = url.openConnection();
		urlConnection.setRequestProperty("User-Agent", USER_AGENT);
		BufferedReader br = new BufferedReader(new InputStreamReader(
				urlConnection.getInputStream()));
		String result = br.readLine();
		br.close();
		Log.i("translateByGoogle", result);
		result = result.substring(2, result.indexOf("]]") + 1);
		StringBuilder sb = new StringBuilder();
		String[] splits = result.split("(?<!\\\\)\"");
		for (int i = 1; i < splits.length; i += 8) {
			sb.append(splits[i]);
		}
		return sb.toString().replace("\\n", "\n").replaceAll("\\\\(.)", "$1");
	}

	public static String translateByLongDo(String text) throws IOException {
		// fetch
		URL url = new URL(URL_TSL_LONGDO.replace(WEBTEXT,
				URLEncoder.encode(text, "UTF-8")));
		URLConnection urlConnection = url.openConnection();
		urlConnection.setRequestProperty("User-Agent", USER_AGENT);
		BufferedReader br = new BufferedReader(new InputStreamReader(
				urlConnection.getInputStream()));
		String result = br.readLine();
		br.close();
		Log.i("xxx", result);
		// result = result.substring(2, result.indexOf("]]") + 1);
		StringBuilder sb = new StringBuilder();
		String[] splits = result.split("(?<!\\\\)\"");
		for (int i = 0; i < splits.length; ++i) {
			sb.append(splits[i] + '\n');
		}
		return sb.toString().replace("\\n", "\n").replaceAll("\\\\(.)", "$1");
	}

	public static String textToSpeechByGoogle(String language, String text)
			throws IOException {
		// fetch
		String url_complete = URL_SPEECH_GOOGLE.replace(LANGUAGE_CODE,
				URLEncoder.encode(language, "UTF-8")).replace(WEBTEXT,
				URLEncoder.encode(text, "UTF-8"));
		URL url = new URL(url_complete);
		URLConnection urlConnection = url.openConnection();
		urlConnection.setRequestProperty("User-Agent", USER_AGENT);
		BufferedReader br = new BufferedReader(new InputStreamReader(
				urlConnection.getInputStream()));
		String result = br.readLine();
		br.close();
		Log.d("", result);
		return result;
	}
}