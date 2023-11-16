package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.listeners;

import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.GameOneLayout;
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.Tile;

/**
 * Created by hakeemsackes-bramble on 10/23/17.
 */

public interface TileAlignmentListener {
    void checkTileAlignment(GameOneLayout gameOneLayout, Tile tile, int position);
    void checkPathAlignment(int pathId, GameOneLayout gameOneLayout);

    void onPathComplete();

    void onAllTilesAligned(boolean alignedTiles);
}
