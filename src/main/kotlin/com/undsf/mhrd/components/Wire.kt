package com.undsf.mhrd.components

abstract class Wire<P: Port>(
    val source: P,
    val dest: P,
) {
    init {
    }

    @Throws(RuntimeException::class)
    abstract fun connect()

    companion object {
        fun build(source: Port, dest: Port): Wire<*> {
            if (source is Pin && dest is Pin) {
                return buildPinWire(source, dest)
            }
            if (source is Bus && dest is Bus) {
                return buildBusWire(source, dest)
            }
            throw RuntimeException("端口类型不匹配，无法创建线缆")
        }

        private fun buildPinWire(source: Pin, dest: Pin): PinWire {
            return PinWire(source, dest)
        }

        private fun buildBusWire(source: Bus, dest: Bus): BusWire {
            if (source.size != dest.size) {
                throw RuntimeException("总线宽度不一致，无法创建线缆")
            }
            return BusWire(source, dest)
        }
    }
}

class PinWire(source: Pin, dest: Pin): Wire<Pin>(source, dest) {
    override fun connect() {
        source.connectTo(dest)
    }

    override fun toString(): String {
        return "$source -> $dest"
    }
}

class BusWire(source: Bus, dest: Bus): Wire<Bus>(source, dest) {
    private val pinWires: List<PinWire>

    init {
        val wires: MutableList<PinWire> = mutableListOf()
        pinWires = wires
    }

    override fun connect() {
    }
}
