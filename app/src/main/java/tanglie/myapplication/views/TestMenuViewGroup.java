package tanglie.myapplication.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

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
        addView(menu);
    }
}
