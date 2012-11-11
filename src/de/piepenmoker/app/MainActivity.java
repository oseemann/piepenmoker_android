package de.piepenmoker.app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    public JSONObject[] getPipes() {
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

    @Override
    public void onResume() {
        super.onResume();

        ListView pipelist = (ListView) findViewById(R.id.mainPipeList);
        new PipeLoadingTask(this, pipelist).execute();
    }
}
