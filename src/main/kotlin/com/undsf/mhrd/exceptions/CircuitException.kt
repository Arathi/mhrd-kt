package com.undsf.mhrd.exceptions

open class CircuitException(message: String = "未知原因")
    : RuntimeException("电路构建异常：${message}") {
}