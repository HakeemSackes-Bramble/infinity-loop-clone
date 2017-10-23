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

        if ((topEdge || bottomEdge) && (rightEdge || leftEdge)) { // is corner
            maxType = 3;
        } else if (!center) { //is edge
            maxType = 5;
        }
        if (i >= columns) {
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
            if (rightEdge) {
                return rand.nextInt(2) * 2 + 2;
            }
            if (bottomEdge) {
                return rand.nextInt(2) * 2 + 2;
            }
            return (rand.nextInt(3) + 2) % 4 + 2;
        }

//        int t;
//        Log.d(TAG, "getTileOptions: i " + i + " :max " + maxType + "\n min " + minType + " empty top " + isEmptyTop + "\n");
//        t = rand.nextInt(maxType - minType) + minType;
//        if (!isEmptyLeft && !isEmptyTop && t == 3) {
//            t++;
//        }
//        Log.d(TAG, "getTileOptions: pos " + i + " :choice " + t + " min type " + minType);
//        return t;
    }

    private int getOrientationOption(int tileType, int i, Tile top, Tile lefty) {
        int prongsM1 = Integer.valueOf(tilePossibilities.get(tileType)[0]) - 1;
        int maxOption;
        int shifter = 0;
        boolean topEdge = i < columns;
        boolean bottomEdge = i >= ((rows * columns) - columns);
        boolean rightEdge = i % columns == columns - 1;
        boolean leftEdge = i % columns == 0;
        boolean center = !topEdge && !bottomEdge && !rightEdge && !leftEdge;
        // assume max prongsM1

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

        if ((topEdge || bottomEdge) && (rightEdge || leftEdge)) { // is corner
            maxOption = 2;
            if (rightEdge && bottomEdge) { // right edge
                shifter = 3;
            } else if (bottomEdge && leftEdge) { //if bottom edge
                shifter = 0;
            } else if (rightEdge && topEdge) { // if left edge
                shifter = 2;
            } else if (topEdge && leftEdge) {  // top edge
                shifter = 1;
            }
        } else if (center) {
            maxOption = 4;
        } else { //is edge
            maxOption = 3;
            if (rightEdge) { // right edge
                shifter = 2;
            } else if (bottomEdge) { //if bottom edge
                shifter = 3;
            } else if (leftEdge) { // if left edge
                shifter = 0;
            } else if (topEdge) {  // top edge
                shifter = 1;
            }
        }

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

//        if (i >= columns) {
//            isEmptyTop = tilePossibilities.get(top.getTileType())[top.getCorrectOrientation() + 1].charAt(2) == '0';
//            if (isEmptyTop && maxOption > prongsM1) {
//                maxOption--;
//            } else if (!isEmptyTop) {
//
//            }
//        }
//        if (!leftEdge) {
//            isEmptyLeft = tilePossibilities.get(lefty.getTileType())[lefty.getCorrectOrientation() + 1].charAt(1) == '0';
//            if (isEmptyLeft && maxOption > prongsM1) {
//                maxOption--;
//            } else if (!isEmptyLeft) {
//                if (topEdge) {
//
//                    maxOption = prongsM1 + 1;// top edge
//                    shifter = 3 - prongsM1;
//                }
//                if (center) {
//                    maxOption = 2 * prongsM1 + 1;
//                    shifter = 3 - shifter;
//                }
//            }
//
//        }

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

    public void resetGame() {
        gameTiles.clear();
        createGameTiles();
    }

    /* brainstorming
    maxOption -- maximum number of possible orientations of given position
    shifter -- rotates selection to conform to edges;
    prongsM1 -- number of open edges minus one. used to limit orientations based on prongs
    int f = (rand.nextInt(maxOption - prongsM1) + shifter + prongsM1) % 4;
    istopempty |

    isleftempy |

    first if left and top is empty choose electable tile(corresponding to position, edge corner center)
    if top 0 is not
    if left is not empty then choose from electable tile with prong facing left
    istop
    */

}
