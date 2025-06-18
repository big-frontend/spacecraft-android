package com.electrolytej.tool

import android.content.ContentResolver
import android.database.Cursor
import com.facebook.stetho.dumpapp.ArgsHelper
import com.facebook.stetho.dumpapp.DumpUsageException
import com.facebook.stetho.dumpapp.DumperContext
import com.facebook.stetho.dumpapp.DumperPlugin
import com.electrolytej.tool.platform.LogDBContract
import java.io.PrintStream

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Dec/16/2019  Mon
 */
class DBDumperPlugin(val contentResolver: ContentResolver) : DumperPlugin {
    companion object {
        private const val NAME = "log"
        private const val CMD_LIST = "list"
        private const val CMD_CLEAR = "clear"
        private const val CMD_DELETE = "delete"
        private const val CMD_REFRESH = "refresh"
    }

    override fun dump(dumpContext: DumperContext?) {
        val writer = dumpContext?.stdout
        val argsIter = dumpContext?.argsAsList?.iterator()
        val command = ArgsHelper.nextOptionalArg(argsIter, null)
        if (CMD_LIST.equals(command, true)) {
            val cursor: Cursor? = contentResolver.query(
                    LogDBContract.CONTENT_URI,
                    null /* projection */,
                    null /* selection */,
                    null /* selectionArgs */,
                    LogDBContract.LogEntity.COLUMNS_ID)

            var count = 0

            while (cursor?.moveToNext() != null) {
                writer?.println(String.format("Row #%d", count++))
                for (i in 0 until cursor.columnCount) {
                    writer?.println(String.format("  %s: %s", cursor.getColumnName(i), cursor.getString(i)))
                }
            }

            writer?.println()
            writer?.println("successful")
        } else {
            usage(writer)
            if (command != null) {
                throw DumpUsageException("Unknown command: $command")
            }
        }

    }

    override fun getName(): String = NAME

    private fun usage(writer: PrintStream?) {
        if (writer == null) return
        val cmdName = "dumpapp $NAME"
        val usagePrefix = "Usage: $cmdName "
        writer.println("$usagePrefix<command> [command-options]")
        writer.print(usagePrefix + CMD_LIST)
        writer.println()
        writer.print(usagePrefix + CMD_CLEAR)
        writer.println()
        writer.print("$usagePrefix$CMD_DELETE <rowId>")
        writer.println()
        writer.print(usagePrefix + CMD_REFRESH)
        writer.println()
    }
}