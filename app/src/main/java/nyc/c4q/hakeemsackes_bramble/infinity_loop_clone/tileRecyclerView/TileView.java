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
    private int sizeDP = 30;
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

    private void drawTileType(short tileType, Canvas canvas) {
        if (tileType == 0) {
        } else if (tileType == 1) {
            canvas.drawCircle(getWidth()/2,getHeight()/2,getHeight()/4,paint);
            canvas.drawLine(getWidth()/2,getHeight()/2,getWidth()/2,0,paint);
        } else if (tileType == 2) {
            canvas.drawLine(getWidth()/2,getHeight(),getWidth()/2,0,paint);
        } else if (tileType == 3) {
            canvas.drawCircle(0,0,getHeight()/2,paint);
        } else if (tileType == 4) {
            canvas.drawCircle(0,0,getHeight()/2,paint);
            canvas.drawCircle(0,getHeight(),getHeight()/2,paint);
        } else if (tileType == 5) {
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
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);
    }
}
