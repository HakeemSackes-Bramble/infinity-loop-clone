package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects;

import android.content.SharedPreferences;

public class GameOne extends GameLayout{
    public GameOne(SharedPreferences sharedPreferences, SharedPreferences.Editor editor) {
        super( sharedPreferences, editor);
    }

    public GameOne(GameLayout gameLayout){
        super(gameLayout.getmSharedPreferences(),gameLayout.getEdit());

    }
}
