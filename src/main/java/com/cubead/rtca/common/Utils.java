package com.cubead.rtca.common;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    /**
     * 从ip的字符串形式得到字节数组形式
     * @param ip 字符串形式的ip
     * @return 字节数组形式的ip
     */
    public static byte[] getIpByteArrayFromString(String ip) {
        byte[] ret = new byte[4];
        java.util.StringTokenizer st = new java.util.StringTokenizer(ip, ".");
        try {
            ret[0] = (byte)(Integer.parseInt(st.nextToken()) & 0xFF);
            ret[1] = (byte)(Integer.parseInt(st.nextToken()) & 0xFF);
            ret[2] = (byte)(Integer.parseInt(st.nextToken()) & 0xFF);
            ret[3] = (byte)(Integer.parseInt(st.nextToken()) & 0xFF);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ret;
    }
    public static boolean isSimpleType(Class<?> clazz) {
        return (clazz.isPrimitive()
                || String.class.isAssignableFrom(clazz)
                || Boolean.class.isAssignableFrom(clazz)
                || Integer.class.isAssignableFrom(clazz)
                || Long.class.isAssignableFrom(clazz)
                || Float.class.isAssignableFrom(clazz)
                || Character.class.isAssignableFrom(clazz)
                || Short.class.isAssignableFrom(clazz)
                || Double.class.isAssignableFrom(clazz)
                || Byte.class.isAssignableFrom(clazz)
                || BigDecimal.class.isAssignableFrom(clazz)
                || byte[].class.isAssignableFrom(clazz));
    }
    public static boolean isBlank(Object o) {
        if (o == null)
            return true;
        Class<?> clazz = o.getClass();
        if (isSimpleType(clazz)){
            return "".equals(o.toString().trim());
        } else if (clazz.isArray()){
            return Array.getLength(o) <= 0;
        } else if (Collection.class.isAssignableFrom(clazz)) {
            return ((Collection<?>) o).size() <= 0;
        } else if (Map.class.isAssignableFrom(clazz)) {
            return ((Map<?, ?>) o).size() <= 0;
        } else if (File.class.isAssignableFrom(clazz)){
            return !((File)o).exists() || (((File)o).length() == 0);
        }
        return false;
    }
//    public static void main(String args[]){
//        byte[] a=getIpByteArrayFromString(args[0]);
//        for(int i=0;i< a.length;i++)
//            System.out.println(a[i]);
//        System.out.println(getIpStringFromBytes(a));
//    }
    /**
     * 对原始字符串进行编码转换，如果失败，返回原始的字符串
     * @param s 原始字符串
     * @param srcEncoding 源编码方式
     * @param destEncoding 目标编码方式
     * @return 转换编码后的字符串，失败返回原始字符串
     */
    public static String getString(String s, String srcEncoding, String destEncoding) {
        try {
            return new String(s.getBytes(srcEncoding), destEncoding);
        } catch (UnsupportedEncodingException e) {
            return s;
        }
    }

    /**
     * 根据某种编码方式将字节数组转换成字符串
     * @param b 字节数组
     * @param encoding 编码方式
     * @return 如果encoding不支持，返回一个缺省编码的字符串
     */
    public static String getString(byte[] b, String encoding) {
        try {
            return new String(b, encoding);
        } catch (UnsupportedEncodingException e) {
            return new String(b);
        }
    }

    /**
     * 根据某种编码方式将字节数组转换成字符串
     * @param b 字节数组
     * @param offset 要转换的起始位置
     * @param len 要转换的长度
     * @param encoding 编码方式
     * @return 如果encoding不支持，返回一个缺省编码的字符串
     */
    public static String getString(byte[] b, int offset, int len, String encoding) {
        try {
            return new String(b, offset, len, encoding);
        } catch (UnsupportedEncodingException e) {
            return new String(b, offset, len);
        }
    }

    /**
     * @param ip ip的字节数组形式
     * @return 字符串形式的ip
     */
    public static String getIpStringFromBytes(byte[] ip) {
        StringBuffer sb = new StringBuffer();
        sb.append(ip[0] & 0xFF);
        sb.append('.');
        sb.append(ip[1] & 0xFF);
        sb.append('.');
        sb.append(ip[2] & 0xFF);
        sb.append('.');
        sb.append(ip[3] & 0xFF);
        return sb.toString();
    }
    //是否是中文
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    //是否乱码
    public static boolean isMessyCode(String strName) {
        Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = ch.length;
        float count = 0;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!Character.isLetterOrDigit(c)) {
                if (!isChinese(c)) {
                    count = count + 1;
                }
            }
        }
        float result = count / chLength;
        if (result > 0.4) {
            return true;
        } else {
            return false;
        }

    }
    public static String convertCode(String s, String from_charset, String to_charset){
        if(s != null){
            try {
                return new String(s.getBytes(from_charset), to_charset);
            }
            catch (UnsupportedEncodingException ex) {
                return s;
            }
        }
        return "";
    }

    public static String iso2gbk(String s){
        return convertCode(s, "ISO8859_1", "GBK");
    }

    public static String gbk2iso(String s){
        return convertCode(s, "GBK", "ISO8859_1");
    }

    public static String gbk2utf8(String s){
        return convertCode(s, "GBK", "UTF-8");
    }

    public static String utf82iso(String s){
        return convertCode(s, "UTF-8", "ISO8859_1");
    }

    public static String utf82gbk(String s){
        return convertCode(s, "UTF-8", "GBK");
    }

}
