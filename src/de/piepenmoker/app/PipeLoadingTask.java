package de.piepenmoker.app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

public class PipeLoadingTask extends AsyncTask<Void, Void, JSONObject[]> {

    ListView pipelist = null;
    Context context = null;

    public PipeLoadingTask(Context context, ListView pipelist) {
        this.context = context;
        this.pipelist = pipelist;
    }

    protected JSONObject[] doInBackground(Void... nothing) {

        String json = "[{\"price\": 140, \"name\": \"PM-121005-b\", \"teaser\":\"http://www.piepenmoker.de/static/images/pipes/421/teaser/3322.jpg\"}, "
                +"{\"price\": 120, \"name\":\"PM-120821-ax\", \"teaser\": \"http://www.piepenmoker.de/static/images/pipes/390/teaser/3071.jpg\"}]";

        JSONObject[] pipes = null;
        try {
            JSONArray arr = (JSONArray) new JSONTokener(json).nextValue();
            pipes = new JSONObject[arr.length()];
            pipes[0] = (JSONObject)arr.get(0);
            pipes[1] = (JSONObject)arr.get(1);
        } catch (JSONException e) {

        }
        return pipes;
    }

    protected void onPostExecute(JSONObject[] pipes) {
        PipeArrayAdapter adapter = new PipeArrayAdapter(this.context,
                android.R.layout.simple_list_item_1, pipes);

        this.pipelist.setAdapter(adapter);
    }
}
