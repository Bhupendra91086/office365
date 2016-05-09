package test.android.outlook.com.outlookandroidtest.network;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import test.android.outlook.com.outlookandroidtest.AppController;

/**
 * Created by bhupendra_tomar on 5/9/2016.
 */
public class WebServiceCallTask extends AsyncTask<String, Void, Void> {

    DataUpdateListener dataUpdateListener = null;
    private Activity activity;

    public WebServiceCallTask(Activity activity) {
        this.activity = activity;
        dataUpdateListener = (DataUpdateListener) activity;
    }

    private String Content;
    private String Error = null;
    String data = "";
    ProgressDialog progressDialog;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(activity, "", "Please wait");
        Log.d("TestWB", "On Pre");
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            downloadUrl(params[0]);
        } catch (IOException e) {
        }
        return null;
    }


    private void downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {


            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    myurl, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("Data ", response.toString());
                            dataUpdateListener.onResultSuccess(response.toString());
                            progressDialog.dismiss();
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // hide the progress dialog
                    Log.e("error ", error.getMessage().toString());
                    dataUpdateListener.onResultFail(error.getMessage());
                    progressDialog.dismiss();
                }
            });

            AppController.getInstance().addToRequestQueue(jsonObjReq, myurl);

        } catch (Exception e) {
            Log.e("Error ", e.getMessage());
        }
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
    }
}