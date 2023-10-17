package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.tileRecyclerView

import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.custom_views.TileView
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.GameLayout

/**
 * Created by hakeemsackes-bramble on 10/15/17.
 */
class TileViewHolder(itemView: View?, private val mGameLayout: GameLayout?) : ViewHolder(
    itemView!!
) {
    fun bind(position: Int) {
        val tile = mGameLayout.getGameTiles()[position]
        (itemView as TileView).setTileValues(tile)
        mGameLayout!!.runCheckTileAlignmentListener(tile, position)
        mGameLayout.addCorrectedTile(position, tile!!.isProperlyAligned)
        itemView.setOnClickListener {
            /**
             * here i'll run color change animations
             */
            /**
             * here i'll run color change animations
             */
            if (!mGameLayout.hasAllTilesAligned()) {
                (itemView as TileView).rotateView(tile!!.orientation, (tile!!.orientation + 1) % 4)
                tile!!.orientation = (tile!!.orientation + 1) % 4
                mGameLayout.runCheckTileAlignmentListener(tile, position)
            }
        }
    }

    companion object {
        private val TAG = TileViewHolder::class.java.name
    }
}