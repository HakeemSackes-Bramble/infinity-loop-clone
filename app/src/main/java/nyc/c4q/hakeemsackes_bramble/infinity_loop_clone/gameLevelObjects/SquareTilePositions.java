package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects;

import java.util.HashMap;

/**
 * Created by hakeemsackes-bramble on 1/15/18.
 * <p>
 * enums numerical order matters!!!!!
 * TOP_EDGE,
 * RIGHT_EDGE,
 * BOTTOM_EDGE,
 * LEFT_EDGE,
 * CENTER
 * WARNING:::::: DO NOT CHANGE ORDER!!!
 */
public enum SquareTilePositions {
    TOP_EDGE(0),
    RIGHT_EDGE(1),
    BOTTOM_EDGE(2),
    LEFT_EDGE(3),
    CENTER(4),
    NULL_POS(-1);

    private int value;
    private static HashMap<Integer, SquareTilePositions> tilePositionsFromValue = new HashMap<>();
    static {
        for (SquareTilePositions pos: SquareTilePositions.values()){
            tilePositionsFromValue.put(pos.value, pos);
        }
    }

    SquareTilePositions(int i) {
        this.value = i;
    }

    public static SquareTilePositions getTilePositionsFromValue(int i) {
        return tilePositionsFromValue.get(i);
    }

}
