package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Random;

import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.GameLayout;
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.tileRecyclerView.TileAdapter;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GameLayout gameLayout;
    private Button button;
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
        button = (Button) findViewById(R.id.activity_button);
        rows = rand.nextInt(8) + 4;
        columns = rand.nextInt(5) + 4;
        tileColor = Color.argb(255, 0, 0, 0);
        recyclerView = (RecyclerView) findViewById(R.id.tile_grid_activity);
        gameLayout = new GameLayout(rows, columns, tileColor);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), columns, RecyclerView.VERTICAL, false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        recyclerView.setAdapter(new TileAdapter(gameLayout));
        Log.d(TAG, "onCreate: " + gameLayout.getGameTiles());

        //temp button used for testing
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameLayout.resetGame();
                recyclerView.setAdapter(new TileAdapter(gameLayout));
            }
        });
    }
}
