package com.zxsoure.contactdemo;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.Arrays;

/**
 * Created
 *
 * @author 张枭
 * @version V1.0
 * @Title: ${enclosing_method}
 * @Description: ${todo}(这里用一句话描述这个方法的作用)
 * @date 2016/1/14
 * @throws ${tags}
 */
public class PinYinUtil {

    private PinYinUtil(){

    }


    public static String converCnToPY(String inputString) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        char[] input = inputString.trim().toCharArray();
        StringBuffer output = new StringBuffer();

        try {
            for (int i = 0; i < input.length; i++) {
                if (java.lang.Character.toString(input[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);
                    Arrays.sort(temp);
                    output.append(temp[0]);
                } else if (java.lang.Character.toString(input[i]).matches("[A-Z]+")) {
                    output.append(Character.toString(input[i]));
                } else if (java.lang.Character.toString(input[i]).matches("[a-z]+")) {
                    output.append(Character.toString(input[i]).toUpperCase());
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
