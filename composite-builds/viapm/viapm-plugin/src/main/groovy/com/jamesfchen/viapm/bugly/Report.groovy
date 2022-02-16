package com.jamesfchen.viapm.bugly


import org.apache.http.HttpEntity
import org.apache.http.entity.mime.MultipartEntityBuilder


class Report {

    private static final String SYMBOL_UPLOAD_URL = 'https://api.bugly.qq.com/openapi/file/upload/symbol'

    static void uploadSymtab(UploadInfo info) {
        String url =
                new StringBuilder().append(SYMBOL_UPLOAD_URL)
                        .append("?app_id=${info.appId}")
                        .append("&app_key=${info.appKey}")
                        .toString()
        println "url:" + url
        println "Uploading request body: ${info}"
        if (info == null || !info.file.exists() || info.file == null) {
            return
        }

        HttpEntity entity = MultipartEntityBuilder.create()
                .addTextBody("app_id", info.appId)
                .addTextBody("app_key", info.appKey)
                .addTextBody("api_version", "1")
                .addTextBody("channel", info.channel != null && info.channel.length() > 0 ? info.channel : "")
                .addTextBody("symbolType", info.isMapping ? "1" : "3")
                .addTextBody("bundleId", info.packageName)
                .addTextBody("productVersion", info.version)
                .addTextBody("fileName", info.file.name)
                .addBinaryBody("file", info.file.getAbsoluteFile())
                .build()
//        Api.post(url, entity)

    }

    static void uploadSymtab(List<UploadInfo> uploadInfoList) {
        uploadInfoList.each { info ->
            uploadSymtab(info)
        }

    }

    static class UploadInfo {
        public File file
        public boolean isMapping
        public String appId
        public String appKey
        public String version
        public String packageName
        public String channel

        @Override
        public String toString() {
            return "UploadInfo{" +
                    "file=" + file +
                    ", isMapping=" + isMapping +
                    ", appId='" + appId + '\'' +
                    ", appKey='" + appKey + '\'' +
                    ", version='" + version + '\'' +
                    ", packageName='" + packageName + '\'' +
                    ", channel='" + channel + '\'' +
                    '}';
        }
    }
}




