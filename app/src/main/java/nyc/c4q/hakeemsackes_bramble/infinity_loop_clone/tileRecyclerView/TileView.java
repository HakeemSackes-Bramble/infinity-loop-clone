package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.tileRecyclerView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.Tile;

/**
 * Created by hakeemsackes-bramble on 10/16/17.
 */

class TileView extends View {

    private Tile tileId;
    private Paint paint;

    public TileView(Context context) {
        super(context);
        paint = new Paint();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    public Tile getTileId() {
        return tileId;
    }

    public void setTileId(Tile tileId) {
        this.tileId = tileId;
        paint.setColor(tileId.getTileColor());
    }
}
