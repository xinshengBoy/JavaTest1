package com.just.test.tools;

/**
 * 二进制和字符串互转工具
 * Created by admin on 2017/5/22.
 */

public class BinarySystemTools {

    /**
     *二进制转字符串
     * @param b 二进制数组
     * @return 返回的字符串
     */
    public static String byteToString(byte[] b){
        String hs = "";
        for (int i=0;i<b.length;i++){
            String tmp = Integer.toHexString(b[i] & 0XFF);
            if (tmp.length() == 1){
                hs +=  "0" + tmp;
            }else {
                hs += tmp;
            }
        }
        return hs;
    }

    /**
     * 字符串转二进制数组
     * @param msg 字符串
     * @return 二进制数组
     */
    public static String stringToByte(String msg){
        if (msg == null){
            return null;
        }

        char[] strChar = msg.toCharArray();
        String result = "";
        for (int i = 0; i < strChar.length; i++) {
            result += Integer.toBinaryString(strChar[i]) + " ";
        }
        return result;
    }
}
