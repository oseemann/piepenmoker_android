package de.piepenmoker.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends Activity {
    public final static String PIPE_ID = "de.piepenmoker.app.PIPE_ID";

    // A class variable that keeps a list of all received pipes in memory
    // to avoid repeated loading of the pipes list.
    private static Pipe[] pipes = null;

    public static Pipe[] getPipes() {
        return MainActivity.pipes;
    }

    public static void setPipes(Pipe[] pipes) {
        MainActivity.pipes = pipes;
    }

    public static Pipe getPipe(Integer pipeId) {

        for (int i = 0; i < MainActivity.pipes.length; i++) {
            if (MainActivity.pipes[i].id.equals(pipeId)) {
               return MainActivity.pipes[i];
            }
        }
        return null;
    }

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

    public void displayPipeList(Pipe[] pipes) {

        // We need to call setPipes here in case it was called via the
        // PipeLoadingTask and we have not yet cached a pipes array.
        setPipes(pipes);

        ListView pipelist = (ListView) findViewById(R.id.mainPipeList);
        PipeArrayAdapter adapter = new PipeArrayAdapter(this,
                android.R.layout.simple_list_item_1, pipes);
        pipelist.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (pipes == null) {
            new PipeLoadingTask(this).execute();
        } else {
            displayPipeList(pipes);
        }
    }

    public void displayPipe(View view) {
        Pipe pipe = (Pipe) view.getTag();
        Intent intent = new Intent(this, DisplayPipeActivity.class);
        intent.putExtra(PIPE_ID, pipe.id);
        startActivity(intent);
    }

}
