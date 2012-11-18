package de.piepenmoker.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

public class PipeLoadingTask extends AsyncTask<Void, Void, Pipe[]> {

    ListView pipelist = null;
    Context context = null;

    public PipeLoadingTask(Context context, ListView pipelist) {
        this.context = context;
        this.pipelist = pipelist;
    }

    protected Pipe[] doInBackground(Void... nothing) {
        String url = "http://oebs.net/pipes.json";
        Pipe[] pipes = null;
        String jsonBuf = null;

        try {
            InputStream in = null;
            in = new java.net.URL(url).openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder b = new StringBuilder();

            for (String line = null; (line = reader.readLine()) != null;) {
                b.append(line).append("\n");
            }
            jsonBuf = b.toString();
        } catch (IOException e) {
            // TODO
        }

        try {
            JSONArray arr = (JSONArray) new JSONTokener(jsonBuf).nextValue();
            pipes = new Pipe[arr.length()];
            for (int i = 0; i < arr.length(); i++) {
                pipes[i] = new Pipe(arr.getJSONObject(i));
            }
        } catch (JSONException e) {
            // TODO
        }

        return pipes;
    }

    protected void onPostExecute(Pipe[] pipes) {
        PipeArrayAdapter adapter = new PipeArrayAdapter(this.context,
                android.R.layout.simple_list_item_1, pipes);

        this.pipelist.setAdapter(adapter);
    }
}
