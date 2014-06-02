package com.ztrixack.api.restcountries;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ztrixack.service.ServiceHandler;
import com.ztrixack.utils.ZLog;

import android.app.Activity;
import android.os.AsyncTask;

public class RESTCountriesAPI {

	private static final String DEBUG_TAG = RESTCountriesAPI.class.getName();

	public static final String ARG_CURRENCY = "__CURRENCY__";
	public static final String ARG_CAPITAL_CITY = "__CAPITAL_CITY__";
	public static final String ARG_CALLING_CODE = "__CALLING_CODE__";
	public static final String ARG_REGION = "__REGION__";
	public static final String ARG_SUB_REGION = "__SUB_REGION__";
	public static final String ARG_LANGUAGE = "__LANGUAGE__";
	public static final String ARG_COUNTRY_NAME = "__COUNTRY_NAME__";
	public static final String ARG_COUNTRY_CODE = "__COUNTRY_CODE__";

	public static final String URL_GET_ALL_THE_COUNTRIES = "http://restcountries.eu/rest/v1";
	public static final String URL_CURRENCY = "http://restcountries.eu/rest/v1/currency/"
			+ ARG_CURRENCY;
	public static final String URL_CAPITAL_CITY = "http://restcountries.eu/rest/v1/capital/"
			+ ARG_CAPITAL_CITY;
	public static final String URL_CALLING_CODE = "http://restcountries.eu/rest/v1/callingcode/"
			+ ARG_CALLING_CODE;
	public static final String URL_REGION = "http://restcountries.eu/rest/v1/region/"
			+ ARG_REGION;
	public static final String URL_SUB_REGION = "http://restcountries.eu/rest/v1/subregion/"
			+ ARG_SUB_REGION;
	public static final String URL_LANGUAGE = "http://restcountries.eu/rest/v1/lang/"
			+ ARG_LANGUAGE;
	public static final String URL_COUNTRY_NAME = "http://restcountries.eu/rest/v1/lang/"
			+ ARG_COUNTRY_NAME;
	public static final String URL_COUNTRY_CODE = "http://restcountries.eu/rest/v1/alpha/"
			+ ARG_COUNTRY_CODE;

	// JSON Node names
	private static final String TAG_NAME = "name";
	private static final String TAG_CAPITAL = "capital";
	private static final String TAG_ALT_SPELLINGS = "altSpellings";
	private static final String TAG_RELEVANCE = "relevance";
	private static final String TAG_REGION = "region";
	private static final String TAG_SUB_REGION = "subregion";
	private static final String TAG_TRANSLATIONS = "translations";
	private static final String TAG_POPULATION = "population";
	private static final String TAG_LAT_LNG = "latlng";
	private static final String TAG_DEMONYM = "demonym";
	private static final String TAG_AREA = "area";
	private static final String TAG_GINI = "gini";
	private static final String TAG_TIMEZONES = "timezones";
	private static final String TAG_BORDERS = "borders";
	private static final String TAG_CALLING_CODES = "callingCodes";
	private static final String TAG_TOP_LEVEL_DOMAIN = "topLevelDomain";
	private static final String TAG_ALPHA_2_CODE = "alpha2Code";
	private static final String TAG_ALPHA_3_CODE = "alpha3Code";
	private static final String TAG_CURRENCIES = "currencies";
	private static final String TAG_LANGUAGES = "languages";

	// JSON Node translate
	private static final String TAG_TRANSLATIONS_DE = "de";
	private static final String TAG_TRANSLATIONS_ES = "es";
	private static final String TAG_TRANSLATIONS_FR = "fr";
	private static final String TAG_TRANSLATIONS_JA = "ja";
	private static final String TAG_TRANSLATIONS_IT = "it";

	public RESTCountriesAPI(Activity activity) {
		// Calling async task to get json
		new GetRESTCountriesAPI().execute();
	}

	/**
	 * Async task class to get json by making HTTP call
	 * */
	private class GetRESTCountriesAPI extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... Data) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();
			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(Data[0], ServiceHandler.GET);
			ZLog.d("Response: ", "> " + jsonStr);

			if (jsonStr != null) {
				getAll(jsonStr);
			} else {
				ZLog.e("ServiceHandler", "Couldn't get any data from the url");
			}
			return null;

		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

		}

	}

	public void getAll(String jsonStr) {
		try {
			JSONArray jsonArray = new JSONArray(jsonStr);
			// looping through All Contacts
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jObj = jsonArray.getJSONObject(i);

				String REST_NAME = jObj.getString(TAG_NAME);
				String REST_CAPITAL = jObj.getString(TAG_CAPITAL);

				// alt spellings JSON Array
				JSONArray REST_ALT_SPELLINGS = jObj
						.getJSONArray(TAG_ALT_SPELLINGS);

				String REST_RELEVANCE = jObj.getString(TAG_RELEVANCE);
				String REST_REGION = jObj.getString(TAG_REGION);
				String REST_SUB_REGION = jObj.getString(TAG_SUB_REGION);

				// translates JSON Objects
				JSONObject REST_TRANSLATIONS = jObj
						.getJSONObject(TAG_TRANSLATIONS);
				String REST_TRANSLATIONS_DE = REST_TRANSLATIONS
						.getString(TAG_TRANSLATIONS_DE);
				String REST_TRANSLATIONS_ES = REST_TRANSLATIONS
						.getString(TAG_TRANSLATIONS_ES);
				String REST_TRANSLATIONS_FR = REST_TRANSLATIONS
						.getString(TAG_TRANSLATIONS_FR);
				String REST_TRANSLATIONS_JA = REST_TRANSLATIONS
						.getString(TAG_TRANSLATIONS_JA);
				String REST_TRANSLATIONS_IT = REST_TRANSLATIONS
						.getString(TAG_TRANSLATIONS_IT);

				String REST_POPULATION = jObj.getString(TAG_POPULATION);

				// lat lng JSON Array
				JSONArray REST_LAT_LNG = jObj.getJSONArray(TAG_LAT_LNG);

				String REST_DEMONYM = jObj.getString(TAG_DEMONYM);
				String REST_AREA = jObj.getString(TAG_AREA);
				String REST_GINI = jObj.getString(TAG_GINI);

				// timezones JSON Array
				JSONArray REST_TIMEZONES = jObj.getJSONArray(TAG_TIMEZONES);

				// border JSON Array
				JSONArray REST_BORDERS = jObj.getJSONArray(TAG_BORDERS);

				// calling codes JSON Array
				JSONArray REST_CALLING_CODES = jObj
						.getJSONArray(TAG_CALLING_CODES);

				// top level domain JSON Array
				JSONArray REST_TOP_LEVEL_DOMAIN = jObj
						.getJSONArray(TAG_TOP_LEVEL_DOMAIN);

				String REST_ALPHA_2_CODE = jObj.getString(TAG_ALPHA_2_CODE);
				String REST_ALPHA_3_CODE = jObj.getString(TAG_ALPHA_3_CODE);

				// currencies JSON Array
				JSONArray REST_CURRENCIES = jObj.getJSONArray(TAG_CURRENCIES);

				// languages JSON Array
				JSONArray REST_LANGUAGES = jObj.getJSONArray(TAG_LANGUAGES);

				// tmp hashmap for single contact
				HashMap<String, String> hmRESTCountries = new HashMap<String, String>();

				// adding each child node to HashMap key => value
				hmRESTCountries.put(TAG_NAME, REST_NAME);
				hmRESTCountries.put(TAG_CAPITAL, REST_CAPITAL);

				// Array TAG_ALT_SPELLINGS
				for (int j = 0; j < REST_ALT_SPELLINGS.length(); ++j) {
					hmRESTCountries.put(TAG_ALT_SPELLINGS + j,
							REST_ALT_SPELLINGS.getString(j));
				}

				hmRESTCountries.put(TAG_RELEVANCE, REST_RELEVANCE);
				hmRESTCountries.put(TAG_REGION, REST_REGION);
				hmRESTCountries.put(TAG_SUB_REGION, REST_SUB_REGION);

				// hmRESTCountries.put(TAG_TRANSLATIONS,
				// REST_TRANSLATIONS);
				hmRESTCountries.put(TAG_TRANSLATIONS_DE, REST_TRANSLATIONS_DE);
				hmRESTCountries.put(TAG_TRANSLATIONS_ES, REST_TRANSLATIONS_ES);
				hmRESTCountries.put(TAG_TRANSLATIONS_FR, REST_TRANSLATIONS_FR);
				hmRESTCountries.put(TAG_TRANSLATIONS_JA, REST_TRANSLATIONS_JA);
				hmRESTCountries.put(TAG_TRANSLATIONS_IT, REST_TRANSLATIONS_IT);

				hmRESTCountries.put(TAG_POPULATION, REST_POPULATION);

				// Array TAG_LAT_LNG
				for (int j = 0; j < REST_LAT_LNG.length(); ++j) {
					hmRESTCountries.put(TAG_LAT_LNG + j,
							REST_LAT_LNG.getString(j));
				}

				hmRESTCountries.put(TAG_DEMONYM, REST_DEMONYM);
				hmRESTCountries.put(TAG_AREA, REST_AREA);
				hmRESTCountries.put(TAG_GINI, REST_GINI);

				// Array TAG_TIMEZONES
				for (int j = 0; j < REST_TIMEZONES.length(); ++j) {
					hmRESTCountries.put(TAG_TIMEZONES + j,
							REST_TIMEZONES.getString(j));
				}

				// Array TAG_BORDERS
				for (int j = 0; j < REST_BORDERS.length(); ++j) {
					hmRESTCountries.put(TAG_BORDERS + j,
							REST_BORDERS.getString(j));
				}

				// Array TAG_CALLING_CODES
				for (int j = 0; j < REST_CALLING_CODES.length(); ++j) {
					hmRESTCountries.put(TAG_CALLING_CODES + j,
							REST_CALLING_CODES.getString(j));
				}

				// Array TAG_TOP_LEVEL_DOMAIN
				for (int j = 0; j < REST_TOP_LEVEL_DOMAIN.length(); ++j) {
					hmRESTCountries.put(TAG_TOP_LEVEL_DOMAIN + j,
							REST_TOP_LEVEL_DOMAIN.getString(j));
				}

				hmRESTCountries.put(TAG_ALPHA_2_CODE, REST_ALPHA_2_CODE);
				hmRESTCountries.put(TAG_ALPHA_3_CODE, REST_ALPHA_3_CODE);

				// Array TAG_CURRENCIES
				for (int j = 0; j < REST_CURRENCIES.length(); ++j) {
					hmRESTCountries.put(TAG_CURRENCIES + j,
							REST_CURRENCIES.getString(j));
				}

				// Array TAG_LANGUAGES
				for (int j = 0; j < REST_LANGUAGES.length(); ++j) {
					hmRESTCountries.put(TAG_LANGUAGES + j,
							REST_LANGUAGES.getString(j));
				}

				// adding contact to contact list
				// contactList.add(hmRESTCountries);
			}
		} catch (JSONException e) {
			ZLog.e(DEBUG_TAG, e.getMessage());
		}
	}

}
