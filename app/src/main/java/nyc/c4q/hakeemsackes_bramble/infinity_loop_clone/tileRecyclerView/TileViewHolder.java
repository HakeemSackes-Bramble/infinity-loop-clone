package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.tileRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.GameLayout;
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.Tile;

import static android.content.ContentValues.TAG;

/**
 * Created by hakeemsackes-bramble on 10/15/17.
 */

class TileViewHolder extends RecyclerView.ViewHolder {
    private GameLayout mGameLayout;

    public TileViewHolder(View itemView, GameLayout gameLayout) {
        super(itemView);
        this.mGameLayout = gameLayout;
    }

    public void bind(int position) {
        final Tile tile = mGameLayout.getGameTiles().get(position);
        ((TileView) itemView).setTileId(tile);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tile.setOrientation(((tile.getOrientation() + 1) % 4));
                itemView.setRotation(tile.getOrientation() * 90);
                Log.d(TAG, "onClick: " + tile.getOrientation() + "correct " + tile.getCorrectOrientation());
            }
        });
    }


}
