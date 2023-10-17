package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.listeners

import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.GameLayout
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.Tile

/**
 * Created by hakeemsackes-bramble on 10/23/17.
 */
interface TileAlignmentListener {
    fun checkTileAlignment(gameLayout: GameLayout, tile: Tile, position: Int)
    fun checkPathAlignment()
    fun onAllTilesAligned(alignedTiles: Boolean)
}