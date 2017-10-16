package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.tileRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.Tile;

/**
 * Created by hakeemsackes-bramble on 10/15/17.
 */

class TileViewHolder extends RecyclerView.ViewHolder {

    public TileViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(Tile tile) {
        ((TileView) itemView).setTileId(tile);
    }
}
