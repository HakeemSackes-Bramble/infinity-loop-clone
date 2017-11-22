package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.tileRecyclerView;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by hakeemsackes-bramble on 11/12/17.
 */

public class TileDrawings {
    private Paint mPaint;
    private View mView;

    public TileDrawings(Paint paint, View view) {
        this.mPaint = paint;
        this.mView = view;
    }

    private void drawSquareTile(int tileType, Canvas canvas) {
        float CENTER_X = mView.getWidth() / 2;
        float CENTER_Y = mView.getHeight() / 2;
        if (tileType == 0) {
            //blank tile
        } else if (tileType == 1) {
            // circle line tile
            canvas.drawCircle(CENTER_X, CENTER_Y, mView.getHeight() / 4, mPaint);
            canvas.drawCircle(CENTER_X, CENTER_Y, mView.getHeight() / 16, mPaint);
            canvas.drawLine(CENTER_X, CENTER_Y, CENTER_X, 0, mPaint);
        } else if (tileType == 2) {
            //quarter circle tile
            canvas.drawCircle(0, 0, CENTER_Y, mPaint);
        } else if (tileType == 3) {
            //line tile
            canvas.drawLine(CENTER_X, mView.getHeight(), CENTER_X, 0, mPaint);
        } else if (tileType == 4) {
            //three prong tile
            canvas.drawCircle(0, 0, CENTER_Y, mPaint);
            canvas.drawCircle(0, mView.getHeight(), CENTER_Y, mPaint);
        } else if (tileType == 5) {
            //four prong tile
            canvas.drawCircle(0, 0, CENTER_Y, mPaint);
            canvas.drawCircle(0, mView.getHeight(), CENTER_Y, mPaint);
            canvas.drawCircle(mView.getWidth(), mView.getHeight(), CENTER_Y, mPaint);
            canvas.drawCircle(mView.getWidth(), 0, CENTER_Y, mPaint);
        }
    }
}
