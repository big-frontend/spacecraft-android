package com.jamesfchen.mockserver.util;

import com.jamesfchen.mockserver.DispatcherImpl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawskjamesf
 * @since: Sep/25/2018  Tue
 */
public class RestServiceTestHelper {
    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public static String getStringFromFile(String filePath) throws Exception {
        final InputStream stream = DispatcherImpl.getContext().getResources().getAssets().open(filePath);
        String ret = convertStreamToString(stream);
        //Make sure you close all streams.
        stream.close();
        return ret;
    }
}
