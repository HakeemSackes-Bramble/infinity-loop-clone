package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Random;

import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.GameLayout;
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.Tile;
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.listeners.TileAlignmentListener;
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.tileRecyclerView.TileAdapter;
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.tileRecyclerView.TileView;

public class MainActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    private GameLayout gameLayout;
    private Button button;
    private LinearLayout linearLayout;
    private MediaPlayer mP3november;
    private int rows;
    private int columns;
    private float hue;
    private float saturation;
    private float value;
    private int backgoundColor;
    private int tileColor;

    private static final String TAG = MainActivity.class.getName();
    private Random rand = new Random();
    private GridLayoutManager gridLayoutManager;
    private View.OnTouchListener allTilesAligned = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            setValues();
            linearLayout.setBackgroundColor(backgoundColor);
            gameLayout.resetGame(rows, columns, tileColor);
            gridLayoutManager.setSpanCount(columns);
            recyclerView.setAdapter(new TileAdapter(gameLayout));
            button.setOnTouchListener(null);
            button.setBackgroundColor(Color.alpha(0));
            button.setText("");
            Log.d(TAG, "onClick: " + tileColor);
            return false;
        }
    };
    private TileAlignmentListener tileAlignmentListener = new TileAlignmentListener() {

        @Override
        public void checkTileAlignment(GameLayout gameLayout, View itemView, Tile tile, int position) {
            int oldPos = tile.getOrientation();
            if (tile.getTileType() == 3) {
                tile.setOrientation(((tile.getOrientation() + 1) % 2));
            } else {
                tile.setOrientation(((tile.getOrientation() + 1) % 4));
            }

            ((TileView) itemView).rotateView(oldPos, tile.getOrientation());
            if (tile.getTileType() < 5 && tile.getTileType() > 0) {
                if (tile.getOrientation() == tile.getCorrectOrientation()) {
                    gameLayout.addCorrectedTile(position);
                } else {
                    gameLayout.removeWrongTile(position);
                }
            }

        }

        @Override
        public void onAllTilesAligned() {
            // linearLayout.setBackgroundColor(tileColor);
            // gameLayout.setTileColor(backgoundColor);
            // recyclerView.setAdapter(new TileAdapter(gameLayout));
            button.setOnTouchListener(allTilesAligned);
            button.setBackgroundColor(Color.alpha(0));
            button.setTextColor(tileColor);
            button.setText("NEXT");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mP3november = new MediaPlayer().create(this, R.raw.bensound_november);
        button = findViewById(R.id.activity_button);
        linearLayout = findViewById(R.id.activity_main_LinearLayout);
        recyclerView = findViewById(R.id.tile_grid_activity);
        button.setBackgroundColor(Color.alpha(0));
        setValues();
        linearLayout.setBackgroundColor(backgoundColor);
        gameLayout = new GameLayout(rows, columns, tileColor, backgoundColor);
        gameLayout.createGameTiles();
        gameLayout.setListener(tileAlignmentListener);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), columns, RecyclerView.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(new TileAdapter(gameLayout));
        Log.d(TAG, "onCreate: " + gameLayout.getGameTiles());
        Log.d(TAG, "onCreate: " + gameLayout.getCorrectlyOrientedTileSize());
    }

    @Override
    protected void onStart() {
        super.onStart();
//        mP3november.setLooping(true);
//        mP3november.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mP3november.setLooping(true);
        mP3november.start();
    }

    @Override
    protected void onStop() {
        mP3november.pause();
        super.onStop();

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

    private void setValues() {
        rows = rand.nextInt(9) + 5;
        columns = rand.nextInt(5) + 5;
        saturation = .1f;
        value = 1f;
        hue = rand.nextFloat() * 360;
        backgoundColor = Color.HSVToColor(new float[]{hue, saturation, value});
        tileColor = Color.BLACK;
    }


}
