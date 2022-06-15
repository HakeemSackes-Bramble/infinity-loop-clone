package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone;

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

import java.util.Random;

import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.GameLayout;
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.Tile;
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.SquareTilePositions;
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.listeners.TileAlignmentListener;
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.tileRecyclerView.TileAdapter;

public class MainActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    private GameLayout gameLayout;
    private Button button;
    private LinearLayout linearLayout;
    private int rows;
    private int columns;
    private int tileSize;
    private int backgroundColor;
    private int tileColor;
    private static final String TAG = MainActivity.class.getName();
    private static final int maxGameWidth = 360;
    private static final int maxGameHeight = 540;
    private Random rand = new Random();
    private GridLayoutManager gridLayoutManager;
    private SharedPreferences.Editor preferences;
    private View.OnTouchListener allTilesAligned = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            MainActivity.this.setValues();
            linearLayout.setBackgroundColor(backgroundColor);
            gameLayout.resetGame(rows, columns, tileColor);
            gridLayoutManager.setSpanCount(columns);
            recyclerView.setAdapter(new TileAdapter(gameLayout, tileSize));
            button.setOnTouchListener(null);
            button.setBackgroundColor(Color.alpha(0));
            button.setText("");
            Log.d(TAG, "onClick: " + tileColor);
            return false;
        }
    };
    private TileAlignmentListener tileAlignmentListener = new TileAlignmentListener() {

        @Override
        public void checkTileAlignment(GameLayout gameLayout, Tile tile, int position) {

            String prongPos = tile.getStringOrientation();
            int[] surroundingTilePositions = gameLayout.getSurroundingTileNumbers(position);
            for (int i = 0; i < 4; i++) {
                int surPos = (i + 2) % 4;
                // check center tiles
                if (tile.getTilePositions().contains(SquareTilePositions.getTilePositionsFromValue(i))) {
                    checkForEdgeFacingProngs(tile, i);
                } else {
                    checkSurroundingTiles(i, prongPos, surroundingTilePositions, tile, surPos);
                }
            }
            gameLayout.addCorrectedTile(position, tile.isProperlyAligned());
            onAllTilesAligned(gameLayout.hasAllTilesAligned());
        }

        @Override
        public void checkPathAlignment() {
        }

        @Override
        public void onAllTilesAligned(boolean alignedTiles) {
            if (alignedTiles) {
                button.setOnTouchListener(allTilesAligned);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences(getString(R.string.most_recent_game_list), MODE_PRIVATE).edit();
        setContentView(R.layout.activity_main);
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
        recyclerView.setAdapter(new TileAdapter(gameLayout, tileSize));
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
        tileSize = maxGameHeight / rows > maxGameWidth / columns
                ? maxGameWidth / columns
                : maxGameHeight / rows;
        float hue = rand.nextFloat() * 360;
        float saturation = .05f;
        float value = 1f;
        rustTheme(hue, saturation, value);
    }

    void rustTheme(float hue, float saturation, float value) {
        backgroundColor = Color.HSVToColor(new float[]{hue, saturation, value});
        ;
        tileColor = Color.HSVToColor(new float[]{hue, saturation, 0.5f});
    }

    void neoDarkTheme(float hue, float saturation, float value) {
        backgroundColor = Color.HSVToColor(new float[]{hue, saturation, 0.2f});
        tileColor = Color.HSVToColor(new float[]{hue, saturation + .6f, value});
    }

    void neoLightTheme(float hue, float saturation, float value) {
        backgroundColor = Color.HSVToColor(new float[]{hue, saturation, value});
        tileColor = Color.HSVToColor(new float[]{hue, saturation + .6f, value});
    }

}
