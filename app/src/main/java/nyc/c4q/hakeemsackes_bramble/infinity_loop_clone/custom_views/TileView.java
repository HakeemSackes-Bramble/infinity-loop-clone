package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.custom_views;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.Tile;
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.tileRecyclerView.TileDrawings;

/**
 * Created by hakeemsackes-bramble on 10/16/17.
 */

public class TileView extends View {

    private float scale;
    private Tile tileId;
    private Paint paint;
    private int sizeDP;
    private float sizePX;
    private int color;
    private TileDrawings tileDrawings;


    public TileView(Context context, int color, int column) {
        super(context);
        this.color = color;
        this.sizeDP = column;
        scale = context.getResources().getDisplayMetrics().density;
        sizePX = scale * sizeDP;


    }

    public TileView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        scale = context.getResources().getDisplayMetrics().density;
        sizePX = scale * sizeDP;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension((int) sizePX, (int) sizePX);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTileType(tileId.getTileType(), canvas);
        setRotation(posToDegrees(tileId.getOrientation()));
    }

    private void drawTileType(int tileType, Canvas canvas) {
        tileDrawings.drawSquareTile(tileType, canvas);
    }

    public void setTileValues(Tile tileId) {
        this.tileId = tileId;
        paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(sizePX / 9);
        paint.setAntiAlias(true);
        tileDrawings = new TileDrawings(paint, this);
    }

    public void rotateView(int oldPos, int newPos) {
        if (oldPos > newPos) {
            newPos = oldPos + 1;
        }
        ObjectAnimator tileViewObjectAnimator = ObjectAnimator.ofFloat(this,
                "rotation", posToDegrees(oldPos), posToDegrees(newPos));
        tileViewObjectAnimator.setDuration(160);
        tileViewObjectAnimator.start();
    }

    private int posToDegrees(int pos) {
        return pos * 90;
    }
}
