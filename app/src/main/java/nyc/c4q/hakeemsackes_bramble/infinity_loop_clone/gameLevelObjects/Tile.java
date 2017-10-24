package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects;

/**
 * Created by hakeemsackes-bramble on 10/15/17.
 */

public class Tile {
    private int orientation;
    private int tileType;
    private int tileColor;

    public void setCorrectOrientation(int correctOrientation) {
        this.correctOrientation = correctOrientation;
    }

    private int correctOrientation;

    public Tile(int orientation, int tileType, int color, int correctOrientation) {
        this.orientation = orientation;
        this.tileType = tileType;
        this.tileColor = color;
        this.correctOrientation = correctOrientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getOrientation() {
        return orientation;
    }

    public int getTileType() {
        return tileType;
    }
    public int getTileColor() {
        return tileColor;
    }

    public int getCorrectOrientation() {
        return correctOrientation;
    }

}
