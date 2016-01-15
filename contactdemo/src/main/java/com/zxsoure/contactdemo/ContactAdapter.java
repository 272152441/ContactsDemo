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

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private List<ContactBean> mDataSet = new ArrayList<>();

    private Map<String,Integer> navToPosition = new HashMap<>();

    private Context mContext;

    private LayoutInflater layoutInflater;

    private RecycleClickListener mListener;

    public ContactAdapter(Context context, Map<String, List<ContactBean>> originData, List<String> allNavData) {
        mContext = context;
        layoutInflater = LayoutInflater.from(mContext);

        mDataSet.clear();
        navToPosition.clear();
        // 初始化数据
        initData(originData, allNavData);
    }

    // 设置adapter的监听事件
    public void setOnItemClickListener(RecycleClickListener listener){
        mListener = listener;
    }

    // 用来组装数据
    private void initData(Map<String, List<ContactBean>> originData, List<String> allNavData) {
        for (String item : allNavData) {
            if(originData.containsKey(item)){
                List<ContactBean> groupList =  originData.get(item);
                ContactBean firstItem = groupList.get(0);
                firstItem.setType(ContactBean.NAV_WITH_NORMAL);
                firstItem.setNavName(item);

                navToPosition.put(item, mDataSet.size());
                mDataSet.addAll(groupList);
            }
        }

        ContactBean lastItem = new ContactBean();
        lastItem.setType(ContactBean.DESC);
        lastItem.setName("总共" + mDataSet.size() + "个联系人");
        mDataSet.add(lastItem);

    }

    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.


        RecyclerView.ViewHolder holder = null;
        // 根据type的不同来创建3个holder
        switch (viewType) {
            case ContactBean.NORMAL:
                holder = new ContactNormal(layoutInflater.inflate(R.layout.contact_normal, viewGroup, false));
                break;
            case ContactBean.NAV_WITH_NORMAL:

                holder = new ContactWithNav(layoutInflater.inflate(R.layout.contact_nav, viewGroup, false));
                break;
            case ContactBean.DESC:

                holder = new ContactDesc(layoutInflater.inflate(R.layout.contact_desc, viewGroup, false));
                break;
            default:
                break;
        }

        return holder;
    }
    // END_INCLUDE(recyclerViewOnCreateViewHolder)


    @Override
    public int getItemViewType(int position) {

        return mDataSet.get(position).getType();
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ContactBean item = mDataSet.get(position);

        // 由于带导航的holder是不带导航的holder的子类，所以通用赋值逻辑放在一起进行处理

        if (holder instanceof ContactNormal) {
            // 赋值 名称和图片
            ContactNormal norHolder = (ContactNormal) holder;
            norHolder.getImg().setImageResource(item.getImgRes());
            norHolder.getName().setText(item.getName());
            // 如果是带导航的holder则赋值导航的名称
            if (holder instanceof ContactWithNav) {
                ContactWithNav navHolder = (ContactWithNav) norHolder;
                navHolder.getNav().setText(item.getNavName());
            }
            //如果是总结holder，赋值总结holder内容
        } else if (holder instanceof ContactDesc) {
            ContactDesc totlHolder = (ContactDesc) holder;
            totlHolder.getTotleName().setText(item.getName());
        }

        // 只是为了实现动画效果
        if(!holder.itemView.isClickable()){
            holder.itemView.setClickable(true);
        }
        if(mListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(v);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return mListener.onItemLongClick(v);
                }
            });
        }



    }

    // 根据导航的文字内容来获取item的位置
    public int getPositionByNav(String nav) {
        if(nav==null){
            return -1;
        }

        if(navToPosition.containsKey(nav)){
            return navToPosition.get(nav);
        }
        return -1;
    }

    /**
     * 根据位置来获取数据item
     * @param position
     * @return
     */
    public ContactBean getItemByPosition(int position){
        return mDataSet.get(position);
    }
}
