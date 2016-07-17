package tanglie.myapplication.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;
import android.widget.Scroller;

/**
 * Created by Administrator on 2016/7/16 0016.
 */
public class TestContentViewGroup extends ViewGroup {

    private View content;
    private Scroller scroller;
    private Interpolator decelerateInterpolator = new Interpolator() {
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * t * t * t + 1.0f;
        }
    };


    public TestContentViewGroup(Context context) {
        this(context, null);
        init(context);
    }

    public TestContentViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        scroller = new Scroller(context, decelerateInterpolator);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        content.layout(l, t, r, b);
    }

    public void setContent(View v) {
        if (content != null)
            this.removeView(content);
        content = v;
        addView(content, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);

        final int contentWidth = getChildMeasureSpec(widthMeasureSpec, 0, LayoutParams.MATCH_PARENT);
        final int contentHeight = getChildMeasureSpec(heightMeasureSpec, 0, LayoutParams.MATCH_PARENT);
        content.measure(contentWidth, contentHeight);
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), 0);
            postInvalidate();
        }
    }

    public void smoothScrollBy(int startX, int distance){
        scroller.startScroll(startX, 0, distance, 0);
    }

}
