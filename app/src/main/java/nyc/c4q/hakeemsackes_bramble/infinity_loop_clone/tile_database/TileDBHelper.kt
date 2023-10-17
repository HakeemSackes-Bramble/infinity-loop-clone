package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.tile_database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by hakeemsackes-bramble on 11/22/17.
 */
class TileDBHelper(context: Context?, name: String?, factory: CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, NAME, factory, VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val query =
            (" CREATE TABLE " + TABLE_PASTGAMES + "(" + COLUMN_GAMEID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + COLUMN_COLORID + ", TEXT " + ");")
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PASTGAMES")
        onCreate(db)
    }

    companion object {
        private const val VERSION = 1
        private const val NAME = "PastGames.db"
        private const val TABLE_PASTGAMES = "TABLE_PAST_GAMES"
        private const val COLUMN_GAMEID = "game_id"
        private const val COLUMN_COLORID = "color_id"
    }
}