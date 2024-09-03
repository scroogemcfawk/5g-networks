package generator

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
}
