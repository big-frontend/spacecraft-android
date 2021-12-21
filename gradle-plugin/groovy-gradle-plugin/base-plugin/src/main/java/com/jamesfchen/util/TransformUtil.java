package com.jamesfchen.util;

import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.TransformOutputProvider;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Copyright ® $ 2021
 * All right reserved.
 *
 * author jamesfchen
 * since 六月/24/2021  星期四
 */
public class TransformUtil {
    public static File toNextTransform(QualifiedContent qualifiedContent, TransformOutputProvider outputProvider) throws IOException {
        Format format;
        if (qualifiedContent instanceof DirectoryInput) {
            format = Format.DIRECTORY;
        } else {
            format = Format.JAR;
        }
        File dest = outputProvider.getContentLocation(
                qualifiedContent.getName(), qualifiedContent.getContentTypes(),
                qualifiedContent.getScopes(), format
        );
        if (qualifiedContent instanceof DirectoryInput) {
            FileUtils.copyDirectory(qualifiedContent.getFile(), dest);
        } else {
            FileUtils.copyFile(qualifiedContent.getFile(), dest);
        }
        return dest;
    }
}
