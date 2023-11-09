package com.undsf.mhrd.components

import com.undsf.mhrd.components.internal.*
import com.undsf.mhrd.domains.PinMode

open class Module(
    name: String,
    override val type: String = "自定义组件",
): Part(name) {
    override val inputs: MutableMap<String, Port> = mutableMapOf()
    override val outputs: MutableMap<String, Port> = mutableMapOf()
    override val parts: MutableMap<String, Part> = mutableMapOf()
    override val wires: MutableList<Wire<*>> = mutableListOf()

    protected val updatablePartList: MutableList<Part> = mutableListOf()

    private val sortedParts: List<Part> get() {
        if (updatablePartList.isEmpty()) {
            sortParts()
        }
        return updatablePartList
    }

    open fun sortParts() {
        // TODO 根据连接顺序对parts中的元素进行排序
    }

    fun declareInputPin(name: String) {
        inputs[name] = Pin(name, PinMode.Input)
    }

    fun declareInputBus(name: String, size: Int) {
        inputs[name] = Bus(name, PinMode.Input, size)
    }

    fun declareInputs(vararg ports: Port) {
        for (port in ports) {
            this.inputs[port.name] = port
        }
    }

    fun declareOutputPin(name: String) {
        outputs[name] = Pin(name, PinMode.Output)
    }

    fun declareOutputBus(name: String, size: Int) {
        outputs[name] = Bus(name, PinMode.Output, size)
    }

    fun declareOutputs(vararg ports: Port) {
        for (port in ports) {
            this.outputs[port.name] = port
        }
    }

    fun declarePart(name: String, type: String) {
        var part: Part? = null
        when (type) {
            "AND" -> part = AndGate(name)
            "NAND" -> part = NandGate(name)
            "OR" -> part = OrGate(name)
            "NOR" -> part = NorGate(name)
            "XOR" -> part = XorGate(name)
            "XNOR" -> part = XnorGate(name)
            "NOT" -> part = NotGate(name)
            "HALF_ADDER" -> part = HalfAdder(name)
            "FULL_ADDER" -> part = FullAdder(name)
            "DECODER_38" -> part = Decoder38(name)
        }

        if (part != null) {
            parts[name] = part
        }
    }

    fun declareParts(vararg parts: Part) {
        for (part in parts) {
            this.parts[part.name] = part
        }
    }

    fun declareWires(vararg wires: Wire<*>) {
        for (wire in wires) {
            this.wires.add(wire)
        }
    }

    fun connectParts() {
        for (wire in wires) {
            wire.connect()
        }
    }

    override fun updateOutputs() {
        // 先更新组件
        for (part in sortedParts) {
            part.updateWithTick(updatingTick)
        }

        for ((name, port) in outputs) {
            port.update()
        }
    }
}