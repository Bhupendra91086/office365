package test.android.outlook.com.outlookandroidtest.activity;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;

import test.android.outlook.com.outlookandroidtest.AppController;
import test.android.outlook.com.outlookandroidtest.R;
import test.android.outlook.com.outlookandroidtest.adapter.CustomListAdapter;
import test.android.outlook.com.outlookandroidtest.model.SearchItem;
import test.android.outlook.com.outlookandroidtest.model.ServerResponse;
import test.android.outlook.com.outlookandroidtest.network.DataUpdateListener;
import test.android.outlook.com.outlookandroidtest.network.HttpsTrustManager;
import test.android.outlook.com.outlookandroidtest.network.WebServiceCallTask;
import test.android.outlook.com.outlookandroidtest.util.Constant;

public class HomeScreen extends Activity implements DataUpdateListener {

    private EditText editTextToSearch;
    private ListView listeViewToDisplayResult;
    private ArrayList<SearchItem> itemList = new ArrayList<SearchItem>();
    AppController appController;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        init();
    }

    private void init() {
       // progressDialog = ProgressDialog.show(this, "", "Please wait for a while");
        editTextToSearch = (EditText) findViewById(R.id.editText);
        appController = AppController.getInstance();
        listeViewToDisplayResult = (ListView) findViewById(R.id.listView);
        editTextToSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Toast.makeText(HomeScreen.this, s.toString(), Toast.LENGTH_LONG).show();

                if (appController.isNetworkAvailable()) {
                    if(s.toString().length() > 0) {
                        doSearch(s.toString());
                        progressDialog.show();
                    }

                } else {
                    Toast.makeText(HomeScreen.this, R.string.connection_error, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        CustomListAdapter customListAdapter = new CustomListAdapter(this, itemList);
        listeViewToDisplayResult.setAdapter(customListAdapter);
    }

    private void doSearch(String searchString) {
        try {
            itemList.removeAll(itemList);
        } catch (Exception e) {

        }
        String urlToSearch = Constant.SERVER_URL + searchString;
        WebServiceCallTask webServiceCallTask = new WebServiceCallTask(this);
        webServiceCallTask.execute(urlToSearch);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void doParsingJson(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject jsonQuery = jsonObject.getJSONObject("query");
            JSONObject jsonPages = jsonQuery.getJSONObject("pages");
            System.out.println("jsonPages >>>>>>>>> " + jsonPages);
            JSONObject jObject = new JSONObject(jsonPages.toString().trim());
            Iterator<?> keys = jObject.keys();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                if (jObject.get(key) instanceof JSONObject) {
                    Gson gson = new Gson();
                    SearchItem searchItem = gson.fromJson(jObject.get(key).toString(), SearchItem.class);
                    System.out.println("searchItem >>>>>>>>> " + searchItem.getTitle());
                    itemList.add(searchItem);
                    // listeViewToDisplayResult.invalidate();
                    ((BaseAdapter) listeViewToDisplayResult.getAdapter()).notifyDataSetChanged();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        progressDialog.dismiss();
    }

    @Override
    public void onResultSuccess(String data) {

        doParsingJson(data);
        progressDialog.dismiss();
    }

    @Override
    public void onResultFail(String data) {
        progressDialog.dismiss();
    }
}
