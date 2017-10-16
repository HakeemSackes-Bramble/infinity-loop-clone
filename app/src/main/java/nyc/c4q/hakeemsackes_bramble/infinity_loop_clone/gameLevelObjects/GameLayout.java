package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by hakeemsackes-bramble on 10/15/17.
 */

public class GameLayout {
    private int tileColor;
    private int rows;
    private int columns;
    private ArrayList<Tile> gameTiles;

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
            gameTiles.add(new Tile((short) rand.nextInt(4),(short) rand.nextInt(6), tileColor, (short) rand.nextInt(4)));
        }
    }

    public ArrayList<Tile> getGameTiles() {
        return gameTiles;
    }
}
