package fr.perfectblue.magiccircle.Models

import android.graphics.Color
import android.graphics.Paint
import kotlin.random.Random

data class MagicCircle (val maxX: Float, val maxY: Float, var cx: Float = 0f, var cy: Float = 0f, private var isTouched: Boolean = false) {

    companion object {
        var DEATHSIZE: Float = 80f
        var DEATHTIMER: Int = 30
    }

    private var delta: Int = 0
    private var dx: Int = 0
    private var dy: Int = 0
    var rad: Float = 0f
    var deadTimer: Int = 0
    var bgColor = Paint()

    private var isDead: Boolean = false

    fun getIsTouched(): Boolean {
        return this.isTouched
    }
    fun setIsTouched(newState: Boolean) {
        this.isTouched = newState
    }
    fun getIsDead(): Boolean {
        return this.isDead
    }

    fun init () {
        this.setRad()

        if (this.cx == 0f) this.cx = Random.nextInt(this.rad.toInt(), this.maxX.toInt()).toFloat()
        if (this.cy == 0f) this.cy = Random.nextInt(this.rad.toInt(), this.maxY.toInt()).toFloat()

        this.delta = Random.nextInt(3, 10)
        var moveDirection: Boolean = Random.nextBoolean()
        if (moveDirection) {
            this.delta = this.delta
        } else {
            this.delta = -this.delta
        }
        this.dx = this.delta
        this.dy = this.delta

        this.bgColor.color = Color.rgb(Random.nextInt(0, 255), Random.nextInt(0, 255), Random.nextInt(0, 255))
    }

    fun move() {
        if (!this.isTouched) {
            when {
                this.cx !in 0f..this.maxX -> this.dx = -this.dx
                this.cy !in 0f..this.maxY -> this.dy = -this.dy
            }
            this.cx += this.dx
            this.cy += this.dy
        }
    }

    fun setRad () {
        if (this.isTouched == false) {
            this.rad = Random.nextInt(30, 70).toFloat()
        } else {
            if (this.deadTimer >= DEATHTIMER) {
                if (this.rad > 0f) {
                    this.rad -= 3f
                } else {
                    this.isDead = true
                }
            } else {
                if (this.rad < DEATHSIZE) {
                    this.rad += 3f
                } else {
                    if (this.deadTimer < DEATHTIMER) {
                        this.deadTimer += 1
                    }
                }
            }
        }
    }
}