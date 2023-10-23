package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.tileRecyclerView;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import java.util.Random;

/**
 * Created by hakeemsackes-bramble on 11/12/17.
 */

public class TileDrawings {
    private final Paint newPaint = new Paint();
    Paint mPaint;
    private final View mView;
    private final Random rand = new Random();
    float hue = rand.nextFloat() * 360;
    float saturation = .25f;
    float value = 1f;
    private final int color = Color.HSVToColor(new float[]{hue, saturation, value});

    public TileDrawings(Paint paint, View view) {
        this.mPaint = paint;
        this.mView = view;
        newPaint.setStyle(Paint.Style.FILL);
    }

    public void drawSquareTile(int tileType, Canvas canvas) {
        float CENTER_X = mView.getWidth() / 2f;
        float CENTER_Y = mView.getHeight() / 2f;
        float QUARTER_X = mView.getWidth() / 5f;
        float QUARTER_Y = mView.getHeight() / 5f;
        float QUARTER3_X = mView.getWidth() * 4 / 5f;
        float QUARTER3_Y = mView.getHeight() * 4 / 5f;
        RectF oval1 = new RectF(QUARTER_X, QUARTER_Y, QUARTER3_X, QUARTER3_Y);
        if (tileType == 0) {
            //blank tile
        } else if (tileType == 1) {
            // circle line tile
            newPaint.setColor(color);
            canvas.drawOval(oval1, newPaint);
            canvas.drawOval(oval1, mPaint);
            canvas.drawLine(CENTER_X, QUARTER_Y, CENTER_X, 0, mPaint);
        } else if (tileType == 2) {
            //QUARTER circle tile
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
