package com.zhanggb.contacts.app.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author owensun
 * @since 26/04/2013
 */
public class CharsUtils {
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]+$");
        return pattern.matcher(str).matches();
    }

    public static boolean isLetter(String str) {
        if (str == null || str.length() < 0) {
            return false;
        }
        Pattern pattern = Pattern.compile("[\\w\\.-_]*");
        return pattern.matcher(str).matches();
    }

    public static boolean isChinese(String str) {
        Pattern pattern = Pattern.compile("[\u0391-\uFFE5]+$");
        return pattern.matcher(str).matches();
    }

    public static boolean isChineseSignal(char c){
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION ||
                ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION ||
                ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    public static boolean isChinese(char c) {
        /**
         * block == CJK_UNIFIED_IDEOGRAPHS // 中日韩统一表意文字
         || block == CJK_COMPATIBILITY_IDEOGRAPHS // 中日韩兼容字符
         || block == CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A // 中日韩统一表意文字扩充A
         || block == GENERAL_PUNCTUATION // 一般标点符号, 判断中文的“号
         || block == CJK_SYMBOLS_AND_PUNCTUATION // 符号和标点, 判断中文的。号
         || block == HALFWIDTH_AND_FULLWIDTH_FORMS // 半角及全角字符, 判断中文的，号
         */
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B) {
            return true;
        }
//        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
//                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
//                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
//                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
//            return true;
//        }
        return false;
    }

    public static void main(String[] args) {
        String s = "一きtest,.?!%^&*(){}[]“测试”，。？！%……&*（）——{}【】”中国阿范德萨积分卡睡懒觉发快递了设计费卡上九分裤萨拉丁清热去人情味亲热我琴日接口啊了第三方";
        char[] chars = s.toCharArray();
        for(char c : chars){
            System.out.println(c + ":" + isChinese(c));
        }
//        System.out.println(isChinese("き"));
//        System.out.println(isChinese("test,.?!%^&*(){}[]"));
//        System.out.println(isChinese("测试"));
//        System.out.println(isChinese("“测试”，。？！%……&*（）——{}【】”"));
    }

    /**
     * 根据关键词把sortKey中匹配的字符取出
     *
     * @param sortKey "zhong 中 guo 国 ren 人 123"
     * @param keyword "zhongguoren", "zhongguo", "zhongren", "zhongg", "zhongr", "guoren", "gren", "zhg", "zgr", "zr", "中", "人", "中国", "中人", "123", "12", "23", "2", "13"
     * @return
     */
    public static String[] getMatchLetters(String sortKey, String keyword) {
        if (StringUtils.isBlank(sortKey) || StringUtils.isBlank(keyword)) {
            return null;
        }
        sortKey = " " + sortKey.toUpperCase();
        keyword = keyword.toUpperCase();
        List<String> lst = new ArrayList<String>();
        boolean hit = true;
        if (CharsUtils.isInteger(keyword)) {
            int index = StringUtils.indexOf(sortKey, keyword);
            if (index > -1) {
                return new String[]{keyword};
            } else {
                hit = false;
            }
        } else {
            do {
                String s = String.valueOf(keyword.toCharArray()[0]);
                int index = StringUtils.indexOf(sortKey, " " + s);
                if (index != -1) {
                    if (CharsUtils.isChinese(s)) {
                        lst.add(s);
                        keyword = StringUtils.replace(keyword, s, "");
                        sortKey = StringUtils.substring(sortKey, index + 2);
                    } else {
                        String find = StringUtils.substring(sortKey, index);
                        String[] splits = StringUtils.split(find);
                        find = splits[0];
                        if (splits.length >= 2) {
                            String letter = splits[1];
                            if (CharsUtils.isChinese(letter)) {
                                lst.add(letter);
                                sortKey = StringUtils.substring(sortKey, index + find.length() + 2);
                            } else {
                                lst.add(find);
                                sortKey = StringUtils.substring(sortKey, index + 2);
                            }
                        } else {
                            lst.add(find);
                            sortKey = StringUtils.substring(sortKey, index + 2);
                        }
                        keyword = replaceExistsLetter(keyword, find.toCharArray());
                    }
                } else {
                    hit = false;
                    break;
                }

            } while (StringUtils.isNotBlank(keyword));
        }
        if (hit) {
            return ArrayUtils.toArray(lst, String.class);
        } else {
            return null;
        }
    }

    /**
     * 把source中在exists存在的字符挨个替换
     *
     * @param source
     * @param exists
     * @return
     */
    public static String replaceExistsLetter(String source, char[] exists) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < exists.length; i++) {
            String s = String.valueOf(exists[i]);
            if (StringUtils.startsWith(source, s)) {
                source = StringUtils.substring(source, 1);
            } else {
                return source;
            }
        }
        return source;
    }

//    public static void main(String[] args) {
//        String sortKey = "zhong 中 S guo 国 ren 人 123 Z";
//        String[] testStrings = new String[]{"zhongs", "zhongz", "guoz", "renz", "guo123", "zhongguoren", "zhongguo", "zhongren", "zhongg", "zhongr", "guoren", "gren", "zhg", "zgr", "zr", "中", "人", "中国", "中人", "123", "12", "23", "2", "13"};
//
//        for (int i = 0; i < testStrings.length; i++) {
//            Date start = new Date();
//            String testString = testStrings[i];
//            String[] chineseLetter = getMatchLetters(sortKey, testString);
//            Date end = new Date();
//            System.out.println(testString + ":" + stringArrayToString(chineseLetter) + " time:" + String.valueOf(end.getTime() - start.getTime()));
//        }
//    }

    public static String stringArrayToString(String[] array) {
        if (null != array) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < array.length; i++) {
                builder.append(array[i]);
            }
            return builder.toString();
        }
        return null;
    }

    public static String getSame(String source, String exists, int index) {
        if (StringUtils.isNotBlank(source) && StringUtils.isNotBlank(exists)) {
            char[] sourceChars = source.toCharArray();
            char[] existsChars = exists.toCharArray();
            StringBuilder builder = new StringBuilder();
            for (int i = index; i < existsChars.length; i++) {
                if (i < sourceChars.length && existsChars[i] == sourceChars[i]) {
                    builder.append(existsChars[i]);
                } else {
                    break;
                }
            }
            return builder.toString();
        }
        return null;
    }
}
