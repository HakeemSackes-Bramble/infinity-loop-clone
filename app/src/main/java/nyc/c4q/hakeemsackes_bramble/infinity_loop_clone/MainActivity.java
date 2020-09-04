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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.GameLayout;
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.Tile;
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.TilePositions;
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.listeners.TileAlignmentListener;
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.tileRecyclerView.TileAdapter;

import static nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.TilePositions.BOTTOM_EDGE;
import static nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.TilePositions.LEFT_EDGE;
import static nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.TilePositions.NULL_POS;
import static nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.TilePositions.RIGHT_EDGE;
import static nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.TilePositions.TOP_EDGE;

public class MainActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    private GameLayout gameLayout;
    private Button button;
    private LinearLayout linearLayout;
    MediaPlayer mP3november;
    private int rows;
    private int columns;
    private int backgroundColor;
    private int tileColor;
    private static final String TAG = MainActivity.class.getName();
    private Random rand = new Random();
    private GridLayoutManager gridLayoutManager;
    private View.OnTouchListener allTilesAligned = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            MainActivity.this.setValues();
            linearLayout.setBackgroundColor(backgroundColor);
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
        public void checkTileAlignment(GameLayout gameLayout, Tile tile, int position, int oldPos, int newPos) {

            tile.setOrientation(newPos);
            String prongPos = tile.getStringOrientation();
            HashSet<TilePositions> positionlist = tile.getTilePositions();
            Iterator<TilePositions> positionsIterator = positionlist.iterator();
            TilePositions positionParser = positionsIterator.next();
            int[] surroundingTilePositions = new int[]{
                    position - columns,
                    position + 1,
                    position + columns,
                    position - 1
            };
            for (int i = 0; i < prongPos.length(); i++) {
                int surPos = (i + 2) % 4;
                // check center tiles

                if (surroundingTilePositions[i] < 0 && i == 0) {
                    if (tile.getStringOrientation().charAt(i) == '0') {
                        tile.makeAlignmentTrue(i);
                    } else if (tile.getStringOrientation().charAt(i) == '1') {
                        tile.makeAlignmentFalse(i);
                    }
                } else if ((surroundingTilePositions[i] >= (rows * columns) || surroundingTilePositions[i] % columns == 0) && i == 1) {
                    if (tile.getStringOrientation().charAt(i) == '0') {
                        tile.makeAlignmentTrue(i);
                    } else if (tile.getStringOrientation().charAt(i) == '1') {
                        tile.makeAlignmentFalse(i);
                    }
                } else if (surroundingTilePositions[i] >= (rows * columns) && i == 2) {
                    if (tile.getStringOrientation().charAt(i) == '0') {
                        tile.makeAlignmentTrue(i);
                    } else if (tile.getStringOrientation().charAt(i) == '1') {
                        tile.makeAlignmentFalse(i);
                    }
                } else if ((surroundingTilePositions[i] < 0 || surroundingTilePositions[i] % columns == columns - 1) && i == 3) {
                    if (tile.getStringOrientation().charAt(i) == '0') {
                        tile.makeAlignmentTrue(i);
                    } else if (tile.getStringOrientation().charAt(i) == '1') {
                        tile.makeAlignmentFalse(i);
                    }
                } else {
                    Log.i(TAG, "checkTileAlignment: " + i);
                    checkSurroundingTiles(i, prongPos, surroundingTilePositions, tile, surPos);
                }
            }
            if (tile.isProperlyAligned()) {
                gameLayout.addCorrectedTile(position);
            } else {
                gameLayout.removeWrongTile(position);
            }

        }

        @Override
        public void onAllTilesAligned() {
            button.setOnTouchListener(allTilesAligned);
            button.setTextColor(tileColor);
            button.setText("NEXT");
        }
    };

    private void checkSurroundingTiles(int i, String prongPosition, int[] tilePositions, Tile currentTile, int adjacentTileProngPosition) {
        Tile surroundingTile = gameLayout.getGameTiles().get(tilePositions[i]);
        if (prongPosition.charAt(i) == surroundingTile.getStringOrientation().charAt(adjacentTileProngPosition)) {
            currentTile.makeAlignmentTrue(i);
            surroundingTile.makeAlignmentTrue(adjacentTileProngPosition);
        } else {
            currentTile.makeAlignmentFalse(i);
            surroundingTile.makeAlignmentFalse(adjacentTileProngPosition);
        }
        if (surroundingTile.isProperlyAligned()) {
            gameLayout.addCorrectedTile(tilePositions[i]);
        } else {
            gameLayout.removeWrongTile(tilePositions[i]);
        }
    }

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
        linearLayout.setBackgroundColor(backgroundColor);
        gameLayout = new GameLayout(rows, columns, tileColor, tileAlignmentListener);
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
    }

    @Override
    protected void onStart() {
        super.onStart();
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
        float hue = rand.nextFloat() * 360;
        float saturation = .1f;
        float value = 1f;
        backgroundColor = Color.HSVToColor(new float[]{hue, saturation, value});
        tileColor = Color.BLACK;
    }
}
