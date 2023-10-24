package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects;

import java.util.HashMap;

/**
 * Created by hakeemsackes-bramble on 10/17/17.
 */

public class TileTypes {

    /*
     * this section will be used to store different types for future configurations
     * TODO: create method that adds square tile orientations to HashMap programmatically
     */
    private HashMap<Integer, String[]> tiles = new HashMap<>();
    private HashMap<Integer, String[]> tilePaths = new HashMap<>();

    public TileTypes() {
        addSquareTilePossibilities();
    }

    private void addSquareTilePossibilities() {
        tiles.put(0, new String[]{"0", "0000", "0000", "0000", "0000"});
        tiles.put(1, new String[]{"1", "1000", "0100", "0010", "0001"});
        tiles.put(2, new String[]{"2", "1001", "1100", "0110", "0011"});
        tiles.put(3, new String[]{"3", "1010", "0101", "1010", "0101"});
        tiles.put(4, new String[]{"3", "1011", "1101", "1110", "0111"});
        tiles.put(5, new String[]{"4", "1111", "1111", "1111", "1111"});
    }

    public HashMap<Integer, String[]> getTiles() {
        return tiles;
    }
}

