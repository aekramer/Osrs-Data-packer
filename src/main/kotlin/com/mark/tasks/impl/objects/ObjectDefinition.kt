package com.mark.tasks.impl.objects

import OutputStream
import java.util.*

data class ObjectDefinition(
    val id: Int = 0,
    var retextureToFind: IntArray? = null,
    var decorDisplacement : Int = 16,
    var isHollow : Boolean = false,
    var name: String  = "null",
    var objectModels: IntArray? = null,
    var objectTypes: IntArray? = null,
    var recolorToFind: IntArray? = null,
    var mapAreaId: Int = -1,
    var retextureToReplace: IntArray? = null,
    var sizeX: Int = 1,
    var sizeY: Int = 1,
    var anInt2083: Int = 0,
    var ambientSoundIds: IntArray? = null,
    var offsetX: Int = 0,
    var nonFlatShading: Boolean = false,
    var wallOrDoor: Int = -1,
    var animationId: Int = -1,
    var varbitId: Int = -1,
    var ambient: Int = 0,
    var contrast: Int = 0,
    var actions : MutableList<String?> = mutableListOf(null, null, null, null, null),
    var interactType: Int = 2,
    var mapSceneID: Int = -1,
    var blockingMask: Int = 0,
    var recolorToReplace: IntArray? = null,
    var clipped: Boolean = true,
    var modelSizeX: Int = 128,
    var modelSizeZ: Int = 128,
    var modelSizeY: Int = 128,
    var offsetZ: Int = 0,
    var offsetY: Int = 0,
    var obstructsGround: Boolean = false,
    var randomizeAnimStart: Boolean = true,
    var clipType: Int = -1,
    var category : Int = -1,
    var supportsItems: Int = -1,
    var configs: IntArray? = null,
    var isRotated: Boolean = false,
    var varpId: Int = -1,
    var ambientSoundId: Int = -1,
    var modelClipped: Boolean = false,
    var anInt2112: Int = 0,
    var anInt2113: Int = 0,
    var blocksProjectile: Boolean = true,
    var params : MutableMap<Int,String> = mutableMapOf()
) {

    fun encode() : ByteArray {
        val dos = OutputStream()

        if (objectModels != null) {
            if (objectTypes != null) {
                dos.writeByte(1)
                dos.writeByte(objectModels!!.size)
                if (objectModels!!.isNotEmpty()) {
                    for (i in 0 until objectModels!!.size) {
                        dos.writeShort(objectModels!![i])
                        dos.writeByte(objectTypes!![i])
                    }
                }
            } else {
                dos.writeByte(5)
                dos.writeByte(objectModels!!.size)
                if (objectModels!!.isNotEmpty()) {
                    for (i in 0 until objectModels!!.size) {
                        dos.writeShort(objectModels!![i])
                    }
                }
            }
        }

        if (name != "null") {
            dos.writeByte(2)
            dos.writeString(name)
        }

        if (sizeX != 1) {
            dos.writeByte(14)
            dos.writeByte(sizeX)
        }

        if (sizeY != 1) {
            dos.writeByte(15)
            dos.writeByte(sizeY)
        }

        if (interactType == 0) {
            dos.writeByte(17)
        }

        if (!blocksProjectile) {
            dos.writeByte(18)
        }

        if (wallOrDoor != -1) {
            dos.writeByte(19)
            dos.writeByte(wallOrDoor)
        }

        if (clipType == 0) {
            dos.writeByte(21)
        }

        if (nonFlatShading) {
            dos.writeByte(22)
        }

        if (modelClipped) {
            dos.writeByte(23)
        }

        if (animationId != -1) {
            dos.writeByte(24)
            dos.writeShort(animationId)
        }

        if (interactType == 1) {
            dos.writeByte(27)
        }

        if (decorDisplacement != 16) {
            dos.writeByte(28)
            dos.writeByte(decorDisplacement)
        }

        if (ambient != 0) {
            dos.writeByte(29)
            dos.writeByte(ambient)
        }

        if (contrast != 0) {
            dos.writeByte(39)
            dos.writeByte(contrast / 25)
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
            for (i in 0 until recolorToFind!!.size) {
                dos.writeShort(recolorToFind!![i].toInt())
                dos.writeShort(recolorToReplace!![i].toInt())
            }
        }

        if (retextureToFind != null && retextureToReplace != null) {
            dos.writeByte(41)
            dos.writeByte(retextureToFind!!.size)
            for (i in 0 until retextureToFind!!.size) {
                dos.writeShort(retextureToFind!![i].toInt())
                dos.writeShort(retextureToReplace!![i].toInt())
            }
        }

        if (category != -1) {
            dos.writeByte(61)
            dos.writeShort(category)
        }

        if (isRotated) {
            dos.writeByte(62)
        }

        if (!clipped) {
            dos.writeByte(64)
        }

        if (modelSizeX != 128) {
            dos.writeByte(65)
            dos.writeShort(modelSizeX)
        }

        if (modelSizeZ != 128) {
            dos.writeByte(66)
            dos.writeShort(modelSizeZ)
        }

        if (modelSizeY != 128) {
            dos.writeByte(67)
            dos.writeShort(modelSizeY)
        }

        if (mapSceneID != -1) {
            dos.writeByte(68)
            dos.writeShort(mapSceneID)
        }

        if (blockingMask != 0) {
            dos.writeByte(69)
            dos.writeByte(blockingMask)
        }

        if (offsetX != 0) {
            dos.writeByte(70)
            dos.writeShort(offsetX)
        }

        if (offsetZ != 0) {
            dos.writeByte(71)
            dos.writeShort(offsetZ)
        }

        if (offsetY != 0) {
            dos.writeByte(72)
            dos.writeShort(offsetY)
        }

        if (obstructsGround) {
            dos.writeByte(73)
        }

        if (isHollow) {
            dos.writeByte(74)
        }

        if (supportsItems != -1) {
            dos.writeByte(75)
            dos.writeByte(supportsItems)
        }

        if (ambientSoundId != -1 || anInt2083 != 0) {
            dos.writeByte(78)
            dos.writeShort(ambientSoundId)
            dos.writeByte(anInt2083)
        }

        if (anInt2112 != 0 || anInt2113 != 0 || anInt2083 != 0 && ambientSoundIds != null) {
            dos.writeByte(79)
            dos.writeShort(anInt2112)
            dos.writeShort(anInt2113)
            dos.writeByte(anInt2083)
            dos.writeByte(ambientSoundIds!!.size)
            for (i in ambientSoundIds!!.indices) {
                dos.writeShort(ambientSoundIds!![i])
            }
        }

        if (clipType != -1) {
            dos.writeByte(81)
            dos.writeByte(clipType)
        }

        if (mapAreaId != -1) {
            dos.writeByte(82)
            dos.writeShort(mapAreaId)
        }

        if (!randomizeAnimStart) {
            dos.writeByte(89)
        }

        if ((varbitId != -1 || varpId != -1) && configs != null && configs!!.isNotEmpty()) {
            val value: Int = configs!![configs!!.size - 1]
            dos.writeByte(if (value != -1) 92 else 77)
            dos.writeShort(varbitId)
            dos.writeShort(varpId)
            if (value != -1) {
                dos.writeShort(configs!![configs!!.size - 1])
            }
            dos.writeByte(configs!!.size - 2)
            for (i in 0..configs!!.size - 2) {
                dos.writeShort(configs!![i])
            }
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
