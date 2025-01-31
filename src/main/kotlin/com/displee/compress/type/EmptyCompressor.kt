package com.displee.compress.type

import com.displee.io.impl.InputBuffer

object EmptyCompressor : Compressor {
    override fun compress(bytes: ByteArray): ByteArray {
        return bytes
    }

    override fun decompress(buffer: InputBuffer, compressedData: ByteArray, compressedSize: Int, decompressedSize: Int, offset: Int): ByteArray {
        return buffer.readBytes(compressedSize)
    }
}