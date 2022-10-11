package com.cjxch.supermybatis.generate.util;

import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.URL;
import java.util.Scanner;

/**
 * @Author Czy
 * @Description //TODO $
 * @Date $ $
 * @Param $
 * @return $
 **/
public class FileUtil {
    public static String readFile(String path){
        StringBuffer sb = new StringBuffer();
        ClassLoader classLoader = FileUtil.class.getClassLoader();
        URL resource = classLoader.getResource(path);
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(resource.openStream(), "utf-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\r\n");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }
}
