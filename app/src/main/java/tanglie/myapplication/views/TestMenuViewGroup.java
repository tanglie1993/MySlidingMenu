package tanglie.myapplication.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2016/7/16 0016.
 */
public class TestMenuViewGroup extends ViewGroup {

    private View menu;

    public TestMenuViewGroup(Context context) {
        this(context, null);
    }

    public TestMenuViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        menu.layout(l, t, r, b);
    }

    public void setMenu(View v) {
        if (menu != null)
            this.removeView(menu);
        menu = v;
        addView(menu, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);

        final int contentWidth = getChildMeasureSpec(widthMeasureSpec, 0, LayoutParams.MATCH_PARENT);
        final int contentHeight = getChildMeasureSpec(heightMeasureSpec, 0, LayoutParams.MATCH_PARENT);
        menu.measure(contentWidth, contentHeight);

    }
}
