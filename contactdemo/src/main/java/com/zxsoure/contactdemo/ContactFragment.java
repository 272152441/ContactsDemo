/*
* Copyright (C) 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.zxsoure.contactdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用来显示联系人的frag
 */
public class ContactFragment extends Fragment {

    private static final String TAG = "ContactFragment";

    protected RecyclerView mRecyclerView;

    public static final String OTHER_CATE_NAV = "#";

    private TextView  mDialogText;

    // 原始需要的基本数据，当请求服务后返回的话，需要进行逻辑组装成为这种数据
    private Map<String, List<ContactBean>> originNeedData = new HashMap<>();

    private String[] demoName = {
            "陈迪","肖东菁","汪佳丽","傅双育","王聪","李璇","马舒滟","许青云","徐丹","张奕","田野",
            "彭亚光","殷夏","黄欢","庞琳","张子腾","姜春阳","刘冰若","万菲","何丽","刘龙飞","顾天天",
            "宣苗","周彦楠","朱小玲","毛雅清","江皎","王伊","黄登高","王莉","董润","谭雪","董方帅",
            "黄明光","李凯生","黄乃康","贾玲","陈晓露","刘艳芳","刘丽艳","任黎平","王盼盼","杨欢",
            "张舒婷","杨芳","茹然","许朝亮","邢洋","陆欣楠","刘易","王文霞","刘佳","刁海鹏","杜亚琼",
            "程玲","高川","金鼎","苏旭","李永清","龙慧","柴扉","张琳","董薇","余婧","李亦然","徐歌",
            "孔维源","冯栋","汪潇潇","张文婷","勘晓妹","张依弛","孙榕","廖丽霞","解欣","谭军红","张玲玲",
            "傅姁妮","张润","郝蓓蓓","张青梅","乔亚楠","冯敬宇","黄莎莎","潘佼佼","李欣","龚旻","李丹",
            "陈翔洁","李刚","吴思莹","罗兰君","李森","周莹","严岩","文妮","吕昊","李珏","潘小旋","赵晓敏",
            "闫颖慧","何洁萍","韩香茹","徐婷婷","高彬","孙巍","薄坤坤","刘旭阳","王思敏","张蕾","卜文月",
            "张媛媛","王鹏飞","黄颖","张琼","姚秋月","廉雁捷","周玮","马杰","郭佳","赵淑洁","李琴","谢海莲",
            "刘京","李晓燕","王聪","范亚娟","徐娟","张硕","贾绰","范逊敏","杨丽","单雅丽","谢云生","韩冬",
            "庞书娟","史乃原","孙雅念","李旗挺","李艳婷","马雁翔","尹姗姗","黄鼎","庞哲","朱佳婧","张珊珊",
            "郝思雨","王鑫","马健","叶恒","杨帆","闫雪花","段晓雯","张子晶","郑婷婷","高淑贤","孟佳","黄琦",
            "郭静","余小娟","贾彩娟","陈荣锦","于姝斐","阮盛婧","温洁","李英健","庞晓瑜","方娟","许晓凌","谢娜",
            "张锐","孟竹","李欣","张红玲","张杰","贾翠晓","巩潇然","王琴","杨文田","韩志保","宋燕","张东群",
            "赵爱香","赵雪培","李雪飞","范召琳","楚红梅","魏荣梅","赵晨","尚继鹏","张一珠","申志敏","殷晓婷",
            "李慧芳","贾利利","刘欣","杨剑东","张变英","苏丹","邓罕攀","李焰","郑玉兰","李作健","宁晓蕾",
            "张志诚","刘璐","李军林","陈敏杰","梁峰","肖蕾","李维奇","喻唯","王博","袁丹","曲芳","武妹",
            "宫素香","刁文洁","王雪","范佳慧","周芸","刘婷婷","盛文凤","王雪","崔丹","刘颖","王玉洁","姜葳",
            "董英灏","王侃","刘琨","冯超","宁升椿","陈光琦","陈晓晨","郭欣","李杨","徐冬蕾","王华梅","董达勇",
            "ash#@!","djaffs","3214324","#$@%#@$@","231$#@123","sadd@#$123"
    };

    private int[] demoRes = {
            R.drawable.mytest1, R.drawable.mytest2, R.drawable.mytest3, R.drawable.mytest4, R.drawable.mytest5,
            R.drawable.mytest6, R.drawable.mytest7, R.drawable.mytest8, R.drawable.mytest9, R.drawable.mytest10,
            R.drawable.mytest11, R.drawable.mytest12, R.drawable.mytest13, R.drawable.mytest14, R.drawable.mytest15,
            R.drawable.mytest16, R.drawable.mytest17, R.drawable.mytest18, R.drawable.mytest19, R.drawable.mytest20,
            R.drawable.mytest21, R.drawable.mytest22, R.drawable.mytest23, R.drawable.mytest24, R.drawable.mytest25,
            R.drawable.mytest26, R.drawable.mytest27, R.drawable.mytest28, R.drawable.mytest29, R.drawable.mytest30,
            R.drawable.mytest31, R.drawable.mytest32, R.drawable.mytest33, R.drawable.mytest34, R.drawable.mytest35,
            R.drawable.mytest36, R.drawable.mytest37, R.drawable.mytest38, R.drawable.mytest39, R.drawable.mytest40,
            R.drawable.mytest41, R.drawable.mytest42, R.drawable.mytest43
    };

    private String[] demoNav = {
            "↥", "☆", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
            "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDataset();
        // 需要先进行初始化所有导航数据
        initNavList();
    }

    // 生成全部的导航数据
    private List<String> initNavList() {
        return Arrays.asList(demoNav);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contact_frag_view, container, false);
        // 先初始化导航数据
        List<String> allNavData = initNavList();

        // 提示dialog的view，

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.contact_recyvle_id);
        // 设置布局管理器
        initRecycleView(mRecyclerView);
        // 设置数据适配器

        final ContactAdapter mAdapter = new ContactAdapter(getContext(), originNeedData, allNavData);
        mRecyclerView.setAdapter(mAdapter);

        // 设置分割线
        mRecyclerView.addItemDecoration(new ContactDecoration(getResources().getDrawable(R.drawable.contact_division)));

        NavView navView = (NavView) rootView.findViewById(R.id.nav_id);

        // 初始化nav数据
        navView.initNavData(allNavData);
//        navView.setShowAllNav(false,showNavData);
        navView.invalidate();

        /**
         * 2中注册事件的方式，2选1
         */
        final Toast toast = Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);

//        mRecyclerView.addOnItemTouchListener(new MyRecyclerTouchListener(getContext(), mRecyclerView, new RecycleClickWithPosListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//        ContactBean item = mAdapter.getItemByPosition(position);
//                Log.i(TAG, "onClick: "+position);
//        toast.setText("position:" + position);
//        toast.show();
//            }
//
//            @Override
//            public void onItemLongClick(View view, int position) {
//                Log.i(TAG, "onLong: "+position);
//            }
//        }));

        mAdapter.setOnItemClickListener(new RecycleClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = mRecyclerView.getChildAdapterPosition(view);
                ContactBean item = mAdapter.getItemByPosition(position);
                Log.i(TAG, "item position" + position);
                toast.setText("position:" + position);
                toast.show();
            }

            @Override
            public boolean onItemLongClick(View view) {
                return false;
            }
        });

        navView.setNavListener(new NavView.NavViewListener() {
            @Override
            public void navChangeListener(String navContent) {
                Log.e(TAG, navContent);
                int position = mAdapter.getPositionByNav(navContent);
                if (position > -1) {
                    ((LinearLayoutManager)(mRecyclerView.getLayoutManager())).scrollToPositionWithOffset(position, 0);
                }
            }
        });

        return rootView;
    }

    /**
     * 初始化recycleview
     *
     * @param mRecyclerView
     */
    public void initRecycleView(RecyclerView mRecyclerView) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    /**
     * 初始化数据
     */
    private void initDataset() {

        List<ContactBean> newTempData = new ArrayList<>();


        for (int i = 0; i < demoName.length; i++) {
            ContactBean item = new ContactBean();
            item.setImgRes(demoRes[i%43]);
            item.setName(demoName[i]);
            item.setType(ContactBean.NORMAL);
            newTempData.add(item);
        }

        // 先进行排序
        List<ContactBean> sortData = converPinyinAndSort(newTempData);
        // 再进行分组
        Map<String, List<ContactBean>> divGroupMap = divGroupData(sortData);

        originNeedData = divGroupMap;

    }

    /**
     * 根据数据转换后的拼音内容进行分组
     * @param sortData
     * @return
     */
    private Map<String, List<ContactBean>> divGroupData(List<ContactBean> sortData) {

        Map<String, List<ContactBean>> mapData = new HashMap<>();

        for(ContactBean item :sortData){
            if(item==null){
                continue;
            }

            String converPinyin = item.getPinyin();

            String nav ;

            // 先进行判断是否是其他分类的数据
            if(converPinyin==null||"".equals(converPinyin)){
                nav = OTHER_CATE_NAV;
            }else{
                nav = converPinyin.substring(0,1);
            }

            // 处理新的导航数据
            if(mapData.containsKey(nav)){
                List<ContactBean> navData = mapData.get(nav);
                navData.add(item);
            }else{
                List<ContactBean> navData = new ArrayList<>();
                navData.add(item);
                mapData.put(nav,navData);
            }

        }
        return mapData;
    }

    /**
     * 转化数据把名字转化成为拼音，并进行第一次排序
     * @param originData
     * @return
     */
    private List<ContactBean> converPinyinAndSort(List<ContactBean> originData) {
        for(ContactBean item :originData){
            if(item instanceof  ConverPinyinInterface){
                item.setPinyin(PinYinUtil.converCnToPY(item.getName()));
            }
        }

        Collections.sort(originData,new PinyinCompareAsc());

        return originData;
    }

    // 用来创建假数据的工具方法
    private int getOverIndex(Map<String, Integer> navPositionTempMap, String key) {
        String tempBigValue = null;

        for (String item : navPositionTempMap.keySet()) {
            if (item.compareTo(key) > 0) {
                if (tempBigValue == null || tempBigValue.compareTo(item) > 0) {
                    tempBigValue = item;
                }

            }
        }

        if (tempBigValue == null) {
            return -1;
        }

        return navPositionTempMap.get(tempBigValue);
    }


}
