package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects;

import java.util.HashSet;

/**
 * Created by hakeemsackes-bramble on 10/15/17.
 */

public class Tile {
    private int orientation;
    private int tileType;
    private int correctOrientation;
    private boolean[] alignment = new boolean[4];
    private String[] prongOrientations;
    private String uuid;
    private HashSet<TilePositions> tilePositions;
    private String stringOrientation;

    Tile(int orientation, int tileType, int correctOrientation, String[] prongOrientations) {
        this.orientation = orientation;
        this.prongOrientations = prongOrientations;
        stringOrientation = prongOrientations[orientation + 1];
        this.tileType = tileType;
        this.correctOrientation = correctOrientation;
    }

    public String getStringOrientation() {
        return stringOrientation;
    }

    public String[] getProngOrientations() {
        return prongOrientations;
    }

    public void setProngOrientations(String[] prongOrientations) {
        this.prongOrientations = prongOrientations;
        this.stringOrientation = prongOrientations[orientation + 1];
    }

    public void setStringOrientation(String stringOrientation) {
        this.stringOrientation = stringOrientation;
    }

    public void setTileType(int tileType) {
        this.tileType = tileType;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
        stringOrientation = prongOrientations[orientation + 1];
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

    public HashSet<TilePositions> getTilePositions() {
        return tilePositions;
    }

    void setTilePositions(HashSet<TilePositions> tilePositions) {
        this.tilePositions = tilePositions;
    }

    public void isProngConnected(int pos, boolean checkConnection) {
        alignment[pos] = checkConnection;
    }

    public void makeAlignmentFalse(int pos) {
        alignment[pos] = false;
    }

    public boolean isProperlyAligned() {
        return alignment[0] && alignment[1] && alignment[2] && alignment[3];
    }
}
