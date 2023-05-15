package com.mark.util

import com.beust.klaxon.Klaxon
import me.tongfei.progressbar.ProgressBar
import me.tongfei.progressbar.ProgressBarStyle
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

fun getFiles(dir : File, vararg typeList : String) : List<File> = dir.listFiles()!!.filter { typeList.find { type -> it.extension == type }!!.count() == 1  }
fun getFilesNoFilter(dir : File) : List<File> = dir.listFiles()!!.toList()