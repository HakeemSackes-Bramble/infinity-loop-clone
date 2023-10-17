package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects

/**
 * Created by hakeemsackes-bramble on 10/17/17.
 */
class TileTypes (){
    /*
     * this section will be used to store different types for future configurations
     * TODO: create method that adds square tile orientations to HashMap programmatically
     */
    var tiles = HashMap<Int, Array<String>>()
    private var tilePaths = HashMap<Int, Array<String>>()

    init {
        addSquareTilePossibilities()
        addSquarePathsForTiles()
    }

    private fun addSquareTilePossibilities() {
        tiles[0] = arrayOf("0", "0000", "0000", "0000", "0000")
        tiles[1] = arrayOf("1", "1000", "0100", "0010", "0001")
        tiles[2] = arrayOf("2", "1001", "1100", "0110", "0011")
        tiles[3] = arrayOf("3", "1010", "0101", "1010", "0101")
        tiles[4] = arrayOf("3", "1011", "1101", "1110", "0111")
        tiles[5] = arrayOf("4", "1111", "1111", "1111", "1111")
    }

    private fun addSquarePathsForTiles() {
        tilePaths[0] = arrayOf("0")
        tilePaths[1] = arrayOf("0")
        tilePaths[2] = arrayOf("1", "03")
        tilePaths[3] = arrayOf("1", "02")
        tilePaths[4] = arrayOf("3", "03", "02", "23")
        tilePaths[5] = arrayOf("2", "03", "12")
    }
}