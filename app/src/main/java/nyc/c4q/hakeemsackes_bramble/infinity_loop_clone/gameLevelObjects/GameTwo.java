package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects;

import android.content.SharedPreferences;

public class GameTwo extends GameLayout{
    public GameTwo(SharedPreferences sharedPreferences, SharedPreferences.Editor editor) {
        super( sharedPreferences, editor);
    }

    public GameTwo(GameLayout gameLayout){
        super(gameLayout.getmSharedPreferences(),gameLayout.getEdit());
    }


}
