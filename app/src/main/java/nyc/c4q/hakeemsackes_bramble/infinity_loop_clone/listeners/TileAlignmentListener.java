package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.listeners;

import java.util.ArrayList;
import java.util.HashMap;

import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.Tile;

/**
 * Created by hakeemsackes-bramble on 10/23/17.
 */

public interface TileAlignmentListener {

    void onCheckTileAlignment(HashMap<Integer, String[]> tilePossibilities, ArrayList<Tile> gameLayout, int position);

    void onAllTilesAligned();
}
