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

    fun nextPointInSquare(size: Number): Point {
        return nextPointInRectangle(size, size)
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

    fun nextOnDistance(point: Point, distance: Number): Point {
        val angle = Random.nextDouble() * 2 * PI
        return Point(point.x + distance.toDouble() * cos(angle), point.y + distance.toDouble() * sin(angle))
    }

    fun nextPariInSquare(distance: Number, squareSize: Number): Pair<Point, Point> {
        var first = nextPointInSquare(squareSize)
        var second = nextOnDistance(first, distance)
        if (!(first.isInSquare(squareSize) && second.isInSquare(squareSize))) {
            val squareCenter = Point(squareSize.toDouble() / 2, squareSize.toDouble() / 2)
            val middle = first.middleTo(second)
            val adjustment = middle.getAdjustment(squareCenter)
            first = first.adjust(adjustment)
            second = second.adjust(adjustment)
        }
        return Pair(first, second)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        var A = Point(5, 6)
        var B = Point(11, 2)
        var C = A.middleTo(B)
        println("C: $C")

        val center = Point(4, 4)
        val adjust = C.getAdjustment(center)
        println("Adjust: $adjust")

        A = A.adjust(adjust)
        B = B.adjust(adjust)

        println(A)
        println(B)
    }
}
