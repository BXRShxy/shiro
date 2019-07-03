package com.njust.shiro.util;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文本工具类
 *
 * @author 修身 since 2018/10/23
 **/

public class TextUtil {

    /**
     * function:将数据库中的bigdecimal转化为long
     *
     * @param pram
     * @return
     */
    public static long getLong(Object pram) {

        if (pram != null) {

            BigDecimal bg = new BigDecimal(pram + "");
            return bg.longValue();
        }
        return 0;

    }

    /**
     * function:将数据库中的bigdecimal转化为long
     *
     * @param pram
     * @return
     */
    public static short getShort(Object pram) {

        if (pram != null) {

            BigDecimal bg = new BigDecimal(pram + "");
            return bg.shortValue();
        }
        return 0;

    }

    /**
     * function:将数据库中的bigdecimal转化为Long
     *
     * @param pram
     * @return
     */
    public static Long getOlong(Object pram) {

        if (pram != null) {

            BigDecimal bg = new BigDecimal(pram + "");
            return new Long(bg.longValue());
        }
        return null;

    }


    /**
     * function:将数据库中的string类型进行null处理
     *
     * @param pram
     * @return
     */
    public static String getString(Object pram) {

        if (pram == null) {
            return "";
        } else {
            return pram + "";
        }
    }

    /**
     * function:将数据库中的时间转化为String类型
     *
     * @param pram
     * @return
     */
    public static String getDateFormate(Object pram) {

        if (pram != null) {
            //java.sql.Date ts = (java.sql.Date)pram;
            Timestamp ts = (Timestamp) pram;

            long date = ts.getTime();

            Date jdate = new Date(date);

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            String str = df.format(jdate);

            return str;
        } else {

            return "";
        }


    }

    /**
     * function:将string 类型转换为short类型
     *
     * @param obj
     * @return
     */
    public static short getShort(String obj) {

        if (obj != null && !"".equals(obj)) {

            return Short.parseShort(obj);
        }

        return 0;

    }

    /**
     * function:将string 类型转换为short类型
     *
     * @param obj
     * @return
     */
    public static Short getOshort(String obj) {

        if (obj != null && !"".equals(obj) && !"undefined".equals(obj)) {

            return new Short(Short.parseShort(obj));
        }

        return null;

    }

    public static Long getLong(String obj) {

        if (obj != null && !"".equals(obj)) {

            return new Long(Long.parseLong(obj));
        }

        return null;

    }

    public static int getInt(String obj) {

        if (obj != null && !"".equals(obj)) {

            return Integer.parseInt(obj);
        }

        return 0;

    }

    public static Byte getByte(String obj) {
        Byte result = 0;
        try {
            if (obj != null && !"".equals(obj)) {

                result = Byte.parseByte(obj);
            }
        } catch (Exception e) {
            return 0;
        }

        return result;
    }

    public static float getFloat(String obj) {

        if (obj != null && !"".equals(obj) && !"null".equals(obj)) {

            return Float.parseFloat(obj);
        }

        return 0;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {

        String a = "１";
        byte[] b = a.getBytes("GBK");
        System.out.println(b.length);
//	   String obj = " <link id='110000201012170354415940'   doLinkId=\"1598\" assignee='' doLinkId=\"159\" staffName=''/>";
//		TextUtil t = new TextUtil();
//		String a = t.replaceXmlAttribute(obj,"doLinkId","159","123");
//		System.out.println(a);
    }

    /**
     * function:将blob转化为string类型
     *
     * @param blob
     * @return
     */
    public static String getContentString(Blob blob) {

        try {

            BufferedInputStream bi = new BufferedInputStream(blob.getBinaryStream());
            byte[] data = new byte[(int) blob.length()];
            String outfile = "";
            bi.read(data);
            outfile = new String(data);
            bi.close();
            return outfile;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * function:将string 类型转换为BigDecimal类型
     *
     * @param obj
     * @return
     */
    public static BigDecimal getBigDecimal(String obj) {

        if (obj != null && !"".equals(obj)) {

            return new BigDecimal(obj);
        }
        //else{

        //return new BigDecimal("");
        //}
        return null;
    }

    /**
     * function:将string 转为Integer
     *
     * @param obj
     * @return
     */
    public static Integer getOInteger(String obj) {

        if (obj != null && !"".equals(obj)) {

            return new Integer(obj);
        }
        return null;
    }

    /**
     * function:获取xml属性
     *
     * @param data
     */
    public static List split(String data, String propertyName) {

        List resList = new ArrayList();
        data = data.toLowerCase();
        //	 Pattern pattern2 = Pattern.compile("<customstate *?[^>]*?((>.*?</customstate>)|(/>))");
        String str = "<transition .*?/>";
        Pattern pattern2 = Pattern.compile(str);
        Matcher matcher2 = pattern2.matcher(data);
        while (matcher2.find()) {

            String data1 = matcher2.group();

            resList.add(getAttribute(data1, propertyName));
        }

        return resList;

    }

    /**
     * function:获取xml属性
     *
     * @param data
     */
    public static List getNodeID(String data, String propertyName) {

        List resList = new ArrayList();
        //data = data.toLowerCase();
        //	 Pattern pattern2 = Pattern.compile("<customstate *?[^>]*?((>.*?</customstate>)|(/>))");
        String str = "<task .*?>";
        Pattern pattern2 = Pattern.compile(str);
        Matcher matcher2 = pattern2.matcher(data);
        while (matcher2.find()) {

            String data1 = matcher2.group();

            resList.add(getAttribute(data1, propertyName));
        }

        return resList;

    }

    /**
     * function:获取xml属性
     *
     * @param data
     */
    public static List getNode(String data, String property, String propertyName) {

        List resList = new ArrayList();
        //data = data.toLowerCase();
        //	 Pattern pattern2 = Pattern.compile("<customstate *?[^>]*?((>.*?</customstate>)|(/>))");
        Pattern pattern2 = Pattern.compile("<" + property + " .*?>");
        Matcher matcher2 = pattern2.matcher(data);
        while (matcher2.find()) {

            String data1 = matcher2.group();

            resList.add(getAttribute(data1, propertyName));
        }

        return resList;

    }

    /**
     * 在给定的元素中获取指定属性的值.该元素应该从getElementsByTag方法中获取
     *
     * @param elementString String
     * @param attributeName String
     * @return String
     */
    public static String getAttribute(String elementString, String attributeName) {

        String str = "<[^>]+>";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(elementString);
        String tmp = m.find() ? m.group() : "";
        p = Pattern.compile("(" + attributeName + "+)\\s*=\\s*\"([^\"]+)\"");
        m = p.matcher(tmp);
        while (m.find()) {
            return m.group(2);
        }
        return "";
    }

    /**
     * function:将obj转为int
     *
     * @param obj
     * @return
     */
    public static int getInt(Object obj) {

        if (obj != null && !"null".equals(obj)) {
            String intValue = obj + "";
            if (!"".equals(intValue) && intValue != null) {
                return Integer.parseInt(intValue);
            }

        }
        return 0;
    }

    /**
     * function:将Boolean转为int
     *
     * @param obj
     * @return 当传入参数为true是返回1，其余情况一律返回0
     */
    public static int turnBoolean2Int(Object obj) {
        int val = 0;
        if (obj != null && !"null".equals(obj)) {

            try {
                Boolean value = (Boolean) obj;
                if (null != value && value) {
                    val = 1;
                }
            } catch (Exception e) {
                return 0;
            }

        }
        return val;
    }

    /**
     * function:将Boolean转为int
     *
     * @param obj
     * @return 当传入参数为Integer类型并且数值大于0，则返回true，其余一律返回false
     */
    public static boolean turnInt2Boolean(Object obj) {
        boolean val = false;
        if (obj != null && !"null".equals(obj)) {

            try {
                Integer value = Integer.parseInt("" + obj);
                if (null != value && value.compareTo(0) > 0) {
                    val = true;
                }
            } catch (Exception e) {
                return false;
            }

        }
        return val;
    }

    public static String replaceXmlAttribute(String xmlData, String fromAttribute, String toAttribute, String fromArg, String toArg) {


        if (xmlData != null && !"".equals(xmlData)) {

            xmlData = xmlData.replaceAll("" + fromAttribute + "=\"" + fromArg + "\"", "" + toAttribute + "=\"" + toArg + "\"");
        }
        return xmlData;
    }

    /**
     * function:将数据库中的bigdecimal转化为Long
     *
     * @param pram
     * @return
     */
    public static int getBD2Int(Object pram) {

        if (pram != null) {

            BigDecimal bg = new BigDecimal(pram + "");
            return bg.intValue();
        }
        return 0;

    }

    /**
     * function:判断字符长短
     *
     * @param arg
     * @param maxSize
     * @return
     * @throws UnsupportedEncodingException
     */
    public static Map checkFrontNum(String arg, int maxSize) throws UnsupportedEncodingException {

        Map map = new HashMap();
        if (arg != null && !"".equals(arg)) {

            byte[] b = arg.getBytes("GBK");

            if (maxSize < b.length) {

                int overLength = b.length - maxSize;
                map.put("overFlag", "1");
                map.put("overLength", overLength);
                return map;
            }
            map.put("overFlag", "0");
            return map;
        }
        map.put("overFlag", "0");
        return map;

    }


    /**
     * function:file读到byte[]
     *
     * @param file
     * @return
     * @throws IOException
     */
    private static byte[] getBytesFromFile(File file) throws IOException {
        //file size
        long length = file.length();
        InputStream is = null;
        is = new BufferedInputStream(new FileInputStream(file));
        if (length > Integer.MAX_VALUE) {
            throw new IOException("File is to large " + file.getName());
        }
        byte[] bytes = new byte[(int) length];
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }
        is.close();
        return bytes;

    }

    /**
     * function:inputstream to byte
     *
     * @param is
     * @return
     * @throws IOException
     */
    public static byte[] inputStreamToByte(InputStream is) throws IOException {

        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        int ch;
        while ((ch = is.read()) != -1) {
            bytestream.write(ch);
        }
        byte imgData[] = bytestream.toByteArray();
        bytestream.close();
        return imgData;
    }

    /**
     * list转string
     *
     * @param list      需转换的list
     * @param splisChar 分隔符
     * @return
     */
    public static String toString(List<String> list, String splisChar) {
        String result = "";
        if (null == list) {
            return result;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            String temp = list.get(i).trim();
            if (i == 0) {
                result = result + temp;
            } else {
                result = result + splisChar + temp;
            }
        }
        return result;
    }
    /**
     * xml结构的String转Map
     * @param xmlString
     * @return
     */
//    public static Map<String, Object> dom2Map(String xmlString){
//        Map map = new HashMap<String,Object>();
//        try{
//            Document doc = DocumentHelper.parseText(xmlString);
//            Element rootElement = doc.getRootElement();
//            if(doc == null) {
//                return map;
//            }
//            Element root = doc.getRootElement();
//            for (Iterator iterator = root.elementIterator(); iterator.hasNext();) {
//                Element e = (Element) iterator.next();
//                //System.out.println(e.getName());
//                List list = e.elements();
//                if(list.size() > 0){
//                    map.put(e.getName(), dom2Map(e));
//                }else {
//                    map.put(e.getName(), e.getText());
//                }
//            }
//        }catch (DocumentException e){
//            System.out.println(e.toString());
//        }
//        return map;
//    }
//
//
//    public static Map dom2Map(Element e){
//        Map map = new HashMap();
//        List list = e.elements();
//        if(list.size() > 0){
//            for (int i = 0;i < list.size(); i++) {
//                Element iter = (Element) list.get(i);
//                List mapList = new ArrayList();
//
//                if(iter.elements().size() > 0){
//                    Map m = dom2Map(iter);
//                    if(map.get(iter.getName()) != null){
//                        Object obj = map.get(iter.getName());
//                        if(!"java.util.ArrayList".equals(obj.getClass().getName())){
//                            mapList = new ArrayList();
//                            mapList.add(obj);
//                            mapList.add(m);
//                        }
//                        if("java.util.ArrayList".equals(obj.getClass().getName())){
//                            mapList = (List) obj;
//                            mapList.add(m);
//                        }
//                        map.put(iter.getName(), mapList);
//                    }else {
//                        map.put(iter.getName(), m);
//                    }
//                }
//                else{
//                    if(map.get(iter.getName()) != null){
//                        Object obj = map.get(iter.getName());
//                        if(!"java.util.ArrayList".equals(obj.getClass().getName())){
//                            mapList = new ArrayList();
//                            mapList.add(obj);
//                            mapList.add(iter.getText());
//                        }
//                        if("java.util.ArrayList".equals(obj.getClass().getName())){
//                            mapList = (List) obj;
//                            mapList.add(iter.getText());
//                        }
//                        map.put(iter.getName(), mapList);
//                    }else {
//                        map.put(iter.getName(), iter.getText());
//                    }
//                }
//            }
//        }else {
//            map.put(e.getName(), e.getText());
//        }
//        return map;
//    }


    /**
     * 获取随机位数的字符串
     *
     * @author huangxy
     * @Date 2017/12/21 14:09
     */
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }


}
