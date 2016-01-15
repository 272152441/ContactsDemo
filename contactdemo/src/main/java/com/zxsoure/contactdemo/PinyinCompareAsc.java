package com.zxsoure.contactdemo;

import java.util.Comparator;

/**
 * Created
 *
 * @author 张枭
 * @version V1.0
 * @Title: ${enclosing_method}
 * @Description: 比较器，用来比较判断拼音排序，不可识别字符排最后
 * @date 2016/1/14
 */
public class PinyinCompareAsc implements Comparator<ConverPinyinInterface> {

    @Override
    public int compare(ConverPinyinInterface lhs, ConverPinyinInterface rhs) {
        if(lhs==null||lhs.getPinyin()==null){
            return 1;
        }

        if(rhs==null||rhs.getPinyin()==null){
            return -1;
        }

        return lhs.getPinyin().compareTo(rhs.getPinyin());
    }
}
