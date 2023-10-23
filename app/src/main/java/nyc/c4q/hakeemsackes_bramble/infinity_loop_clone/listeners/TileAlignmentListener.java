package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.listeners;

import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.GameLayout;
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.Tile;

/**
 * Created by hakeemsackes-bramble on 10/23/17.
 */

public interface TileAlignmentListener {
    void checkTileAlignment(GameLayout gameLayout, Tile tile, int position);
    void checkPathAlignment(int pathId, GameLayout gameLayout);

    void onPathComplete();

    void onAllTilesAligned(boolean alignedTiles);
}
