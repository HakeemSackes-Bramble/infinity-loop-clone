package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.tileRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.GameLayout;
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.Tile;

/**
 * Created by hakeemsackes-bramble on 10/15/17.
 */

class TileViewHolder extends RecyclerView.ViewHolder {
    private GameLayout mGameLayout;

    public TileViewHolder(View itemView, GameLayout gameLayout) {
        super(itemView);
        this.mGameLayout = gameLayout;
    }

    public void bind(final int position) {
        //here i'll run color change animations
        final Tile tile = mGameLayout.getGameTiles().get(position);
        ((TileView) itemView).setTileId(tile);
        if (tile.getTileType() == 3) {
            tile.setCorrectOrientation((tile.getCorrectOrientation() % 2));
            tile.setOrientation((tile.getOrientation() % 2));
        }
        if (tile.getTileType() == 5 || tile.getTileType() == 0 || tile.getOrientation() == tile.getCorrectOrientation()) {
            mGameLayout.addCorrectedTile(position);
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGameLayout.getGameTiles().size() != mGameLayout.getCorrectlyOrientedTileSize()) {
                    mGameLayout.runCheckTileAlignmentListener(itemView, tile, position);
                    if (mGameLayout.getGameTiles().size() == mGameLayout.getCorrectlyOrientedTileSize()) {
                        mGameLayout.runAllTilesAlignedListener();
                    }
                } else {
                    mGameLayout.runAllTilesAlignedListener();
                }
            }
        });
    }


}
