package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.Random;

import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.GameLayout;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GameLayout gameLayout;
    private int rows;
    private int columns;
    private int backgoundColor;
    private int tileColor;
    private static final String TAG = MainActivity.class.getName();
    private Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rows = rand.nextInt(10);
        columns = rand.nextInt(12);
        tileColor = Color.argb(0,0,0,0);
        recyclerView = (RecyclerView) findViewById(R.id.tile_grid_activity);
        gameLayout = new GameLayout(rows,columns,tileColor);
        Log.d(TAG, "onCreate: " + gameLayout.getGameTiles());
    }
}
