package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Random;

import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.GameLayout;
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.SquareTilePositions;
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.Tile;
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.listeners.TileAlignmentListener;
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.tileRecyclerView.TileAdapter;

public class MainActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String CURRENT_PUZZLE = "puzzle";
    public static final String CURRENT_ROW_SIZE = "row_size";
    public static final String CURRENT_COLUMN_SIZE = "column_size";
    public static final String CURRENT_BACKGROUND_COLOR = "background_color";
    public static final String CURRENT_TILE_COLOR = "tile_color";
    private Gson gson;
    public RecyclerView recyclerView;
    private GameLayout gameLayout;
    private Button button;
    private LinearLayout linearLayout;
    private int rows = 0;
    private int columns = 0;
    private int tileSize = 0;
    private int backgroundColor = 0;
    private int tileColor = 0;
    private static final String TAG = MainActivity.class.getName();
    private static final int maxGameWidth = 360;
    private static final int maxGameHeight = 540;
    private Random rand = new Random();
    private GridLayoutManager gridLayoutManager;
    SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;
    private final View.OnTouchListener allTilesAligned = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();

            if (action == MotionEvent.ACTION_DOWN && gameLayout.hasAllTilesAligned()) {
                MainActivity.this.setValues();
                linearLayout.setBackgroundColor(backgroundColor);
                gameLayout.removeSavedData();
                gameLayout.resetGame(rows, columns);
                gridLayoutManager.setSpanCount(columns);
                recyclerView.setAdapter(new TileAdapter(gameLayout, tileSize));
                button.setBackgroundColor(Color.alpha(0));
                v.performClick();
                return true;
            } else {
                button.setText("");
            }
            return MainActivity.super.onTouchEvent(event);
        }
    };
    private final TileAlignmentListener tileAlignmentListener = new TileAlignmentListener() {

        @Override
        public void checkTileAlignment(GameLayout gameLayout, Tile tile, int position) {

            String prongPos = tile.getStringOrientation();
            int[] surroundingTilePositions = gameLayout.surroundingTileNumbers(position);
            for (int i = 0; i < 4; i++) {
                int surPos = (i + 2) % 4;
                // check center tiles
                if (tile.getTilePositions().contains(SquareTilePositions.getTilePositionsFromValue(i))) {
                    checkForEdgeFacingProngs(tile, i);
                } else {
                    checkSurroundingTiles(i, prongPos, surroundingTilePositions, tile, surPos);
                }
            }
            checkPathAlignment(tile.getPathId(), gameLayout);
            gameLayout.addCorrectedTile(position, tile.isProperlyAligned());
            onAllTilesAligned(gameLayout.hasAllTilesAligned());
        }

        @Override
        public void checkPathAlignment(int pathId, GameLayout gameLayout) {
//            gameLayout.getPathMap().get(pathId).isPathComplete();
        }

        @Override
        public void onPathComplete() {
            //disappear and slide down, change color,  do somehting.
        }

        @Override
        public void onAllTilesAligned(boolean alignedTiles) {
            if (alignedTiles) {
                button.setTextColor(tileColor);
                button.setText("NEXT");
            }
        }
    };

    private void checkForEdgeFacingProngs(Tile tile, int i) {
        tile.isProngConnected(i, tile.getStringOrientation().charAt(i) == '0');
    }

    private void checkSurroundingTiles(int i, String prongPosition, int[] tilePositions, Tile currentTile, int adjacentTileProngPosition) {
        Tile surroundingTile = gameLayout.getGameTiles().get(tilePositions[i]);
        boolean connectionChecker = prongPosition.charAt(i) == surroundingTile.getStringOrientation().charAt(adjacentTileProngPosition);
        currentTile.isProngConnected(i, connectionChecker);
        surroundingTile.isProngConnected(adjacentTileProngPosition, connectionChecker);
        gameLayout.addCorrectedTile(tilePositions[i], surroundingTile.isProperlyAligned());
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson = new Gson();
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.activity_button);
        linearLayout = findViewById(R.id.activity_main_LinearLayout);
        recyclerView = findViewById(R.id.tile_grid_activity);
        button.setBackgroundColor(Color.alpha(0));
        setValues();
        linearLayout.setBackgroundColor(backgroundColor);
        gameLayout = new GameLayout(rows, columns, tileColor, sharedPreferences, editor);
        gameLayout.createGame();
        gameLayout.setListener(tileAlignmentListener);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), columns, RecyclerView.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(new TileAdapter(gameLayout, tileSize));
        button.setOnTouchListener(allTilesAligned);
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: triggered");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: triggered");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: triggered");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: triggered");
        saveData();
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

        rows = sharedPreferences.getInt(CURRENT_ROW_SIZE, rand.nextInt(9) + 5);
        columns = sharedPreferences.getInt(CURRENT_COLUMN_SIZE, rand.nextInt(5) + 5);
        backgroundColor = sharedPreferences.getInt(CURRENT_BACKGROUND_COLOR,
                Color.HSVToColor(new float[]{rand.nextFloat() * 360, .05f, 1f}));
        tileColor = sharedPreferences.getInt(CURRENT_TILE_COLOR,
                Color.HSVToColor(new float[]{rand.nextFloat() * 360, .05f, 0.6f}));
        editor.apply();

        int widthTileSize = maxGameWidth / columns;
        int heightTileSize = maxGameHeight / rows;
        tileSize = Math.min(heightTileSize, widthTileSize);
    }

    void rustTheme(float hue, float saturation, float value) {
        backgroundColor = Color.HSVToColor(new float[]{hue, saturation, value});
        tileColor = Color.HSVToColor(new float[]{hue, saturation, 0.6f});
    }

    void neoDarkTheme(float hue, float saturation, float value) {
        backgroundColor = Color.HSVToColor(new float[]{hue, saturation, 0.2f});
        tileColor = Color.HSVToColor(new float[]{hue, saturation + .6f, value});
    }

    void neoLightTheme(float hue, float saturation, float value) {
        backgroundColor = Color.HSVToColor(new float[]{hue, saturation, value});
        tileColor = Color.HSVToColor(new float[]{hue, saturation + .6f, value});
    }

    private void saveData() {
        ArrayList<Tile> gameTiles = gameLayout.getGameTiles();
        String jsonGame = gson.toJson(gameTiles);
        editor.putString(CURRENT_PUZZLE, jsonGame);
        editor.putInt(CURRENT_ROW_SIZE, rows);
        editor.putInt(CURRENT_COLUMN_SIZE, columns);
        editor.putInt(CURRENT_BACKGROUND_COLOR, columns);
        editor.putInt(CURRENT_TILE_COLOR, tileColor);
        editor.apply();
    }

}
