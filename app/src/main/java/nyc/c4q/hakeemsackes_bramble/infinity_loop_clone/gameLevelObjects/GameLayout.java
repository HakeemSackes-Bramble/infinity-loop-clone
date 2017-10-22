package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects;

import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import static android.content.ContentValues.TAG;

/**
 * Created by hakeemsackes-bramble on 10/15/17.
 */

public class GameLayout {
    private int tileColor;
    private int rows;
    private int columns;
    private Random rand = new Random();
    private ArrayList<Tile> gameTiles;
    private HashSet<Integer> isCorrectlyOriented;
    private HashMap<Integer, String[]> tilePossibilities = new TileTypes().getTiles();

    public GameLayout(int rows, int columns, int tileColor) {
        this.tileColor = tileColor;
        this.rows = rows;
        this.columns = columns;
        gameTiles = new ArrayList<>();
        createGameTiles();


    }

    private void createGameTiles() {
        for (int i = 0; i < rows * columns; i++) {
            Tile top = null;
            Tile lefty = null;
            if (i >= columns) {
                top = gameTiles.get(i - columns);
            }
            if (i > 0) {
                lefty = gameTiles.get(i - 1);
            }
            int tileType = getTileOptions(i, top, lefty);
            int correctOrientation = getOrientationOption(tileType, i, top, lefty);
            gameTiles.add(new Tile(correctOrientation, tileType, tileColor, correctOrientation));
        }
    }

    public ArrayList<Tile> getGameTiles() {
        return gameTiles;
    }

    private int getTileOptions(int i, @Nullable Tile top, @Nullable Tile lefty) {
        int maxType = 6;
        int minType = 0;
        boolean isEmptyTop = true;
        boolean isEmptyLeft = true;
        boolean topEdge = i < columns;
        boolean bottomEdge = i >= ((rows * columns) - columns);
        boolean rightEdge = i % columns == columns - 1;
        boolean leftEdge = i % columns == 0;
        boolean center = !(topEdge || bottomEdge || rightEdge || leftEdge);
        if (i >= columns) {
            isEmptyTop = tilePossibilities.get(top.getTileType())[top.getCorrectOrientation() + 1].charAt(2) == '0';
        }
        if (!leftEdge) {
            isEmptyLeft = tilePossibilities.get(lefty.getTileType())[lefty.getCorrectOrientation() + 1].charAt(1) == '0';
        }

        if ((topEdge || bottomEdge) && (rightEdge || leftEdge)) { // is corner
            maxType = 3;
        } else if ((topEdge || bottomEdge) || (rightEdge || leftEdge)) { //is edge
            maxType = 5;
        }


        Log.d(TAG, "getTileOptions: pos " + i + " : tiletype " + maxType + " min type " + minType);

        int t = rand.nextInt(maxType);
        Log.d(TAG, "getTileOptions: pos " + i + " : tiletype " + t + " min type " + minType);
        return t;
    }

    private int getOrientationOption(int tileType, int i, Tile top, Tile lefty) {
        int prongs = Integer.valueOf(tilePossibilities.get(tileType)[0]) - 1;
        int maxOption = 4 ;
        int minOption = 0;
        int shifter = 0;
        boolean topEdge = i < columns;
        boolean bottomEdge = i >= ((rows * columns) - columns);
        boolean rightEdge = i % columns == columns - 1;
        boolean leftEdge = i % columns == 0;
        boolean center = !topEdge && !bottomEdge && !rightEdge && !leftEdge;
        // assume max prongs

        //if right edge then orientation is  0
        //if bottom edge then orientation is  1
        //if left edge then orientation is 2
        //if top edge then orientation is  3

        //if bottom right corner then orientation is  0
        //if bottom left corner then orientation is  1
        //if top left corner then orientation is 2
        //if top right  corner then orientation is 3
        boolean isEmptyTop = true;
        boolean isEmptyLeft = true;
        if (i >= columns) {
            isEmptyTop = tilePossibilities.get(top.getTileType())[top.getCorrectOrientation() + 1].charAt(2) == '0';
            Log.d(TAG, "getOrientationOption: istopempty " + isEmptyTop);
        }
        if (i > 0) {
            isEmptyLeft = tilePossibilities.get(lefty.getTileType())[lefty.getCorrectOrientation() + 1].charAt(1) == '0';
        }
        if ((topEdge || bottomEdge) && (rightEdge || leftEdge)) { // is corner
            maxOption = 2;
            if (rightEdge && bottomEdge) { // right edge
                shifter = 3;
            }else if (bottomEdge && leftEdge) { //if bottom edge
                shifter = 0;
            }else if (rightEdge && topEdge) { // if left edge
                shifter = 2;
            }else if (topEdge && leftEdge) {  // top edge
                shifter = 1;

            }
        } else if(center){
            maxOption = 4;
        } else { //is edge
            maxOption = 3;
            if (rightEdge) { // right edge
                shifter = 2;
            }else if (bottomEdge) { //if bottom edge
                shifter = 3;
            }else if (leftEdge) { // if left edge
                shifter = 0;
            }else if (topEdge) {  // top edge
                shifter = 1;
            }
        }

        if (!isEmptyTop) {

        }
        if (isEmptyLeft) {

        }

        Log.d(TAG, "getOrientationOptions: max options " + maxOption);
        if (prongs < 0) {
            return 1;
        }
        int f = (rand.nextInt(maxOption - prongs) + shifter + prongs) % 4;
        Log.d(TAG, "getOrientationOptions:  tiletype, orientation choice " + f);
        return f;
    }

    public void resetGame() {
        gameTiles.clear();
        createGameTiles();
    }
}
