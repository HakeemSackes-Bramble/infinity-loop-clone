package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.MainActivity;
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.listeners.TileAlignmentListener;

/**
 * Created by hakeemsackes-bramble on 10/15/17.
 */

public class GameLayout {

    private int tileColor;
    private int rows;
    private int columns;
    private Gson gson = new Gson();
    private final Random rand = new Random();
    private ArrayList<Tile> gameTiles = new ArrayList<>();
    private HashSet<Integer> correctlyOriented = new HashSet<>();
    private HashMap<Integer, String[]> tilePossibilities = new TileTypes().getTiles();
    private TileAlignmentListener listener;
    int num = 0;
    private boolean allTilesAreAligned;
    SharedPreferences mSharedPreferences;

    SharedPreferences.Editor edit;

    private HashMap<Integer, Path> pathMap = new HashMap<>();

    public GameLayout(int rows, int columns, int tileColor, SharedPreferences sharedPreferences, SharedPreferences.Editor editor) {
        this.tileColor = tileColor;
        this.rows = rows;
        this.columns = columns;
        this.mSharedPreferences = sharedPreferences;
        this.edit = editor;
    }

    public void createGameTiles() {
        for (int i = 0; i < rows * columns; i++) {
            HashSet<SquareTilePositions> positions = checkPosition(i);
            int correctOrientation;
            String typeOfPosition = positionType(i, positions);
            int tileType = returnValidTile(typeOfPosition);
            correctOrientation = returnOrientationOption(tileType, typeOfPosition);
            Tile tile = new Tile(rand.nextInt(4), tileType, correctOrientation, Objects.requireNonNull(tilePossibilities.get(tileType)));
            tile.setTilePositions(positions);
            tile.setCorrectOrientation(correctOrientation);
            tile.assignOrientation(correctOrientation);
            if (i % columns == (columns - 1)) {
                num++;
            }
            gameTiles.add(tile);
        }
        num = 0;
    }

    private void loadTileData() {
        String currPuzzle = mSharedPreferences.getString(MainActivity.CURRENT_PUZZLE, "");
        Type listType = new TypeToken<ArrayList<Tile>>() {
        }.getType();
        ArrayList<Tile> list = gson.fromJson(currPuzzle, listType);
        this.gameTiles = list;
        removeSavedData();
    }

    public void removeSavedData() {
        edit.remove(MainActivity.CURRENT_PUZZLE);
        edit.remove(MainActivity.CURRENT_COLUMN_SIZE);
        edit.remove(MainActivity.CURRENT_ROW_SIZE);
        edit.remove(MainActivity.CURRENT_TILE_COLOR);
        edit.remove(MainActivity.CURRENT_BACKGROUND_COLOR);
        edit.apply();
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
    private int returnValidTile(String positionType) {
        int tileType;
        String prongs = positionType.replace("0", "");
        if (prongs.length() > 2) {
            tileType = prongs.length() + 1;
        } else if (positionType.contains("101")) {
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
    private String positionType(int i, Set<SquareTilePositions> positions) {
        char[] type = new char[4];
        int[] surroundingTilePositions = surroundingTileNumbers(i);
        for (int j = 3; j < 7; j++) {
            int k = j % 4;
            if (positions.contains(SquareTilePositions.getTilePositionsFromValue(k))) {
                type[k] = '0';
            } else if (j < 5) {
                Tile adjacentTile = gameTiles.get(surroundingTilePositions[k]);
                type[k] = adjacentTile.getProngOrientations()[adjacentTile.getCorrectOrientation() + 1].charAt((k + 2) % 4);
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
    private int returnOrientationOption(int tileType, String type) {
        for (int j = 1; j < 5; j++) {
            if (Objects.requireNonNull(tilePossibilities.get(tileType))[j].equals(type)) {
                return j - 1;
            }
        }
        return 0;
    }

    public void resetGame(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        gameTiles.clear();
        correctlyOriented.clear();
        createGameTiles();
    }

    private HashSet<SquareTilePositions> checkPosition(int i) {
        HashSet<SquareTilePositions> positions = new HashSet<>();
        boolean topEdge = i < columns;
        boolean bottomEdge = i >= ((rows * columns) - columns);
        boolean rightEdge = i % columns == columns - 1;
        boolean leftEdge = i % columns == 0;
        if (topEdge)
            positions.add(SquareTilePositions.TOP_EDGE);
        if (bottomEdge)
            positions.add(SquareTilePositions.BOTTOM_EDGE);
        if (rightEdge)
            positions.add(SquareTilePositions.RIGHT_EDGE);
        if (leftEdge)
            positions.add(SquareTilePositions.LEFT_EDGE);
        if (positions.size() == 0)
            positions.add(SquareTilePositions.CENTER);
        return positions;
    }

    public void addCorrectedTile(int position, boolean isCorrected) {
        if (isCorrected) {
            correctlyOriented.add(position);
            allTilesAreAligned = correctlyOriented.size() == gameTiles.size();
        } else {
            correctlyOriented.remove(position);
        }
    }

    public int[] surroundingTileNumbers(int position) {
        return new int[]{
                position - columns,
                position + 1,
                position + columns,
                position - 1
        };
    }

    public void setListener(TileAlignmentListener listener) {
        this.listener = listener;
    }

    public int getTileColor() {
        return tileColor;
    }

    public void runCheckTileAlignmentListener(Tile tile, int position) {
        listener.checkTileAlignment(this, tile, position);
    }

    public boolean hasAllTilesAligned() {
        return allTilesAreAligned;
    }

    public void createGame() {
        if (mSharedPreferences.contains(MainActivity.CURRENT_PUZZLE)) {
            loadTileData();
        } else {
            createGameTiles();
        }
    }

    void addNewPathToMap(int pathId, Path path) {
        pathMap.put(pathId, path);
    }

    void removePathFromMap(int pathId) {
        pathMap.remove(pathId);
    }

    public HashMap<Integer, Path> getPathMap() {
        return pathMap;
    }

    public void setPathMap(HashMap<Integer, Path> pathMap) {
        this.pathMap = pathMap;
    }

}
