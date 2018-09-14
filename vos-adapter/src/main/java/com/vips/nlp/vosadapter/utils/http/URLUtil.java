package com.vips.nlp.vosadapter.utils.http;

import lombok.extern.log4j.Log4j2;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

@Log4j2
public class URLUtil {

    public static final String UTF8 = "utf-8";

    /**
     * 字符串编码
     * @param str 编码字符
     * @param enc 使用编码
     * @return
     */
    public static String encode(String str, String enc){
        String result = null;
        try {
            result =  URLEncoder.encode(str, enc);
        } catch (UnsupportedEncodingException e) {
           log.info("URLEncoder异常, 错误信息：{}", e.getMessage());
        }
        return result;
    }

    /**
     * 字符串解码
     * @param str 解码字符串
     * @param enc 使用编码
     * @return
     */
    public static String decode(String str, String enc){
        String result = null;
        try {
            result =  URLDecoder.decode(str, enc);
        } catch (UnsupportedEncodingException e) {
            log.info("URLDecoder异常, 错误信息：{}", e.getMessage());
        }
        return result;
    }

}
