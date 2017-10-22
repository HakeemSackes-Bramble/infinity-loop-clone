package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.tileRecyclerView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.Tile;

/**
 * Created by hakeemsackes-bramble on 10/16/17.
 */

class TileView extends View {

    private float scale;
    private Tile tileId;
    private Paint paint;
    private int sizeDP = 40;
    private float sizePX;

    public TileView(Context context) {
        super(context);
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
        drawTileType(tileId.getTileType(),canvas);
        setRotation(tileId.getOrientation() * 90);
    }

    private void drawTileType(int tileType, Canvas canvas) {
        if (tileType == 0) {
            //blank tile
        } else if (tileType == 1) {
            // circle line tile
            canvas.drawCircle(getWidth()/2,getHeight()/2,getHeight()/4,paint);
            canvas.drawCircle(getWidth()/2,getHeight()/2,getHeight()/16,paint);
            canvas.drawLine(getWidth()/2,getHeight()/2,getWidth()/2,0,paint);
        } else if (tileType == 2) {
            //quarter circle tile
            canvas.drawCircle(0,0,getHeight()/2,paint);
        } else if (tileType == 3) {
            //line tile
            canvas.drawLine(getWidth()/2,getHeight(),getWidth()/2,0,paint);
        } else if (tileType == 4) {
            //three prong tile
            canvas.drawCircle(0,0,getHeight()/2,paint);
            canvas.drawCircle(0,getHeight(),getHeight()/2,paint);
        } else if (tileType == 5) {
            //four prong tile
            canvas.drawCircle(0,0,getHeight()/2,paint);
            canvas.drawCircle(0,getHeight(),getHeight()/2,paint);
            canvas.drawCircle(getWidth(),getHeight(),getHeight()/2,paint);
            canvas.drawCircle(getWidth(),0,getHeight()/2,paint);
        }
    }

    public Tile getTileId() {
        return tileId;
    }

    public void setTileId(Tile tileId) {
        this.tileId = tileId;
        paint = new Paint();
        paint.setColor(tileId.getTileColor());
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);
        paint.setAntiAlias(true);
    }
}
