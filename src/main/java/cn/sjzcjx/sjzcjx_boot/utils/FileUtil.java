package cn.sjzcjx.sjzcjx_boot.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : hasd
 * @version 1.0.0
 * @since : 2023/11/17 16:14
 **/


public class FileUtil {

    public static Map<String, Object> SplitFileName(String fileName) {
        HashMap<String, Object> map = new HashMap<>();
        int dotIndex = fileName.indexOf(".");
        String beforeDot = fileName.substring(0, dotIndex);
        String afterDot = fileName.substring(dotIndex + 1);
        map.put("name", beforeDot);
        map.put("type", afterDot);
        return map;
    }
}
