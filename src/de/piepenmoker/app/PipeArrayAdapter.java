package de.piepenmoker.app;

import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PipeArrayAdapter extends ArrayAdapter<JSONObject> {

    private Context parentContext;
    private JSONObject[] pipes;

    public PipeArrayAdapter(Context context, int resid, JSONObject[] objects ) {
        super(context, resid, objects);

        this.parentContext = context;
        this.pipes = objects;
    }

    private class ViewHolder {
        ImageView teaser;
        TextView name;
        TextView price;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ViewHolder holder;

        public DownloadImageTask(ViewHolder holder) {
            this.holder = holder;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            holder.name.setVisibility(android.view.View.VISIBLE);
            holder.price.setVisibility(android.view.View.VISIBLE);
            holder.teaser.setVisibility(android.view.View.VISIBLE);
            holder.teaser.setImageBitmap(result);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        JSONObject pipe = pipes[position];

        LayoutInflater mInflater = (LayoutInflater)
                this.parentContext.getSystemService(
                        Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.pipe_item, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.pipeName);
            holder.price = (TextView) convertView.findViewById(R.id.pipePrice);
            holder.teaser = (ImageView) convertView.findViewById(
                    R.id.pipeTeaser);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            holder.name.setText(pipe.getString("name"));
            holder.price.setText(pipe.getString("price") + " EUR");
            String url = "http://www.piepenmoker.de" + 
                    pipe.getJSONObject("image").get("teaser");

            new DownloadImageTask(holder).execute(url);

        } catch (JSONException e) {

        }
        return convertView;

	}
}

