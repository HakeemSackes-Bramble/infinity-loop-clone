package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.tileRecyclerView;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.Random;

/**
 * Created by hakeemsackes-bramble on 11/12/17.
 */

public class TileDrawings {
    private Paint newPaint  = new Paint();
    private int color;
    private Paint mPaint;
    private View mView;
    private Random rand = new Random();

    public TileDrawings(Paint paint, View view) {
        this.mPaint = paint;
        newPaint.setStyle(Paint.Style.FILL);
        this.mView = view;
        float hue = rand.nextFloat() * 360;
        float saturation = .25f;
        float value = 1f;
        color = Color.HSVToColor(new float[]{hue, saturation, value});
    }

    public void drawSquareTile(int tileType, Canvas canvas) {
        float CENTER_X = mView.getWidth() / 2f;
        float CENTER_Y = mView.getHeight() / 2f;
        if (tileType == 0) {
            //blank tile
        } else if (tileType == 1) {
            // circle line tile
            newPaint.setColor(color);
            canvas.drawCircle(CENTER_X, CENTER_Y, mView.getHeight() / 4f, newPaint);
            canvas.drawCircle(CENTER_X, CENTER_Y, mView.getHeight() / 4f, mPaint);
            canvas.drawLine(CENTER_X, CENTER_Y / 2, CENTER_X, 0, mPaint);
        } else if (tileType == 2) {
            //quarter circle tile
            canvas.drawCircle(0, 0, CENTER_Y, mPaint);
            canvas.drawCircle(CENTER_X, CENTER_Y, 1, mPaint);

        } else if (tileType == 3) {
            //line tile
            canvas.drawLine(CENTER_X, mView.getHeight(), CENTER_X, 0, mPaint);
        } else if (tileType == 4) {
            //three prong tile
            canvas.drawCircle(0, 0, CENTER_Y, mPaint);
            canvas.drawCircle(0, mView.getHeight(), CENTER_Y, mPaint);
            canvas.drawCircle(CENTER_X, CENTER_Y, 1, mPaint);
        } else if (tileType == 5) {
            //four prong tile
            canvas.drawCircle(0, 0, CENTER_Y, mPaint);
            canvas.drawCircle(mView.getWidth(), mView.getHeight(), CENTER_Y, mPaint);
            canvas.drawCircle(CENTER_X, CENTER_Y, 1, mPaint);
        }

    }
}
