package com.undsf.mhrd.components

import com.undsf.mhrd.domains.Level
import com.undsf.mhrd.domains.PinMode
import com.undsf.mhrd.exceptions.CircuitException
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger(Pin::class.java)

class Pin(
    name: String,
    mode: PinMode,
    owner: Part? = null,
    var level: Level = Level.Low,
): Port(name, mode, owner) {
    var previousPin: Pin? = null
    val nextPins: MutableSet<Pin> = mutableSetOf()

    override val value: Int
        get() = if (level == Level.High) 1 else 0

    @Throws(CircuitException::class)
    fun connectTo(dest: Pin) {
        if (dest.previousPin != null) {
            throw CircuitException("目标引脚${dest}已被其他引脚（${dest.previousPin}）连接")
        }
        dest.previousPin = this
        nextPins.add(dest)
        // logger.debug("${this}已连接到${dest}")
    }

    override fun update() {
        if (previousPin != null) {
            logger.debug("正在更新$this <- $previousPin")
            level = previousPin!!.level
        }
    }
}