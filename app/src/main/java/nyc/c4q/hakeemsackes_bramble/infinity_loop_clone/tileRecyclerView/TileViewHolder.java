package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.tileRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.custom_views.TileView;
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.GameLayout;
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.Tile;

/**
 * Created by hakeemsackes-bramble on 10/15/17.
 */

class TileViewHolder extends RecyclerView.ViewHolder {
    private GameLayout mGameLayout;
    private static final String TAG = TileViewHolder.class.getName();

    public TileViewHolder(View itemView, GameLayout gameLayout) {
        super(itemView);
        this.mGameLayout = gameLayout;
    }

    public void bind(final int position) {

        final Tile tile = mGameLayout.getGameTiles().get(position);
        ((TileView) itemView).implementTileValues(tile);
        mGameLayout.runCheckTileAlignmentListener(tile, position);
        mGameLayout.addCorrectedTile(position, tile.isProperlyAligned());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * here i'll run color change animations
                 */
                if (!mGameLayout.hasAllTilesAligned()) {
                    ((TileView) itemView).rotateView(tile.getOrientation(), (tile.getOrientation() + 1) % 4);
                    tile.assignOrientation((tile.getOrientation() + 1) % 4);
                    mGameLayout.runCheckTileAlignmentListener(tile, position);
                }
            }
        });


    }
}
