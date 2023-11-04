package com.mark.tasks.impl.defs.impl

import OutputStream

data class NpcDefinition(
    val id: Int = 0,
    var name: String = "null",
    var size : Int = 1,
    var category : Int = -1,
    var models: IntArray? = null,
    var chatheadModels: IntArray? = null,
    var standingAnimation : Int = -1,
    var rotateLeftAnimation : Int = -1,
    var rotateRightAnimation : Int = -1,
    var walkingAnimation : Int = -1,
    var rotate180Animation : Int = -1,
    var rotate90RightAnimation : Int = -1,
    var rotate90LeftAnimation : Int = -1,
    var recolorToFind: IntArray? = null,
    var recolorToReplace: IntArray? = null,
    var retextureToFind: IntArray? = null,
    var retextureToReplace: IntArray? = null,
    var actions : MutableList<String?> = mutableListOf(null, null, null, null, null),
    var isMinimapVisible : Boolean = true,
    var combatLevel : Int = -1,
    var widthScale : Int = 128,
    var heightScale : Int = 128,
    var hasRenderPriority : Boolean = false,
    var ambient : Int = 0,
    var contrast : Int = 0,
    var headIconArchiveIds: IntArray? = null,
    var headIconSpriteIndex: IntArray? = null,
    var rotation : Int = 32,
    var configs: MutableList<Int>? = null,
    var varbitId : Int = -1,
    var varpIndex : Int = -1,
    var isInteractable : Boolean = true,
    var isClickable : Boolean = true,
    var isPet : Boolean = false,
    var runSequence : Int = -1,
    var runBackSequence : Int = -1,
    var runRightSequence : Int = -1,
    var runLeftSequence : Int = -1,
    var crawlSequence : Int = -1,
    var crawlBackSequence : Int = -1,
    var crawlRightSequence : Int = -1,
    var crawlLeftSequence : Int = -1,
    var params : MutableMap<Int,String> = mutableMapOf()
) {

    fun encode() : ByteArray {
        val dos = OutputStream()

        if (models != null && models!!.isNotEmpty()) {
            dos.writeByte(1)
            dos.writeByte(models!!.size)
            for (i in models!!.indices) {
                dos.writeShort(models!![i])
            }
        }

        if (name != "null") {
            dos.writeByte(2)
            dos.writeString(name)
        }

        if (size != -1) {
            dos.writeByte(12)
            dos.writeByte(size)
        }

        if (standingAnimation != -1) {
            dos.writeByte(13)
            dos.writeShort(standingAnimation)
        }

        if (walkingAnimation != -1) {
            dos.writeByte(14)
            dos.writeShort(walkingAnimation)
        }

        if (rotateLeftAnimation != -1) {
            dos.writeByte(15)
            dos.writeShort(rotateLeftAnimation)
        }

        if (rotateRightAnimation != -1) {
            dos.writeByte(16)
            dos.writeShort(rotateRightAnimation)
        }

        if (walkingAnimation != -1 || rotate180Animation != -1 || rotate90RightAnimation != -1 || rotate90LeftAnimation != -1) {
            dos.writeByte(17)
            dos.writeShort(walkingAnimation)
            dos.writeShort(rotate180Animation)
            dos.writeShort(rotate90RightAnimation)
            dos.writeShort(rotate90LeftAnimation)
        }

        if (category != -1) {
            dos.writeByte(18)
            dos.writeShort(category)
        }

        if (actions.any { it != null }) {
            for (i in 0 until actions.size) {
                if (actions[i] == null) {
                    continue
                }
                dos.writeByte(30 + i)
                dos.writeString(actions[i]!!)
            }
        }

        if (recolorToFind != null && recolorToReplace != null) {
            dos.writeByte(40)
            dos.writeByte(recolorToFind!!.size)
            repeat(recolorToFind!!.size) {
                dos.writeShort(recolorToFind!![it].toInt())
                dos.writeShort(recolorToReplace!![it].toInt())
            }
        }

        if (retextureToFind != null && retextureToReplace != null) {
            dos.writeByte(41)
            dos.writeByte(retextureToFind!!.size)
            repeat(retextureToFind!!.size) {
                dos.writeShort(retextureToFind!![it].toInt())
                dos.writeShort(retextureToReplace!![it].toInt())
            }
        }

        if (chatheadModels != null) {
            dos.writeByte(60)
            dos.writeByte(chatheadModels!!.size)
            for (i in chatheadModels!!.indices) {
                dos.writeShort(chatheadModels!![i])
            }
        }

        if (!isMinimapVisible) {
            dos.writeByte(93)
        }
        if (combatLevel != -1) {
            dos.writeByte(95)
            dos.writeShort(combatLevel)
        }


        dos.writeByte(97)
        dos.writeShort(widthScale)

        dos.writeByte(98)
        dos.writeShort(heightScale)


        if (hasRenderPriority) {
            dos.writeByte(99)
        }


        dos.writeByte(100)
        dos.writeByte(ambient)

        dos.writeByte(101)
        dos.writeByte(contrast)

        if (headIconArchiveIds != null) {
            dos.writeByte(102)
            dos.writeShort(headIconArchiveIds!!.size)
            repeat(headIconArchiveIds!!.size) {
                dos.writeShort(headIconArchiveIds!![it])
                dos.writeShort(headIconSpriteIndex!![it])
            }
        }


        dos.writeByte(103)
        dos.writeShort(rotation)
        
        if ((varbitId != -1 || varpIndex != -1) && configs != null) {
            val `var` = configs!![configs!!.size - 1]
            dos.writeByte(if (`var` != -1) 118 else 106)
            dos.writeShort(varbitId)
            dos.writeShort(varpIndex)
            if (`var` != -1) {
                dos.writeShort(`var`)
            }
            dos.writeByte(configs!!.size - 2)
            for (i in 0..configs!!.size - 2) {
                dos.writeShort(configs!![i])
            }
        }

        if (!isInteractable) {
            dos.writeByte(107)
        }

        if (!isClickable) {
            dos.writeByte(109)
        }

        if (isPet) {
            dos.writeByte(111)
        }

        if (runSequence != -1) {
            dos.writeByte(114)
            dos.writeShort(runSequence)
        }

        if (runSequence != -1) {
            dos.writeByte(115)
            dos.writeShort(runSequence)
            dos.writeShort(runBackSequence)
            dos.writeShort(runRightSequence)
            dos.writeShort(runLeftSequence)
        }

        if (crawlSequence != -1) {
            dos.writeByte(116)
            dos.writeShort(crawlSequence)
        }

        if (crawlSequence != -1) {
            dos.writeByte(117)
            dos.writeShort(crawlSequence)
            dos.writeShort(crawlBackSequence)
            dos.writeShort(crawlRightSequence)
            dos.writeShort(crawlLeftSequence)
        }

        if (params != mutableMapOf<Int, String>()) {
            dos.writeByte(249)
            dos.writeByte(params.size)
            for (key in params.keys) {
                val value: Any? = params[key]
                dos.writeByte(if (value is String) 1 else 0)
                dos.write24BitInt(key)
                if (value is String) {
                    dos.writeString(value as String?)
                } else {
                    (value as Int?)?.let { dos.writeInt(it) }
                }
            }
        }

        dos.writeByte(0)
        return dos.flip()
    }

}
