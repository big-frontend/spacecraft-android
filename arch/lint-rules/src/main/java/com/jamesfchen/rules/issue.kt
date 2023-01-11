package com.jamesfchen.rules

import com.android.tools.lint.checks.SdCardDetector
import com.android.tools.lint.detector.api.*

/**
 * Copyright ® $ 2021
 * All right reserved.
 *
 * @author: jamesfchen
 * @email: hawksjamesf@gmail.com
 * @since: 六月/10/2021  星期四
 */
@JvmField
val ISSUE = Issue.create(
        id = "SdCardPath",
        briefDescription = "Hardcoded reference to `/sdcard`",
        explanation = """
                Your code should not reference the `/sdcard` path directly; \
                instead use `Environment.getExternalStorageDirectory().getPath()`.

                Similarly, do not reference the `/data/data/` path directly; it \
                can vary in multi-user scenarios. Instead, use \
                `Context.getFilesDir().getPath()`.
                """,
        moreInfo = "https://developer.android.com/training/data-storage#filesExternal",
        category = Category.CORRECTNESS,
        severity = Severity.WARNING,
        androidSpecific = true,
        implementation = Implementation(
                SdCardDetector::class.java,
                Scope.JAVA_FILE_SCOPE
        )
)