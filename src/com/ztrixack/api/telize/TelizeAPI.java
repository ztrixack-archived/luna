package com.ztrixack.api.telize;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;

import com.ztrixack.feature.chat.MessageTask;
import com.ztrixack.service.ServiceHandler;
import com.ztrixack.utils.Preconditions;
import com.ztrixack.utils.ZLog;

public class TelizeAPI {

	private static final String DEBUG_TAG = TelizeAPI.class.getCanonicalName();

	public static final String URL_TELIZE_VISITOR = "http://www.telize.com/geoip";

	// JSON Node names
	// (Visitor IP address, or IP address specified as parameter)
	private static final String TAG_IP = "ip";
	// (Two-letter ISO 3166-1 alpha-2 country code)
	private static final String TAG_COUNTRY_CODE = "country_code";
	// (Three-letter ISO 3166-1 alpha-3 country code)
	private static final String TAG_COUNTRY_CODE3 = "country_code3";
	// (Name of the country)
	private static final String TAG_COUNTRY = "country";
	// (Two-letter ISO-3166-2 state / region code)
	private static final String TAG_REGION_CODE = "region_code";
	// (Name of the region)
	private static final String TAG_REGION = "region";
	// (Name of the city)
	private static final String TAG_CITY = "city";
	// (Postal code / Zip code)
	private static final String TAG_POSTAL_CODE = "postal_code";
	// (Two-letter continent code)
	private static final String TAG_COUNTINENT_CODE = "continent_code";
	// (Latitude)
	private static final String TAG_LATITUDE = "latitude";
	// (Longitude)
	private static final String TAG_LONGITUDE = "longitude";
	// (DMA Code)
	private static final String TAG_DMA_CODE = "dma_code";
	// (Area Code)
	private static final String TAG_AREA_CODE = "area_code";
	// (Autonomous System Number)
	private static final String TAG_ASN = "asn";
	// (Internet service provider)
	private static final String TAG_ISP = "isp";
	// (Time Zone)
	private static final String TAG_TIMEZONE = "timezone";

	private static MessageTask mTask;

	private static HashMap<String, String> hmTelize;

	public TelizeAPI(Activity activity, MessageTask task) {
		mTask = task;
		// Calling async task to get json
		new GetTelizeAPI().execute();
	}

	public String getHashMapFromTag(String tag) {
		mTask.receiveMessage(hmTelize.get(tag));
		return hmTelize.get(tag);
	}

	public String getIP() {
		return hmTelize.get(TAG_IP);
	}

	public String getCountryCode() {
		return hmTelize.get(TAG_COUNTRY_CODE);
	}

	public String getCountryCode3() {
		return hmTelize.get(TAG_COUNTRY_CODE3);
	}

	public String getCountry() {
		return hmTelize.get(TAG_COUNTRY);
	}

	public String getRegionCode() {
		return hmTelize.get(TAG_REGION_CODE);
	}

	public String getRegion() {
		return hmTelize.get(TAG_REGION);
	}

	public String getCity() {
		return hmTelize.get(TAG_CITY);
	}

	public String getPostalCode() {
		return hmTelize.get(TAG_POSTAL_CODE);
	}

	public String getCountinentCode() {
		return hmTelize.get(TAG_COUNTINENT_CODE);
	}

	public String getLatitude() {
		return hmTelize.get(TAG_LATITUDE);
	}

	public String getLongitude() {
		return hmTelize.get(TAG_LONGITUDE);
	}

	public String getDMACode() {
		return hmTelize.get(TAG_DMA_CODE);
	}

	public String getAreaCode() {
		return hmTelize.get(TAG_AREA_CODE);
	}

	public String getASN() {
		return hmTelize.get(TAG_ASN);
	}

	public String getISP() {
		return hmTelize.get(TAG_ISP);
	}

	public String getTimezone() {
		return hmTelize.get(TAG_TIMEZONE);
	}

	/**
	 * Async task class to get json by making HTTP call
	 * */
	private class GetTelizeAPI extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... param) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();
			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(URL_TELIZE_VISITOR,
					ServiceHandler.GET);
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

			mTask.receiveMessage(hmTelize.get(TAG_IP));
		}

	}

	public void getAll(String jsonStr) {
		try {
			JSONObject jsonObject = new JSONObject(jsonStr);

			String REST_IP = getJSONString(jsonObject, TAG_IP);
			String REST_COUNTRY_CODE = getJSONString(jsonObject,
					TAG_COUNTRY_CODE);
			String REST_COUNTRY_CODE3 = getJSONString(jsonObject,
					TAG_COUNTRY_CODE3);
			String REST_COUNTRY = getJSONString(jsonObject, TAG_COUNTRY);
			String REST_REGION_CODE = getJSONString(jsonObject, TAG_REGION_CODE);
			String REST_REGION = getJSONString(jsonObject, TAG_REGION);
			String REST_CITY = getJSONString(jsonObject, TAG_CITY);
			String REST_POSTAL_CODE = getJSONString(jsonObject, TAG_POSTAL_CODE);
			String REST_COUNTINENT_CODE = getJSONString(jsonObject,
					TAG_COUNTINENT_CODE);
			String REST_LATITUDE = getJSONString(jsonObject, TAG_LATITUDE);
			String REST_LONGITUDE = getJSONString(jsonObject, TAG_LONGITUDE);
			String REST_DMA_CODE = getJSONString(jsonObject, TAG_DMA_CODE);
			String REST_AREA_CODE = getJSONString(jsonObject, TAG_AREA_CODE);
			String REST_ASN = getJSONString(jsonObject, TAG_ASN);
			String REST_ISP = getJSONString(jsonObject, TAG_ISP);
			String REST_TIMEZONE = getJSONString(jsonObject, TAG_TIMEZONE);

			// tmp hashmap for single contact
			hmTelize = new HashMap<String, String>();

			// adding each child node to HashMap key => value
			hmTelize.put(TAG_IP, REST_IP);
			hmTelize.put(TAG_COUNTRY_CODE, REST_COUNTRY_CODE);
			hmTelize.put(TAG_COUNTRY_CODE3, REST_COUNTRY_CODE3);
			hmTelize.put(TAG_COUNTRY, REST_COUNTRY);
			hmTelize.put(TAG_REGION_CODE, REST_REGION_CODE);
			hmTelize.put(TAG_REGION, REST_REGION);
			hmTelize.put(TAG_CITY, REST_CITY);
			hmTelize.put(TAG_POSTAL_CODE, REST_POSTAL_CODE);
			hmTelize.put(TAG_COUNTINENT_CODE, REST_COUNTINENT_CODE);
			hmTelize.put(TAG_LATITUDE, REST_LATITUDE);
			hmTelize.put(TAG_LONGITUDE, REST_LONGITUDE);
			hmTelize.put(TAG_DMA_CODE, REST_DMA_CODE);
			hmTelize.put(TAG_AREA_CODE, REST_AREA_CODE);
			hmTelize.put(TAG_ASN, REST_ASN);
			hmTelize.put(TAG_ISP, REST_ISP);
			hmTelize.put(TAG_TIMEZONE, REST_TIMEZONE);

		} catch (JSONException e) {
			ZLog.e(DEBUG_TAG + "$getAll", e.getMessage());
		}
	}

	public String getJSONString(JSONObject jsonObject, String tag) {
		try {
			String result = jsonObject.getString(tag);
			if (result == null) {
				return "";
			} else {
				return result;
			}
		} catch (JSONException e) {
			ZLog.e(DEBUG_TAG + "$getJSONString", e.getMessage());
		}
		return "";
	}

}
