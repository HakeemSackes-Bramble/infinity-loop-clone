package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * Created by hakeemsackes-bramble on 10/15/17.
 */

public class GameLayout {
    private int tileColor;
    private int rows;
    private int columns;
    private ArrayList<Tile> gameTiles;
    private HashSet<Integer> isCorrectlyOriented;

    public GameLayout(int rows, int columns, int tileColor) {
        this.tileColor = tileColor;
        this.rows = rows;
        this.columns = columns;
        gameTiles = new ArrayList<>();
        createGameTiles();
    }

    private void createGameTiles() {
        Random rand = new Random();
        for (int i = 0; i < rows * columns; i++) {
            int tileType = 6;
            boolean edge = (i % columns == 4 || i % columns == 0) //left and right edges
                    ||(i < columns || i >((rows * columns) - columns));
            boolean corner = (i % columns == 4 || i % columns == 0) //left and right edges
                    ||(i < columns || i >((rows * columns) - columns));
            if(edge){
                tileType = 5;
            }
            if(corner){
                tileType = 3;
            }
            gameTiles.add(new Tile((short) rand.nextInt(4),(short) rand.nextInt(tileType), tileColor, (short) rand.nextInt(4)));
        }
    }

    public ArrayList<Tile> getGameTiles() {
        return gameTiles;
    }
}
