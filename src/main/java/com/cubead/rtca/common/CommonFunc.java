package com.cubead.rtca.common;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;

import com.cubead.rtca.model.Constant;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

/**
 * Created by xiaoao on 5/7/15.
 */
public class CommonFunc {
    private static final Logger log = Logger.getLogger(CommonFunc.class);
    private static Pattern pattern = Pattern.compile("(\\d{1,3}\\.){3}\\d{1,3}");
    public static final String CS_GBK = "GBK";
    public static final String CS_GB2312 = "GB2312";
    public static final String CS_UTF8 = "UTF-8";

    public static int getIntValue(Map map, String key, int def) {
        Object value = map.get(key);
        if (value == null)
            return def;
        else {
            try {
                return Integer.parseInt(value.toString());
            } catch (Exception exp) {
                return def;
            }
        }
    }
    public static HashMap<String, String> getBlackList(){
    	long startTime = System.currentTimeMillis();
        HashMap<String, String> retMap = new HashMap<String, String> ();
        HTableInterface table=null;
        try {
            Scan scan=new Scan();
            SingleColumnValueFilter fStatus = new SingleColumnValueFilter(
                    Bytes.toBytes("f"),
                    Bytes.toBytes("status"),
                    CompareFilter.CompareOp.EQUAL,
                    new BinaryComparator(Bytes.toBytes(String.valueOf(1))));
            scan.setFilter(fStatus);
            scan.setCaching(100);

            table=HbaseTool.getTable(Constant.CA_BLACKLIST);
            ResultScanner scanner;
            try {
                scanner = table.getScanner(scan);
                for (Result result : scanner) {
                    retMap.put(Bytes.toString(result.getRow()),Bytes.toString(result.getValue(Bytes.toBytes("f"), Bytes.toBytes("ip"))));
                }
                scanner.close();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(),e);
            }

        }
        finally{
            if(table!=null)
                try {
                    table.close();
                } catch (IOException e) {
                    //ignore
                }
        }
        log.info("Execute Method:getBlackList() cost " + (System.currentTimeMillis() - startTime) + " ms.");
        return retMap;
    }
    /**
     * @param url
     * @return
     */
    public static String  getIpOfStr(String url){

        Matcher m = pattern.matcher(url);
        String result = "";
        while (m.find()) {
            result = m.group();
            if (!"".equals(result)) {
                break;
            }
        }

        return result;
    }
    public static long getLongValue(Map map, String key, long def) {
        Object value = map.get(key);
        if (value == null)
            return def;
        else {
            try {
                return Long.parseLong(value.toString());
            } catch (Exception exp) {
                return def;
            }
        }
    }

    public static String getStringValue(Map map, String key, String def) {
        Object value = map.get(key);
        if (value == null)
            return def;
        else
            return value.toString();
    }

    public static boolean getStringValue(Map map, String key, boolean def) {
        Object value = map.get(key);
        if (value == null)
            return def;
        else
            return Boolean.parseBoolean(value.toString());
    }

    public static void loadParameters(String file, Properties params) {
        if (params == null)
            return;

        InputStream in = null;
        try {
            in = new FileInputStream(new File(file));
            params.load(in);
        } catch (Exception exp) {
            log.warn(" meet error when load parameter from " + file + " :"
                    + exp.getMessage());
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (Exception exp) {
            }
        }
    }

    public static Map<String, String> parseParameter(String query) {
        String rawParams = query;
        Map<String, String> params = new HashMap<String, String>();

        if (rawParams == null || rawParams.isEmpty())
            return params;

        String[] items = rawParams.split("&");
        String tmp[] = null;
        for (String item : items) {
            tmp = item.split("=");
            params.put(tmp[0], tmp.length > 1 ? tmp[1] : "");
        }
        return params;
    }

    /**
     * close IO object don't throw exception
     *
     * @param obj
     */
    public static void closeIO(Object obj) {
        if (obj == null)
            return;

        try {
            if (obj instanceof InputStream)
                ((InputStream) obj).close();
            else if (obj instanceof OutputStream)
                ((OutputStream) obj).close();
            else if (obj instanceof Reader)
                ((Reader) obj).close();
            else if (obj instanceof Writer)
                ((Writer) obj).close();
            else if (obj instanceof FileChannel)
                ((FileChannel) obj).close();
            else if (obj instanceof Connection)
                ((Connection) obj).close();
            else if (obj instanceof Statement)
                ((Statement) obj).close();
            else if (obj instanceof PreparedStatement)
                ((PreparedStatement) obj).close();
            else if (obj instanceof ResultSet)
                ((ResultSet) obj).close();
        } catch (Exception exp) {
            log.warn("close io object meet error:" + exp.getMessage());
        }
    }

    public static void transmitFile(String filePath, OutputStream out,
                                    boolean gzip) throws Exception {
        InputStream src = null;
        OutputStream dest = null;

        try {
            src = new BufferedInputStream(new FileInputStream(filePath));
            if (gzip)
                dest = new GZIPOutputStream(out, 1280 * 1024);
            else
                dest = out;

            byte[] buff = new byte[1024 * 1024];
            int len = 0;
            while ((len = src.read(buff)) > 0) {
                dest.write(buff, 0, len);
            }

            if (dest instanceof GZIPOutputStream)
                ((GZIPOutputStream) dest).finish();
            dest.flush();
        } catch (Exception exp) {
            throw exp;
        } finally {
            closeIO(src);
            closeIO(dest);
        }
    }

    public static String unescape(String src) {
        StringBuilder tmp = new StringBuilder();
        tmp.ensureCapacity(src.length());
        int lastPos = 0, pos = 0;
        char ch;
        while (lastPos < src.length()) {
            pos = src.indexOf("%", lastPos);
            if (pos == lastPos) {
                if (pos + 6 <= src.length() && src.charAt(pos + 1) == 'u') {
                    ch = (char) Integer.parseInt(
                            src.substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                } else if (pos + 3 <= src.length()) {
                    try {
                        ch = (char) Integer.parseInt(
                                src.substring(pos + 1, pos + 3), 16);
                    } catch (Exception e) {
                        ch = ' ';
                        log.warn(" unescape url:" + src
                                + " error when parse int  '"
                                + src.substring(pos + 1, pos + 3) + "':"
                                + e.getMessage());
                    }

                    tmp.append(ch);
                    lastPos = pos + 3;
                } else {
                    tmp.append(src.substring(lastPos, pos));
                    lastPos = pos + 1;
                }
            } else {
                if (pos == -1) {
                    tmp.append(src.substring(lastPos));
                    lastPos = src.length();
                } else {
                    tmp.append(src.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }
        return tmp.toString();
    }

    public static String escape(String src) {
        int i;
        char j;
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length() * 6);

        for (i = 0; i < src.length(); i++) {

            j = src.charAt(i);

            if (Character.isDigit(j) || Character.isLowerCase(j)
                    || Character.isUpperCase(j))
                tmp.append(j);
            else if (j < 256) {
                tmp.append("%");
                if (j < 16)
                    tmp.append("0");
                tmp.append(Integer.toString(j, 16));
            } else {
                tmp.append("%u");
                tmp.append(Integer.toString(j, 16));
            }
        }
        return tmp.toString();
    }

    public static String tryDecode(String value, StringBuilder encode) {
        if (value == null || encode == null || encode.length() == 0
                || value.trim().isEmpty())
            return value;
        try {
            // check whether is unicode
            if (value.indexOf("%u") >= 0) {
                String unicode = value.replaceAll("%u", "\\\\u");
                return decodeUnicode(unicode);
            }

            // check whether has Chinese character
            int bpos = value.indexOf("%");
            if (bpos < 0 || (bpos + 1) >= value.length())
                return value;
            else {
                int npos = value.indexOf("%", bpos + 1);
                if (npos > 0 && npos - bpos != 3)
                    return value;
            }

            byte[] src = convertToBytes(value, encode);
            String enc = encode.toString();
            String result = new String(src, enc);
            if (isSame(src, result.getBytes(enc)))
                return result;

            if (encode.equals(CS_UTF8) == false) {
                result = new String(src, CS_UTF8);
                if (isSame(src, result.getBytes(CS_UTF8))) {
                    encode.setLength(0);
                    encode.append(CS_UTF8);
                    return result;
                }
            }

            if (encode.equals(CS_GB2312) == false) {
                result = new String(src, CS_GB2312);
                if (isSame(src, result.getBytes(CS_GB2312))) {
                    encode.setLength(0);
                    encode.append(CS_GB2312);
                    return result;
                }
            }

            if (encode.equals(CS_GBK) == false) {
                result = new String(src, CS_GBK);
                if (isSame(src, result.getBytes(CS_GBK))) {
                    encode.setLength(0);
                    encode.append(CS_GBK);
                    return result;
                }
            }

            return value;
        } catch (Exception exp) {
            return value;
        }
    }

    private static byte[] convertToBytes(String value, StringBuilder encode) {
        if (value == null)
            return null;

        try {
            int zhLen = 0;
            int zhFirst = -1;
            int len = value.length();
            ByteBuffer buff = ByteBuffer.allocate(len);
            for (int i = 0; i < len; i++) {
                char ch = value.charAt(i);
                if (ch == '%') {
                    while (i + 2 < len && ch == '%') {
                        int one = Integer.parseInt(
                                value.substring(i + 1, i + 3), 16);
                        buff.put((byte) one);
                        if (one > 127) {
                            zhLen++;
                            if (zhFirst == -1)
                                zhFirst = buff.position();
                        }
                        i += 3;
                        if (i < len)
                            ch = value.charAt(i);
                    }
                    if (i >= len)
                        break;
                    else
                        buff.put((byte) ch);
                } else if (ch == '+')
                    buff.put((byte) ' ');
                else {
                    buff.put((byte) ch);
                }
            }

            byte[] src = new byte[buff.position()];
            buff.flip();
            buff.get(src, 0, src.length);

            encode.setLength(0);
            encode.append(guessChatSet(src, zhFirst, zhLen));
            return src;
        } catch (Exception exp) {
            log.warn("decode url to bytes meet error:" + exp.getMessage());
            return null;
        }
    }

    private static String guessChatSet(byte[] src, int zhFirst, int zhLen) {
        if (zhLen > 0 && zhLen % 3 == 0) {
            if (zhLen % 2 != 0) {
                return CS_UTF8;
            } else {
                if (((src[zhFirst] & 0x11100000) == 0x11100000)
                        && ((src[zhFirst + 1] & 0x1000000) == 0x1000000)
                        && ((src[zhFirst + 2] & 0x1000000) == 0x1000000)
                        && ((src[zhFirst + 3] & 0x1110000) == 0x1110000)) {
                    return CS_UTF8;
                } else if (src[zhFirst] >= 0xa1 && src[zhFirst] <= 0xf7
                        && src[zhFirst + 1] >= 0xa1 && src[zhFirst + 1] <= 0xfe) {
                    return CS_GB2312;
                } else {
                    return CS_GBK;
                }
            }
        } else if (zhLen > 0 && src[zhFirst] >= 0xa1 && src[zhFirst] <= 0xf7
                && src[zhFirst + 1] >= 0xa1 && src[zhFirst + 1] <= 0xfe) {
            return CS_GB2312;
        } else {
            return CS_GBK;
        }
    }

    private static boolean isSame(byte[] src, byte[] dest) {
        if (src == null || dest == null)
            return false;

        if (src.length != dest.length)
            return false;

        for (int i = 0; i < src.length; i++) {
            if (src[i] != dest[i])
                return false;
        }

        return true;
    }

    public static byte[] toIPBytes(String ip) {
        if (ip == null)
            return null;

        String[] tmp = ip.split("\\.");
        if (tmp.length != 4)
            return null;
        byte[] addr = new byte[4];
        for (int i = 0; i < tmp.length; i++)
            addr[i] = (byte) Short.parseShort(tmp[i]);
        return addr;
    }

    public static String toHex(byte[] data) {
        return toHex(data, false);
    }

    /**
     * Convert byte array to Hex code
     *
     * @param data
     * @param isFormat
     *            format output string: 16 byte one line, one byte is separted
     *            by space
     * @return
     */
    public static String toHex(byte[] data, boolean isFormat) {
        if (data == null || data.length == 0)
            return "";

        int tmp;
        int s1, s2;
        StringBuilder sb = new StringBuilder(data.length * 2
                + (isFormat ? data.length + data.length / 16 : 0));
        for (int i = 0; i < data.length; i++) {
            tmp = data[i] & 0x000000FF;
            s1 = tmp / 16;
            s2 = tmp % 16;
            if (s1 < 10)
                sb.append((char) (s1 + 48));
            else if (s1 >= 10)
                sb.append((char) (s1 + 55));
            if (s2 < 10)
                sb.append((char) (s2 + 48));
            else if (s2 >= 10)
                sb.append((char) (s2 + 55));

            if (isFormat) {
                sb.append(" ");

                if ((i + 1) % 16 == 0)
                    sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Convert Hex string to byte array hex string format is: 2 char represent
     * one byte,can use space as separator for example, "12 1e 3d ee FF 09"
     *
     * @param hex
     * @return
     */
    public static byte[] toBytes(String hex) {
        byte[] buff = new byte[hex.length() / 2];

        int s1, s2, count = 0;
        for (int i = 0; i < hex.length(); i++) {
            if (hex.charAt(i) >= '0' && hex.charAt(i) <= '9')
                s1 = hex.charAt(i) - 48;
            else if (hex.charAt(i) >= 'A' && hex.charAt(i) <= 'F')
                s1 = hex.charAt(i) - 55;
            else if (hex.charAt(i) >= 'a' && hex.charAt(i) <= 'f')
                s1 = hex.charAt(i) - 87;
            else
                continue;

            i++;
            if (hex.charAt(i) >= '0' && hex.charAt(i) <= '9')
                s2 = hex.charAt(i) - 48;
            else if (hex.charAt(i) >= 'A' && hex.charAt(i) <= 'F')
                s2 = hex.charAt(i) - 55;
            else if (hex.charAt(i) >= 'a' && hex.charAt(i) <= 'f')
                s2 = hex.charAt(i) - 87;
            else
                continue;

            buff[count] = ((byte) (s1 * 16 + s2));
            count++;
        }
        byte[] result = new byte[count];
        System.arraycopy(buff, 0, result, 0, result.length);
        return result;
    }

    public static long getLong(Object value, long def) {
        if (value == null)
            return def;
        else if (value instanceof Integer)
            return ((Integer) value).longValue();
        else if (value instanceof Long)
            return ((Long) value).longValue();
        if (value instanceof BigInteger)
            return ((BigInteger) value).longValue();
        else if (value instanceof BigDecimal)
            return ((BigDecimal) value).longValue();
        else
            return def;
    }

    public static double getDouble(Object value, double def) {
        if (value == null)
            return def;
        else if (value instanceof Double)
            return ((Double) value).doubleValue();
        else if (value instanceof Float)
            return ((Float) value).doubleValue();
        else if (value instanceof Integer)
            return ((Integer) value).doubleValue();
        else if (value instanceof Long)
            return ((Long) value).doubleValue();
        if (value instanceof BigInteger)
            return ((BigInteger) value).doubleValue();
        else if (value instanceof BigDecimal)
            return ((BigDecimal) value).doubleValue();
        else
            return def;
    }

    public static String decodeUnicode(String keyword) {
        int start = 0;
        int offset = 0;
        StringBuilder buffer = new StringBuilder();
        while (start < keyword.length()) {
            offset = keyword.indexOf("\\u", start);
            if (offset == -1) {
                buffer.append(keyword.substring(start));
                break;
            } else {
                start = offset + 6;
                String charStr = keyword.substring(offset + 2, offset + 6);
                buffer.append((char) Integer.parseInt(charStr, 16));
            }
        }
        return buffer.toString();
    }
//    public static String getPropertiesValue(String filename,String item){
//        String retValue="";
//        Resource resource = new ClassPathResource(filename);
//        try {
//            Properties props = PropertiesLoaderUtils.loadProperties(resource);
//            retValue = props.getProperty(item);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return retValue;
//    }

}