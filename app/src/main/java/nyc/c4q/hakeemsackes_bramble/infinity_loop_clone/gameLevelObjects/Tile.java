package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects;

import java.util.HashSet;

import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.loopDetectionSystem.TileNode;

/**
 * Created by hakeemsackes-bramble on 10/15/17.
 */

public class Tile extends TileNode {
    private int pathId;
    private int orientation;
    private int tileType;
    private int correctOrientation;
    private boolean[] alignment = new boolean[4];

    private String[] prongOrientations;
    private HashSet<SquareTilePositions> tilePositions;

    private HashSet<Integer> connectedProngs = new HashSet<>();
    private String stringOrientation;
    private String correctStringOrientation;

    Tile(int orientation, int tileType, int correctOrientation, String[] prongOrientations) {
        super();
        this.prongOrientations = prongOrientations;
        this.tileType = tileType;
        this.correctOrientation = correctOrientation;
        this.orientation = orientation;
        this.correctStringOrientation = prongOrientations[correctOrientation + 1];
        assignStringOrientation(orientation);
    }

    public String getStringOrientation() {
        return stringOrientation;
    }

    public String[] getProngOrientations() {
        return prongOrientations;
    }


    public void assignOrientation(int orientation) {
        this.orientation = orientation;
        assignStringOrientation(orientation);
    }

    public void assignStringOrientation(int orient){
        stringOrientation = prongOrientations[orient + 1];
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
        if (checkConnection && stringOrientation.charAt(pos) == '1') {
            connectedProngs.add(pos);
        } else {
            connectedProngs.remove(pos);
        }
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

    public String getCorrectStringOrientation() {
        return correctStringOrientation;
    }
}
