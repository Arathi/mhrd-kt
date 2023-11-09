package com.undsf.mhrd.components

import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger(Part::class.java)

abstract class Part(
    val name: String,
) {
    private var tick: Int = 0
    protected var updatingTick: Int = 0

    /**
     * 输入端口
     */
    abstract val inputs: Map<String, Port>

    /**
     * 输出端口
     */
    abstract val outputs: Map<String, Port>

    /**
     * 内部组件
     */
    abstract val parts: Map<String, Part>

    /**
     * 线路
     */
    abstract val wires: List<Wire<*>>

    /**
     * 类型名称
     */
    abstract val type: String

    /**
     * 更新输入端口电平
     */
    open fun updateInputs() {
        for ((name, port) in inputs) {
            // logger.info("正在更新${port}")
            port.update()
        }
    }

    /**
     * 更新输出端口电平
     */
    abstract fun updateOutputs()

    /**
     * 根据当前tick更新
     */
    open fun updateWithTick(tick: Int) {
        if (this.tick >= tick) {
            logger.warn("${this}已更新，当前刻：${this.tick}，更新刻：${tick}")
            return
        }

        try {
            updatingTick = tick

            // logger.debug("开始更新${this}输入端口")
            updateInputs()
            // logger.debug("完成更新${this}输入端口")

            // logger.debug("开始更新${this}输出端口")
            updateOutputs()
            // logger.debug("完成更新${this}输出端口")

            this.tick = updatingTick
        }
        catch (ex: Exception) {
            logger.warn("更新${this}第${tick}刻出现异常", ex)
        }
    }

    override fun toString(): String {
        return "$type($name)"
    }
}