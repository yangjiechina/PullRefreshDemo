package com.ywdcf.pullrefreshdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2017/5/8.
 */

public class PullRefreshLayout extends RelativeLayout {
    private View mHeaderView, mFooterView;
    private int mCount = 0;
    public PullRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHeaderView = LayoutInflater.from(context).inflate(R.layout.header_view, null);
        mFooterView = LayoutInflater.from(context).inflate(R.layout.footer_view, null);
        Log.i("TAG", "PullRefreshLayout");

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        RelativeLayout.LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mHeaderView.setLayoutParams(lp);
        mFooterView.setLayoutParams(lp);
        addView(mHeaderView);
        addView(mFooterView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mCount = getChildCount();
        for(int i = 0; i < mCount; i ++){
            measureChild(getChildAt(i),widthMeasureSpec,heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        for(int i = 0 ;i <mCount; i++ ){
            View childAt = getChildAt(i);
            if(childAt == mHeaderView){
                childAt.layout(0,-(childAt.getMeasuredHeight()),childAt.getMeasuredWidth(),0);
            }else if(childAt == mFooterView){
                childAt.layout(0,childAt.getMeasuredHeight(),childAt.getMeasuredWidth(),(childAt.getMeasuredHeight())*2);
            }else {
                childAt.layout(0,0,childAt.getMeasuredWidth(),childAt.getMeasuredHeight());
            }
        }
    }
    private int firstY= 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int  y = (int )event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                firstY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int lastY = y - firstY;
                if(lastY>0){
                    scrollBy(0,-lastY);
                }else {
                    scrollBy(0,-(lastY));
                }
                break;
        }
        firstY = y;
        return true;
    }
}
