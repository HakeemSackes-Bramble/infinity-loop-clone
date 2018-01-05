package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.tileRecyclerView;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.Tile;

/**
 * Created by hakeemsackes-bramble on 10/16/17.
 */

public class TileView extends View {

    private float scale;
    private Tile tileId;
    private Paint paint;
    private int sizeDP = 36;
    private float sizePX;
    private int color;
    private int oldPos;
    private int newPos;
    private Paint fillPaint;
    private int mColor;
    private float pivpos;
    private GradientDrawable bkgdGradient;


    public TileView(Context context, int color) {
        super(context);
        this.color = color;
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
        setRotation(pivpos);
        setBackground(bkgdGradient);
    }

    private void drawTileType(int tileType, Canvas canvas) {
        float CENTER_X = getWidth() / 2;
        float CENTER_Y = getHeight() / 2;
        if (tileType == 0) {

            //blank tile
        } else if (tileType == 1) {
            // circle line tile
            canvas.drawCircle(CENTER_X, CENTER_Y, getHeight() / 4, paint);
            canvas.drawCircle(CENTER_X, CENTER_Y, getHeight() / 16, paint);
            canvas.drawLine(CENTER_X, CENTER_Y, CENTER_X, 0, paint);
        } else if (tileType == 2) {
            //quarter circle tile
            canvas.drawCircle(0, 0, CENTER_Y, paint);
            canvas.drawCircle(CENTER_X, CENTER_Y, getHeight() / 100, paint);
        } else if (tileType == 3) {
            //line tile
            canvas.drawLine(CENTER_X, getHeight(), CENTER_X, 0, paint);
        } else if (tileType == 4) {
            //three prong tile
            canvas.drawCircle(0, 0, CENTER_Y, paint);
            canvas.drawCircle(0, getHeight(), CENTER_Y, paint);
            canvas.drawCircle(CENTER_X, CENTER_Y, getHeight() / 100, paint);
        } else if (tileType == 5) {
            //four prong tile
            canvas.drawCircle(0, 0, CENTER_Y, paint);
            canvas.drawCircle(getWidth(), getHeight(), CENTER_Y, paint);
            canvas.drawCircle(CENTER_X, CENTER_Y, getHeight() / 100, paint);
        }
    }

    public void setTileId(Tile tileId) {
        this.tileId = tileId;
        paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(sizePX / 10);
        paint.setAntiAlias(true);
        oldPos = tileId.getOrientation();
        pivpos = oldPos * 90;
        mColor = tileId.getCornerColors()[tileId.getOrientation()];

    }

    public void rotateView(int oldPos, int newPos) {
        this.oldPos = oldPos;
        this.newPos = newPos;
        if (oldPos > newPos) {
            newPos = oldPos + 1;
        }
        ObjectAnimator tileViewObjectAnimator = ObjectAnimator.ofFloat(this,
                "rotation", oldPos * 90, newPos * 90);
        tileViewObjectAnimator.setDuration(200);
        tileViewObjectAnimator.start();
    }
}
