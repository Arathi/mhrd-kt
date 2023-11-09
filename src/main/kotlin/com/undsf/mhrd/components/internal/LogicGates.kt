package com.undsf.mhrd.components.internal

import com.undsf.mhrd.components.Pin
import com.undsf.mhrd.components.Port
import com.undsf.mhrd.domains.PinMode

abstract class LogicGate21(name: String, type: String): InternalModule(name, type) {
    val in1: Pin = Pin("in1", PinMode.Input, this)
    val in2: Pin = Pin("in2", PinMode.Input, this)
    val out: Pin = Pin("out", PinMode.Output, this)

    override val inputs: Map<String, Port> get() = mapOf(
        in1.name to in1,
        in2.name to in2,
    )

    override val outputs: Map<String, Port> get() = mapOf(
        out.name to out,
    )
}

class AndGate(name: String): LogicGate21(name, "AND") {
    override fun updateOutputs() {
        out.level = in1.level and in2.level
    }
}

class NandGate(name: String): LogicGate21(name, "NAND") {
    override fun updateOutputs() {
        out.level = in1.level nand in2.level
    }
}

class OrGate(name: String): LogicGate21(name, "OR") {
    override fun updateOutputs() {
        out.level = in1.level or in2.level
    }
}

class NorGate(name: String): LogicGate21(name, "NOR") {
    override fun updateOutputs() {
        out.level = in1.level nor in2.level
    }
}

class XorGate(name: String): LogicGate21(name, "XOR") {
    override fun updateOutputs() {
        out.level = in1.level xor in2.level
    }
}

class XnorGate(name: String): LogicGate21(name, "XNOR") {
    override fun updateOutputs() {
        out.level = in1.level xnor in2.level
    }
}

class NotGate(name: String): InternalModule(name, "NOT") {
    val inp: Pin = Pin("in", PinMode.Input)
    val out: Pin = Pin("out", PinMode.Output)

    override val inputs: Map<String, Port> get() = mapOf(
        inp.name to inp,
    )

    override val outputs: Map<String, Port> get() = mapOf(
        out.name to out,
    )

    override fun updateOutputs() {
        out.level = !inp.level
    }
}
