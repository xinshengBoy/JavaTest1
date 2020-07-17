package com.just.test.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MD5 {

    /**
     * MD5加密  32位
     *
     * @param str 需要加密的字符
     * @return
     */
    public static String MD5(String str) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }

        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }

            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }

    /**
     * 可逆的加密算法
     */
    public static String encryptMD5(String str) {
        char[] a = str.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ '1');
        }

        String s = new String(a);
        return s;
    }

    /**
     * base64加密
     */
    public static String Base64(String str) {
        String base = android.util.Base64.encodeToString(str.trim().getBytes(), android.util.Base64.NO_WRAP);//base64加密
        return base;
    }

    /**
     * 将网络上的文字解码转成手机上可以看的文字   即将文字解密
     *
     * @param str  还未转码的文字
     * @param code 你所要转成的格式（如GBK、UTF-8等，一般常见的都是后者）
     * @return
     */
    public static String URLDecode(String str, String code) {
        try {
            str = URLDecoder.decode(str, code);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return str;
    }

    /**
     * 将网络上的文字解码转成手机上可以看的文字   即将文字加密
     *
     * @param str  还未转码的文字
     * @param code 你所要转成的格式（如GBK、UTF-8等，一般常见的都是后者）
     * @return
     */
    public static String URLEncode(String str, String code) {
        try {
            str = URLEncoder.encode(str, code);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 获取当前系统时间
     *
     * @return
     */
    public static String getCurrentDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(System.currentTimeMillis());
        String dates = format.format(date);
        return dates;
    }
}

