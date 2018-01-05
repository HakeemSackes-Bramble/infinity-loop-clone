package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects;

import android.graphics.Color;
import android.util.Log;

import java.util.HashMap;
import java.util.Random;

import static android.content.ContentValues.TAG;

/**
 * Created by hakeemsackes-bramble on 11/14/17.
 */

public class ColorList {
    private int columns;
    private int rows;
    private Random rand = new Random();
    private HashMap<Integer, Integer> colors = new HashMap<>();

    public ColorList(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
    }

    public void createColorList() {
        int my;
        Log.d(TAG, "createColorList: row:column " + rows + " " + columns);
        for (int i = 0; i < (rows + 1); i++) {
            for (int j = 0; j < (columns + 1); j++) {
                if (i == 0 || i == rows) {
                    my = Color.argb(0, 0, 0, 0);
                } else if (j == 0 || j == columns) {
                    my = Color.argb(0, 0, 0, 0);
                } else {
                    my = Color.HSVToColor(new float[]{rand.nextFloat() * 360, .30f, 1f});
                }
                int x = i * (columns + 1) + j;
                colors.put(x, my);
            }
        }
    }

    public HashMap<Integer, Integer> getColors() {
        return colors;
    }


    public void createNewColorList(int rows, int columns) {
        colors.clear();
        this.columns = columns;
        this.rows = rows;
        createColorList();
    }
}
