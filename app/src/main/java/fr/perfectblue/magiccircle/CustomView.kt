package fr.perfectblue.magiccircle

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import fr.perfectblue.magiccircle.Models.MagicCircle

class CustomView: View, View.OnTouchListener {

    constructor(context: Context) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        this.init()
    }

    companion object {
        val NBCIRCLES: Int = 20
    }

    var mArray: ArrayList<MagicCircle> = ArrayList<MagicCircle>()
    var mMcToRemove: ArrayList<MagicCircle> = ArrayList<MagicCircle>()

    private fun init() {
        setOnTouchListener(this)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            this.mArray.add(this.circle(event.getX(), event.getY(), true))
            return true
        }
        return false
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        this.generateCircles()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        for (circle in this.mArray) {
            if (circle.getIsDead()) {
                this.mMcToRemove.add(circle)
            } else {
                if (circle.getIsTouched() == false) {
                    circle.move()
                    this.checkCollision(circle)
                } else {
                    circle.setRad()
                }
                canvas?.drawCircle(circle.cx, circle.cy, circle.rad, circle.bgColor)
            }
        }

        for (circle in this.mMcToRemove) {
            this.mArray.remove(circle)
        }
        this.mMcToRemove.clear()
        invalidate()
    }

    private fun generateCircles() {
        for (i in 0 until NBCIRCLES) {
            this.mArray.add(this.circle())
        }
    }

    private fun circle(cx: Float = 0f, cy: Float = 0f, isTouched: Boolean = false): MagicCircle {
        var magicCircleTemp = MagicCircle(width.toFloat(), height.toFloat(), cx, cy, isTouched)
        magicCircleTemp.init()
        return magicCircleTemp
    }

    private fun checkCollision (circle: MagicCircle) {
        for (i in this.mArray) {
            if (i.getIsTouched() == true) {
                var distance: Float = (circle.cx - i.cx) * (circle.cx - i.cx) +
                        (circle.cy - i.cy) * (circle.cy - i.cy)
                var rad: Float = (circle.rad + i.rad) * (circle.rad + i.rad)

                if (distance == rad) {
                    circle.setIsTouched(true)
                    circle.setRad()
                } else if (distance > rad) {
                    // les cercles ne se touchent pas
                } else {
                    circle.setIsTouched(true)
                    circle.setRad()
                }
            }
        }
    }
}