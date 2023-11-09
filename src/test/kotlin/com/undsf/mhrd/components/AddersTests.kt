package com.undsf.mhrd.components

import com.undsf.mhrd.components.internal.AndGate
import com.undsf.mhrd.components.internal.HalfAdder
import com.undsf.mhrd.components.internal.NandGate
import com.undsf.mhrd.components.internal.XorGate
import com.undsf.mhrd.domains.Level
import com.undsf.mhrd.domains.Level.*
import com.undsf.mhrd.domains.PinMode
import kotlin.test.*

class AddersTests {
    @Test
    fun testInternalHalfAdder() {
        println("正在测试内部半加器")
        val adder = HalfAdder("adder")
        testHalfAdder(adder, 1, Low, Low, Low, Low)
        testHalfAdder(adder, 2, Low, High, High, Low)
        testHalfAdder(adder, 3, High, Low, High, Low)
        testHalfAdder(adder, 4, High, High, Low, High)
    }

    @Test
    fun testModuleHalfAdder() {
        println("正在测试自定义半加器")
        val adder = createModuleHalfAdder()
        testHalfAdder(adder, 1, Low, Low, Low, Low)
        testHalfAdder(adder, 2, Low, High, High, Low)
        testHalfAdder(adder, 3, High, Low, High, Low)
        testHalfAdder(adder, 4, High, High, Low, High)
    }

    @Test
    fun testModuleNandHalfAdder() {
        println("正在测试全NAND半加器")
        val adder = createNandHalfAdder()
        testHalfAdder(adder, 1, Low, Low, Low, Low)
        testHalfAdder(adder, 2, Low, High, High, Low)
        testHalfAdder(adder, 3, High, Low, High, Low)
        testHalfAdder(adder, 4, High, High, Low, High)
    }

    private fun createModuleHalfAdder(): Module {
        val adder = object: Module("adder", "HalfAdder") {
            // inputs
            val in1 = Pin("in1", PinMode.Input)
            val in2 = Pin("in2", PinMode.Input)

            // outputs
            val out = Pin("out", PinMode.Output)
            val carry = Pin("carry", PinMode.Output)

            // parts
            val xor = XorGate("xor")
            val and = AndGate("and")

            init {
                declareInputs(in1, in2)
                declareOutputs(out, carry)
                declareParts(xor, and)
            }

            override fun sortParts() {
                this.updatablePartList.clear()
                this.updatablePartList.addAll(
                    listOf(xor, and)
                )
            }
        }

        with(adder) {
            declareWires(
                in1 - xor.in1,
                in2 - xor.in2,
                in1 - and.in1,
                in2 - and.in2,
                xor.out - out,
                and.out - carry,
            )
        }

        adder.connectParts()
        return adder
    }

    private fun createNandHalfAdder(): Module {
        val adder = object: Module("adder", "HalfAdder4Nand") {
            // inputs
            val in1 = Pin("in1", PinMode.Input)
            val in2 = Pin("in2", PinMode.Input)

            // outputs
            val out = Pin("out", PinMode.Output)
            val carry = Pin("carry", PinMode.Output)

            // parts
            val xora = NandGate("xora")
            val xorb1 = NandGate("xorb1")
            val xorb2 = NandGate("xorb2")
            val xorc = NandGate("xorc")
            val not = NandGate("not")

            init {
                declareInputs(in1, in2)
                declareOutputs(out, carry)
                declareParts(
                    xora, xorb1, xorb2, xorc,
                    not
                )
            }

            override fun sortParts() {
                this.updatablePartList.clear()
                this.updatablePartList.addAll(
                    listOf(xora, xorb1, xorb2, xorc, not)
                )
            }
        }

        with(adder) {
            declareWires(
                in1 - xora.in1, in2 - xora.in2,
                in1 - xorb1.in1, xora.out - xorb1.in2,
                in2 - xorb2.in1, xora.out - xorb2.in2,
                xorb1.out - xorc.in1, xorb2.out - xorc.in2,
                xorc.out - out,

                xora.out - not.in1, xora.out - not.in2,
                not.out - carry,
            )
        }

        adder.connectParts()
        return adder
    }

    private fun createFullAdder(): Module {
        val adder = object: Module("adder", "FullAdder") {
            // inputs
            val in1 = Pin("in1", PinMode.Input)
            val in2 = Pin("in2", PinMode.Input)
            val carryIn = Pin("carryIn", PinMode.Input)

            // outputs
            val out = Pin("out", PinMode.Output)
            val carryOut = Pin("carryOut", PinMode.Output)

            // parts
            val ha1a = NandGate("ha1a")
            val ha1b1 = NandGate("ha1b1")
            val ha1b2 = NandGate("ha1b2")
            val ha1c = NandGate("ha1c")
            val ha2a = NandGate("ha2a")
            val ha2b1 = NandGate("ha2b1")
            val ha2b2 = NandGate("ha2b2")
            val ha2c = NandGate("ha2c")
            val faco = NandGate("faco")

            init {
                declareInputs(in1, in2, carryIn)
                declareOutputs(out, carryOut)
                declareParts(
                    ha1a, ha1b1, ha1b2, ha1c,
                    ha2a, ha2b1, ha2b2, ha2c,
                    faco,
                )
            }

            override fun sortParts() {
                this.updatablePartList.clear()
                this.updatablePartList.addAll(
                    listOf(
                        ha1a, ha1b1, ha1b2, ha1c,
                        ha2a, ha2b1, ha2b2, ha2c,
                        faco
                    )
                )
            }
        }

        with(adder) {
            declareWires(
                // Half Adder 1
                in1 - ha1a.in1, in2 - ha1a.in2,
                in1 - ha1b1.in1, ha1a.out - ha1b1.in2,
                in2 - ha1b2.in2, ha1a.out - ha1b2.in1,
                ha1b1.out - ha1c.in1, ha1b2.out - ha1c.in2,

                // Half Adder 2
                ha1c.out - ha2a.in1, carryIn - ha2a.in2,
                ha1c.out - ha2b1.in1, ha2a.out - ha2b1.in2,
                carryIn - ha2b2.in2, ha2a.out - ha2b2.in1,
                ha2b1.out - ha2c.in1, ha2b2.out - ha2c.in2,

                // out
                ha2c.out - out,

                // carryOut
                ha1a.out - faco.in1, ha2a.out - faco.in2,
                faco.out - carryOut
            )
        }

        adder.connectParts()
        return adder
    }

    private fun testHalfAdder(adder: Part, tick: Int, lv1: Level, lv2: Level, expectedOut: Level, expectedCarry: Level) {
        println("正在测试${lv1}+${lv2}")

        val in1 = adder.inputs["in1"] as Pin
        val in2 = adder.inputs["in2"] as Pin
        val out = adder.outputs["out"] as Pin
        val carry = adder.outputs["carry"] as Pin

        in1.level = lv1
        in2.level = lv2
        adder.updateWithTick(tick)

        println("${lv1}+${lv2}=${carry.value}${out.value}")
        assertEquals(expectedOut, out.level)
        assertEquals(expectedCarry, carry.level)
    }
}