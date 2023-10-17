package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.MainActivity
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.listeners.TileAlignmentListener
import java.util.Random

/**
 * Created by hakeemsackes-bramble on 10/15/17.
 */
class GameLayout(
    private var rows: Int,
    private var columns: Int,
    val tileColor: Int,
    sharedPreferences: SharedPreferences
) {
    private val gson: Gson
    private val rand = Random()
    public var gameTiles: ArrayList<Tile>
    private val correctlyOriented = HashSet<Int>()
    private val tilePossibilities = TileTypes().tiles
    private var listener: TileAlignmentListener? = null
    var num = 0
    private var allTilesAreAligned = false
    var mSharedPreferences: SharedPreferences

    init {
        gameTiles = ArrayList()
        gson = Gson()
        mSharedPreferences = sharedPreferences
    }

    fun createGameTiles() {
        for (i in 0 until rows * columns) {
            val positions = checkPosition(i)
            var correctOrientation: Int
            val typeOfPosition = positionType(i, positions)
            val tileType = getTileOptions(typeOfPosition)
            correctOrientation = getOrientationOption(tileType, typeOfPosition)
            val possibilities: Array<String> = tilePossibilities[tileType]!!
            val tile = Tile(
                rand.nextInt(4), tileType, correctOrientation, possibilities
            )
            tile.correctOrientation = correctOrientation
            tile.orientation = rand.nextInt(4)
            if (i % columns == columns - 1) {
                num++
            }
            gameTiles.add(tile)
        }
        num = 0
    }

    private fun loadTileData() {
        val currPuzzle = mSharedPreferences!!.getString(MainActivity.Companion.CURRENT_PUZZLE, "")
        val listType = object : TypeToken<ArrayList<Tile>>() {}.type
        val list = gson.fromJson<ArrayList<Tile>>(currPuzzle, listType)
        gameTiles = list
        mSharedPreferences!!.edit().clear().apply()
    }

    /**
     * This method returns a valid tile given its position and relation to other existing tiles.
     *
     *
     *
     * @return
     */
    private fun getTileOptions(positionType: String): Int {
        val tileType: Int
        val prongs = positionType.replace("0", "")
        tileType = if (prongs.length > 2) {
            prongs.length + 1
        } else if (positionType.contains("101")) {
            3
        } else {
            prongs.length
        }
        return tileType
    }

    /**
     * Method returns a 4 character string that represents both the tile type and the orientation of the tile itself.
     *
     * @param i
     * @param positions
     * @return
     */
    private fun positionType(i: Int, positions: Set<SquareTilePositions?>): String {
        val type = CharArray(4)
        val surroundingTilePositions = getSurroundingTileNumbers(i)
        for (j in 3..6) {
            val k = j % 4
            if (positions.contains(SquareTilePositions.Companion.getTilePositionsFromValue(k))) {
                type[k] = '0'
            } else if (j < 5) {
                val adjacentTile = gameTiles[surroundingTilePositions[k]]
                type[k] =
                    adjacentTile!!.prongOrientations[adjacentTile.correctOrientation + 1]!![(k + 2) % 4]
            } else {
                type[k] = (rand.nextInt(2) + 48).toChar()
            }
        }
        return String(type)
    }

    /**
     * This method returns a valid tile given its position and relation to other existing tiles.
     *
     *
     *
     * @param tileType
     * @return
     */
    private fun getOrientationOption(tileType: Int, type: String): Int {
        for (j in 1..4) {
            if (tilePossibilities!![tileType]!![j] == type) {
                return j - 1
            }
        }
        return 0
    }

    fun resetGame(rows: Int, columns: Int, tileColor: Int) {
        this.rows = rows
        this.columns = columns
        gameTiles.clear()
        correctlyOriented.clear()
        createGameTiles()
        mSharedPreferences!!.edit().clear().apply()
    }

    private fun checkPosition(i: Int): HashSet<SquareTilePositions?> {
        val positions = HashSet<SquareTilePositions?>()
        val topEdge = i < columns
        val bottomEdge = i >= rows * columns - columns
        val rightEdge = i % columns == columns - 1
        val leftEdge = i % columns == 0
        if (topEdge) positions.add(SquareTilePositions.TOP_EDGE)
        if (bottomEdge) positions.add(SquareTilePositions.BOTTOM_EDGE)
        if (rightEdge) positions.add(SquareTilePositions.RIGHT_EDGE)
        if (leftEdge) positions.add(SquareTilePositions.LEFT_EDGE)
        if (positions.size == 0) positions.add(SquareTilePositions.CENTER)
        return positions
    }

    fun addCorrectedTile(position: Int, isCorrected: Boolean) {
        if (isCorrected) {
            correctlyOriented.add(position)
            allTilesAreAligned = correctlyOriented.size == gameTiles.size
        } else {
            correctlyOriented.remove(position)
        }
    }

    fun getSurroundingTileNumbers(position: Int): IntArray {
        return intArrayOf(
            position - columns,
            position + 1,
            position + columns,
            position - 1
        )
    }

    val correctlyOrientedTileSize: Int
        get() = correctlyOriented.size

    fun setListener(listener: TileAlignmentListener?) {
        this.listener = listener
    }

    fun runAllTilesAlignedListener() {
        listener!!.onAllTilesAligned(allTilesAreAligned)
    }

    fun runCheckTileAlignmentListener(tile: Tile, position: Int) {
        listener!!.checkTileAlignment(this, tile, position)
    }

    fun hasAllTilesAligned(): Boolean {
        return allTilesAreAligned
    }

    fun createGame() {
        if (mSharedPreferences!!.contains(MainActivity.Companion.CURRENT_PUZZLE)) {
            loadTileData()
        } else {
            createGameTiles()
        }
    }

    companion object {
        private val TAG = GameLayout::class.java.name
    }
}