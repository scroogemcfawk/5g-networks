package generator

import kotlin.math.*

/**
 * Stepan Bylkov (smf)<br/>
 * Date: 03.09.2024<br/>
 * Copyright 2024 Intrice LLC. All rights reserved.
 */
data class Point(
    val x: Double,
    val y: Double
) {

    constructor(x: Number, y: Number) : this(x.toDouble(), y.toDouble())

    fun distTo(other: Point): Double {
        return sqrt((x - other.x).pow(2) + (y - other.y).pow(2))
    }

    fun middleTo(other: Point): Point {
        return Point((x + other.x) / 2, (y + other.y) / 2)
    }

    fun adjust(dx: Number, dy: Number): Point {
        return Point(x + dx.toDouble(), y + dy.toDouble())
    }

    fun adjust(adjustment: Adjustment): Point {
        return adjust(adjustment.x, adjustment.y)
    }

    fun getAdjustment(to: Point): Adjustment {
        return Adjustment(to.x - x, to.y - y)
    }

    fun isInSquare(size: Number): Boolean {
        return x in 0.0..size.toDouble() && y in 0.0..size.toDouble()
    }

    fun isInCircle(center: Point, radius: Number): Boolean {
        return distTo(center) <= radius.toDouble()
    }

    fun interpolate(other: Point, n: Int): List<Point> {
        val res = ArrayList<Point>()
        val a = getAdjustment(other)
        val b = Adjustment(a.x / (n + 1), a.y / (n + 1))
        var p = this
        repeat(n) {
            val t = p.adjust(b)
            res.add(t)
            p = t
        }
        return res
    }
}

data class Adjustment(
    val x: Double,
    val y: Double
) {
    constructor(x: Number, y: Number) : this(x.toDouble(), y.toDouble())
}

private const val TWO_PI = 2 * PI

data class Sector(
    val pivot: Point,
    val direction: Double,
    val fov: Double
) {
    private val boundUpper = direction + fov / 2
    private val boundLower = direction - fov / 2

    fun getDirectionEndPoint(distance: Number): Point {
        return pointFromPivotWithAngle(distance, direction)
    }

    private fun pointFromPivotWithAngle(distance: Number, directionAngle: Double): Point {
        val x = cos(directionAngle) * distance.toDouble()
        val y = sin(directionAngle) * distance.toDouble()
        return Point(pivot.x + x, pivot.y + y)
    }

    fun contains(p: Point): Boolean {
        println("Sector: $this")
        println("Other: $p")

        val adjustment = pivot.getAdjustment(p)
        val angle = atan2(adjustment.y, adjustment.x).let {
            if (it < 0) it + TWO_PI else
                if (it > TWO_PI) it - TWO_PI else it
        }
        println("Angle: ${degrees(angle)}")
        return angle in boundLower..boundUpper
    }

    fun getLineEndsInSquare(distance: Number): Pair<Point, Point> {
        return Pair(
            pointFromPivotWithAngle(distance, boundUpper),
            pointFromPivotWithAngle(distance, boundLower)
        )
    }

    override fun toString(): String {
        return "Sector(pivot=$pivot, direction=${degrees(direction)}, fov=${degrees(fov)}, bu=${degrees(boundUpper)}, bl=${degrees(boundLower)})"
    }

    private fun degrees(angle: Number): Int {
        return ((angle.toDouble() / TWO_PI) * 360).toInt()
    }
}

fun main() {
    val f = Point(0, 0)

    val t0 = Point(1, 0)
    val t45 = Point(1, 1)
    val t90 = Point(0, 1)
    val t135 = Point(-1, 1)
    val t180 = Point(-1, 0)
    val t225 = Point(-1, -1)
    val t270 = Point(0, -1)
    val t315 = Point(1, -1)

    val s = Sector(f, PI / 2, PI)
    println(s)
    for (p in listOf(t0, t45, t90, t135, t180, t225, t270, t315)) {
        println("$p ${s.contains(p)}")
    }
}
