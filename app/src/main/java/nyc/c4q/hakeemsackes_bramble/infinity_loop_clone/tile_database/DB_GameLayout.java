package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.tile_database;

/**
 * Created by hakeemsackes-bramble on 11/30/17.
 */

public class DB_GameLayout {

    private String mGameLayout;
    private String mGameColor;

    public DB_GameLayout() {
    }

    public DB_GameLayout(String gameLayout) {
        this.mGameLayout = gameLayout;
    }

    public String getmGameLayout() {
        return mGameLayout;
    }

    public void setmGameLayout(String mGameLayout) {
        this.mGameLayout = mGameLayout;
    }

    public String getmGameColor() {
        return mGameColor;
    }

    public void setmGameColor(String mGameColor) {
        this.mGameColor = mGameColor;
    }
}
