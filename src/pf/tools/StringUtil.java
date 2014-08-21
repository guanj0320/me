/**
 *@(#)StringUtil.java	
 */
package pf.tools;

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pf.constant.SystemConfig;

/**
 * 该类提供对字符串的各种操作处理
 * @author 王清培
 * @version 4.0.0.
 */
public class StringUtil {
	public StringUtil()
    {
    }
	
	/**
	 * 替换字符串中的指定字符
	 * @return String 替换后的字符串
	 * @param source 待替换的原字符串
	 * @param replaced 将要替换为的字符串
	 * @param target 将要被替换的字符串
	 */
    public static String replaceAll(String source, String replaced, String target)
    {
        return replace(source, replaced, target, false);
    }
    
    /**
     * 替换字符串中的指定字符，该方法提供是否只替换前一个匹配项
     * @return String 替换后的字符串
     * @param source 待替换的原字符串
     * @param replaced 将要替换为的字符串
     * @param target 将要被替换的字符串
     * @param bFirst 是否只匹配前一个匹配项
     */
    private static String replace(String source, String replaced, String target, boolean bFirst)
    {
        if(replaced == null || target == null || source == null)
            return source;
        StringBuffer buf = new StringBuffer(source);
        int len = buf.length();
        int rLen = replaced.length();
        int tLen = target.length();
        for(int i = 0; i < len;)
            if(i + rLen <= len && buf.substring(i, i + rLen).equals(replaced))
            {
                buf.replace(i, i + rLen, target);
                i += tLen;
                len = (len + tLen) - rLen;
                if(bFirst)
                    break;
            } else
            {
                i++;
            }

        return buf.toString();
    }

    /**
     * 替换字符串中的指定字符，该方法提供从指定位置开始替换字符串剩下的所有项
     * @return String 替换后的字符串
     * @param source 待替换的原字符串
     * @param start 从该位置开始向后替换字符串项
     * @param replaced 将要被替换为的字符串
     * @param target 将要被替换的字符串
     */
    public static String replaceFirst(String source, int start, String replaced, String target)
    {
        String pre = "";
        String post = source;
        if(source != null && start < source.length() && start >= 0)
        {
            pre = source.substring(0, start);
            post = source.substring(start);
        }
        return pre + replace(post, replaced, target, true);
    }

    /**
     * 使用给定的Values字符数组替换source字符串中的所以是英文字母的字符
     * @return String 替换后的字符串
     * @param source 待替换的原字符串
     * @param values 将要替换为的字符串数组
     */
    public static String replaceParams(String source, String values[])
    {
        int nCount = values.length;
        String ret = source;
        for(int i = 0; i < nCount; i++)
            ret = ret.replaceFirst("%s", values[i]);

        return ret;
    }

    /**
     * 该方法用Properties对象中的属性值，替换字符串中的以'{'、'}'对出现的相对应于Properties中的值
     * @return String 替换后的字符串
     * @param source 待替换的原字符串
     * @param params Properties对象实例
     */
    public static String handleParams(String source, Properties params)
    {
        if(source != null && params != null)
        {
            StringBuffer buf = new StringBuffer(source);
            int start = -1;
            int end = -1;
            for(int i = 0; i < buf.length();)
            {
                if(buf.charAt(i) == '{')
                    start = i;
                if(buf.charAt(i) == '}')
                    end = i;
                if(start >= 0 && end > start)
                {
                    String paramName = buf.substring(start + 1, end);
                    buf.delete(start, end + 1);
                    String value = params.getProperty(paramName);
                    if(value != null)
                    {
                        buf.insert(start, value);
                        i = start + value.length();
                    }
                    start = -1;
                    end = -1;
                } else
                {
                    i++;
                }
            }

            return buf.toString();
        } else
        {
            return source;
        }
    }

    /**
     *该方法截取并拼接字符串，从start开始截取到end处的字符串，并拼接value字符串
     *@return String 替换后的字符串
     *@param source 待替换的原字符串
     *@param start 将要截取的开始处索引
     *@param end 将要截取的结束处索引
     *@param value 将要拼接的字符串
     */
    public static String replace(String source, int start, int end, String value)
    {
        String temp = source.substring(0, start);
        temp = temp + value;
        temp = temp + source.substring(end, source.length());
        return temp;
    }

    /**
     * 使用给定的Values字符串替换source字符串中的所以是英文字母的字符
     * @return String 替换后的字符串
     * @param source 待替换的原字符串
     * @param value 待替换为的字符串
     */
    public static String replaceParam(String source, String value)
    {
        String values[] = new String[1];
        values[0] = value;
        return replaceParams(source, values);
    }

    /**
     * 将给定的字符串添加字符串数组中
     * @return String[] 添加后的字符串数组
     * @param target 将要添加的字符串数组
     * @param temp  待添加的字符串
     */
    public static String[] addStringToArray(String target[], String temp)
    {
        int nLength = 0;
        if(target != null)
            nLength = target.length;
        String rets[] = new String[nLength + 1];
        for(int i = 0; i < nLength; i++)
            rets[i] = target[i];

        rets[nLength] = temp;
        return rets;
    }

    /**
     * 判断source数组中是否有等于value的字符串
     * @return boolean 返回是否有相等的串，true是有、false没有
     * @param value 待比较的串
     * @param source 待比较的串数组
     */
    public static boolean exists(String value, String source[])
    {
        if(source != null)
        {
            int len = source.length;
            for(int i = 0; i < len; i++)
                if(source[i].equals(value))
                    return true;

        }
        return false;
    }


    /**
     * 重载exists方法判断source数组中是否有等于value,判断时是否统一转换大小写在进行判断
     * @return boolean 是否有相等的串，true是有、false没有
     * @param value 待比较的串
     * @param source 待比较的串数组
     * @param caseInsensitive  是否进行转换在比较
     */
    public static boolean exists(String value, String source[], boolean caseInsensitive)
    {
        if(source != null)
        {
            int len = source.length;
            for(int i = 0; i < len; i++)
            {
                String sTemp = source[i];
                if(value == null)
                {
                    if(sTemp == null)
                        return true;
                } else
                if(sTemp != null)
                    if(caseInsensitive)
                    {
                        if(sTemp.equalsIgnoreCase(value))
                            return true;
                    } else
                    if(sTemp.equals(value))
                        return true;
            }

        }
        return false;
    }

    /**
     * 返回value字符串在source字符串数组中的索引位置
     * @return INT 返回的索引位置
     * @param value 要查找的字符串
     * @param source 待查找的字符串数组
     * @param caseInsensitive 是否进行转换在查找
     */
    public static int indexOfArray(String value, String source[], boolean caseInsensitive)
    {
        if(source != null && value != null)
        {
            int len = source.length;
            for(int i = 0; i < len; i++)
                if(source[i] != null)
                    if(caseInsensitive)
                    {
                        if(source[i].toUpperCase().equals(value.toUpperCase()))
                            return i;
                    } else
                    if(source[i].equals(value))
                        return i;

        }
        return -1;
    }

    /**
     * 该方法提供将字符插入、附加到字符串中
     * @return String 插入或附加后的字符串
     * @param source 待操作的字符串
     * @param len 字符串长度
     * @param patch 待插入或附加的字符
     * @param beforePos 是将字符插入到字符串的头还是附加到字符串的尾;true插入到字符串的头、false附加到字符串的尾
     */
    public static String patchString(String source, int len, char patch, boolean beforePos)
    {
        StringBuffer buf;
        for(buf = new StringBuffer(source); buf.length() < len;)
            if(beforePos)
                buf.insert(0, patch);
            else
                buf.append(patch);

        return buf.toString();
    }

    /**
     * 该方法对字符串进行从左向右整理删除
     * @return String 整理后的字符串
     * @param source 待整理的字符串
     * @param trim 整理的子字符串
     */
    public static String leftTrim(String source, String trim)
    {
        StringBuffer buf = new StringBuffer(source);
        if(trim == null)
            return source;
        for(; buf.indexOf(trim) == 0; buf = buf.delete(0, trim.length()));
        return buf.toString();
    }
    /**
     * 该方法对字符串进行从右向左整理删除
     * @return String 整理后的字符串
     * @param source 待整理的字符串
     * @param trim 整理的子字符串
     */
    public static String rightTrim(String source, String trim)
    {
        StringBuffer buf = new StringBuffer(source);
        if(trim == null)
            return source;
        int len;
        for(int lenTrim = trim.length(); buf.lastIndexOf(trim) == buf.length() - lenTrim; buf = buf.delete(len - lenTrim, len))
            len = buf.length();

        return buf.toString();
    }

    /**
     * 将字符patch插入到source串的头
     * @return String 插入后的字符串
     * @param source 待插入的字符串
     * @param len 字符长度
     * @param patch 待插入的字符
     */
    public static String patchStringHead(String source, int len, char patch)
    {
        return patchString(source, len, patch, true);
    }

    /**
     * 将字符patch字符附加到字符串source串的尾
     * @return String 附加后的字符串
     * @param source 待附加的字符串
     * @param len 字符长度
     * @patch 待附加的字符
     */
    public static String patchStringTail(String source, int len, char patch)
    {
        return patchString(source, len, patch, false);
    }

    /**
     * 替换字符串中的指定字符串
     * @return String 替换后的字符串
     * @param source 待替换的字符串
     * @param paramName 将要替换的字符串
     * @param value 将要替换为的字符串
     */
    public static String replaceParams(String source, String paramName, String value)
    {
        return replaceParams(source, "%", paramName, "", value);
    }

    /**
     * 该方法拼接字符串的前后关系并进行替换操作
     * @return String 替换后的字符串
     * @param pre 将要拼接字符串的前缀
     * @param paramName 将要拼接字符串的中间部分
     * @param post 将要拼接字符串的结尾部分
     * @param value 将要替换为的字符串
     */
    public static String replaceParams(String source, String pre, String paramName, String post, String value)
    {
        if(source != null)
            return source.replaceAll(pre + paramName + post, value);
        else
            return source;
    }

    /**
     * 根据指定编码代码序列化byte对象为string类型
     * @return String 序列化后的字符串
     * @param source 待序列的byte流
     * @param coding 序列代码名称如:UTF-8
     */
    public static String deCodingString(byte source[], String coding)
    {
        try
        {
            return new String(source, coding);
        }
        catch(Exception exception)
        {
            return new String(source);
        }
    }

    /**
     * 根据指定编码代码序列化string对象为byte类型
     * @return Byte 序列化后的流
     * @param source 待序列化的字符串
     * @param coding 序列代码名称如:ASCll
     */
    public static byte[] enCodingString(String source, String coding)
    {
        try
        {
            return source.getBytes(coding);
        }
        catch(Exception exception)
        {
            return source.getBytes();
        }
    }

    /**
     * 将字符串value编码转换成GBK编码格式
     * @return String 编码后的字符串
     * @param value 待编码的字符串
     */
    public static String chgGBK(String value)
    {
        if(value == null || value.equals(""))
            return "";
        try
        {
            return new String(value.getBytes("ISO-8859-1"), "GBK");
        }
        catch(Exception exception)
        {
            return null;
        }
    }

    /**
     * 将字符串编码转换成Encoding类型
     * @return String 转换后的编码类型
     * @param value 待转换的字符串
     */
    public static String enCoding(String value)
    {
        try
        {
            return codeTran(value, SystemConfig.CONTAINER_ENCODING, SystemConfig.ENCODING);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 将字符串编码转换成CONTAINER_ENCODING类型
     * @return String 转换后的编码类型
     * @param value 待转换的字符串
     */
    public static String deCoding(String value)
    {
        try
        {
            return codeTran(value, SystemConfig.ENCODING, SystemConfig.CONTAINER_ENCODING);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 转换字符串的编码类型
     * @return String 转换后的字符串
     * @param value 待转换的字符串
     * @param sourceCode 原编码的类型
     * @param targetCode 待编码的类型
     */
    public static String codeTran(String value, String sourceCode, String targetCode)
        throws UnsupportedEncodingException
    {
        if(sourceCode.equalsIgnoreCase(targetCode))
            return value;
        if(value == null || value.equals(""))
            return value;
        else
            return new String(value.getBytes(sourceCode), targetCode);
    }

    /**
     * 将字符串转换成"UTF-8"类型
     * @return String 转换后的类型
     * @param value 待转换的字符串
     */
    public static String URLEncoding(String value)
    {
        try
        {
            return URLEncoder.encode(value,"UTF-8");
        }
        catch(Exception exception)
        {
            return value;
        }
    }

    /**
     * 将字符串实例化成"UTF-8"类型的对象
     * @return String 转换后的字符串
     * @param value 待转换的字符串
     */
    public static String URLDecoding(String value)
    {
        try
        {
            return URLDecoder.decode(value,"UTF-8");
        }
        catch(Exception exception)
        {
            return value;
        }
    }
    /**
     * 作用：在指定的字符串中查找以pre开头，last结尾的的首个字符串
     * 
     * @param	pre					需要查找的以pre开头的字符串
     * @param	last				需要查找的以last结尾的字符串
     * @param	source				在该字符串中查找
     * @return	String				返回查找到的首个pre+str+last的字符串
     * */
    public static String findFirstParam(String pre, String last, String source)
    {
        String ret = source;
        int nStart = ret.indexOf(pre);
        if(nStart != -1)
            ret = ret.substring(nStart + 1, ret.length());
        int nEnd = ret.indexOf(last);
        if(nStart != -1 && nEnd != -1)
            return source.substring(nStart + pre.length(), nEnd + nStart + 1);
        else
            return "";
    }
    /**
     * 作用：查找一个无分隔的字符串
     * 
     * */
    public static String findFirstParam(String value)
    {
        return findFirstParam("%", "%", value);
    }

    /**
     * 生成随机数,根据生成随机数的长度生成随机数
     * @return String 以生成的随机数
     * @param nLength 要生成的随机数长度
     */
    public static String getRandomNumbers(int nLength)
    {
        Random rd = new Random();
        String temp = "";
        for(int i = 0; i < nLength; i++)
        {
            int ret = rd.nextInt(10);
            if(ret < 0)
                ret *= -1;
            temp = temp + ret;
        }

        return temp;
    }

    /**
     *将字符串转换整型
     *@return INT 转换后的数值
     *@param value 待转换的字符串
     */
    public static int toInteger(String value)
    {
        return Integer.valueOf(value).intValue();
    }

    /**
     * 将字符串转换成浮点型Float类型
     * @return 转换后的Float类型
     * @param value 待转换的字符串
     */
    public static float toFloat(String value)
    {
        return Float.valueOf(value).floatValue();
    }

    /**
     * 将字符串转换成双精度Double类型
     * @return double 转换后的double类型
     * @param value 待转后的字符串
     */
    public static double toDouble(String value)
    {
        return Double.valueOf(value).doubleValue();
    }

    /**
     * 比较两个字符串是否相等,不进行字符串的大小转换
     * @return boolean True表示相等，False表示不相等
     * @param target 待比较的字符串
     * @param source 待比较的字符串
     */
    public static boolean equalsIgnoreCase(String target, String source)
    {
        if(target == null && source == null)
            return true;
        if(target == null || source == null)
            return false;
        else
            return target.equalsIgnoreCase(source);
    }

    /**
     * 将字符串数组转换成字符串并用分隔符分开来
     * @return String 转换后的字符串
     * @param array 待转换的字符串数组
     * @param splitSign 分隔符
     */
    public static String arrayToString(String array[], String splitSign)
    {
        if(array != null)
        {
            int length = array.length;
            StringBuffer buf = new StringBuffer();
            for(int i = 0; i < length; i++)
            {
                buf.append(array[i]);
                if(i < length - 1)
                    buf.append(splitSign);
            }

            return buf.toString();
        } else
        {
            return "";
        }
    }

    /**
     *将两个字符串数组合并成一个字符串数组(注：只合并两个数组中的独立部分)
     *@return String[] 合并后的数组
     *@param source 待合并的数组
     *@param target 待合并的数组
     */
    public static String[] mergeArray(String source[], String target[])
    {
        List ret = new ArrayList();
        int length = source.length;
        for(int i = 0; i < length; i++)
            ret.add(source[i]);

        length = target.length;
        for(int i = 0; i < length; i++)
        {
            String temp = target[i];
            if(!exists(temp, source))
                ret.add(temp);
        }

        return (String[])ret.toArray(new String[ret.size()]);
    }

    /**
     * 将两个字符串数组连接成一个字符串数组(注：连接两个数组中的共有部分)
     * @return String[] 连接后的字符串数组
     * @param source 待连接的数组
     * @param target 待连接的数组
     */
    public static String[] joinArray(String source[], String target[])
    {
        List ret = new ArrayList();
        int length = source.length;
        for(int i = 0; i < length; i++)
        {
            String temp = source[i];
            if(exists(temp, target))
                ret.add(temp);
        }

        return (String[])ret.toArray(new String[ret.size()]);
    }

    /**
     * 将throwable转换成字符串
     * @return String 转换后的字符串
     * @param e 待转换的Throwable对象 
     */
    public static String throwableToString(Throwable e)
    {
        CharArrayWriter cW = new CharArrayWriter();
        e.printStackTrace(new PrintWriter(cW));
        return cW.toString();
    }

    /**
     * 划分字符串数组(注：提取两个数组没有的部分)
     * @return String[] 划分后的字符串数组
     * @param source 待划分的字符串数组
     * @param target 待划分的字符串数组
     */
    public static String[] divideArray(String source[], String target[])
    {
        List ret = new ArrayList();
        int length = source.length;
        for(int i = 0; i < length; i++)
        {
            String temp = source[i];
            if(!exists(temp, target))
                ret.add(temp);
        }

        return (String[])ret.toArray(new String[ret.size()]);
    }

    /**
     * 将指定区间的字符串段转换成大写字符串
     * @return String 转换后的字符串
     * @param source 待转换的字符串
     * @param end 指定区间的段尾索引数值
     */
    public static String upperPrefix(String source, int end)
    {
        if(source != null)
        {
            if(end > source.length())
            {
                return source.toUpperCase();
            } else
            {
                String prefix = source.substring(0, end);
                prefix = prefix.toUpperCase();
                return prefix + source.substring(end);
            }
        } else
        {
            return null;
        }
    }

    /**
     * 替换字符串中的指定字符串
     * @param buf 待替换的可变长字符串
     * @param source 将要替换的字符串
     * @param value 将要替换为的字符串
     */
    public static void replaceAll(StringBuffer buf, String source, String value)
    {
        int nIndex = buf.indexOf(source);
        if(nIndex != -1)
        {
            buf.replace(nIndex, nIndex + source.length(), value);
            replaceAll(buf, source, value);
        }
    }

    /**
     * 将字符串转换成等价的ASCLL格式的字符串
     *@return String 转换后的ASCLL字符串
     *@param s 待转换的字符串 
     */
    public static String escape(String s)
    {
        StringBuffer sbuf = new StringBuffer();
        int len = s.length();
        for(int i = 0; i < len; i++)
        {
            int ch = s.charAt(i);
            if(65 <= ch && ch <= 90)
                sbuf.append((char)ch);
            else
            if(97 <= ch && ch <= 122)
                sbuf.append((char)ch);
            else
            if(48 <= ch && ch <= 57)
                sbuf.append((char)ch);
            else
            if(ch == 45 || ch == 95 || ch == 46 || ch == 42 || ch == 43 || ch == 64 || ch == 47)
                sbuf.append((char)ch);
            else
            if(ch <= 127)
            {
                sbuf.append('%');
                sbuf.append(hex[ch]);
            } else
            {
                sbuf.append('%');
                sbuf.append('u');
                sbuf.append(hex[ch >> 8]);
                sbuf.append(hex[255 & ch]);
            }
        }

        return sbuf.toString();
    }

    public static String unescape(String s)
    {
        StringBuffer sbuf = new StringBuffer();
        int i = 0;
        for(int len = s.length(); i < len; i++)
        {
            int ch = s.charAt(i);
            if(65 <= ch && ch <= 90)
                sbuf.append((char)ch);
            else
            if(97 <= ch && ch <= 122)
                sbuf.append((char)ch);
            else
            if(48 <= ch && ch <= 57)
                sbuf.append((char)ch);
            else
            if(ch == 45 || ch == 95 || ch == 46 || ch == 42 || ch == 43 || ch == 64 || ch == 47)
                sbuf.append((char)ch);
            else
            if(ch == 37)
            {
                int cint = 0;
                if('u' != s.charAt(i + 1))
                {
                    cint = cint << 4 | val[s.charAt(i + 1)];
                    cint = cint << 4 | val[s.charAt(i + 2)];
                    i += 2;
                } else
                {
                    cint = cint << 4 | val[s.charAt(i + 2)];
                    cint = cint << 4 | val[s.charAt(i + 3)];
                    cint = cint << 4 | val[s.charAt(i + 4)];
                    cint = cint << 4 | val[s.charAt(i + 5)];
                    i += 5;
                }
                sbuf.append((char)cint);
            }
        }

        return sbuf.toString();
    }
    /**
     * 作用：当value字符串代表的意思为“true”时，返回true，反之为false
     * 
     * @param	value			需要判断的字符串
     * @return	boolean			当与需要判断的信息相等时，返回true，反之返回false
     * */
    public static boolean isTrue(String value)
    {
        return "true".equalsIgnoreCase(value) || "1".equals(value) || "\u771F".equals(value) || "\u662F".equals(value) || "y".equalsIgnoreCase(value);
    }
    /**
     * 作用：当value字符串代表的意思为“false”时，返回true，反之为false
     * 
     * @param	value			需要判断的字符串
     * @return	boolean			当与需要判断的信息相等时，返回true，反之返回false
     * */
    public static boolean isFalse(String value)
    {
        return value == null || "".equals(value) || "false".equalsIgnoreCase(value) || "0".equals(value) || "\u5047".equals(value) || "\u5426".equals(value) || "n".equalsIgnoreCase(value);
    }

    /**
     * 解析XML文档中的符号为等价的字符串
     * @return String 解析后的XML文档字符串
     * @param source 待解析的XML文档字符串
     */
    public static String XMLEncode(String source)
    {
        if(source != null)
        {
            StringBuffer buf = new StringBuffer(source);
            for(int i = 0; i < buf.length();)
            {
                char c = buf.charAt(i);
                if(c == '<')
                {
                    buf.replace(i, i + 1, "&lt;");
                    i += 3;
                } else
                if(c == '>')
                    buf.replace(i, i + 1, "&gt;");
                else
                if(c == '"')
                {
                    buf.replace(i, i + 1, "&quot;");
                    i += 6;
                } else
                {
                    i++;
                }
            }

            return buf.toString();
        } else
        {
            return "";
        }
    }

    /**
     * 解析XML文档中的特殊字符串为等价的符号
     * @return String 解析后的XML文档字符串
     * @param source 待解析的XML文档字符串
     */
    public static String XMLDecode(String source)
    {
        if(source != null)
        {
            StringBuffer buf = new StringBuffer(source);
            int len = buf.length();
            for(int i = 0; i < len; i++)
            {
                char c = buf.charAt(i);
                if(c == '&')
                {
                    String temp = buf.substring(i, i + 4);
                    if(temp.equals("&lt;"))
                        buf.replace(i, i + 4, "<");
                    else
                    if(temp.equals("&gt;"))
                    {
                        buf.replace(i, i + 4, ">");
                    } else
                    {
                        temp = buf.substring(i, i + 6);
                        if(temp.equals("&quot;"))
                            buf.replace(i, i + 6, "\"");
                    }
                    len = buf.length();
                }
            }

            return buf.toString();
        } else
        {
            return "";
        }
    }

    /**
     * 该方法将HTML字符串中的符号转换成等价的特殊字符
     * @return 转换后的字符串
     * @param source 待转换的字符串
     */
    public static String html2plan(String source)
    {
        if(source != null)
        {
            StringBuffer buf = new StringBuffer(source);
            for(int i = 0; i < buf.length();)
            {
                char c = buf.charAt(i);
                if(c == '<')
                {
                    buf.replace(i, i + 1, "&lt;");
                    i += 3;
                } else
                if(c == '>')
                    buf.replace(i, i + 1, "&gt;");
                else
                if(c == '"')
                {
                    buf.replace(i, i + 1, "&quot;");
                    i += 6;
                } else
                if(c == ' ')
                {
                    buf.replace(i, i + 1, "&nbsp;");
                    i += 6;
                } else
                if(c == '\n')
                {
                    buf.replace(i, i + 1, "<br>");
                    i += 4;
                } else
                {
                    i++;
                }
            }

            return buf.toString();
        } else
        {
            return "";
        }
    }

    /**
     * 替换JavaScript脚本中的字符
     * @return String 转换后的JavaScript字符串
     * @param  source 待转换的字符串
     */
    public static String JavaScriptEncode(String source)
    {
        String ret = source;
        ret = replaceAll(ret, "\\", "\\\\");
        ret = replaceAll(ret, "\"", "\\\"");
        ret = replaceAll(ret, "'", "\\'");
        ret = replaceAll(ret, "\r\n", "\\r\\n");
        ret = replaceAll(ret, "\n", "\\n");
        ret = replaceAll(ret, "\t", "\\t");
        return ret;
    }

    /**
     * 将HTML字符串中的特殊字符转换成等价的符号
     * @return String 转换后的字符串
     * @param source 待转换的字符串
     */
    public static String plan2html(String source)
    {
        if(source != null)
        {
            StringBuffer buf = new StringBuffer(source);
            for(int i = 0; i < buf.length();)
            {
                char c = buf.charAt(i);
                if(c == '&')
                {
                    String temp = buf.substring(i, i + 4);
                    if(temp.equals("&lt;"))
                        buf.replace(i, i + 4, "<");
                    else
                    if(temp.equals("&gt;"))
                    {
                        buf.replace(i, i + 4, ">");
                    } else
                    {
                        temp = buf.substring(i, i + 6);
                        if(temp.equals("&quot;"))
                            buf.replace(i, i + 6, "\"");
                    }
                    i++;
                } else
                if(c == ' ')
                {
                    buf.replace(i, i + 1, "&nbsp;");
                    i += 5;
                } else
                if(c == '\n')
                {
                    buf.replace(i, i + 1, "<br>");
                    i += 3;
                } else
                {
                    i++;
                }
            }

            return buf.toString();
        } else
        {
            return "";
        }
    }

    /**
     * 分割字符串,将字符串根据特定字符分割成字符串数组
     * @return String[] 分割后的字符串数组
     * @param value 待分割的字符串
     * @param sep 分隔符
     */
    public static String[] split(String value, String sep)
    {
        if(value == null || sep == null)
            return new String[0];
        if(value.length() == 0)
            return (new String[] {
                ""
            });
        List rets = new ArrayList();
        StringBuffer buf = new StringBuffer(value);
        int len = buf.length();
        int sepLen = sep.length();
        boolean endWithSep = buf.toString().endsWith(sep);
        for(int i = 0; i < len;)
            if(i + sepLen <= len && buf.substring(i, i + sepLen).equals(sep))
            {
                rets.add(buf.substring(0, i));
                buf.delete(0, i + sepLen);
                len = buf.length();
                i = 0;
            } else
            {
                i++;
            }

        if(buf.length() > 0)
            rets.add(buf.toString());
        else
        if(endWithSep)
            rets.add("");
        return (String[])rets.toArray(new String[rets.size()]);
    }

    /**
     * 去除字符串中的空格、换行、制表符
     * @param str
     * @return
     */
    public static String trimTRN(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|t|r|n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static void main(String args[])
    {
        String stest = "@-_\\=+|~!@#$%^&*(){}:\"<>?MNBVCXZASQWRERFYTGUU  UJJIJJ[];'/.'";
        System.out.println(escape(stest));
        System.out.println(stest);
        System.out.println(unescape(escape(stest)));
        System.out.println(JavaScriptEncode(stest));
        String enstest = XMLEncode(stest);
        System.out.println(enstest);
        System.out.println(replaceFirst(stest, 18, "d", "yydyyy"));
        System.out.println(XMLDecode(enstest));
        String sss = "1111;111144;;";
        System.out.println(arrayToString(split(sss, ";"), "#"));
        Properties props = new Properties();
        props.setProperty("param1", "\u4F60\u597D");
        props.setProperty("param2", "\u4F60\u597D\u5417\uFF01");
        String s = "\u6EF4\u6EF4\u55D2\u55D2\u6253\u53D1\u6389Aadddf\uFF08\327\uFF088902{param1}DFDFASFDAddDE1\u5730\u5730\u9053\u9053\u5B95{param2}ddfdfa{param1}";
        System.out.println(handleParams(s, props));
    }

    private static final String hex[] = {
        "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", 
        "0A", "0B", "0C", "0D", "0E", "0F", "10", "11", "12", "13", 
        "14", "15", "16", "17", "18", "19", "1A", "1B", "1C", "1D", 
        "1E", "1F", "20", "21", "22", "23", "24", "25", "26", "27", 
        "28", "29", "2A", "2B", "2C", "2D", "2E", "2F", "30", "31", 
        "32", "33", "34", "35", "36", "37", "38", "39", "3A", "3B", 
        "3C", "3D", "3E", "3F", "40", "41", "42", "43", "44", "45", 
        "46", "47", "48", "49", "4A", "4B", "4C", "4D", "4E", "4F", 
        "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", 
        "5A", "5B", "5C", "5D", "5E", "5F", "60", "61", "62", "63", 
        "64", "65", "66", "67", "68", "69", "6A", "6B", "6C", "6D", 
        "6E", "6F", "70", "71", "72", "73", "74", "75", "76", "77", 
        "78", "79", "7A", "7B", "7C", "7D", "7E", "7F", "80", "81", 
        "82", "83", "84", "85", "86", "87", "88", "89", "8A", "8B", 
        "8C", "8D", "8E", "8F", "90", "91", "92", "93", "94", "95", 
        "96", "97", "98", "99", "9A", "9B", "9C", "9D", "9E", "9F", 
        "A0", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", 
        "AA", "AB", "AC", "AD", "AE", "AF", "B0", "B1", "B2", "B3", 
        "B4", "B5", "B6", "B7", "B8", "B9", "BA", "BB", "BC", "BD", 
        "BE", "BF", "C0", "C1", "C2", "C3", "C4", "C5", "C6", "C7", 
        "C8", "C9", "CA", "CB", "CC", "CD", "CE", "CF", "D0", "D1", 
        "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "DA", "DB", 
        "DC", "DD", "DE", "DF", "E0", "E1", "E2", "E3", "E4", "E5", 
        "E6", "E7", "E8", "E9", "EA", "EB", "EC", "ED", "EE", "EF", 
        "F0", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", 
        "FA", "FB", "FC", "FD", "FE", "FF"
    };
    private static final byte val[] = {
        63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 
        63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 
        63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 
        63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 
        63, 63, 63, 63, 63, 63, 63, 63, 0, 1, 
        2, 3, 4, 5, 6, 7, 8, 9, 63, 63, 
        63, 63, 63, 63, 63, 10, 11, 12, 13, 14, 
        15, 63, 63, 63, 63, 63, 63, 63, 63, 63, 
        63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 
        63, 63, 63, 63, 63, 63, 63, 10, 11, 12, 
        13, 14, 15, 63, 63, 63, 63, 63, 63, 63, 
        63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 
        63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 
        63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 
        63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 
        63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 
        63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 
        63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 
        63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 
        63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 
        63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 
        63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 
        63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 
        63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 
        63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 
        63, 63, 63, 63, 63, 63
    };
}
