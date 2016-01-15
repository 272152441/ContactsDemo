package com.zxsoure.contactdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NavView extends View {

    // 所有的导航数据
    private List<String> allNavData;
    // 当前数据中存在的导航数据
    private List<String> curNavData;
    // 当前在view中显示的导航数据
    private List<String> showNavData;
    // 用来处理定位信息的map
    private Map<RectF, String> navRangeMap = new HashMap<>();
    // 用来处理显示数据的map
    private Map<String, RectF> revNavRangeMap = new HashMap<>();
    // 用来处理快速定位到指定位置的list
    private List<RectF> navRangeList = new ArrayList<>();
    // 文字所需要绘制的画笔
    private TextPaint mPaint;
    // 当前view所占的大小
    private int mViewWidth, mViewHeight;
    // 导航文字所占的空间大小
    private Rect textRect;
    // 设置是否显示全部的导航 如果不是显示全部的导航的话，需要导航和数据相结合进行处理
    private boolean showAllNav = true;
    // 当前是否在进行触摸操作
    private boolean touchStatus;
    // 当前view触摸的监听事件
    private NavViewListener mListener;
    // 默认的文字大小尺寸
    private static final int DEFAULT_TEXTSIZE = 12;
    // 文字当前设置的尺寸大小
    private int mTextSize = DEFAULT_TEXTSIZE;
    // 用作缓存，用来加速定位导航内容
    private RectF temRectF;
    // 默认的文字颜色
    private int textColor = 0xff888888;
    // 点击后背景的颜色
    private int touchBackgroundColor = 0x30000000;
    // 提示的内容文字
    private TextView mDialogText;
    // 设置是否显示提示
    private boolean isNavPrompt = true;

    public NavView(Context context) {
        super(context);
        init(null, 0);
    }

    public NavView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public NavView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    public void initNavData(List<String> allNavData) {
        if (allNavData == null) {
            throw new IllegalArgumentException("navData is error");
        }
        this.allNavData = allNavData;
    }

    /**
     * 根据当前的数据组装要显示导航数据的尺寸大小
     */
    private void calNavData() {

        if (allNavData == null) {
            return;
        }

        // 是否显示全部的导航数据内容
        if (isShowAllNav()) {
            showNavData = allNavData;
        } else {
            showNavData = curNavData;
        }
        // 所要显示的元素个数
        int elementNum = showNavData.size();
        float itemHeight = mViewHeight / (float) elementNum;
        // 文字大小 获取高度与宽度的最小值，并且空余4像素，用来做padding
        mTextSize = (int) Math.min(itemHeight, mViewWidth) - 4;
        // 重新计算范围，以及清除之前的map内容
        navRangeMap.clear();
        revNavRangeMap.clear();
        navRangeList.clear();
        for (int i = 0; i < showNavData.size(); i++) {
            // 计算出每个值对应的位置范围rect
            RectF item = new RectF();
            item.set(0, i * itemHeight, mViewWidth, (i + 1) * itemHeight);
            // 把显示的值和范围存入到map中
            navRangeMap.put(item, showNavData.get(i));

            revNavRangeMap.put(showNavData.get(i), item);
            navRangeList.add(item);
        }
    }

    /**
     * 初始化设置，用来设置基本参数
     *
     * @param attrs
     * @param defStyle
     */
    private void init(AttributeSet attrs, int defStyle) {

        mDialogText = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.contact_nav_prompt, null);
        mDialogText.setVisibility(View.INVISIBLE);
        setScrollPopPrompt();

        mPaint = new TextPaint();
        textRect = new Rect();

        mPaint.setColor(textColor);
        mPaint.setTypeface(Typeface.DEFAULT);
        mPaint.setAntiAlias(true);
    }

    /**
     * 初始化显示提示的内容
     */
    private void setScrollPopPrompt() {

        WindowManager mWindowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        mWindowManager.addView(mDialogText, lp);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (touchStatus) {
            canvas.drawColor(touchBackgroundColor);
        }

        mPaint.setTextSize(mTextSize * 3.0f / 4.0f);
        // 绘制每个导航文字
        for (int i = 0; i < showNavData.size(); i++) {

            mPaint.getTextBounds(showNavData.get(i), 0, showNavData.get(i).length(), textRect);

            RectF itemRange = revNavRangeMap.get(showNavData.get(i));

            float formX = itemRange.centerX() - textRect.width() / 2;
            float formY = itemRange.centerY() + textRect.height() / 2;
            canvas.drawText(showNavData.get(i), formX, formY, mPaint);

        }


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
        calNavData();
    }

    /**
     * 测量当前view的宽度
     *
     * @param widthMeaSpec
     * @return
     */
    private int measureWidth(int widthMeaSpec) {
        /*定义view的宽度*/
        int width;
        /*获取当前 View的测量模式*/
        int mode = MeasureSpec.getMode(widthMeaSpec);
        /*
        * 获取当前View的测量值，这里得到的只是初步的值，
        * 我们还需根据测量模式来确定我们期望的大小
        * */
        int size = MeasureSpec.getSize(widthMeaSpec);
        /*
        * 如果，模式为精确模式
        * 当前View的宽度，就是我们的size ；
        * */
        if (mode == MeasureSpec.EXACTLY) {
            width = size;
        } else {
            /*否则的话我们就需要结合padding的值来确定*/
            int desire = size + getPaddingLeft() + getPaddingRight();
            if (mode == MeasureSpec.AT_MOST) {
                width = Math.min(desire, size);
            } else {
                width = desire;
            }
        }
        mViewWidth = width;
        return width;
    }

    /**
     * 测量当前view的高度
     *
     * @param heightMeaSpec
     * @return
     */
    private int measureHeight(int heightMeaSpec) {
        int height;
        int mode = MeasureSpec.getMode(heightMeaSpec);
        int size = MeasureSpec.getSize(heightMeaSpec);
        if (mode == MeasureSpec.EXACTLY) {
            height = size;
        } else {
            /*否则的话我们就需要结合padding的值来确定*/
            int desire = size + getPaddingTop() + getPaddingBottom();
            if (mode == MeasureSpec.AT_MOST) {
                height = Math.min(desire, size);
            } else {
                height = desire;
            }
        }
        mViewHeight = height;
        return mViewHeight;
    }

    /**
     * 是否显示全部设置的导航内容，把无数据的导航内容也显示
     *
     * @return
     */
    public boolean isShowAllNav() {
        return showAllNav;
    }

    /**
     * 设置显示导航内容的设定，如果显示全部导航的话，则需要传入true，如果显示部分导航的话，需要传入list部分导航的数据
     *
     * @param showAllNav
     * @param curNavData
     */
    public void setShowAllNav(boolean showAllNav, List<String> curNavData) {
        if (!showAllNav) {
            if (allNavData == null || curNavData == null) {
                throw new IllegalArgumentException("allNavData or curNavData is error");
            }
            if (!checkIsPart(allNavData, curNavData)) {
                throw new IllegalArgumentException("partData is not in allData");
            }

            this.curNavData = curNavData;
        }
        this.showAllNav = showAllNav;
        calNavData();

    }

    /**
     * 用来检测是否第二个list是第一个的子集
     *
     * @param allData
     * @param partData
     * @return
     */
    private boolean checkIsPart(List<String> allData, List<String> partData) {
        for (String item : partData) {
            if (!allData.contains(item)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 初始化点击状态
                touchStatus = true;
                if (mListener != null) {
                    // 定位当前点击的范围
                    temRectF = locateRectF(x, y);
                    if (temRectF != null) {
                        // 调用外部回调事件
                        mListener.navChangeListener(navRangeMap.get(temRectF));
                    }
                }
                invalidate();
                if (isNavPrompt) {
                    mDialogText.setVisibility(View.VISIBLE);
                    mDialogText.setText(navRangeMap.get(temRectF));
                }
                break;
            case MotionEvent.ACTION_MOVE:
                // 这个判断用来进行判断当前这个移动事件是否是一个有效的事件，只有当监听不为空
                if (y > 0 && y < mViewHeight && mListener != null) {
                    // 正常不会出现null，加入是为了防止突然会发生的异常，只是做检测
                    if (temRectF == null) {
                        temRectF = locateRectF(x, y);
                    }
                    if (temRectF != null) {
                        // 如果当前的移动事件坐标超出当前的临时存储的范围的话，则进行重新定位，主要用来节约此次定位的时间消耗
                        if (temRectF.top > y || temRectF.bottom < y) {
                            temRectF = locateRectF(temRectF.centerX(), y);
                            if (temRectF != null) {
                                // 调用外部回调事件

                                if (isNavPrompt) {
                                    mDialogText.setText(navRangeMap.get(temRectF));
                                }

                                mListener.navChangeListener(navRangeMap.get(temRectF));
                            }
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                touchStatus = false;
                invalidate();
                if (isNavPrompt) {
                    mDialogText.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                break;
        }


        return true;
    }

    /**
     * 导航view的回调事件，用来方便外部来进行判断当前触摸到那个view
     */
    public interface NavViewListener {
        // 回调接口
        public void navChangeListener(String navContent);

    }

    /**
     * 定位当前点击坐标的位置
     *
     * @param x
     * @param y
     * @return
     */
    public RectF locateRectF(float x, float y) {

        for (RectF item : navRangeList) {
            if (item.contains(x, y)) {
                return item;
            }
        }

        return null;
    }

    /**
     * 设置回调监听
     *
     * @param mListener
     */
    public void setNavListener(NavViewListener mListener) {
        this.mListener = mListener;
    }

    /**
     * 设置显示导航的文字的颜色 不是资源id 是颜色值
     *
     * @param textColor
     */
    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    /**
     * 设置点击导航背景的颜色 不是资源id 是颜色值
     *
     * @param touchBackgroundColor
     */
    public void setTouchBackgroundColor(int touchBackgroundColor) {
        this.touchBackgroundColor = touchBackgroundColor;
    }

    /**
     * 设置是否显示导航提示
     *
     * @param isNavPrompt
     */
    public void setIsNavPrompt(boolean isNavPrompt) {
        this.isNavPrompt = isNavPrompt;
    }
}
