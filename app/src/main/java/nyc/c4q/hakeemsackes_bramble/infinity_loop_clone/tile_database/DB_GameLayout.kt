package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.tile_database

/**
 * Created by hakeemsackes-bramble on 11/30/17.
 */
class DB_GameLayout {
    private var mGameLayout: String? = null
    private var mGameColor: String? = null

    constructor()
    constructor(gameLayout: String?) {
        mGameLayout = gameLayout
    }

    fun getmGameLayout(): String? {
        return mGameLayout
    }

    fun setmGameLayout(mGameLayout: String?) {
        this.mGameLayout = mGameLayout
    }

    fun getmGameColor(): String? {
        return mGameColor
    }

    fun setmGameColor(mGameColor: String?) {
        this.mGameColor = mGameColor
    }
}