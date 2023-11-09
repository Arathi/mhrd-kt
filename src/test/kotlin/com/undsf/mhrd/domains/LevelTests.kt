package com.undsf.mhrd.domains

import kotlin.test.*
import kotlin.test.Test
import com.undsf.mhrd.domains.Level.*

class LevelTests {
    @Test
    fun testAND() {
        assertEquals(Low, Low and Low)    // 0 0 0
        assertEquals(Low, Low and High)   // 0 1 0
        assertEquals(Low, High and Low)   // 1 0 0
        assertEquals(High, High and High) // 1 1 1
    }

    @Test
    fun testNAND() {
        assertEquals(High, Low nand Low)  // 0 0 1
        assertEquals(High, Low nand High) // 0 1 1
        assertEquals(High, High nand Low) // 1 0 1
        assertEquals(Low, High nand High) // 1 1 0
    }

    @Test
    fun testOR() {
        assertEquals(Low, Low or Low)    // 0 0 0
        assertEquals(High, Low or High)  // 0 1 1
        assertEquals(High, High or Low)  // 1 0 1
        assertEquals(High, High or High) // 1 1 1
    }

    @Test
    fun testNOR() {
        assertEquals(High, Low nor Low)  // 0 0 1
        assertEquals(Low, Low nor High)  // 0 1 0
        assertEquals(Low, High nor Low)  // 1 0 0
        assertEquals(Low, High nor High) // 1 1 0
    }

    @Test
    fun testXOR() {
        assertEquals(Low, Low xor Low)   // 0 0 0
        assertEquals(High, Low xor High) // 0 1 1
        assertEquals(High, High xor Low) // 1 0 1
        assertEquals(Low, High xor High) // 1 1 0
    }

    @Test
    fun testXNOR() {
        assertEquals(High, Low xnor Low)   // 0 0 1
        assertEquals(Low, Low xnor High)   // 0 1 0
        assertEquals(Low, High xnor Low)   // 1 0 0
        assertEquals(High, High xnor High) // 1 1 1
    }

    @Test
    fun testNOT() {
        assertEquals(High, !Low) // 0 1
        assertEquals(Low, !High) // 1 0
    }
}