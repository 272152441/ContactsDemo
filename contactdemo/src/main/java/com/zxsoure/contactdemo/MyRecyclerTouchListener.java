package com.zxsoure.contactdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created
 *
 * @author 张枭
 * @version V1.0
 * @Title: ${enclosing_method}
 * @Description: recycleview的手势点击事件
 * @date 2016/1/14
 * @throws
 */
public class MyRecyclerTouchListener implements RecyclerView.OnItemTouchListener {

    // 手势处理事件
    private GestureDetector gestureDetector;
    // 外部回调点击事件
    private RecycleClickWithPosListener RecycleclickListener;

    public MyRecyclerTouchListener(Context context, final RecyclerView recyclerView, final RecycleClickWithPosListener listener) {

        this.RecycleclickListener = listener;

        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                // 处理点击事件回调
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                listener.onItemClick(child, recyclerView.getChildAdapterPosition(child));
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                // 处理长按事件回调
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                listener.onItemLongClick(child, recyclerView.getChildAdapterPosition(child));
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        // 监听事件分发
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && RecycleclickListener != null) {
           gestureDetector.onTouchEvent(e);
        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}