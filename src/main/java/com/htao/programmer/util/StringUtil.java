package com.htao.programmer.util;


/**
 * @Author: kedaya55
 * @Date: 2019-12-21 13:45
 */
public class StringUtil {
    public static String generateSn(String prefix,String suffix){
        return prefix+System.currentTimeMillis()+suffix;
    }
}
