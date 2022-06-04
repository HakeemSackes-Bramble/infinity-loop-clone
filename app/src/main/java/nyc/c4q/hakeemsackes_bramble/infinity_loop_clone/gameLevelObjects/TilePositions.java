package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects;

import java.util.HashMap;

/**
 * Created by hakeemsackes-bramble on 1/15/18.
 * <p>
 * enum order matters!!!!!
 * TOP_EDGE,
 * RIGHT_EDGE,
 * BOTTOM_EDGE,
 * LEFT_EDGE,
 * CENTER
 * WARNING:::::: DO NOT CHANGE ORDER!!!
 */
public enum TilePositions {
    TOP_EDGE(0),
    RIGHT_EDGE(1),
    BOTTOM_EDGE(2),
    LEFT_EDGE(3),
    CENTER(4),
    NULL_POS(-1);

    private int value;
    private static HashMap<Integer, TilePositions> tilePositionsFromValue = new HashMap<>();
    static {
        for (TilePositions pos: TilePositions.values()){
            tilePositionsFromValue.put(pos.value, pos);
        }
    }

    TilePositions(int i) {
        this.value = i;
    }

    public static TilePositions getTilePositionsFromValue(int i) {
        return tilePositionsFromValue.get(i);
    }

}
