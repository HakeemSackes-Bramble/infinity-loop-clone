package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

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
    int num = 0;

    public GameLayout(int rows, int columns, int tileColor, TileAlignmentListener tileAlignmentListener) {
        this.tileAlignmentListener = tileAlignmentListener;
        this.tileColor = tileColor;
        this.rows = rows;
        this.columns = columns;
        gameTiles = new ArrayList<>();
    }

    public void createGameTiles() {
        for (int i = 0; i < rows * columns; i++) {
            Set<TilePositions> positions = checkPosition(i);
            int correctOrientation;
            String typeOfPosition = positionType(i, positions);
            int tileType = getTileOptions(typeOfPosition);
            correctOrientation = getOrientationOption(tileType, typeOfPosition);
            Tile tile = new Tile(rand.nextInt(4), tileType, correctOrientation, tilePossibilities.get(tileType));
            tile.setTilePositions((HashSet<TilePositions>) positions);
            tile.setCorrectOrientation(correctOrientation);
            tile.setOrientation(correctOrientation);
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

    /**
     * This method returns a valid tile given its position and relation to other existing tiles.
     * <p>
     *
     * @return
     */
    private int getTileOptions(String positionType) {
        int tileType;
        String type = positionType;
        String prongs = type.replace("0", "");
        if (prongs.length() > 2) {
            tileType = prongs.length() + 1;
        } else if (type.contains("101")) {
            tileType = 3;
        } else {
            tileType = prongs.length();
        }
        return tileType;
    }

    /**
     * Method returns a 4 character string that represents both the tile type and the orientation of the tile itself.
     *
     * @param i
     * @param positions
     * @return
     */
    private String positionType(int i, Set<TilePositions> positions) {
        char[] type = new char[4];
        int[] surroundingTilePositions = new int[]{
                i - columns,
                i + 1,
                i + columns,
                i - 1
        };
        for (int j = 3; j < 7; j++) {
            int k = j % 4;
            if (positions.contains(TilePositions.getTilePositionsFromValue(k))) {
                type[k] = '0';
            } else if (j < 5) {
                Tile adjacentTile = gameTiles.get(surroundingTilePositions[k]);
                type[k] = adjacentTile.getProngOrientations()[adjacentTile.getOrientation() + 1].charAt((k + 2) % 4);
            } else {
                type[k] = (char) (rand.nextInt(2) + 48);
            }
        }
        return String.valueOf(type);
    }

    /**
     * This method returns a valid tile given its position and relation to other existing tiles.
     * <p>
     *
     * @param tileType
     * @return
     */
    private int getOrientationOption(int tileType, String type) {
        for (int j = 1; j < 5; j++) {
            if (tilePossibilities.get(tileType)[j].equals(type)) {
                return j - 1;
            }
        }
        return 0;
    }

    public void resetGame(int rows, int columns, int tileColor) {
        this.rows = rows;
        this.columns = columns;
        this.tileColor = tileColor;
        gameTiles.clear();
        correctlyOriented.clear();
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
