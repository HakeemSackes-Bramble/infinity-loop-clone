package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.custom_views

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.Tile
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.tileRecyclerView.TileDrawings

/**
 * Created by hakeemsackes-bramble on 10/16/17.
 */
class TileView : View {
    private var scale: Float
    private var tileId: Tile? = null
    private var paint: Paint = Paint()
    private var sizeDP = 0
    private var sizePX: Float
    private var color = 0
    private var tileDrawings: TileDrawings? = null

    constructor(context: Context, color: Int, column: Int) : super(context) {
        this.color = color
        sizeDP = column
        scale = context.resources.displayMetrics.density
        sizePX = scale * sizeDP
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        scale = context.resources.displayMetrics.density
        sizePX = scale * sizeDP
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(sizePX.toInt(), sizePX.toInt())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawTileType(tileId!!.tileType, canvas)
        rotation = posToDegrees(tileId!!.orientation).toFloat()
    }

    private fun drawTileType(tileType: Int, canvas: Canvas) {
        tileDrawings!!.drawSquareTile(tileType, canvas)
    }

    fun setTileValues(tileId: Tile?) {
        this.tileId = tileId
        paint!!.color = color
        paint!!.style = Paint.Style.STROKE
        paint!!.strokeWidth = sizePX / 9
        paint!!.isAntiAlias = true
        tileDrawings = TileDrawings(paint!!, this)
    }

    fun rotateView(oldPos: Int, newPos: Int) {
        var newPos = newPos
        if (oldPos > newPos) {
            newPos = oldPos + 1
        }
        val tileViewObjectAnimator = ObjectAnimator.ofFloat(
            this,
            "rotation", posToDegrees(oldPos).toFloat(), posToDegrees(newPos).toFloat()
        )
        tileViewObjectAnimator.duration = 160
        tileViewObjectAnimator.start()
    }

    private fun posToDegrees(pos: Int): Int {
        return pos * 90
    }
}