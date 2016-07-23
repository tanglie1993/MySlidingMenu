package tanglie.myapplication.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import tanglie.myapplication.util.ScreenUtils;

/**
 * Created by Administrator on 2016/7/16 0016.
 */
public class TestViewGroup extends RelativeLayout {

    private TestMenuViewGroup menu;
    private TestContentViewGroup content;

    public TestViewGroup(Context context) {
        super(context);
        init(context);
    }

    public TestViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TestViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public TestViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        menu = new TestMenuViewGroup(context);
        content = new TestContentViewGroup(context);
        addView(this.menu, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(this.content, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }


    public void setMenu(ViewGroup menu) {
        this.menu.setMenu(menu);
        this.content.setMenu(this.menu);
    }

    public void setContent(View content) {
        this.content.setContent(content);
    }

    public TestMenuViewGroup getMenu() {
        return menu;
    }

    public TestContentViewGroup getContent() {
        return content;
    }
}
