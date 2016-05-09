package test.android.outlook.com.outlookandroidtest.util;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;

/**
 * Created by GhostBooster on 5/9/2016.
 */
public class ImageDownloader extends AsyncTask<Void,Void,Void> {

    ImageView imageView;
    String url;
    public ImageDownloader(ImageView imageView , String url)
    {
        this.url = url;
        this.imageView = imageView;

    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(Void... params) {

        ImageRequest ir = new ImageRequest(url, new Response.Listener<Bitmap>() {

            @Override
            public void onResponse(Bitmap response) {
                imageView.setImageBitmap(response);

            }
        }, 0, 0, null, null);
        return null;
    }
}
