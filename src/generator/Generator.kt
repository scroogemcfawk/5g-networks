package generator

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

/**
 * Stepan Bylkov (smf)<br/>
 * Date: 03.09.2024<br/>
 * Copyright 2024 Intrice LLC. All rights reserved.
 */
object Generator {

    fun nextPointInRectangle(width: Number, height: Number): Point {
        return Point(
            Random.nextDouble(0.0, width.toDouble()),
            Random.nextDouble(0.0, height.toDouble())
        )
    }

    fun nextPointInCircleAsSquare(radius: Number = 1.0): Point {
        return nextPointInCircleAsSquare(radius.toDouble())
    }

    private fun nextPointInCircleAsSquare(radius: Double = 1.0): Point {
        val zeroPoint = Point(0.0, 0.0)
        var point: Point
        do {
            point = Point(
                Random.nextDouble(-radius, radius),
                Random.nextDouble(-radius, radius),
            )
        } while (point.distTo(zeroPoint) > radius)
        return point
    }

    fun nextPointInCircle(maxRadius: Number = 0.5): Point {
        val angle = Random.nextDouble() * 2 * PI
        val radius = sqrt(Random.nextDouble()) * maxRadius.toDouble()
        return Point(radius * cos(angle), radius * sin(angle))
    }
}
