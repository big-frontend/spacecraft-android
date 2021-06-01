package com.jamesfchen.map;

import android.location.Location;
import android.os.Environment;
import android.telephony.CellInfo;
import android.util.JsonWriter;
import android.util.Log;

import com.jamesfchen.map.model.AppCellInfo;
import com.jamesfchen.map.model.AppLocation;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;


class FileIOUtils {
    File moviesFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
    File downloadsFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    File picturesFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    byte[] quarter_k_butter=new byte[256];//256个字节,0.25k
    byte[] one_k_butter =new byte[1024];//1k
    byte[] four_k_butter = new byte[4096];//4096个字节,4k
    byte[] eight_k_butter = new byte[8192];//4096个字节,8k
    byte[] half_m_butter =new byte[524288];//512k,0.5M
    static File defalutLbsFile = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), "lbsPath.json");
    public static void write2File(File file, Location location, List<CellInfo> cellInfoList, long count,String auth) {
        if (file != null) {
            defalutLbsFile = file;
        }
        AppLocation appLocation = AppLocation.convertSysLocation(location);
        List<AppCellInfo> appCellInfos = AppCellInfo.convertSysCellInfo(cellInfoList);
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append("{");
        contentBuilder.append("\"myId\":" + count + ",");
        contentBuilder.append("\"auth\":" +"\""+ auth +"\""+ ",");
        contentBuilder.append("\"appCellInfos\":[");
        contentBuilder.append(appCellInfos.toString());
        contentBuilder.append("],");
        contentBuilder.append(appLocation.toString());
        contentBuilder.append("}");
        ReportApi.reportLocation(contentBuilder.toString());
        Log.d("FileUtils", "write2File: " + defalutLbsFile.getAbsolutePath() + "\n" + contentBuilder);
        if (!defalutLbsFile.exists()) {
            try {
                defalutLbsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("FileUtils", "create new file error");
            }
        }
        JsonWriter jsonWriter = null;
        try {
            jsonWriter = new JsonWriter(new OutputStreamWriter(new FileOutputStream(defalutLbsFile, true)));
            jsonWriter.beginObject();
            jsonWriter.name("myId").value(count);
            jsonWriter.name("appCellInfos");
            jsonWriter.beginArray();
            for (int i = 0; i < appCellInfos.size(); i++) {
                AppCellInfo appCellInfo = appCellInfos.get(i);
                if (appCellInfo != null) {
                    jsonWriter.beginObject();
                    jsonWriter.name("bid").value(appCellInfo.bid);
                    jsonWriter.name("cdmalat").value(appCellInfo.cdmalat);
                    jsonWriter.name("cdmalon").value(appCellInfo.cdmalon);
                    jsonWriter.name("cgiage").value(appCellInfo.cgiage);
                    jsonWriter.name("cid").value(appCellInfo.cid);
                    jsonWriter.name("lac").value(appCellInfo.lac);
                    jsonWriter.name("mcc").value(appCellInfo.mcc);
                    jsonWriter.name("mnc").value(appCellInfo.mnc);
                    jsonWriter.name("nci").value(appCellInfo.nci);
                    jsonWriter.name("nid").value(appCellInfo.nid);
                    jsonWriter.name("pci").value(appCellInfo.pci);
                    jsonWriter.name("radio_type").value(appCellInfo.radio_type);
                    jsonWriter.name("rss").value(appCellInfo.rss);
                    jsonWriter.name("sid").value(appCellInfo.sid);
                    jsonWriter.name("tac").value(appCellInfo.tac);
                    jsonWriter.endObject();
                }
            }

            jsonWriter.endArray();
            jsonWriter.name("location");
            jsonWriter.beginObject();
            jsonWriter.name("lat").value(appLocation.lat);
            jsonWriter.name("lon").value(appLocation.lon);
            jsonWriter.name("accu").value(appLocation.accu);
            jsonWriter.name("altit").value(appLocation.altit);
            jsonWriter.name("bearing").value(appLocation.bearing);
            jsonWriter.name("speed").value(appLocation.speed);
            jsonWriter.endObject();

            jsonWriter.endObject();
            jsonWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("FileUtils", "write2File: error");
        } finally {
            if (jsonWriter != null) {
                try {
                    jsonWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static int sBufferSize = 524288;

    //    public static final String bodystr="{\n" +
//            "\t\"gps\" : \"1.0\",\n" +
//            "\t\"station\":\"xxxx\"\n" +
//            "}";
    public static void readFile2OS(File file, OutputStream outputStream) {
        BufferedInputStream bufferedInputStream = null;
        try {
//            FileReader fileReader = new FileReader(file);
//            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            byte[] data = new byte[sBufferSize];
            int len;
            while ((len = bufferedInputStream.read(data)) != -1) {
                outputStream.write(data, 0, len);
            }
//            outputStream.write(bodystr.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void readString2OS(String content, OutputStream outputStream) {
        try {
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readIS(InputStream inputStream) {
        int count = 0;
        byte[] buff = new byte[4096];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            while ((count = inputStream.read(buff, 0, buff.length)) != -1) {
                baos.write(buff, 0, count);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(baos.toByteArray());
    }

}
