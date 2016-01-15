package com.zxsoure.contactdemo;

import android.view.View;

/**
 * Created
 *
 * @author 张枭
 * @version V1.0
 * @Title: ${enclosing_method}
 * @Description: recycleview的item的点击事件接口和长按事件接口,主要为在整体RecyclerView的touchlistener中进行使用的
 * @date 2016/1/14
 * @throws ${tags}
 */
public interface RecycleClickWithPosListener {

    public void onItemClick(View view, int position);

    public void onItemLongClick(View view, int position);
}
