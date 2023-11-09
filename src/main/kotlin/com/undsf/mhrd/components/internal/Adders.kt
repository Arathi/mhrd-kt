package com.undsf.mhrd.components.internal

import com.undsf.mhrd.components.Pin
import com.undsf.mhrd.components.Port
import com.undsf.mhrd.domains.Level
import com.undsf.mhrd.domains.PinMode
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("Adders")

class HalfAdder(name: String): InternalModule(name, "HALF_ADDER") {
    val in1: Pin = Pin("in1", PinMode.Input, this)
    val in2: Pin = Pin("in2", PinMode.Input, this)
    val out: Pin = Pin("out", PinMode.Output, this)
    val carry: Pin = Pin("carry", PinMode.Output, this)

    override val inputs: Map<String, Port> get() = mapOf(
        in1.name to in1,
        in2.name to in2,
    )

    override val outputs: Map<String, Port> get() = mapOf(
        out.name to out,
        carry.name to carry,
    )

    override fun updateOutputs() {
        out.level = in1.level xor in2.level
        carry.level = in1.level and in2.level
        logger.debug("${this}更新完成")
    }
}

class FullAdder(name: String): InternalModule(name, "FULL_ADDER") {
    val in1: Pin = Pin("in1", PinMode.Input)
    val in2: Pin = Pin("in2", PinMode.Input)
    val carryIn: Pin = Pin("carryIn", PinMode.Input)
    val out: Pin = Pin("out", PinMode.Output)
    val carryOut: Pin = Pin("carryOut", PinMode.Output)

    override val inputs: Map<String, Port> get() = mapOf(
        in1.name to in1,
        in2.name to in2,
        carryIn.name to carryIn,
    )

    override val outputs: Map<String, Port> get() = mapOf(
        out.name to out,
        carryOut.name to carryOut,
    )

    override fun updateOutputs() {
        val result = in1.value + in2.value + carryIn.value
        out.level = if (result % 2 == 1) Level.High else Level.Low
        carryOut.level = if (result / 2 == 1) Level.High else Level.Low
        logger.debug("${this}更新完成")
    }
}
