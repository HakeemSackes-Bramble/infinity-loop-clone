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
    private HashSet<Integer> correctlyOriented = new HashSet<>();
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
            gameTiles.add(new Tile(rand.nextInt(4), tileType, tileColor, correctOrientation));
        }
    }

    public ArrayList<Tile> getGameTiles() {
        return gameTiles;
    }

    private int getTileOptions(int i, @Nullable Tile top, @Nullable Tile lefty) {
        boolean isEmptyTop = true;
        boolean isEmptyLeft = true;
        boolean topEdge = i < columns;
        boolean bottomEdge = i >= ((rows * columns) - columns);
        boolean rightEdge = i % columns == columns - 1;
        boolean leftEdge = i % columns == 0;
        if (!topEdge) {
            isEmptyTop = tilePossibilities.get(top.getTileType())[top.getCorrectOrientation() + 1].charAt(2) == '0';
        }
        if (!leftEdge) {
            isEmptyLeft = tilePossibilities.get(lefty.getTileType())[lefty.getCorrectOrientation() + 1].charAt(1) == '0';
        }
        if (isEmptyTop && isEmptyLeft) {
            if (bottomEdge && rightEdge) {
                return 0;
            }
            if (rightEdge) {
                return 1;
            }
            if (bottomEdge) {
                return rand.nextInt(2);
            }
            return rand.nextInt(3);
        } else if (!isEmptyTop && isEmptyLeft) {

            if (bottomEdge && rightEdge) {
                return 1;
            }
            if (rightEdge) {
                return rand.nextInt(2) * 2 + 1;
            }
            if (bottomEdge) {
                return rand.nextInt(2) + 1;
            }
            return rand.nextInt(4) + 1;
        } else if (isEmptyTop) {
            if (bottomEdge && rightEdge) {
                return 1;
            }
            if (bottomEdge) {
                return rand.nextInt(2) * 2 + 1;
            }
            if (rightEdge) {
                return rand.nextInt(2) + 1;
            }
            return rand.nextInt(4) + 1;
        } else {
            if (bottomEdge && rightEdge) {
                return 2;
            }
            if (rightEdge || bottomEdge) {
                return rand.nextInt(2) * 2 + 2;
            }
            return (rand.nextInt(3) + 2) % 4 + 2;
        }
    }

    private int getOrientationOption(int tileType, int i, Tile top, Tile lefty) {
        int prongsM1 = Integer.valueOf(tilePossibilities.get(tileType)[0]) - 1;
        int maxOption;
        int shifter;
        boolean topEdge = i < columns;
        boolean bottomEdge = i >= ((rows * columns) - columns);
        boolean rightEdge = i % columns == columns - 1;
        boolean leftEdge = i % columns == 0;
        boolean isEmptyTop = true;
        boolean isEmptyLeft = true;
        if (!topEdge) {
            isEmptyTop = tilePossibilities.get(top.getTileType())[top.getCorrectOrientation() + 1].charAt(2) == '0';
        }
        if (!leftEdge) {
            isEmptyLeft = tilePossibilities.get(lefty.getTileType())[lefty.getCorrectOrientation() + 1].charAt(1) == '0';
        }
        if (!isEmptyLeft && !isEmptyTop) {
            if (rightEdge) {
                return 0;
            }
            if (topEdge) {
                return 2;
            }
            return (prongsM1 + 3) % 4;
        } else if (isEmptyLeft && !isEmptyTop) {
            if (rightEdge) {
                return 0;
            }
            return prongsM1;
        } else if (!isEmptyLeft) {
            return 3;
        } else {
            maxOption = 2;
            shifter = 1;
            if (rightEdge) {
                return 2;
            }
            if (bottomEdge) {
                return prongsM1 + 1;
            }
        }
        Log.d(TAG, "getOrientationOptions: " + prongsM1 + " max options " + maxOption);
        if (prongsM1 < 0) {
            return 1;
        }
        if (maxOption <= prongsM1) {
            maxOption = prongsM1 + 1;
        }
        int f = (rand.nextInt(maxOption - prongsM1) + shifter + prongsM1) % 4;
        Log.d(TAG, "getOrientationOptions:  tiletype, orientation choice " + f);
        return f;
    }

    public void resetGame(int rows, int columns, int tileColor) {
        this.rows = rows;
        this.columns = columns;
        this.tileColor = tileColor;
        gameTiles.clear();
        createGameTiles();
    }

    public void addCorrectedTile(int position) {
        correctlyOriented.add(position);
    }

    public void removeWrongTile(int position) {
        correctlyOriented.remove(position);
    }


}
