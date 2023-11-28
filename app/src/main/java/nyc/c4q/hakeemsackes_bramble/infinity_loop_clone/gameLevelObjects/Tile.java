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
    private int tilePathId;
    private int prongCount;

    Tile(int tileNumber, int orientation, int tileType, int correctOrientation, String[] prongOrientations) {
        super(tileNumber, prongOrientations[1].replace("0", "").length());
        connectedProngs = new HashSet<>();
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

    public void assignStringOrientation(int orient) {
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
    }

    public boolean isProperlyAligned() {
        return alignment[0] && alignment[1] && alignment[2] && alignment[3];
    }

    public boolean thereIsZeroAlignment() {
        return !alignment[0] && !alignment[1] && !alignment[2] && !alignment[3];
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

    public void setTilePathId(int uuid) {
        this.tilePathId = uuid;
    }

    public int getTilePathId() {
        return tilePathId;
    }

    public boolean[] getAlignment() {
        return alignment;
    }

    public int getProngCount() {
        return prongCount;
    }

    public void setProngCount(int prongCount) {
        this.prongCount = prongCount;
    }

}
