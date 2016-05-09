package test.android.outlook.com.outlookandroidtest.network;

import java.util.Map;

import org.json.JSONObject;

import com.android.volley.VolleyError;

public interface WebServiceResponse {

	public void onResponseSuccess(JSONObject response);

	public void onResponseFailure(VolleyError error);
	
	public void parseNetworkResponse(Map<String, String> responseHeaders);

}
