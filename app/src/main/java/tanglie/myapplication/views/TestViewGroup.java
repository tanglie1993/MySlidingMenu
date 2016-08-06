package tanglie.myapplication.views;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import tanglie.myapplication.R;
import tanglie.myapplication.TestActivity;
import tanglie.myapplication.util.ScreenUtils;

/**
 * Created by Administrator on 2016/7/16 0016.
 */
public class TestViewGroup extends RelativeLayout {

    private TestMenuViewGroup menu;
    private TestContentViewGroup content;
    public static final int MENU_RIGHT_MARGIN = 300;


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
        LayoutParams menuParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        menuParams.rightMargin = MENU_RIGHT_MARGIN;
        addView(this.menu, menuParams);
        LayoutParams contentParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(this.content, contentParams);
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

    public void setContentView(Activity activity, ViewGroup menu) {
        ViewGroup contentParent = (ViewGroup) activity.findViewById(android.R.id.content);
        ViewGroup content = (ViewGroup) contentParent.getChildAt(0);
        content.removeView(menu);
        setMenu(menu);

        addContent(activity);
    }


    private void addContent(Activity activity) {
        ViewGroup contentParent = (ViewGroup) activity.findViewById(android.R.id.content);
        View content = contentParent.getChildAt(0);
        content.setClickable(true);
        contentParent.removeView(content);
        contentParent.addView(this);
        setContent(content);
    }
}
