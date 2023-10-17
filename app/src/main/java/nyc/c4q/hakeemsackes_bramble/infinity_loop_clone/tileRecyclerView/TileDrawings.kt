package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.tileRecyclerView

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import java.util.Random

/**
 * Created by hakeemsackes-bramble on 11/12/17.
 */
class TileDrawings(private val mPaint: Paint, view: View) {
    private val newPaint: Paint
    private val color: Int
    private val mView: View
    private val rand = Random()

    init {
        newPaint = Paint()
        newPaint.style = Paint.Style.FILL
        mView = view
        val hue = rand.nextFloat() * 360
        val saturation = .25f
        val value = 1f
        color = Color.HSVToColor(floatArrayOf(hue, saturation, value))
    }

    fun drawSquareTile(tileType: Int, canvas: Canvas) {
        val CENTER_X = mView.width / 2f
        val CENTER_Y = mView.height / 2f
        if (tileType == 0) {
            //blank tile
        } else if (tileType == 1) {
            // circle line tile
            newPaint.color = color
            canvas.drawCircle(CENTER_X, CENTER_Y, mView.height / 4f, newPaint)
            canvas.drawCircle(CENTER_X, CENTER_Y, mView.height / 4f, mPaint)
            canvas.drawLine(CENTER_X, CENTER_Y / 2, CENTER_X, 0f, mPaint)
        } else if (tileType == 2) {
            //quarter circle tile
            canvas.drawCircle(0f, 0f, CENTER_Y, mPaint)
            canvas.drawCircle(CENTER_X, CENTER_Y, 1f, mPaint)
        } else if (tileType == 3) {
            //line tile
            canvas.drawLine(CENTER_X, mView.height.toFloat(), CENTER_X, 0f, mPaint)
        } else if (tileType == 4) {
            //three prong tile
            canvas.drawCircle(0f, 0f, CENTER_Y, mPaint)
            canvas.drawCircle(0f, mView.height.toFloat(), CENTER_Y, mPaint)
            canvas.drawCircle(CENTER_X, CENTER_Y, 1f, mPaint)
        } else if (tileType == 5) {
            //four prong tile
            canvas.drawCircle(0f, 0f, CENTER_Y, mPaint)
            canvas.drawCircle(mView.width.toFloat(), mView.height.toFloat(), CENTER_Y, mPaint)
            canvas.drawCircle(CENTER_X, CENTER_Y, 1f, mPaint)
        }
    }
}