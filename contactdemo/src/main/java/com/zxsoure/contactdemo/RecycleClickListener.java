package com.zxsoure.contactdemo;

import android.view.View;

/**
 * Created
 *
 * @author 张枭
 * @version V1.0
 * @Title: ${enclosing_method}
 * @Description: recycleview的item的点击事件接口和长按事件接口, 主要为在adapter中进行注册使用的
 * @date 2016/1/14
 * @throws ${tags}
 */
public interface RecycleClickListener {

    public void onItemClick(View view);

    public boolean onItemLongClick(View view);
}
