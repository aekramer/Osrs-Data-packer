package com.mark.tasks.impl.defs.impl

import OutputStream
import java.io.DataOutputStream
import java.io.IOException

data class SequenceDefinition(
    val id: Int = 0,
    var frameIDs: IntArray? = null,
    var chatFrameIds: IntArray? = null,
    var frameLengths: IntArray? = null,
    var frameSounds: IntArray? = null,
    var frameStep: Int = -1,
    var interleaveLeave: IntArray? = null,
    var stretches: Boolean = false,
    var forcedPriority: Int = 5,
    var leftHandItem: Int = -1,
    var rightHandItem: Int = -1,
    var maxLoops: Int = 99,
    var precedenceAnimating: Int = -1,
    var priority: Int = -1,
    var skeletalId : Int = -1,
    var skeletalRangeBegin : Int = -1,
    var skeletalRangeEnd : Int = -1,
    var replyMode: Int = 2,
    var skeletalSounds : MutableMap<Int,Int> = emptyMap<Int, Int>().toMutableMap(),
    var mask : BooleanArray? = null,
) {

    fun encode() : ByteArray {
        val dos = OutputStream()
        if (frameLengths != null && frameIDs != null) {
            dos.writeByte(1)
            dos.writeShort(frameLengths!!.size)
            for (i in 0 until frameLengths!!.size) {
                dos.writeShort(frameLengths!![i])
            }
            for (i in 0 until frameIDs!!.size) {
                dos.writeShort(frameIDs!![i])
            }
            for (i in 0 until frameIDs!!.size) {
                dos.writeShort(frameIDs!![i] shr 16)
            }
        }
        if (frameStep != -1) {
            dos.writeByte(2)
            dos.writeShort(frameStep)
        }
        if (interleaveLeave != null) {
            dos.writeByte(3)
            dos.writeByte(interleaveLeave!!.size - 1)
            for (index in 0 until interleaveLeave!!.size - 1) {
                dos.writeByte(interleaveLeave!![index])
            }
        }
        if (stretches) {
            dos.writeByte(4)
        }
        if (forcedPriority != 5) {
            dos.writeByte(5)
            dos.writeByte(forcedPriority)
        }
        if (leftHandItem != -1) {
            dos.writeByte(6)
            dos.writeShort(leftHandItem)
        }
        if (rightHandItem != -1) {
            dos.writeByte(7)
            dos.writeShort(rightHandItem)
        }
        if (maxLoops != 99) {
            dos.writeByte(8)
            dos.writeByte(maxLoops)
        }
        if (precedenceAnimating != -1) {
            dos.writeByte(9)
            dos.writeByte(precedenceAnimating)
        }
        if (priority != -1) {
            dos.writeByte(10)
            dos.writeByte(priority)
        }

        if (replyMode != 2) {
            dos.writeByte(11)
            dos.writeByte(replyMode)
        }

        if (chatFrameIds != null && chatFrameIds!!.isNotEmpty()) {
            dos.writeByte(12)
            dos.writeByte(chatFrameIds!!.size)
            for (i in 0 until chatFrameIds!!.size) {
                dos.writeShort(chatFrameIds!!.get(i))
            }
            for (i in 0 until chatFrameIds!!.size) {
                dos.writeShort(chatFrameIds!!.get(i) shr 16)
            }
        }

        if (frameSounds != null && frameSounds!!.isNotEmpty()) {
            dos.writeByte(13)
            dos.writeByte(frameSounds!!.size)
            for (i in 0 until frameSounds!!.size) {
                dos.write24BitInt(frameSounds!![i])
            }
        }

        if (skeletalId != -1) {
            dos.writeByte(14)
            dos.writeInt(skeletalId)
        }

        if (skeletalSounds.isNotEmpty()) {
            dos.writeByte(15)
            dos.writeShort(skeletalSounds.size)
            skeletalSounds.forEach {
                dos.writeShort(it.key)
                dos.write24BitInt(it.value)
            }
        }

        if (skeletalRangeBegin != -1 || skeletalRangeEnd != -1) {
            dos.writeByte(16)
            dos.writeShort(skeletalRangeBegin)
            dos.writeShort(skeletalRangeEnd)
        }

        if (mask != null) {
            dos.writeByte(17)


            dos.writeByte(mask!!.filter { it }.size)
            mask!!.forEachIndexed { index, state ->
                if(state) {
                    dos.writeByte(index)
                }
            }
        }

        dos.writeByte(0)
        return dos.flip()
    }


}