package generator

import kotlin.math.pow
import kotlin.math.sqrt

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

fun main() {
    val a = Point(0, 0)
    val b = Point(1, 1)
    for (p in a.interpolate(b, 10)) {
        println(p)
    }
}
