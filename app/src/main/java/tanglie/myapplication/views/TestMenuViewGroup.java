package tanglie.myapplication.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import tanglie.myapplication.util.ScreenUtils;

/**
 * Created by Administrator on 2016/7/16 0016.
 */
public class TestMenuViewGroup extends RelativeLayout {

    private ViewGroup menu;
    private Scroller scroller;
    private int currentX;

    private Interpolator decelerateInterpolator = new Interpolator() {
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * t + 1.0f;
        }
    };


    public TestMenuViewGroup(Context context) {
        this(context, null);
        init(context);
    }

    public TestMenuViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        scroller = new Scroller(context, decelerateInterpolator);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(menu != null){
            menu.layout(l, t, r, b);
        }else{
            super.onLayout(changed, l, t, r, b);
        }
    }

    public void setMenu(ViewGroup v) {
        if (menu != null)
            this.removeView(menu);
        menu = v;
        addView(menu, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if(menu != null){
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
            final int contentWidth = getChildMeasureSpec(widthMeasureSpec, 0, LayoutParams.MATCH_PARENT);
            final int contentHeight = getChildMeasureSpec(heightMeasureSpec, 0, LayoutParams.MATCH_PARENT);
            menu.measure(contentWidth, contentHeight);
        }else{
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), 0);
            postInvalidate();
        }
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
        currentX = x;
    }

    public void smoothScrollTo(int x) {
        scroller.startScroll(currentX, 0, -currentX - x, 0);
        System.out.println("currentX: " + currentX + " x:" +x);
        invalidate();
    }

}
