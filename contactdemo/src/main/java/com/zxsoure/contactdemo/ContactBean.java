package com.zxsoure.contactdemo;

/**
 * Created
 *
 * @author 张枭
 * @version V1.0
 * @Title: ${enclosing_method}
 * @Description: ${todo}(这里用一句话描述这个方法的作用)
 * @date 2016/1/12
 */
public class ContactBean implements  ConverPinyinInterface{

    public final static int NORMAL = 1;

    public final static int NAV_WITH_NORMAL = 2;

    public final static int DESC = 3;

    private String name;

    private int type;

    private int imgRes;

    private String navName;
    // 转换的拼音，不包含特殊字符和数字
    private String converPinyin;

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNavName() {
        return navName;
    }

    public void setNavName(String navName) {
        this.navName = navName;
    }

    @Override
    public String getPinyin() {
        return converPinyin;
    }

    @Override
    public void setPinyin(String pinyin) {
        converPinyin = pinyin;
    }
}
