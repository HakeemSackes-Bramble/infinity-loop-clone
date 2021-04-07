package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.listeners.TileAlignmentListener;

/**
 * Created by hakeemsackes-bramble on 10/15/17.
 */

public class GameLayout {

    private static final String TAG = GameLayout.class.getName();
    private TileAlignmentListener tileAlignmentListener;
    private int tileColor;
    private int rows;
    private int columns;
    private Random rand = new Random();
    private ArrayList<Tile> gameTiles;
    private HashSet<Integer> correctlyOriented = new HashSet<>();
    private HashMap<Integer, String[]> tilePossibilities = new TileTypes().getTiles();
    private TileAlignmentListener listener;
    private ColorList colorList;
    int num = 0;

    public GameLayout(int rows, int columns, int tileColor, TileAlignmentListener tileAlignmentListener) {
        colorList = new ColorList(columns, rows);
        colorList.createColorList();
        this.tileAlignmentListener = tileAlignmentListener;
        this.tileColor = tileColor;
        this.rows = rows;
        this.columns = columns;
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
            /**
             * new Tile(orientation, tileType, correctOrientation, prongOrientations)
             */
            Tile tile = new Tile(rand.nextInt(4), tileType, getOrientationOption(tileType, i, top, lefty), tilePossibilities.get(tileType));
            tile.setTilePositions(checkPosition(i));
            int correctOrientation = getOrientationOption(tileType, i, top, lefty);
            tile.setCorrectOrientation(correctOrientation);
            tile.setOrientation(rand.nextInt(4));
            if (i % columns == (columns - 1)) {
                num++;
            }
            gameTiles.add(tile);
        }
        for (int i = 0; i < gameTiles.size(); i++) {
            tileAlignmentListener.checkTileAlignment(this, gameTiles.get(i), i, gameTiles.get(i).getOrientation(), gameTiles.get(i).getOrientation());
        }
        num = 0;
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
            if (rightEdge) {
                return rand.nextInt(2) + 1;
            }
            if (bottomEdge) {
                return rand.nextInt(2) * 2 + 1;
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
        colorList.createNewColorList(rows, columns);
        createGameTiles();
    }

    private HashSet<TilePositions> checkPosition(int i) {
        HashSet<TilePositions> positions = new HashSet<>();
        boolean topEdge = i < columns;
        boolean bottomEdge = i >= ((rows * columns) - columns);
        boolean rightEdge = i % columns == columns - 1;
        boolean leftEdge = i % columns == 0;

        if (topEdge)
            positions.add(TilePositions.TOP_EDGE);
        if (bottomEdge)
            positions.add(TilePositions.BOTTOM_EDGE);
        if (rightEdge)
            positions.add(TilePositions.RIGHT_EDGE);
        if (leftEdge)
            positions.add(TilePositions.LEFT_EDGE);
        if (positions.size() == 0)
            positions.add(TilePositions.CENTER);
        return positions;
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

    public int getTileColor() {
        return tileColor;
    }

    public void runAllTilesAlignedListener() {
        listener.onAllTilesAligned();
    }

    public void runCheckTileAlignmentListener(Tile tile, int position) {
        listener.checkTileAlignment(this, tile, position, tile.getOrientation(), (tile.getOrientation() + 1) % 4);
    }
}
