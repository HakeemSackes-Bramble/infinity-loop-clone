package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.tile_database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hakeemsackes-bramble on 11/22/17.
 */

public class TileDBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "PastGames.db";
    private static final String TABLE_PASTGAMES = "TABLE_PAST_GAMES";
    private static final String COLUMN_GAMEID = "game_id";
    private static final String COLUMN_COLORID = "color_id";


    public TileDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, NAME, factory, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = " CREATE TABLE " + TABLE_PASTGAMES + "(" + COLUMN_GAMEID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + COLUMN_COLORID + ", TEXT " + ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PASTGAMES);
        onCreate(db);
    }
    
}
