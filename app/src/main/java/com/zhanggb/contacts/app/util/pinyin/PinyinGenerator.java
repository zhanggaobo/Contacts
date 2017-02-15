package com.zhanggb.contacts.app.util.pinyin;


import com.zhanggb.contacts.app.util.StringUtils;

/**
 * Created by wangwei
 * Date: 4/17/12
 */
public class PinyinGenerator {
    public static final String EXCLUSIVE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz~!@#$%^&*()_+`1234567890-=[]\\{}|;':\",./<>?";

    public static class Pinyin {
        public String fullName; //WangWei
        public String briefName;//WW

        public Pinyin() {
        }

        public void appendFullName(String str) {
            if (StringUtils.isEmpty(fullName)) {
                fullName = str;
            } else {
                fullName += str;
            }
        }

        public void appendBriefName(char str) {
            if (StringUtils.isEmpty(briefName)) {
                briefName = String.valueOf(str);
            } else {
                briefName += str;
            }
        }
    }

    public static String generate(String name) {
        if (StringUtils.isEmpty(name)) {
            return StringUtils.EMPTY;
        }
        return doGenerate(name, null).toUpperCase();
    }

    public static Pinyin generatePinyin(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        Pinyin pinyin = new Pinyin();
        doGenerate(name, pinyin).toUpperCase();
        return pinyin;
    }

    private static String doGenerate(String name, Pinyin pinyin) {
        StringBuilder builder = new StringBuilder();
        char[] values = name.toCharArray();
        for (char value : values) {
            if (EXCLUSIVE.indexOf(value) != -1) {
                builder.append(value);
            } else {
                final String str = getPinyin(value);
                if (pinyin != null) {
                    pinyin.appendFullName(str);
                    pinyin.appendBriefName(str.charAt(0));
                }
                builder.append(str).append(" ").append(value).append(" ");
            }
        }
        return builder.toString();
    }

    // 返回单个字符的汉字拼音
    private static String getPinyin(char ch) {
        // 如果是英文字母则直接返回；
        if (EXCLUSIVE.indexOf(ch) != -1) {
            return String.valueOf(ch);
        }

        // 如果是中文或数字，则从hash表中检索
        short hash = getHashIndex(ch);
        for (int i = 0; i < PyHash.hashes[hash].length; ++i) {
            short index = PyHash.hashes[hash][i];
            String py = PyCode.codes[index];
            if (StringUtils.isEmpty(py)) {
                return StringUtils.EMPTY;
            }
            int pos = py.indexOf(ch, 7);
            if (pos != -1)
                return StringUtils.trim(py.substring(0, 6));
        }

        return StringUtils.EMPTY;
    }

    private static short getHashIndex(char ch) {
        return (short) ((int) ch % PyCode.codes.length);
    }
}

