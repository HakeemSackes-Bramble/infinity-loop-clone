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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.GameOneLayout;
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
    public static String DATE = "date";
    private Gson gson;
    public RecyclerView recyclerView;
    private GameOneLayout gameOneLayout;
    private Button button;
    private LinearLayout linearLayout;
    private static final String TAG = MainActivity.class.getName();
    private GridLayoutManager gridLayoutManager;
    SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;
    Date c = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss", Locale.getDefault());
    String formattedDate = df.format(c);
    private final View.OnTouchListener allTilesAligned = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            if (action == MotionEvent.ACTION_DOWN && gameOneLayout.hasAllTilesAligned()) {
                gameOneLayout.setValues();
                linearLayout.setBackgroundColor(gameOneLayout.getBackgroundColor());
                gameOneLayout.removeSavedData();
                gameOneLayout.resetGame();
                gridLayoutManager.setSpanCount(gameOneLayout.getColumns());
                recyclerView.setAdapter(new TileAdapter(gameOneLayout, gameOneLayout.getTileSize()));
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
        public void checkTileAlignment(GameOneLayout gameOneLayout, Tile tile, int position) {

            String prongPos = tile.getStringOrientation();
            int[] surroundingTilePositions = gameOneLayout.surroundingTileNumbers(position);
            for (int i = 0; i < 4; i++) {
                int surPos = (i + 2) % 4;
                // check center tiles
                if (tile.getTilePositions().contains(SquareTilePositions.getTilePositionsFromValue(i))) {
                    checkForEdgeFacingProngs(tile, i);
                } else {
                    checkSurroundingTiles(i, prongPos, surroundingTilePositions, tile, surPos);
                }
            }
            checkPathAlignment(tile.getPathId(), gameOneLayout);
            gameOneLayout.addCorrectedTile(position, tile.isProperlyAligned());
            onAllTilesAligned(gameOneLayout.hasAllTilesAligned());
        }

        @Override
        public void checkPathAlignment(int pathId, GameOneLayout gameOneLayout) {
            //gameOneLayout.getPathMap().get(pathId).isPathComplete();
        }

        @Override
        public void onPathComplete() {
            //disappear and slide down, change color,  do something.
        }

        @Override
        public void onAllTilesAligned(boolean alignedTiles) {
            if (alignedTiles) {
                button.setTextColor(gameOneLayout.getTileColor());
                button.setText(sharedPreferences.getString(DATE, "NEXT"));
            }
        }
    };

    private void checkForEdgeFacingProngs(Tile tile, int i) {
        tile.isProngConnected(i, tile.getStringOrientation().charAt(i) == '0');
    }

    private void checkSurroundingTiles(int i, String prongPosition, int[] tilePositions, Tile currentTile, int adjacentTileProngPosition) {
        Tile surroundingTile = gameOneLayout.getGameTiles().get(tilePositions[i]);
        boolean connectionChecker = prongPosition.charAt(i) == surroundingTile.getStringOrientation().charAt(adjacentTileProngPosition);
        currentTile.isProngConnected(i, connectionChecker);
        surroundingTile.isProngConnected(adjacentTileProngPosition, connectionChecker);
        gameOneLayout.addCorrectedTile(tilePositions[i], surroundingTile.isProperlyAligned());
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
        gameOneLayout = new GameOneLayout(sharedPreferences, editor);
        linearLayout.setBackgroundColor(gameOneLayout.getBackgroundColor());
        gameOneLayout.setValues();
        gameOneLayout.createGame();
        gameOneLayout.setListener(tileAlignmentListener);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), gameOneLayout.getColumns(), RecyclerView.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(new TileAdapter(gameOneLayout, gameOneLayout.getTileSize()));
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

    private void saveData() {
        ArrayList<Tile> gameTiles = gameOneLayout.getGameTiles();
        String jsonGame = gson.toJson(gameTiles);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        editor.putString(DATE, formattedDate);
        editor.putString(CURRENT_PUZZLE, jsonGame);
        editor.putInt(CURRENT_ROW_SIZE, gameOneLayout.getRows());
        editor.putInt(CURRENT_COLUMN_SIZE, gameOneLayout.getColumns());
        editor.putInt(CURRENT_BACKGROUND_COLOR, gameOneLayout.getBackgroundColor());
        editor.putInt(CURRENT_TILE_COLOR, gameOneLayout.getTileColor());
        editor.apply();
    }
}
