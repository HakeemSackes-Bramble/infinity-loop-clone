package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects;

import java.util.HashSet;

/**
 * Created by hakeemsackes-bramble on 10/15/17.
 */

public class Tile {
    private int pathId;
    private int orientation;
    private int tileType;
    private int correctOrientation;
    private boolean[] alignment = new boolean[4];
    private String[] prongOrientations;
    private HashSet<SquareTilePositions> tilePositions;
    private String stringOrientation;

    Tile(int orientation, int tileType, int correctOrientation, String[] prongOrientations) {
        this.orientation = orientation;
        this.prongOrientations = prongOrientations;
        this.stringOrientation = prongOrientations[orientation + 1];
        this.tileType = tileType;
        this.correctOrientation = correctOrientation;
    }

    public String getStringOrientation() {
        return stringOrientation;
    }

    public String[] getProngOrientations() {
        return prongOrientations;
    }


    public void assignOrientation(int orientation) {
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

    public HashSet<SquareTilePositions> getTilePositions() {
        return tilePositions;
    }

    void setTilePositions(HashSet<SquareTilePositions> tilePositions) {
        this.tilePositions = tilePositions;
    }

    public void isProngConnected(int pos, boolean checkConnection) {
        alignment[pos] = checkConnection;
    }

    public boolean isProperlyAligned() {
        return alignment[0] && alignment[1] && alignment[2] && alignment[3];
    }

    public int getPathId() {
        return pathId;
    }

    public void setPathId(int pathId) {
        this.pathId = pathId;
    }
}
