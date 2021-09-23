package com.zuipin.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @version Revision 2.0.0
 * @版权：象屿商城 版权所有 (c) 2012
 * @author:zhengry
 * @email:zryuan@xiangyu.cn
 * @see:
 * @创建日期：2012-12-25
 * @功能说明：拼音操作工具类
 */
public class PinyinUtil {

    public static void main(String[] args) {
        String s[] = PinyinUtil.stringToPinyin("厦门搜企软件", "");
        String s5 = PinyinUtil.hanziToPinyin("线路分类", "");
        StringBuilder sbd = new StringBuilder();
        for (int i = 0; i < s.length; i++) {
            sbd.append(s[i]);
        }
        System.out.println(s5);
    }

    public static String stringToPinyinString(String str) {
        if (StringUtils.isBlank(str))
            return "";
        String s[] = PinyinUtil.stringToPinyin(str, "");
        StringBuilder sbd = new StringBuilder();
        for (int i = 0; i < s.length; i++) {
            sbd.append(s[i]);
        }
        return sbd.toString();
    }

    /**
     * @param src
     * @return
     * @author:zhengry
     * @email:zryuan@xiangyu.cn
     * @创建日期:2012-12-25
     * @功能说明：将字符串转换成拼音数组
     */
    public static String[] stringToPinyin(String src) {
        return stringToPinyin(src, false, null);
    }

    /**
     * @param src
     * @param separator
     * @return
     * @author:zhengry
     * @email:zryuan@xiangyu.cn
     * @创建日期:2012-12-25
     * @功能说明：将字符串转换成拼音数组
     */
    public static String[] stringToPinyin(String src, String separator) {
        return stringToPinyin(src, true, separator);
    }

    /**
     * @param src
     * @param isPolyphone 是否查出多音字的所有拼音
     * @param separator   多音字拼音之间的分隔符
     * @return
     * @author:zhengry
     * @email:zryuan@xiangyu.cn
     * @创建日期:2012-12-25
     * @功能说明：将字符串转换成拼音数组(默认使用该方法,多音字处理 如:厦 sha|xia)
     */
    public static String[] stringToPinyin(String src, boolean isPolyphone, String separator) {
        // 判断字符串是否为空
        if ("".equals(src) || null == src) {
            return null;
        }
        char[] srcChar = src.toCharArray();
        int srcCount = srcChar.length;
        String[] srcStr = new String[srcCount];

        for (int i = 0; i < srcCount; i++) {
            srcStr[i] = charToPinyin(srcChar[i], isPolyphone, separator);
        }
        return srcStr;
    }

    /**
     * @param src
     * @param isPolyphone
     * @param separator
     * @return
     * @author:zhengry
     * @email:zryuan@xiangyu.cn
     * @创建日期:2012-12-25
     * @功能说明：将单个字符转换成拼音
     */
    public static String charToPinyin(char src, boolean isPolyphone, String separator) {
        // 创建汉语拼音处理类
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        // 输出设置，大小写，音标方式
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        /*
         * 设置声调格式
         * 方法参数HanyuPinyinToneType有以下常量对象：
         * HanyuPinyinToneType.WITH_TONE_NUMBER 用数字表示声调，例如：liu2
         * HanyuPinyinToneType.WITHOUT_TONE 无声调表示，例如：liu
         * HanyuPinyinToneType.WITH_TONE_MARK 用声调符号表示，例如：liú
         */
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        /*
         * 如果HanyuPinyinToneType.WITH_TONE_MARK
         * 需要设置HanyuPinyinVCharType.WITH_U_UNICODE 处理特殊拼音u的显示格式
         */
        // defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
        StringBuilder tempPinying = new StringBuilder();
        // 如果是中文
        if (src > 128) {
            try {
                // 转换得出结果
                String[] strs = PinyinHelper.toHanyuPinyinStringArray(src, defaultFormat);

                // 是否查出多音字，默认是查出多音字的第一个字符
                if (isPolyphone && null != separator) {
                    for (int i = 0; i < strs.length; i++) {
                        tempPinying.append(strs[i]);
                        if (strs.length != (i + 1)) {
                            // 多音字之间用特殊符号间隔起来
                            tempPinying.append(separator);
                        }
                    }
                } else {
                    tempPinying.append(strs[0]);
                }

            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
        } else {
            tempPinying.append(src);
        }
        return tempPinying.toString();
    }

    public static String hanziToPinyin(String hanzi) {
        return hanziToPinyin(hanzi, " ");
    }

    /**
     * @param hanzi     要转换的字符串
     * @param separator 分隔符
     * @return
     * @author:zhengry
     * @email:zryuan@xiangyu.cn
     * @创建日期:2012-12-25
     * @功能说明：将汉字转换成拼音
     */
    @SuppressWarnings("deprecation")
    public static String hanziToPinyin(String hanzi, String separator) {
        // 创建汉语拼音处理类
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        // 输出设置，大小写，音标方式
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        String pinyingStr = "";
        try {
            pinyingStr = PinyinHelper.toHanyuPinyinString(hanzi, defaultFormat, separator);
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return pinyingStr;
    }

    public static String converterToSpellWithHeader(String chinese) {
        String result = converterToSpell(chinese);
        String[] items = result.split(",");
        for (String item : items) {
            result += "," + item.charAt(0);
        }
        return result;
    }

    /**
     * 汉字转换位汉语全拼，英文字符不变，特殊字符丢失
     * 支持多音字，生成方式如（重当参:zhongdangcen,zhongdangcan,chongdangcen
     * ,chongdangshen,zhongdangshen,chongdangcan）
     *
     * @param chines 汉字
     * @return 拼音
     */
    public static String converterToSpell(String chines) {
        StringBuffer pinyinName = new StringBuffer();
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    // 取得当前汉字的所有全拼
                    String[] strs = PinyinHelper.toHanyuPinyinStringArray(
                            nameChar[i], defaultFormat);
                    if (strs != null) {
                        for (int j = 0; j < strs.length; j++) {
                            pinyinName.append(strs[j]);
                            if (j != strs.length - 1) {
                                pinyinName.append(",");
                            }
                        }
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyinName.append(nameChar[i]);
            }
            pinyinName.append(" ");
        }
        // return pinyinName.toString();
        return parseTheChineseByObject(discountTheChinese(pinyinName.toString()));
    }

    /**
     * 解析并组合拼音，对象合并方案(推荐使用)
     *
     * @return
     */
    private static String parseTheChineseByObject(
            List<Map<String, Integer>> list) {
        Map<String, Integer> first = null; // 用于统计每一次,集合组合数据
        Map<String, Integer> firstHeader = new ConcurrentHashMap<String, Integer>(); // 用于统计每一次,集合组合数据
        Map<String, Integer> shortFirst = null; // 用于统计每一次,集合组合数据
        // 遍历每一组集合
        for (int i = 0; i < list.size(); i++) {
            // 每一组集合与上一次组合的Map
            Map<String, Integer> temp = new Hashtable<String, Integer>();
            Map<String, Integer> tempHeader = new Hashtable<String, Integer>();
            // 第一次循环，first为空
            if (first != null) {
                // 取出上次组合与此次集合的字符，并保存
                for (String s : first.keySet()) {
                    for (String s1 : list.get(i).keySet()) {
                        String str = s + s1;
                        temp.put(str, 1);
                    }
                }
                for (String s : firstHeader.keySet()) {
                    for (String s1 : list.get(i).keySet()) {
                        if (StringUtils.isNotBlank(s1)) {
                            String str = s + String.valueOf(s1.charAt(0));
                            tempHeader.put(str, 1);
                        }
                    }
                }
                // 清理上一次组合数据
                if (temp != null && temp.size() > 0) {
                    first.clear();
                    firstHeader.clear();
                }
            } else {
                for (String s : list.get(i).keySet()) {
                    if (StringUtils.isNotBlank(s)) {
                        String str = s;
                        temp.put(str, 1);
                        tempHeader.put(String.valueOf(str.charAt(0)), 1);
                    }
                }
            }
            // 保存组合数据以便下次循环使用
            if (temp != null && temp.size() > 0) {
                first = temp;
                firstHeader = tempHeader;
            }
        }
        String returnStr = "";
        if (first != null) {
            // 遍历取出组合字符串
            for (String str : first.keySet()) {
                returnStr += (str + " ");
            }
        }
        if (firstHeader != null) {
            // 遍历取出组合字符串
            for (String str : firstHeader.keySet()) {
                returnStr += (str + " ");
            }
        }
        return returnStr.trim();
    }

    /**
     * 去除多音字重复数据
     *
     * @param theStr
     * @return
     */
    private static List<Map<String, Integer>> discountTheChinese(String theStr) {
        // 去除重复拼音后的拼音列表
        List<Map<String, Integer>> mapList = new ArrayList<Map<String, Integer>>();
        // 用于处理每个字的多音字，去掉重复
        Map<String, Integer> onlyOne = null;
        String[] firsts = theStr.split(" ");
        // 读出每个汉字的拼音
        for (String str : firsts) {
            onlyOne = new Hashtable<String, Integer>();
            String[] china = str.split(",");
            // 多音字处理
            for (String s : china) {
                Integer count = onlyOne.get(s);
                if (count == null) {
                    onlyOne.put(s, new Integer(1));
                } else {
                    onlyOne.remove(s);
                    count++;
                    onlyOne.put(s, count);
                }
            }
            mapList.add(onlyOne);
        }
        return mapList;
    }


    /**
     * @param str
     * @param separator 分隔符
     * @return
     * @author:zhengry
     * @email:zryuan@xiangyu.cn
     * @创建日期:2012-12-25
     * @功能说明：将字符串数组转换成字符串
     */
    public static String stringArrayToString(String[] str, String separator) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length; i++) {
            sb.append(str[i]);
            if (str.length != (i + 1)) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    /**
     * @param str
     * @return
     * @author:zhengry
     * @email:zryuan@xiangyu.cn
     * @创建日期:2012-12-25
     * @功能说明：简单的将各个字符数组之间连接起来
     */
    public static String stringArrayToString(String[] str) {
        return stringArrayToString(str, "");
    }

    /**
     * @param ch
     * @param separator 分隔符
     * @return
     * @author:zhengry
     * @email:zryuan@xiangyu.cn
     * @创建日期:2012-12-25
     * @功能说明：将字符数组转换成字符串
     */
    public static String charArrayToString(char[] ch, String separator) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ch.length; i++) {
            sb.append(ch[i]);
            if (ch.length != (i + 1)) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    /**
     * @param ch
     * @return
     * @author:zhengry
     * @email:zryuan@xiangyu.cn
     * @创建日期:2012-12-25
     * @功能说明：将字符数组转换成字符串
     */
    public static String charArrayToString(char[] ch) {
        return charArrayToString(ch, " ");
    }

    /**
     * @param src
     * @param isCapital 是否是大写
     * @return
     * @author:zhengry
     * @email:zryuan@xiangyu.cn
     * @创建日期:2012-12-25
     * @功能说明：取汉字的首字母
     */
    public static char[] getHeadByChar(char src, boolean isCapital) {
        // 如果不是汉字直接返回
        if (src <= 128) {
            return new char[]{src};
        }
        // 获取所有的拼音
        String[] pinyingStr = PinyinHelper.toHanyuPinyinStringArray(src);

        // 创建返回对象
        int polyphoneSize = pinyingStr.length;
        char[] headChars = new char[polyphoneSize];
        int i = 0;
        // 截取首字符
        for (String s : pinyingStr) {
            char headChar = s.charAt(0);
            // 首字母是否大写，默认是小写
            if (isCapital) {
                headChars[i] = Character.toUpperCase(headChar);
            } else {
                headChars[i] = headChar;
            }
            i++;
        }

        return headChars;
    }

    /**
     * @param src
     * @return
     * @author:zhengry
     * @email:zryuan@xiangyu.cn
     * @创建日期:2012-12-25
     * @功能说明：取汉字的首字母(默认是大写)
     */
    public static char[] getHeadByChar(char src) {
        return getHeadByChar(src, true);
    }

    /**
     * @param src
     * @return
     * @author:zhengry
     * @email:zryuan@xiangyu.cn
     * @创建日期:2012-12-25
     * @功能说明： 查找字符串首字母
     */
    public static String[] getHeadByString(String src) {
        return getHeadByString(src, true);
    }

    /**
     * @param src
     * @param isCapital 是否大写
     * @return
     * @author:zhengry
     * @email:zryuan@xiangyu.cn
     * @创建日期:2012-12-25
     * @功能说明：查找字符串首字母
     */
    public static String[] getHeadByString(String src, boolean isCapital) {
        return getHeadByString(src, isCapital, null);
    }

    /**
     * @param src
     * @param isCapital 是否大写
     * @param separator 分隔符
     * @return
     * @author:zhengry
     * @email:zryuan@xiangyu.cn
     * @创建日期:2012-12-25
     * @功能说明：查找字符串首字母
     */
    public static String[] getHeadByString(String src, boolean isCapital, String separator) {
        char[] chars = src.toCharArray();
        String[] headString = new String[chars.length];
        int i = 0;
        for (char ch : chars) {
            char[] chs = getHeadByChar(ch, isCapital);
            StringBuffer sb = new StringBuffer();
            if (null != separator) {
                int j = 1;

                for (char ch1 : chs) {
                    sb.append(ch1);
                    if (j != chs.length) {
                        sb.append(separator);
                    }
                    j++;
                }
            } else {
                sb.append(chs[0]);
            }
            headString[i] = sb.toString();
            i++;
        }
        return headString;
    }

    private PinyinUtil() {
    }
}
