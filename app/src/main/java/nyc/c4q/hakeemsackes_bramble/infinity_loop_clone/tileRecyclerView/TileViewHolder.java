package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.tileRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.custom_views.TileView;
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.GameOneLayout;
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.Tile;

/**
 * Created by hakeemsackes-bramble on 10/15/17.
 */

class TileViewHolder extends RecyclerView.ViewHolder {
    private GameOneLayout mGameOneLayout;
    private static final String TAG = TileViewHolder.class.getName();

    public TileViewHolder(View itemView, GameOneLayout gameOneLayout) {
        super(itemView);
        this.mGameOneLayout = gameOneLayout;
    }

    public void bind(final int position) {

        final Tile tile = mGameOneLayout.getGameTiles().get(position);
        ((TileView) itemView).implementTileValues(tile);
        mGameOneLayout.runCheckTileAlignmentListener(tile, position);
        mGameOneLayout.addCorrectedTile(position, tile.isProperlyAligned());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * here i'll run color change animations
                 */
                if (!mGameOneLayout.hasAllTilesAligned()) {
                    ((TileView) itemView).rotateView(tile.getOrientation(), (tile.getOrientation() + 1) % 4);
                    tile.assignOrientation((tile.getOrientation() + 1) % 4);
                    mGameOneLayout.runCheckTileAlignmentListener(tile, position);
                }
            }
        });


    }
}
