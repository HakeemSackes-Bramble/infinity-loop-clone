package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.tileRecyclerView

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.custom_views.TileView
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.GameLayout

/**
 * Created by hakeemsackes-bramble on 10/15/17.
 */
class TileAdapter(var gameLayout: GameLayout?, var tileSize: Int) :
    RecyclerView.Adapter<TileViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TileViewHolder {
        return TileViewHolder(
            TileView(parent.context, gameLayout.getTileColor(), tileSize),
            gameLayout
        )
    }

    override fun onBindViewHolder(holder: TileViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return gameLayout.getGameTiles().size
    }
}