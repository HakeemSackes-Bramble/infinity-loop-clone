package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Random;

import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.GameLayout;
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.listeners.TileAlignedListener;
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.tileRecyclerView.TileAdapter;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GameLayout gameLayout;
    private Button button;
    private int rows;
    private int columns;
    private LinearLayout linearLayout;
    private int backgoundColor;
    private int tileColor;
    private static final String TAG = MainActivity.class.getName();
    private Random rand = new Random();
    private GridLayoutManager gridLayoutManager;
    private TileAlignedListener tileAlignedListener = new TileAlignedListener() {
        @Override
        public void onTilesAligned() {
            rows = rand.nextInt(8) + 4;
            columns = rand.nextInt(5) + 4;
            int red = rand.nextInt(56) + 200;
            int green = rand.nextInt(56) + 200;
            int blue = rand.nextInt(56) + 200;
            linearLayout.setBackgroundColor(Color.rgb(red, green, blue));
            tileColor = Color.rgb((int) (red * .5 + 10), (int) (green * .5 + 10), (int) (blue * .5 + 10));
            gameLayout.resetGame(rows, columns, tileColor);
            gridLayoutManager.setSpanCount(columns);
            recyclerView.setAdapter(new TileAdapter(gameLayout));
            Log.d(TAG, "onClick: " + tileColor);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.activity_button);
        linearLayout = (LinearLayout) findViewById(R.id.activity_main_LinearLayout);
        int red = rand.nextInt(56) + 200;
        int green = rand.nextInt(56) + 200;
        int blue = rand.nextInt(56) + 200;
        backgoundColor = Color.rgb(red, green, blue);
        linearLayout.setBackgroundColor(backgoundColor);
        tileColor = Color.rgb((int) (red * .6), (int) (green * .6), (int) (blue * .6));
        rows = rand.nextInt(8) + 4;
        columns = rand.nextInt(5) + 4;
        Log.d(TAG, "onCreate: " + backgoundColor + " " + (-15721573 + 13487566) + " " + Color.rgb(50, 50, 50));
        recyclerView = (RecyclerView) findViewById(R.id.tile_grid_activity);
        gameLayout = new GameLayout(rows, columns, tileColor);
        gameLayout.setListener(tileAlignedListener);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), columns, RecyclerView.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(new TileAdapter(gameLayout));
        Log.d(TAG, "onCreate: " + gameLayout.getGameTiles());

        //temp button used for testing
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rows = rand.nextInt(8) + 4;
                columns = rand.nextInt(5) + 4;
                int red = rand.nextInt(56) + 200;
                int green = rand.nextInt(56) + 200;
                int blue = rand.nextInt(56) + 200;
                linearLayout.setBackgroundColor(Color.rgb(red, green, blue));
                tileColor = Color.rgb((int) (red * .5), (int) (green * .5), (int) (blue * .5));
                gameLayout.resetGame(rows, columns, tileColor);
                gridLayoutManager.setSpanCount(columns);
                recyclerView.setAdapter(new TileAdapter(gameLayout));
                Log.d(TAG, "onClick: " + tileColor);
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
