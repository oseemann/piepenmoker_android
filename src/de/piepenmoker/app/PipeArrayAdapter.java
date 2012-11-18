package de.piepenmoker.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PipeArrayAdapter extends ArrayAdapter<Pipe> {

    private Context parentContext;
    private Pipe[] pipes;

    public PipeArrayAdapter(Context context, int resid, Pipe[] objects) {
        super(context, resid, objects);

        this.parentContext = context;
        this.pipes = objects;
    }

    private class ViewHolder {
        ImageView teaser;
        TextView name;
        TextView price;
    }

    private class DownloadPipeTeaserTask extends DownloadImageTask {
        ViewHolder holder;

        public DownloadPipeTeaserTask(ViewHolder holder) {
            super(parentContext);
            this.holder = holder;
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
        Pipe pipe = pipes[position];

        LayoutInflater mInflater = (LayoutInflater) this.parentContext
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.pipe_item, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.pipeName);
            holder.price = (TextView) convertView.findViewById(R.id.pipePrice);
            holder.teaser = (ImageView) convertView
                    .findViewById(R.id.pipeTeaser);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(pipe.name);
        holder.price.setText(pipe.price.toString() + " EUR");

        // Use the pipe object as tag so we can refer to it in the click
        // handler.
        convertView.setTag(pipe);

        new DownloadPipeTeaserTask(holder).execute(pipe.image.teaser);

        return convertView;
    }
}
