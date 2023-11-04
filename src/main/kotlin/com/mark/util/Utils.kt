package com.mark.util

import com.beust.klaxon.Klaxon
import com.displee.cache.CacheLibrary
import com.displee.cache.index.Index
import com.mark.ArchiveIndex
import com.mark.ConfigType
import me.tongfei.progressbar.ProgressBar
import me.tongfei.progressbar.ProgressBarStyle
import org.apache.commons.io.FileUtils
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.nio.file.Path
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.zip.GZIPInputStream
import kotlin.system.exitProcess


fun LocalDateTime.toEchochUTC() : Long {
    return this.toEpochSecond(ZoneOffset.UTC)
}

fun progress(task : String, amt : Long) : ProgressBar {
    return ProgressBar(
        task,
        amt,
        1,
        System.err,
        ProgressBarStyle.ASCII,
        "",
        1,
        false,
        null,
        ChronoUnit.SECONDS,
        0L,
        Duration.ZERO
    )
}

fun CacheLibrary.put(index : ArchiveIndex, id : Int, data : ByteArray) {
    this.put(index.id,id,data)
}

fun CacheLibrary.put(index : ArchiveIndex, id : String, data : ByteArray) {
    this.put(index.id,id,data)
}

fun CacheLibrary.put(index : ArchiveIndex, config : ConfigType, id : String, data : ByteArray) {
    this.put(index.id,config.id,id,data)
}

fun CacheLibrary.index(index : ArchiveIndex) : Index {
    return this.index(index.id)
}

fun progress(task : String, amt : Int) : ProgressBar {
    return ProgressBar(
        task,
        amt.toLong(),
        1,
        System.err,
        ProgressBarStyle.ASCII,
        "",
        1,
        false,
        null,
        ChronoUnit.SECONDS,
        0L,
        Duration.ZERO
    )
}

fun String.stringToTimestamp(): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss")
    val time = this.replace("T", " ").replaceAfterLast(".", "")
    return LocalDateTime.parse(time.replaceLastLetter(""), formatter)
}

fun String.replaceLastLetter(newLetter: String): String {
    val substring = this.substring(0, this.length - 1)
    return substring + newLetter
}

fun Any.jsonToString(prettyPrint: Boolean): String{
    var thisJsonString = Klaxon().toJsonString(this)
    var result = thisJsonString
    if(prettyPrint) {
        if(thisJsonString.startsWith("[")){
            result = Klaxon().parseJsonArray(thisJsonString.reader()).toJsonString(true)
        } else {
            result = Klaxon().parseJsonObject(thisJsonString.reader()).toJsonString(true)
        }
    }
    return result
}


fun decompressGzipToBytes(source: Path): ByteArray {
    val output = ByteArrayOutputStream()
    GZIPInputStream(
        FileInputStream(source.toFile())
    ).use { gis ->

        // copy GZIPInputStream to ByteArrayOutputStream
        val buffer = ByteArray(1024)
        var len: Int
        while (gis.read(buffer).also { len = it } > 0) {
            output.write(buffer, 0, len)
        }
    }
    return output.toByteArray()
}

/*
 * Finds all Files in a Dir
 * @dir Base Dir
 * @typeList List of Extension names no '.'
 */
fun getFiles(dir : File, vararg typeList : String) : List<File> {
    if (dir.listFiles() == null) {
        println("Unable to find dir: $dir")
        exitProcess(0)
    }
    return FileUtils.listFiles(dir,typeList,true).toMutableList()

}
fun getFilesNoFilter(dir : File) : List<File> = dir.listFiles()!!.toList()