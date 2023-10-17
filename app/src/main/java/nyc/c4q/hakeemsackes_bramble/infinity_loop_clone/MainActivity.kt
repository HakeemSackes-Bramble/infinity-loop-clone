package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.View.OnTouchListener
import android.widget.Button
import android.widget.LinearLayout
import com.google.gson.Gson
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.GameLayout
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.SquareTilePositions
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.Tile
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.listeners.TileAlignmentListener
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.tileRecyclerView.TileAdapter
import java.util.Random

class MainActivity : AppCompatActivity() {
    private val CURRENT_ROW_SIZE = "row_size"
    private val CURRENT_COLUMN_SIZE = "column_size"
    private val CURRENT_BACKGROUND_COLOR = "background_color"
    private val CURRENT_TILE_COLOR = "tile_color"
    private var gson: Gson = Gson()
    private var recyclerView: RecyclerView = findViewById(R.id.tile_grid_activity)
    private var button: Button  = findViewById(R.id.activity_button)
    private var linearLayout: LinearLayout = findViewById(R.id.activity_main_LinearLayout)
    private var rows = 0
    private var columns = 0
    private var tileSize = 0
    private var backgroundColor = 0
    private var tileColor = 0
    private val rand = Random()
    private var gridLayoutManager: GridLayoutManager =
    object : GridLayoutManager(applicationContext, columns, RecyclerView.VERTICAL, false) {
        override fun canScrollVertically(): Boolean {
            return false
        }
    }
    private var sharedPreferences: SharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
    private var editor = sharedPreferences.edit()
    private var gameLayout: GameLayout = GameLayout(rows, columns, tileColor, sharedPreferences)
    private val allTilesAligned = OnTouchListener { v, event ->
        setValues()
        linearLayout!!.setBackgroundColor(backgroundColor)
        gameLayout!!.resetGame(rows, columns, tileColor)
        gridLayoutManager!!.spanCount = columns
        recyclerView!!.adapter = TileAdapter(gameLayout, tileSize)
        button!!.setOnTouchListener(null)
        button!!.setBackgroundColor(Color.alpha(0))
        button!!.text = ""
        false
    }
    private val tileAlignmentListener: TileAlignmentListener = object : TileAlignmentListener {
        override fun checkTileAlignment(gameLayout: GameLayout, tile: Tile, position: Int) {
            val prongPos = tile.getStringOrientation()
            val surroundingTilePositions = gameLayout.getSurroundingTileNumbers(position)
            for (i in 0..3) {
                val surPos = (i + 2) % 4
                // check center tiles
                if (tile.getTilePositions()
                        .contains(SquareTilePositions.Companion.getTilePositionsFromValue(i))
                ) {
                    checkForEdgeFacingProngs(tile, i)
                } else {
                    checkSurroundingTiles(i, prongPos, surroundingTilePositions, tile, surPos)
                }
            }
            gameLayout.addCorrectedTile(position, tile!!.isProperlyAligned)
            onAllTilesAligned(gameLayout.hasAllTilesAligned())
        }

        override fun checkPathAlignment() {}
        override fun onAllTilesAligned(alignedTiles: Boolean) {
            if (alignedTiles) {
                button!!.setOnTouchListener(allTilesAligned)
                button!!.setTextColor(tileColor)
                button!!.text = "NEXT"
            }
        }
    }

    private fun checkForEdgeFacingProngs(tile: Tile, i: Int) {
        tile!!.isProngConnected(i, tile.stringOrientation!![i] == '0')
    }

    private fun checkSurroundingTiles(i: Int, prongPosition: String, tilePositions: IntArray, currentTile: Tile, adjacentTileProngPosition: Int) {
        val surroundingTile = gameLayout.getGameTiles()[tilePositions[i]]
        val connectionChecker = prongPosition!![i] == surroundingTile.stringOrientation[adjacentTileProngPosition]
        currentTile!!.isProngConnected(i, connectionChecker)
        surroundingTile!!.isProngConnected(adjacentTileProngPosition, connectionChecker)
        gameLayout!!.addCorrectedTile(tilePositions[i], surroundingTile!!.isProperlyAligned)
    }

    private void checkSurroundingTiles(int i, String prongPosition, int[] tilePositions, Tile currentTile, int adjacentTileProngPosition) {
        Tile surroundingTile = gameLayout.getGameTiles().get(tilePositions[i]);
        boolean connectionChecker = prongPosition.charAt(i) == surroundingTile.getStringOrientation().charAt(adjacentTileProngPosition);
        currentTile.isProngConnected(i, connectionChecker);
        surroundingTile.isProngConnected(adjacentTileProngPosition, connectionChecker);
        gameLayout.addCorrectedTile(tilePositions[i], surroundingTile.isProperlyAligned());
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        button.setBackgroundColor(Color.alpha(0))
        setValues()
        linearLayout.setBackgroundColor(backgroundColor)
        gameLayout =
        gameLayout!!.createGame()
        gameLayout!!.setListener(tileAlignmentListener)
        gridLayoutManager
        recyclerView.setLayoutManager(gridLayoutManager)
        recyclerView.setAdapter(TileAdapter(gameLayout, tileSize))
    }

    override fun onStart() {
        Log.d(TAG, "onStart: triggered")
        super.onStart()
    }

    override fun onResume() {
        Log.d(TAG, "onResume: triggered")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "onPause: triggered")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "onStop: triggered")
        saveData()
        super.onStop()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

    private fun setValues() {
        if (sharedPreferences!!.contains(CURRENT_BACKGROUND_COLOR)) {
            val bgcolor = Color.HSVToColor(floatArrayOf(rand.nextFloat() * 360, .05f, 1f))
            val tColor = Color.HSVToColor(floatArrayOf(rand.nextFloat() * 360, .05f, 0.6f))
            rows = sharedPreferences!!.getInt(CURRENT_ROW_SIZE, rand.nextInt(9) + 5)
            columns = sharedPreferences!!.getInt(CURRENT_COLUMN_SIZE, rand.nextInt(5) + 5)
            backgroundColor = sharedPreferences!!.getInt(CURRENT_BACKGROUND_COLOR, bgcolor)
            tileColor = sharedPreferences!!.getInt(CURRENT_TILE_COLOR, tColor)
            editor!!.apply()
        } else {
            rows = rand.nextInt(9) + 5
            columns = rand.nextInt(5) + 5
            val hue = rand.nextFloat() * 360
            val saturation = .05f
            val value = 1f
            rustTheme(hue, saturation, value)
        }
        val widthTileSize = maxGameWidth / columns
        val heightTileSize = maxGameHeight / rows
        tileSize = Math.min(heightTileSize, widthTileSize)
    }

    fun rustTheme(hue: Float, saturation: Float, value: Float) {
        backgroundColor = Color.HSVToColor(floatArrayOf(hue, saturation, value))
        tileColor = Color.HSVToColor(floatArrayOf(hue, saturation, 0.6f))
    }

    fun neoDarkTheme(hue: Float, saturation: Float, value: Float) {
        backgroundColor = Color.HSVToColor(floatArrayOf(hue, saturation, 0.2f))
        tileColor = Color.HSVToColor(floatArrayOf(hue, saturation + .6f, value))
    }

    fun neoLightTheme(hue: Float, saturation: Float, value: Float) {
        backgroundColor = Color.HSVToColor(floatArrayOf(hue, saturation, value))
        tileColor = Color.HSVToColor(floatArrayOf(hue, saturation + .6f, value))
    }

    private fun saveData() {
        val gameTiles = gameLayout.getGameTiles()
        val jsonGame = gson!!.toJson(gameTiles)
        editor!!.putString(CURRENT_PUZZLE, jsonGame)
        editor!!.putInt(CURRENT_ROW_SIZE, rows)
        editor!!.putInt(CURRENT_COLUMN_SIZE, columns)
        editor!!.putInt(CURRENT_BACKGROUND_COLOR, columns)
        editor!!.putInt(CURRENT_TILE_COLOR, tileColor)
        editor!!.apply()
    }

    companion object {
        const val SHARED_PREFS = "sharedPrefs"
        const val CURRENT_PUZZLE = "puzzle"
        private val TAG = MainActivity::class.java.name
        private const val maxGameWidth = 360
        private const val maxGameHeight = 540
        var editor: SharedPreferences.Editor? = null
        fun clearSharedPrefs() {
            editor!!.clear()
            editor!!.apply()
        }
    }
}