package com.undsf.mhrd.domains

/**
 * 电平
 */
enum class Level(val value: Int) {
    Low(0),
    High(1);

    infix fun and(level: Level): Level {
        val result = this.value and level.value
        return if (result != 0) High else Low
    }

    infix fun nand(level: Level): Level {
        val result = this.value and level.value
        return if (result == 0) High else Low
    }

    infix fun or(level: Level): Level {
        val result = this.value or level.value
        return if (result != 0) High else Low
    }

    infix fun nor(level: Level): Level {
        val result = this.value or level.value
        return if (result == 0) High else Low
    }

    infix fun xor(level: Level): Level {
        val result = this.value xor level.value
        return if (result != 0) High else Low
    }

    infix fun xnor(level: Level): Level {
        val result = this.value xor level.value
        return if (result == 0) High else Low
    }

    operator fun plus(level: Level): Level {
        return this or level
    }

    operator fun times(level: Level): Level {
        return this and level
    }

    operator fun not(): Level {
        return if (this == High) Low else High
    }

    override fun toString(): String {
        return if (this == High) "1" else "0"
    }
}
