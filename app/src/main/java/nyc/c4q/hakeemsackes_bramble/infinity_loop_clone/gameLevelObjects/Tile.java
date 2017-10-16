package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects;

/**
 * Created by hakeemsackes-bramble on 10/15/17.
 */

public class Tile {
    private short orientation;
    private short tileType;
    private int tileColor;
    private short CorrectOrientation;

    public Tile(short orientation, short tileType, int color, short correctOrientation) {
        this.orientation = orientation;
        this.tileType = tileType;
        this.tileColor = color;
        CorrectOrientation = correctOrientation;
    }


    public short getOrientation() {
        return orientation;
    }

    public short getTileType() {
        return tileType;
    }

    public int getTileColor() {
        return tileColor;
    }

    public short getCorrectOrientation() {
        return CorrectOrientation;
    }

}
