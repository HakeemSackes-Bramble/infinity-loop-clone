package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.listeners.TileAlignmentListener;

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
    private TileAlignmentListener listener;
    private int backgroundColor;
    private ColorList colorList;
    private HashMap<Integer, Integer> colorSlices = new HashMap<>();
    int num = 0;

    private enum SquareTileTypes {

        ZERO_PRONG(0),
        ONE_PRONG(1),
        TWO_PRONG_CURVE(2),
        TWO_PRONG_STRAIGHT(3),
        THREE_PRONG(4),
        fourProng(5);
        private int tileType;

        SquareTileTypes(int squareTiletype) {
            this.tileType = squareTiletype;
        }

        public int getTileType() {
            return tileType;
        }
    }

    public GameLayout(int rows, int columns, int tileColor, int backgroundColor) {
        colorList = new ColorList(columns, rows);
        colorList.createColorList();
        this.tileColor = tileColor;
        this.backgroundColor = backgroundColor;
        this.rows = rows;
        this.columns = columns;
        colorSlices = colorList.getColors();
        gameTiles = new ArrayList<>();
    }

    public void createGameTiles() {
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
            Tile tile = new Tile(rand.nextInt(4), tileType, correctOrientation);
            addColorCornersToTile(tile, i);
            if (i % columns == (columns - 1)) {
                num++;
            }
            gameTiles.add(tile);
        }
        num = 0;
    }

    private void addColorCornersToTile(Tile tile, int pos) {
        int[] colors = new int[4];
        int col = 0;
        int org;
        Log.d(TAG, "addColorCornersToTile: tile " + pos);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                int abc = (i * 2) + j;
                int def = pos + col + j + num;
                colors[abc] = colorSlices.get(def);
                Log.d(TAG, "addColorCornersToTile: " + (i * 2 + j) + " color " + colorSlices.get(pos + num + j + col));
            }
            col = columns + 1;
        }
        org = colors[2];
        colors[2] = colors[3];
        colors[3] = org;
        tile.setCornerColors(colors);
    }


    public ArrayList<Tile> getGameTiles() {
        return gameTiles;
    }

    private int getTileOptions(int i, Tile top, Tile lefty) {
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
                return SquareTileTypes.ZERO_PRONG.getTileType();
            }
            if (rightEdge) {
                return SquareTileTypes.ONE_PRONG.getTileType();
            }
            if (bottomEdge) {
                return rand.nextInt(2);
            }
            return rand.nextInt(3);
        } else if (!isEmptyTop && isEmptyLeft) {

            if (bottomEdge && rightEdge) {
                return SquareTileTypes.ONE_PRONG.getTileType();
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
                return SquareTileTypes.ONE_PRONG.getTileType();
            }
            if (rightEdge) {
                return rand.nextInt(2) + 1;
            }
            if (bottomEdge) {
                return rand.nextInt(2) * 2 + 1;
            }
            return rand.nextInt(4) + 1;
        } else {
            if (bottomEdge && rightEdge) {
                return SquareTileTypes.TWO_PRONG_CURVE.getTileType();
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
            return (prongsM1 - 1) % 4;
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
        return (rand.nextInt(maxOption - prongsM1) + shifter + prongsM1) % 4;

    }

    public void resetGame(int rows, int columns, int tileColor) {
        this.rows = rows;
        this.columns = columns;
        this.tileColor = tileColor;
        gameTiles.clear();
        correctlyOriented.clear();
        colorSlices.clear();
        colorList.createNewColorList(rows, columns);
        colorSlices = colorList.getColors();
        createGameTiles();
    }

    public void addCorrectedTile(int position) {
        correctlyOriented.add(position);
    }

    public void removeWrongTile(int position) {
        correctlyOriented.remove(position);
    }

    public int getCorrectlyOrientedTileSize() {
        return correctlyOriented.size();
    }

    public void setListener(TileAlignmentListener listener) {
        this.listener = listener;
    }

    public void setTileColor(int tileColor) {
        this.tileColor = tileColor;
    }

    public int getTileColor() {
        return tileColor;
    }

    public void runAllTilesAlignedListener() {
        listener.onAllTilesAligned();
    }

    public void runCheckTileAlignmentListener(View itemView, Tile tile, int position) {
        listener.checkTileAlignment(this, itemView, tile, position);
    }

    public ColorList getColorList() {
        return colorList;
    }

    public void setColorList(ColorList colorList) {
        this.colorList = colorList;
    }

}
