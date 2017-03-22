package me.blog.korn123.utils;

/**
 * Created by CHO HANJOONG on 2016-10-14.
 */

public class StringUtils {

    /**
     * 해당하는 문자열에 대해서 입력된 길이만큼 부족한 길이를 왼쪽부터 공백으로 채워넣는다.<br><br>
     *
     * StringUtils.leftPad("Anyframe", 12) = "    Anyframe"
     *
     * @param str 문자열
     * @param size 공백이 채워진 문자열의 전체 길이
     * @return 부족한 길이만큼 왼쪽부터 공백이 채워진 문자열
     */
    public static String leftPad(String str, int size) {
        return leftPad(str, size, ' ');
    }

    /**
     * 해당하는 문자열에 대해서 입력된 길이만큼 부족한 길이를 왼쪽부터 지정된 character로 채워넣는다.<br><br>
     *
     * StringUtils.leftPad("Anyframe", 12, 'a') = "aaaaAnyframe"
     *
     * @param str 문자열
     * @param size 캐릭터가 채워진 문자열의 전체 길이
     * @param padChar 채워넣을 캐릭터
     * @return 부족한 길이만큼 왼쪽부터 캐릭터가 채워진 문자열
     */
    public static String leftPad(String str, int size, char padChar) {
        return padChar(str, size, padChar, true);
    }

    private static String padChar(String str, int size, char padChar, boolean isLeft) {
        if (str == null) {
            return null;
        }
        int originalStrLength = str.length();

        if (size < originalStrLength) {
            return str;
        }

        int difference = size - originalStrLength;

        StringBuilder strBuf = new StringBuilder();
        if (!isLeft) {
            strBuf.append(str);
        }

        for (int i = 0; i < difference; i++) {
            strBuf.append(padChar);
        }

        if (isLeft) {
            strBuf.append(str);
        }

        return strBuf.toString();
    }

    /**
     * 해당하는 문자열에 대해서 입력된 길이만큼 부족한 길이를 오른쪽부터 공백으로 채워넣는다.<br><br>
     *
     * StringUtils.rightPad("Anyframe", 12) = "Anyframe    "
     *
     * @param str 문자열
     * @param size 공백이 채워진 문자열의 전체 길이
     * @return 부족한 길이만큼 오른쪽부터 공백이 채워진 문자열
     */
    public static String rightPad(String str, int size) {
        return rightPad(str, size, ' ');
    }

    /**
     * 해당하는 문자열에 대해서 입력된 길이만큼 부족한 길이를 오른쪽부터 지정된 문자로 채워넣는다.<br><br>
     *
     * StringUtils.rightPad("Anyframe", 12, 'a') = "Anyframeaaaa"
     *
     * @param str 문자열
     * @param size 캐릭터가 채워진 문자열의 전체 길이
     * @param padChar 채워넣을 캐릭터
     * @return 부족한 길이만큼 오른쪽부터 캐릭터가 채워진 문자열
     */
    public static String rightPad(String str, int size, char padChar) {
        return padChar(str, size, padChar, false);
    }

}
