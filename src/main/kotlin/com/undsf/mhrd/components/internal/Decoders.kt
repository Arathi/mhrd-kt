package com.undsf.mhrd.components.internal

import com.undsf.mhrd.components.Bus
import com.undsf.mhrd.components.Pin
import com.undsf.mhrd.components.Port
import com.undsf.mhrd.domains.Level
import com.undsf.mhrd.domains.PinMode

class Decoder38(name: String): InternalModule(name, "DECODER_38") {
    val enabled: Pin = Pin("e", PinMode.Input)
    val ins: Bus = Bus("in", PinMode.Input, 3)
    val outs: Bus = Bus("out", PinMode.Output, 8)

    override val inputs: Map<String, Port> get() = mapOf(
        enabled.name to enabled,
        ins.name to ins,
    )

    override val outputs: Map<String, Port> get() = mapOf(
        outs.name to outs
    )

    override fun updateOutputs() {
        for (index in outs.endIndex downTo outs.startIndex) {
            outs[index].level = Level.Low
        }

        val highIndex = ins.value + 1
        outs[highIndex].level = Level.High
    }
}
