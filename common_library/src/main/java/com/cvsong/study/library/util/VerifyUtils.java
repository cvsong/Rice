package com.cvsong.study.library.util;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.TextView;

import com.cvsong.study.library.util.utilcode.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 校验工具类
 * Created by zhuofeng on 2015/4/10.
 */
public class VerifyUtils {

    /**
     * 简单手机正则校验
     *
     * @param MobileNo 手机号
     * @return
     */
    public static boolean isValidMobileNo(@NonNull String MobileNo) {

        String regPattern = "^1[3-9]\\d{9}$";
        return Pattern.matches(regPattern, MobileNo);

    }

    /**
     * 6-12位数字密码正则校验
     *
     * @param Pwd 密码
     * @return
     */
    public static boolean isValidPwd(@NonNull String Pwd) {

       /* Pattern pat = Pattern.compile("[\\da-zA-Z]{8,12}");
        Pattern patno = Pattern.compile(".*\\d.*");
        Pattern paten = Pattern.compile(".*[a-zA-Z].*");
        Matcher mat = pat.matcher(Pwd);
        Matcher matno = patno.matcher(Pwd);
        Matcher maten = paten.matcher(Pwd);
        return (matno.matches() && maten.matches() && mat.matches());*/
        //Pattern patno = Pattern.compile(".*\\d.*");
        //Pattern paten = Pattern.compile(".*[a-zA-Z].*");
        //Matcher matno = patno.matcher(Pwd);
        //Matcher maten = paten.matcher(Pwd);
        //6到16位任意密码
        Pattern pat = Pattern.compile("^[0-9a-zA-Z_#]{6,16}$");
        //只能为数字
        //  Pattern pat = Pattern.compile("[\\d]{8,12}");
        Matcher mat = pat.matcher(Pwd);
        return mat.matches();
    }

    /**
     * 判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isContainChinese(@NonNull String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否为包含中文
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 登录密码的校验,只校验密码的长度
     *
     * @param Pwd 登录密码
     * @return
     */
    public static boolean logonisValidPwd(@NonNull String Pwd) {

       /* Pattern pat = Pattern.compile("[\\da-zA-Z]{8,12}");
        Pattern patno = Pattern.compile(".*\\d.*");
        Pattern paten = Pattern.compile(".*[a-zA-Z].*");
        Matcher mat = pat.matcher(Pwd);
        Matcher matno = patno.matcher(Pwd);
        Matcher maten = paten.matcher(Pwd);
        return (matno.matches() && maten.matches() && mat.matches());*/
        //Pattern patno = Pattern.compile(".*\\d.*");
        //Pattern paten = Pattern.compile(".*[a-zA-Z].*");
        //Matcher matno = patno.matcher(Pwd);
        //Matcher maten = paten.matcher(Pwd);
        //数字和字母的组合密码
        // Pattern pat = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,12}$");
        //只能为数字
        Pattern pat = Pattern.compile("^.{8,12}$");
        Matcher mat = pat.matcher(Pwd);
        return mat.matches();
    }

    /**
     * 密码长度校验,默认不忽略最大长度
     *
     * @param pwd
     * @return
     */
    public static boolean isValidServicePwd(@NonNull String pwd) {
        return isValidServicePwd(pwd, false);
    }

    /**
     * 密码长度校验
     *
     * @param pwd
     * @param ignoreMax, 是否忽略最大长度
     * @return
     */
    public static boolean isValidServicePwd(@NonNull String pwd, boolean ignoreMax) {
        return pwd.length() > 5 && (ignoreMax || pwd.length() < 13);
    }

    /**
     * Luhn算法验证银行卡卡号是否有效
     */
    public static boolean isBankcardNo(String number) {
        int s1 = 0, s2 = 0;
        String reverse = new StringBuffer(number).reverse().toString();
        for (int i = 0; i < reverse.length(); i++) {
            int digit = Character.digit(reverse.charAt(i), 10);
            if (i % 2 == 0) {//this is for odd digits, they are 1-indexed in the algorithm
                s1 += digit;
            } else {//add 2 * digit for 0-4, add 2 * digit - 9 for 5-9
                s2 += 2 * digit;
                if (digit >= 5) {
                    s2 -= 9;
                }
            }
        }
        return (s1 + s2) % 10 == 0;
    }

    /**
     * 判断是否是姓名
     *
     * @param name
     * @return
     */
    public static boolean isUserName(CharSequence name) {
        return !TextUtils.isEmpty(name);
    }


    /**
     * 验证码校验
     *
     * @param code
     * @return
     */
    public static boolean isServiceCode(@NonNull String code) {
        return code.length() > 0 && code.length() < 10;
    }

    /**
     * URL检查<br>
     * <br>
     *
     * @param pInput 要检查的字符串<br>
     * @return boolean   返回检查结果<br>
     */
    public static boolean isUrl(String pInput) {
        if (pInput == null) {
            return false;
        }
        String regEx = "^((https|http|ftp|rtsp|mms)?:\\/\\/)[^\\s]+";
        Pattern p = Pattern.compile(regEx);
        Matcher matcher = p.matcher(pInput);
        return matcher.matches();
    }

    /**
     * 校验邀请码
     *
     * @param pInput
     * @return
     */
    public static boolean isInviteCode(String pInput) {
        if (pInput == null) {
            return false;
        }
        String regEx = "[A-Za-z0-9]{6}";
        Pattern p = Pattern.compile(regEx);
        Matcher matcher = p.matcher(pInput);
        return matcher.matches();
    }

    /**
     * 校验我们发出的验证码
     *
     * @param pInput
     * @return
     */
    public static boolean isVerifyCode(String pInput) {
        if (pInput == null) {
            return false;
        }
        String regEx = "[0-9]{6}";
        Pattern p = Pattern.compile(regEx);
        Matcher matcher = p.matcher(pInput);
        return matcher.matches();
    }

    /**
     * 判断用户的手机号的运营商
     *
     * @param number 要判断的电话号码
     * @return 运营商编号 0---未知运营商 1---移动，2---联通， 3---电信
     */
    public static int PhoneNumKind(String number) {
        // 移动
        String telcomPatternY = "^134[0-8]\\d{7}$|^(?:13[5-9]|147|15[0-27-9]|178|18[2-478])\\d{8}$";
        // 联通
        String telcomPatternU = "^(?:13[0-2]|145|15[56]|176|18[56])\\d{8}";
        //电信
        String telcomPatternD = "^(?:133|153|177|18[019])\\d{8}$";
        if (number.matches(telcomPatternY)) { //移动用户
            return 1;
        } else if (number.matches(telcomPatternU)) { //联通用户
            return 2;
        } else if (number.matches(telcomPatternD)) { //电信用户
            return 3;
        } else //未知用户
            return 0;

    }

    /**
     * 对一个字符串，指定字符“段”替换成对应的字符
     *
     * @param resStr 字符串
     * @param begin  开始
     * @param end    结束
     * @return
     */
    public static String strReplace(String resStr, int begin, int end, char replacChar) {

        //判断是否满足条件
        if (resStr == null || resStr.length() < end) {
            return resStr;
        }

        char[] cs = resStr.toCharArray();

        for (int i = cs.length - 1 - end; i >= begin; i--) {
            cs[i] = replacChar;
        }
        return String.valueOf(cs);

    }

    /**
     * 判断 TextView是否有数据
     *
     * @param textViews
     * @return 有无效数据就返回true
     */
    public static boolean isTextViewHasNull(TextView... textViews) {
        boolean result = false;
        if (textViews == null || textViews.length < 0) {
            return false;
        } else {
            for (TextView et : textViews) {
                result = TextUtils.isEmpty(et.getText().toString());
                if (result)
                    break;
            }
        }

        return result;
    }


    //格式化手机号
    public static String formatPhoneNo(String phoneNo) {
        if (null == phoneNo) {
            return "";
        }
        if (phoneNo.startsWith("+86")) {
            phoneNo = phoneNo.substring(3);
        }
        if (phoneNo.startsWith("86")) {
            phoneNo = phoneNo.substring(2);
        }
        phoneNo = phoneNo.replaceAll("-", "");
        phoneNo = phoneNo.replaceAll(" ", "");
        phoneNo = phoneNo.trim();
        return phoneNo;
    }

    //格式化字符串，去除emoji，防止后台入库异常
    public static String formatString(String name) {
        if (null == name) {
            return "";
        }
        return filterEmoji(name).trim();
    }

    /**
     * 去除emoji表情
     * [\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]
     *
     * @param source 原字符串
     * @return 去除后的字符串
     */
    public static String filterEmoji(String source) {
        if (!TextUtils.isEmpty(source)) {
            return source.replaceAll("[\\ud83c\\udc00-\\ud83c\\udfff]|[\\ud83d\\udc00-\\ud83d" +
                    "\\udfff]|[\\u2600-\\u27ff]", "");
        } else {
            return "";
        }
    }

    /**
     * 是否是连续数字
     *
     * @param numOrStr
     * @return
     */
    public static boolean isOrderNumeric(String numOrStr) {
        if (TextUtils.isEmpty(numOrStr) || isNumber(numOrStr)) {
            return false;
        }
        boolean flag = true;
        for (int i = 0; i < numOrStr.length(); i++) {
            if (i > 0) {// 判断如123456
                int numAfter = Integer.parseInt(numOrStr.charAt(i - 1) + "") - 1;//-1 1
                int num = Integer.parseInt(numOrStr.charAt(i) + "");//2 3
                int numBefore = Integer.parseInt(numOrStr.charAt(i - 1) + "") + 1;//2 3
                if (num != numBefore && num != numAfter) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }


    /**
     * 判断是否是数字字符串
     *
     * @param str
     * @return
     */
    public static boolean isNumber(final String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        final char[] chars = str.toCharArray();
        int sz = chars.length;
        boolean hasExp = false;
        boolean hasDecPoint = false;
        boolean allowSigns = false;
        boolean foundDigit = false;
        // deal with any possible sign up front
        final int start = (chars[0] == '-') ? 1 : 0;
        if (sz > start + 1 && chars[start] == '0') { // leading 0
            if (
                    (chars[start + 1] == 'x') ||
                            (chars[start + 1] == 'X')
                    ) { // leading 0x/0X
                int i = start + 2;
                if (i == sz) {
                    return false; // str == "0x"
                }
                // checking hex (it can't be anything else)
                for (; i < chars.length; i++) {
                    if ((chars[i] < '0' || chars[i] > '9')
                            && (chars[i] < 'a' || chars[i] > 'f')
                            && (chars[i] < 'A' || chars[i] > 'F')) {
                        return false;
                    }
                }
                return true;
            } else if (Character.isDigit(chars[start + 1])) {
                // leading 0, but not hex, must be octal
                int i = start + 1;
                for (; i < chars.length; i++) {
                    if (chars[i] < '0' || chars[i] > '7') {
                        return false;
                    }
                }
                return true;
            }
        }
        sz--; // don't want to loop to the last char, check it afterwords
        // for type qualifiers
        int i = start;
        // loop to the next to last char or to the last char if we need another digit to
        // make a valid number (e.g. chars[0..5] = "1234E")
        while (i < sz || (i < sz + 1 && allowSigns && !foundDigit)) {
            if (chars[i] >= '0' && chars[i] <= '9') {
                foundDigit = true;
                allowSigns = false;

            } else if (chars[i] == '.') {
                if (hasDecPoint || hasExp) {
                    // two decimal points or dec in exponent
                    return false;
                }
                hasDecPoint = true;
            } else if (chars[i] == 'e' || chars[i] == 'E') {
                // we've already taken care of hex.
                if (hasExp) {
                    // two E's
                    return false;
                }
                if (!foundDigit) {
                    return false;
                }
                hasExp = true;
                allowSigns = true;
            } else if (chars[i] == '+' || chars[i] == '-') {
                if (!allowSigns) {
                    return false;
                }
                allowSigns = false;
                foundDigit = false; // we need a digit after the E
            } else {
                return false;
            }
            i++;
        }
        if (i < chars.length) {
            if (chars[i] >= '0' && chars[i] <= '9') {
                // no type qualifier, OK
                return true;
            }
            if (chars[i] == 'e' || chars[i] == 'E') {
                // can't have an E at the last byte
                return false;
            }
            if (chars[i] == '.') {
                if (hasDecPoint || hasExp) {
                    // two decimal points or dec in exponent
                    return false;
                }
                // single trailing decimal point after non-exponent is ok
                return foundDigit;
            }
            if (!allowSigns
                    && (chars[i] == 'd'
                    || chars[i] == 'D'
                    || chars[i] == 'f'
                    || chars[i] == 'F')) {
                return foundDigit;
            }
            if (chars[i] == 'l'
                    || chars[i] == 'L') {
                // not allowing L with an exponent or decimal point
                return foundDigit && !hasExp && !hasDecPoint;
            }
            // last character is illegal
            return false;
        }
        // allowSigns is true iff the val ends in 'E'
        // found digit it to make sure weird stuff like '.' and '1E-' doesn't pass
        return !allowSigns && foundDigit;
    }


    /**
     * 不能全是相同的数字或者字母（如：000000、111111、aaaaaa） 全部相同返回true
     *
     * @param numOrStr
     * @return
     */
    public static boolean isAllSameStr(String numOrStr) {
        if (TextUtils.isEmpty(numOrStr)) {
            return false;
        }
        boolean flag = true;
        char str = numOrStr.charAt(0);
        for (int i = 0; i < numOrStr.length(); i++) {
            if (str != numOrStr.charAt(i)) {
                flag = false;
                break;
            }
        }
        return flag;
    }


    /**
     * 判断邮箱格式是否正确：
     */
    public static boolean isValidEmail(@NonNull String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 去除字符串中的特殊字符只允中文
     */
    public static String getCharacterString(String name) {
        if (TextUtils.isEmpty(name)) {
            return "";
        }
        String regEx = "[^(\\u4e00-\\u9fa5)]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(name);
        return m.replaceAll("").trim();
    }

    /**
     * 简单手机正则校验
     *
     * @param MobileNo 手机号
     * @return
     */
    public static boolean isMobileNo(@NonNull String MobileNo) {

        String regPattern = "^1[3-9]\\d{9}$";
        return Pattern.matches(regPattern, MobileNo);

    }
}
