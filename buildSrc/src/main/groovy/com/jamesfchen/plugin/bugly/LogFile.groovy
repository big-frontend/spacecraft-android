package com.jamesfchen.plugin.bugly

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Get log file.
 *
 * @param logFileName name of log file
 * @return log file
 */
class LogFile {

    public static final String UPLOAD_LOG_FILE_NAME = "BuglyUploadLog.txt"
    public static String SYMBOL_LOG_FILE_NAME = "BuglySymbolLog.txt"
    public static int LOG_RECORD_MAXIMUM_NUMBER = 200
    // Separator of one record.
    public static String LOG_RECORD_SEPARATOR = " --> "

    static File getLogFile(String logFileName,def project) {

        String logDirName = getOutputDirName(project)
        if (null == logDirName) {
            logDirName = project.projectDir.getAbsolutePath()
        }
        return new File(logDirName, logFileName)
    }


    static String getOutputDirName(def project) {
        String dirName = project.bugly.outputDir
        if (dirName == null) {
            return null
        }
        if (!new File(dirName).exists()) {
            if (!new File(dirName).mkdirs()) {
                dirName = project.projectDir.getAbsolutePath() + File.separator + dirName
                if (!new File(dirName).mkdirs()) {
                    return null
                }
            }
        }
        return new File(dirName).getAbsolutePath()
    }
    /**
     * Check whether the file has been recorded to log file.
     *
     * @param file file to be checked
     * @param logFile log file.
     * @return true if the file has been recorded; false otherwise
     */
    static boolean checkLogFile(File logFile, File file) {
        if (parseLogFile(logFile, file) == null) {
            println "BuglyPlugin upload task checkLogFile false"
            return false
        }
        println "BuglyPlugin upload task checkLogFile false"
        return true
    }

    /**
     * Check whether the file has been recorded to log file and return extra info.
     *
     * @param file file to be checked
     * @param logFile log file.
     * @return extra information if the file has been recorded; null otherwise
     */
    static String parseLogFile(File logFile, File file) {
        if (!logFile.exists() || !file.exists()) {
            return null
        }
        String filePath = file.getAbsolutePath()
        String fileSha1 = getFileSha1(filePath)
        println "BuglyPlugin upload task ${fileSha1}"
        if (null == fileSha1) {
            return null
        }
        BufferedReader logReader = null
        try {
            logReader = new BufferedReader(new InputStreamReader(new FileInputStream(logFile), "utf-8"))
            String extraInfo = null
            String record
            int count = 0
            while (null != (record = logReader.readLine())) {
                if (count++ >= LOG_RECORD_MAXIMUM_NUMBER) {
//                    project.logger.info("The number of records in log file exceed the MAX_COUNT(500). It will clear the log.")
                    logFile.delete()
                    break;
                }
                String[] array = record.split(LOG_RECORD_SEPARATOR)
                if (array.length < 2) {
                    continue
                }
                String recordFilePath = array[0]
                String recordSha1 = array[1]
                if (recordFilePath == filePath && recordSha1 == fileSha1) {
                    if (array.length > 2) {
                        extraInfo = array[2]
                    } else {
                        extraInfo = ""
                    }
                }
            }
            println "BuglyPlugin upload task ${extraInfo}"
            return extraInfo
        } catch (UnsupportedEncodingException e) {
//            project.logger.warn(e.getMessage())
            return null
        } catch (FileNotFoundException e) {
//            project.logger.warn(e.getMessage())
            return null
        } finally {
            if (logReader != null) {
                try {
                    logReader.close()
                } catch (IOException e) {
//                    project.logger.warn(e.getMessage())
                }
            }
        }
    }



    /**
     * Get SHA-1 of file.
     *
     * @param fileName path name of file
     * @return SHA-1 of file
     */
    static String getFileSha1(String fileName) {
        if (null == fileName) {
            return null
        }
        FileInputStream fileInputStream = null
        MessageDigest messageDigest
        try {
            fileInputStream = new FileInputStream(new File(fileName))
            messageDigest = MessageDigest.getInstance("SHA1")
            byte[] buffer = new byte[4096]
            int length
            while ((length = fileInputStream.read(buffer)) > 0) {
                messageDigest.update(buffer, 0, length)
            }
            return byteArrayToHexString(messageDigest.digest())
        } catch (FileNotFoundException e) {
//            project.logger.warn(e.getMessage())
            return null
        } catch (IOException e) {
//            project.logger.warn(e.getMessage())
            return null
        } catch (NoSuchAlgorithmException e) {
//            project.logger.warn(e.getMessage())
            return null
        } finally {
            try {
                fileInputStream.close()
            } catch (IOException e) {
//                project.logger.warn(e.getMessage())
            }
        }
    }
    static String byteArrayToHexString(byte[] array) {
        StringBuilder stringBuilder = new StringBuilder()
        for (byte b : array) {
            int n = b & 0xFF
            if (n < 16) {
                stringBuilder.append('0')
            }
            stringBuilder.append(Integer.toHexString(n))
        }
        return stringBuilder.toString()
    }
    /**
     * Append a record to log file.
     *
     * @param file file to log
     * @param logFile log file
     * @return true if success; false otherwise
     */
    static boolean logFile(File logFile, File file) {
        logFileWithExtraInfo(logFile, file, null)
    }

    /**
     * Append a record to log file.
     *
     * @param file file to log
     * @param logFile log file
     * @param extraInfo extra information to record
     * @return true if success; false otherwise
     */
    static boolean logFileWithExtraInfo(File logFile, File file, String extraInfo) {
        BufferedWriter logWriter = null
        try {
            logWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFile, true), "utf-8"))
            if (!logWriter.write(constructRecord(file, extraInfo))) {
                return false
            }
            return true
        } catch (UnsupportedEncodingException e) {
//            project.logger.warn(e.getMessage())
            return false
        } catch (FileNotFoundException e) {
//            project.logger.warn(e.getMessage())
            return false
        } catch (IOException e) {
//            project.logger.warn(e.getMessage())
            return false
        } finally {
            if (logWriter != null) {
                try {
                    logWriter.close()
                } catch (IOException e) {
//                    project.logger.warn(e.getMessage())
                }
            }
        }
    }

    /**
     * Construct one record.
     *
     * @param file file to log
     * @return record constructed
     */
    static String constructRecord(File file, String extraInfo) {
        String filePath = file.getAbsolutePath()
        String sha1 = getFileSha1(filePath)
        if (null == sha1) {
            return null
        }
        StringBuilder stringBuilder = new StringBuilder()
        stringBuilder.append(filePath)
        stringBuilder.append(LOG_RECORD_SEPARATOR)
        stringBuilder.append(sha1)
        if (extraInfo != null) {
            stringBuilder.append(LOG_RECORD_SEPARATOR)
            stringBuilder.append(extraInfo)
        }
        stringBuilder.append("\n")
        return stringBuilder.toString()
    }

}