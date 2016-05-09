package test.android.outlook.com.outlookandroidtest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import test.android.outlook.com.outlookandroidtest.AppController;
import test.android.outlook.com.outlookandroidtest.R;
import test.android.outlook.com.outlookandroidtest.model.SearchItem;
import test.android.outlook.com.outlookandroidtest.util.ImageDownloader;

/**
 * Created by bhupendra_tomar on 5/9/2016.
 */
public class CustomListAdapter extends BaseAdapter {
    private ArrayList<SearchItem> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    Animation animation = null;


    public CustomListAdapter(Context context, ArrayList<SearchItem> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;

    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        imageLoader = AppController.getInstance().getImageLoader();
        animation = AnimationUtils.loadAnimation(context, R.anim.fead_in);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.SearchItem = (TextView) convertView.findViewById(R.id.title);
            // holder.thumbNail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

       /* holder.SearchItem.setText(listData.get(position).getTitle());
        if (null != listData.get(position).getThumbnail().getSource()) {
            holder.thumbNail.setImageUrl(listData.get(position).getThumbnail().getSource(), imageLoader);
        } else {
            holder.thumbNail.setImageResource(R.drawable.ic_launcher);
        }*/

        if (listData != null) {
            if (listData.get(position).getThumbnail().getSource() != null) {
                new ImageDownloader(holder.image, listData.get(position).getThumbnail().getSource()).execute();
            }
        }

        animation.setDuration(500);
        convertView.startAnimation(animation);
        animation = null;

        return convertView;
    }

    static class ViewHolder {
        TextView SearchItem;
        ImageView image;
        // NetworkImageView thumbNail;
    }


}