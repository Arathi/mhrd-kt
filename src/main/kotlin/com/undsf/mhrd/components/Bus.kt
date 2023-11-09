package com.undsf.mhrd.components

import com.undsf.mhrd.domains.PinMode

class Bus(
    name: String,
    mode: PinMode,
    val startIndex: Int,
    val endIndex: Int,
    owner: Part? = null,
    createPins: Boolean = true,
): Port(name, mode, owner) {
    private val pins: MutableMap<Int, Pin> = mutableMapOf()
    val size: Int get() = pins.size

    constructor(name: String, mode: PinMode, size: Int, owner: Part? = null) : this(
        name,
        mode,
        1,
        size,
        owner,
        true,
    )

    init {
        if (createPins) {
            for (index in startIndex .. endIndex) {
                val pin = Pin("${name}[${index}]", mode)
                pins[index] = pin
            }
        }
    }

    @Throws(IndexOutOfBoundsException::class)
    operator fun get(index: Int): Pin {
        return pins[index] ?: throw IndexOutOfBoundsException()
    }

    @Throws(IndexOutOfBoundsException::class)
    operator fun get(startIndex: Int, endIndex: Int): Bus {
        val bus = Bus(name, mode, startIndex, endIndex, owner, false)
        for (index in startIndex .. endIndex) {
            val pin = this[index]
            bus.pins[index] = pin
        }
        return bus
    }

    @get:Throws(IndexOutOfBoundsException::class)
    override val value: Int get() {
        var sum = 0
        for (index in endIndex downTo startIndex) {
            val pin = pins[index] ?: throw IndexOutOfBoundsException()
            sum = sum shl 1
            sum += pin.value
        }
        return sum
    }

    override fun update() {
        for ((index, pin) in pins) {
            pin.update()
        }
    }
}