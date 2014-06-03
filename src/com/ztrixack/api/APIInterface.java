package com.ztrixack.api;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.ztrixack.app.ApplicationController;
import com.ztrixack.feature.chat.MessageTask;
import com.ztrixack.utils.ZLog;

public abstract class APIInterface {

	private static final String TAG = APIInterface.class.getSimpleName();
	
	protected MessageTask mTask;

	protected HashMap<String, String> mHashMap;

	public abstract void RequestPreaser(JSONObject jsonObject, String tag);

	public abstract void RequestPreaser(JSONArray jsonArray, String tag);

	public abstract void RequestPreaser(String response, String tag);

	public void JsonObjectRequest(final String URL, final String tag_json_obj,
			final String tag_req) {
		// pass second argument as "null" for GET requests
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET, URL,
				null, new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						ZLog.d(TAG, response.toString());
						RequestPreaser(response, tag_req);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.e("Error: ", error.getMessage());
					}
				});

		// Adding request to request queue
		ApplicationController.getInstance().addToRequestQueue(jsonObjReq,
				tag_json_obj);
	}

	public void JsonArrayRequest(final String URL, final String tag_json_arry,
			final String tag_req) {
		// Tag used to cancel the request
		JsonArrayRequest req = new JsonArrayRequest(URL,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						ZLog.d(TAG, response.toString());
						RequestPreaser(response, tag_req);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());
					}
				});

		// Adding request to request queue
		ApplicationController.getInstance().addToRequestQueue(req,
				tag_json_arry);
	}

	public void JsonStringRequest(final String URL,
			final String tag_string_req, final String tag_req) {
		// Tag used to cancel the request
		StringRequest strReq = new StringRequest(Method.GET, URL,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						ZLog.d(TAG, response.toString());
						RequestPreaser(response, tag_req);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());
					}
				});

		// Adding request to request queue
		ApplicationController.getInstance().addToRequestQueue(strReq,
				tag_string_req);
	}

	public String getJSONString(JSONObject jsonObject, String Tag) {
		String data = "";
		try {
			data = jsonObject.getString(Tag);
		} catch (JSONException e) {
			ZLog.e(TAG, e.getLocalizedMessage());
		}
		return data;
	}
	
	public void getHashMapToMsgProcess(String tag) {
		try {
			mTask.receiveMessage(mHashMap.get(tag));
		} catch (Exception e) {
			ZLog.e(TAG, e.getLocalizedMessage());
		}
	}
}
