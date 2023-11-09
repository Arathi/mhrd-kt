package com.undsf.mhrd.components

import com.undsf.mhrd.domains.PinMode

abstract class Port(
    val name: String,
    val mode: PinMode,
    val owner: Part? = null,
) {
    /**
     * 值
     */
    abstract val value: Int

    /**
     * 更新电平
     */
    abstract fun update()

    infix fun linkTo(dest: Port): Wire<*> {
        return Wire.build(this, dest)
    }

    operator fun minus(dest: Port): Wire<*> {
        return Wire.build(this, dest)
    }

    override fun toString(): String {
        val builder = StringBuilder()
        if (owner != null) builder.append(owner.name).append(".")
        builder.append(name)
        builder.append("(").append(value).append(")")
        return builder.toString()
    }
}