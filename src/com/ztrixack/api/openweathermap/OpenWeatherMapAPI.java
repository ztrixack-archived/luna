package com.ztrixack.api.openweathermap;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ztrixack.api.APIInterface;
import com.ztrixack.feature.chat.MessageTask;
import com.ztrixack.utils.ZLog;

public class OpenWeatherMapAPI extends APIInterface {

	private static final String TAG = OpenWeatherMapAPI.class.getSimpleName();

	public static final String ARG_COUNTRY_NAME = "__COUNTRY_NAME__";

	public static final String URL_TELIZE_VISITOR = "http://www.openweathermap.org/data/2.5/weather?q="
			+ ARG_COUNTRY_NAME;

	// JSON Node names
	// City identification
	public static final String TAG_ID = "id";
	// Time of data receiving in unixtime GMT
	public static final String TAG_DT = "dt";

	// City location
	public static final String TAG_COORD = "coord";
	public static final String TAG_COORD_LON = "lon";
	public static final String TAG_COORD_LAT = "lat";

	// City name
	public static final String TAG_NAME = "name";

	public static final String TAG_MAIN = "main";
	// Temperature in Kelvin. Subtracted 273.15 from this figure to convert to
	// Celsius.
	public static final String TAG_MAIN_TEMP = "temp";
	// Atmospheric pressure in hPa
	public static final String TAG_MAIN_PRESSURE = "pressure";
	// Minimum and maximum temperature
	public static final String TAG_MAIN_TEMP_MIN = "temp_min";
	public static final String TAG_MAIN_TEMP_MAX = "temp_max";
	// Humidity in %
	public static final String TAG_MAIN_HUMIDITY = "humidity";

	public static final String TAG_WIND = "wind";
	// Wind speed in mps
	public static final String TAG_WIND_SPEED = "speed";
	public static final String TAG_WIND_GUST = "gust";
	// Wind direction in degrees (meteorological)
	public static final String TAG_WIND_DEG = "deg";

	public static final String TAG_CLOUDS = "clouds";
	// Cloudiness in %
	public static final String TAG_CLOUDS_ALL = "all";

	// weather condition More about data Weather Condition Codes
	public static final String TAG_WEATHER = "weather";
	public static final String TAG_WEATHER_ID = "id";
	public static final String TAG_WEATHER_MAIN = "main";
	public static final String TAG_WEATHER_DESCRIPTION = "description";
	public static final String TAG_WEATHER_ICON = "icon";

	// Precipitation volume mm per n hours
	public static final String TAG_RAIN = "rain";
	public static final String TAG_RAIN_1H = "1h";
	public static final String TAG_RAIN_3H = "3h";

	// Precipitation volume mm per n hours
	public static final String TAG_SNOW = "snow";
	public static final String TAG_SNOW_1H = "1h";
	public static final String TAG_SNOW_3H = "3h";

	public static final String TAG_SYS = "sys";
	public static final String TAG_SYS_MESSAGE = "message";
	public static final String TAG_SYS_COUNTRY = "country";
	public static final String TAG_SYS_SUNRISE = "sunrise";
	public static final String TAG_SYS_SUNSET = "sunset";

	public OpenWeatherMapAPI(MessageTask task) {
		mTask = task;

		JsonObjectRequest(
				URL_TELIZE_VISITOR.replace(ARG_COUNTRY_NAME, "Bangkok"), TAG,
				TAG_WEATHER_DESCRIPTION + '0');
	}

	public OpenWeatherMapAPI(MessageTask task, String Country) {
		mTask = task;

		JsonObjectRequest(
				URL_TELIZE_VISITOR.replace(ARG_COUNTRY_NAME, Country), TAG,
				TAG_WEATHER_DESCRIPTION + '0');
	}

	private String WeatherMeaning(int id) {
		switch (id) {
		// Thunderstorm
		case 200:
			return "Thunderstorm with light rain.";
		case 201:
			return "Thunderstorm with rain.";
		case 202:
			return "Thunderstorm with heavy rain.";
		case 210:
			return "Light thunderstorm.";
		case 211:
			return "Thunderstorm.";
		case 212:
			return "Heavy thunderstorm.";
		case 221:
			return "Ragged thunderstorm.";
		case 230:
			return "Thunderstorm with light drizzle.";
		case 231:
			return "Thunderstorm with drizzle.";
		case 232:
			return "Thunderstorm with heavy drizzle.";

			// Drizzle
		case 300:
			return "Light intensity drizzle.";
		case 301:
			return "Drizzle.";
		case 302:
			return "Heavy intensity drizzle.";
		case 310:
			return "Light intensity drizzle rain.";
		case 311:
			return "Drizzle rain.";
		case 312:
			return "Heavy intensity drizzle rain.";
		case 313:
			return "Shower rain and drizzle.";
		case 314:
			return "Heavy shower rain and drizzle.";
		case 321:
			return "Shower drizzle.";

			// Rain
		case 500:
			return "Light rain.";
		case 501:
			return "Moderate rain.";
		case 502:
			return "Heavy intensity rain.";
		case 503:
			return "Very heavy rain.";
		case 504:
			return "Extreme rain.";
		case 511:
			return "Freezing rain.";
		case 520:
			return "Light intensity shower rain.";
		case 521:
			return "Shower rain.";
		case 522:
			return "Heavy intensity shower rain.";
		case 531:
			return "Ragged shower rain.";

			// Snow
		case 600:
			return "Light snow.";
		case 601:
			return "Snow.";
		case 602:
			return "Heavy snow.";
		case 611:
			return "Sleet.";
		case 612:
			return "Shower sleet.";
		case 615:
			return "Light rain and snow.";
		case 616:
			return "Rain and snow.";
		case 620:
			return "Light shower snow.";
		case 621:
			return "Shower snow.";
		case 622:
			return "Heavy shower snow.";

			// Atmosphere
		case 701:
			return "Mist.";
		case 711:
			return "Smoke.";
		case 721:
			return "Haze.";
		case 731:
			return "Sand/Dust Whirls.";
		case 741:
			return "Fog.";
		case 751:
			return "Sand.";
		case 761:
			return "Dust.";
		case 762:
			return "VOLCANIC ASH.";
		case 771:
			return "SQUALLS.";
		case 781:
			return "TORNADO.";

			// Clouds
		case 800:
			return "Sky is clear.";
		case 801:
			return "Few clouds.";
		case 802:
			return "Scattered clouds.";
		case 803:
			return "Broken clouds.";
		case 804:
			return "Overcast clouds.";

			// Extreme
		case 900:
			return "Tornado.";
		case 901:
			return "Tropical storm.";
		case 902:
			return "Hurricane.";
		case 903:
			return "Cold.";
		case 904:
			return "Hot.";
		case 905:
			return "Windy.";
		case 906:
			return "Hail.";

			// Additional
		case 950:
			return "Setting.";
		case 951:
			return "Calm.";
		case 952:
			return "Light breeze.";
		case 953:
			return "Gentle Breeze.";
		case 954:
			return "Moderate breeze.";
		case 955:
			return "Fresh Breeze.";
		case 956:
			return "Strong breeze.";
		case 957:
			return "High wind, near gale.";
		case 958:
			return "Gale.";
		case 959:
			return "Severe Gale.";
		case 960:
			return "Storm.";
		case 961:
			return "Violent Storm.";
		case 962:
			return "Hurricane.";

		default:
			return "Unknown";
		}

	}

	public String WeatherMeaning(String id) {
		try {
			return WeatherMeaning(Integer.parseInt(id));
		} catch (Exception e) {
			ZLog.e(TAG, e.getLocalizedMessage());
		}
		return "Unknown";
	}

	@Override
	public void RequestPreaser(JSONObject jsonObject, String tag) {
		// hashmap
		mHashMap = new HashMap<String, String>();
		try {
			JSONObject REST_COORD = jsonObject.getJSONObject(TAG_COORD);
			mHashMap.put(TAG_COORD_LON,
					getJSONString(REST_COORD, TAG_COORD_LON));
			mHashMap.put(TAG_COORD_LAT,
					getJSONString(REST_COORD, TAG_COORD_LAT));

			JSONObject REST_SYS = jsonObject.getJSONObject(TAG_SYS);
			mHashMap.put(TAG_SYS_MESSAGE,
					getJSONString(REST_SYS, TAG_SYS_MESSAGE));
			mHashMap.put(TAG_SYS_COUNTRY,
					getJSONString(REST_SYS, TAG_SYS_COUNTRY));
			mHashMap.put(TAG_SYS_SUNRISE,
					getJSONString(REST_SYS, TAG_SYS_SUNRISE));
			mHashMap.put(TAG_SYS_SUNSET,
					getJSONString(REST_SYS, TAG_SYS_SUNSET));

			JSONArray REST_WEATHER = jsonObject.getJSONArray(TAG_WEATHER);
			for (int i = 0; i < REST_WEATHER.length(); i++) {
				JSONObject jObj = REST_WEATHER.getJSONObject(i);
				mHashMap.put(TAG_WEATHER_ID + i,
						getJSONString(jObj, TAG_WEATHER_ID));
				mHashMap.put(TAG_WEATHER_MAIN + i,
						getJSONString(jObj, TAG_WEATHER_MAIN));
				mHashMap.put(TAG_WEATHER_DESCRIPTION + i,
						getJSONString(jObj, TAG_WEATHER_DESCRIPTION));
				mHashMap.put(TAG_WEATHER_ICON + i,
						getJSONString(jObj, TAG_WEATHER_ICON));
			}

			JSONObject REST_MAIN = jsonObject.getJSONObject(TAG_MAIN);
			mHashMap.put(TAG_MAIN_TEMP,
					getJSONString(REST_MAIN, TAG_MAIN_TEMP));
			mHashMap.put(TAG_MAIN_HUMIDITY,
					getJSONString(REST_MAIN, TAG_MAIN_HUMIDITY));
			mHashMap.put(TAG_MAIN_PRESSURE,
					getJSONString(REST_MAIN, TAG_MAIN_PRESSURE));
			mHashMap.put(TAG_MAIN_TEMP_MIN,
					getJSONString(REST_MAIN, TAG_MAIN_TEMP_MIN));
			mHashMap.put(TAG_MAIN_TEMP_MAX,
					getJSONString(REST_MAIN, TAG_MAIN_TEMP_MAX));

			JSONObject REST_WIND = jsonObject.getJSONObject(TAG_WIND);
			mHashMap.put(TAG_WIND_SPEED,
					getJSONString(REST_WIND, TAG_WIND_SPEED));
			mHashMap.put(TAG_WIND_DEG,
					getJSONString(REST_WIND, TAG_WIND_DEG));
			mHashMap.put(TAG_WIND_GUST,
					getJSONString(REST_WIND, TAG_WIND_GUST));

			JSONObject REST_RAIN = jsonObject.getJSONObject(TAG_RAIN);
			mHashMap.put(TAG_RAIN_1H,
					getJSONString(REST_RAIN, TAG_RAIN_1H));
			mHashMap.put(TAG_RAIN_3H,
					getJSONString(REST_RAIN, TAG_RAIN_3H));

			JSONObject REST_SNOW = jsonObject.getJSONObject(TAG_SNOW);
			mHashMap.put(TAG_SNOW_1H,
					getJSONString(REST_SNOW, TAG_SNOW_1H));
			mHashMap.put(TAG_SNOW_3H,
					getJSONString(REST_SNOW, TAG_SNOW_3H));

			JSONObject REST_CLOUDS = jsonObject.getJSONObject(TAG_CLOUDS);
			mHashMap.put(TAG_CLOUDS_ALL,
					REST_CLOUDS.getString(TAG_CLOUDS_ALL));

			mHashMap.put(TAG_DT, getJSONString(jsonObject, TAG_DT));
			mHashMap.put(TAG_ID, getJSONString(jsonObject, TAG_ID));
			mHashMap.put(TAG_NAME, getJSONString(jsonObject, TAG_NAME));
		} catch (JSONException e) {
			ZLog.e(TAG, e.getMessage());
		}

		
	}

	@Override
	public void RequestPreaser(JSONArray response, String tag) {
	}

	@Override
	public void RequestPreaser(String response, String tag) {
	}

}
