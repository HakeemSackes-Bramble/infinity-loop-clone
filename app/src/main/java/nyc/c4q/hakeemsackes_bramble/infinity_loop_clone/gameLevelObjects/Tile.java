package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects;

import java.util.HashSet;

/**
 * Created by hakeemsackes-bramble on 10/15/17.
 */

public class Tile {
    private int orientation;
    private int tileType;
    private int correctOrientation;
    int[] cornerColors;
    boolean[] alignment = new boolean[4];
    private String[] prongOrientations = new String[5];
    private HashSet<TilePositions> tilePositions;
    private int prongCount;

    public String getStringOrientation() {
        return stringOrientation;
    }

    private String stringOrientation;

    public Tile() {
    }

    public Tile(int orientation, int tileType, int correctOrientation, String[] prongOrientations) {
        this.orientation = orientation;
        this.prongOrientations = prongOrientations;
        stringOrientation = prongOrientations[orientation + 1];
        this.tileType = tileType;
        this.correctOrientation = correctOrientation;
        this.prongCount = stringOrientation.replaceAll("0", "").length();
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

    public int[] getCornerColors() {
        return cornerColors;
    }

    public void setCornerColors(int[] cornerColors) {
        this.cornerColors = cornerColors;
    }

    public HashSet<TilePositions> getTilePositions() {
        return tilePositions;
    }

    public void setTilePositions(HashSet<TilePositions> tilePositions) {
        this.tilePositions = tilePositions;
    }

    public void makeAlignmentTrue(int pos) {
        alignment[pos] = true;
    }

    public void makeAlignmentFalse(int pos) {
        alignment[pos] = false;
    }

    public boolean isProperlyAligned() {
        boolean isAligned =
                alignment[0]
                && alignment[1]
                && alignment[2]
                && alignment[3];

        return isAligned;
    }

    public boolean[] getAlignment() {
        return alignment;
    }

    public void setTileType(int tileType) {
        this.tileType = tileType;
    }

    public void setAlignment(boolean[] alignment) {
        this.alignment = alignment;
    }

    public String[] getProngOrientations() {
        return prongOrientations;
    }

    public void setProngOrientations(String[] prongOrientations) {
        this.prongOrientations = prongOrientations;
    }

    public int getProngCount() {
        return prongCount;
    }

    public void setProngCount(int prongCount) {
        this.prongCount = prongCount;
    }

    public void setStringOrientation(String stringOrientation) {
        this.stringOrientation = stringOrientation;
    }
}
