package com.undsf.mhrd.components.internal

import com.undsf.mhrd.components.Part
import com.undsf.mhrd.components.Wire

abstract class InternalModule(
    name: String,
    override val type: String
): Part(name) {
    override val parts: Map<String, Part> get() = mapOf()
    override val wires: List<Wire<*>> get() = listOf()

    override fun updateOutputs() {
        TODO("内部组件需要实现update方法更新输出端口电平")
    }
}
