package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects;

import android.content.SharedPreferences;
import android.graphics.Color;

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
    private final Gson gson = new Gson();
    private final Random rand = new Random();
    private ArrayList<Tile> gameTiles = new ArrayList<>();
    private final HashSet<Integer> correctlyOriented = new HashSet<>();
    private final HashMap<Integer, String[]> tilePossibilities = new TileTypes().getTiles();
    private TileAlignmentListener listener;
    private boolean allTilesAreAligned;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor edit;
    private HashMap<Integer, Path> pathMap = new HashMap<>();
    private static final int maxGameWidth = 360;
    private static final int maxGameHeight = 540;
    private int backgroundColor;
    private int tileSize;

    public GameLayout( SharedPreferences sharedPreferences, SharedPreferences.Editor editor) {
        this.mSharedPreferences = sharedPreferences;
        this.edit = editor;
        setValues();
    }

    public void createGameTiles() {
        for (int i = 0; i < rows * columns; i++) {
            HashSet<SquareTilePositions> positions = checkPosition(i);
            String typeOfPosition = positionType(i, positions);
            int tileType = returnValidTile(typeOfPosition);
            int correctOrientation = returnOrientationOption(tileType, typeOfPosition);
            Tile tile = new Tile(rand.nextInt(4), tileType, correctOrientation, Objects.requireNonNull(tilePossibilities.get(tileType)));
            tile.setTilePositions(positions);
            gameTiles.add(tile);
        }
    }

    private void loadTileData() {
        String currPuzzle = mSharedPreferences.getString(MainActivity.CURRENT_PUZZLE, "");
        Type listType = new TypeToken<ArrayList<Tile>>() {
        }.getType();
        this.gameTiles = gson.fromJson(currPuzzle, listType);
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
     * @return integer representation of tileType
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
                type[k] = adjacentTile.getCorrectStringOrientation().charAt((k + 2) % 4);
            } else {
                type[k] = (char) (rand.nextInt(2) + 48);
            }
        }
        return String.valueOf(type);
    }

    /**
     * Method compares the manufactured tile type to the list of tile
     * possibilities within TilePossibilities object ["2", "1001", "1100", "0110", "0011"]  to return the correct orientation of the generated tile
     * represented by an integer between 0 and 3 inclusive
     *
     * @param tileType
     * @param type
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

    /**
     * resets te current game level with new tiles given the new column and row attributes
     *
     */
    public void resetGame() {
        edit.clear().apply();
        setValues();
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

    public void setValues() {

        rows = mSharedPreferences.getInt(MainActivity.CURRENT_ROW_SIZE, rand.nextInt(9) + 5);
        columns = mSharedPreferences.getInt(MainActivity.CURRENT_COLUMN_SIZE, rand.nextInt(5) + 5);
        backgroundColor = mSharedPreferences.getInt(MainActivity.CURRENT_BACKGROUND_COLOR,
                Color.HSVToColor(new float[]{rand.nextFloat() * 360, .05f, 1f}));
        tileColor = mSharedPreferences.getInt(MainActivity.CURRENT_TILE_COLOR,
                Color.HSVToColor(new float[]{rand.nextFloat() * 360, .05f, 0.6f}));
        edit.apply();

        int widthTileSize = maxGameWidth / columns;
        int heightTileSize = maxGameHeight / rows;
        tileSize = Math.min(heightTileSize, widthTileSize);
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

    public void setTileColor(int tileColor) {
        this.tileColor = tileColor;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public SharedPreferences getmSharedPreferences() {
        return mSharedPreferences;
    }

    public void setmSharedPreferences(SharedPreferences mSharedPreferences) {
        this.mSharedPreferences = mSharedPreferences;
    }

    public SharedPreferences.Editor getEdit() {
        return edit;
    }

    public void setEdit(SharedPreferences.Editor edit) {
        this.edit = edit;
    }

    public int getTileSize() {
        return tileSize;
    }
}
