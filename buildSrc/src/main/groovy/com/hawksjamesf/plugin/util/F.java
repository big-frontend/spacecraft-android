package com.hawksjamesf.plugin.util;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

/**
 * Copyright ® $ 2020
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Nov/02/2020  Mon
 */
public class F {
    public static void replace(File file, HashMap<String, String> map) {
        Set<String> keys = map.keySet();
        try (BufferedReader br = new BufferedReader(new FileReader(file)); FileWriter out = new FileWriter(file)) {
            CharArrayWriter tempStream = new CharArrayWriter();
            String line = null;
            while ((line = br.readLine()) != null) {
//                for (int i = 0; i < keys.size(); i++) {
//                    String k = keys[i];
//                    if (line.contains(k)) {
//                        System.out.println(k + " " + line + "\n");
//                        line = line.replace(line, map.get(k));
//                        break;
//                    }
//                }
                tempStream.write(line);
                tempStream.append(System.getProperty("line.separator"));
            }

            tempStream.writeTo(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
