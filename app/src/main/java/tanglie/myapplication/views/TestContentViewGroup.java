package tanglie.myapplication.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/7/16 0016.
 */
public class TestContentViewGroup extends ViewGroup {

    private View content;

    public TestContentViewGroup(Context context) {
        this(context, null);
    }

    public TestContentViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        content.layout(l, t, r, b);
    }

    public void setContent(View v) {
        if (content != null)
            this.removeView(content);
        content = v;
        addView(content);
    }
}
