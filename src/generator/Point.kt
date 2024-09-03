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
}
