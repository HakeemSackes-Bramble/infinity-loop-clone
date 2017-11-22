package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects;

/**
 * Created by hakeemsackes-bramble on 10/15/17.
 */

public class Tile {
    private int orientation;
    private int tileType;
    private int correctOrientation;
    int[] cornerColors;

    public Tile(int orientation, int tileType, int correctOrientation) {
        this.orientation = orientation;
        this.tileType = tileType;
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

    public int getCorrectOrientation() {
        return correctOrientation;
    }

    public void setCorrectOrientation(int correctOrientation) {
        this.correctOrientation = correctOrientation;
    }

    public int[] getCornerColors() {
        return cornerColors;
    }

    public void setCornerColors(int[] cornerColors) {
        this.cornerColors = cornerColors;
    }
}
