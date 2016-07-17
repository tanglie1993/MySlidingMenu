package tanglie.myapplication.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import tanglie.myapplication.R;

/**
 * Created by Administrator on 2016/7/16 0016.
 */
public class TestRootViewGroup extends RelativeLayout {

    private TestMenuViewGroup menu;
    private TestContentViewGroup content;

    public TestRootViewGroup(Context context) {
        super(context);
        init(context);
    }

    public TestRootViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TestRootViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public TestRootViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        content = new TestContentViewGroup(context);
        addView(content, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        menu = new TestMenuViewGroup(context);
        addView(menu, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    public void setMenu(View view) {
        menu.setMenu(view);
        addView(view);
    }

    public void setContent(View view) {
        content.setContent(view);
        addView(view);
    }

    public View getMenu() {
        return menu;
    }

    public View getContent() {
        return content;
    }
}
