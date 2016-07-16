package tanglie.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tanglie.myapplication.slidingmenu.SlidingMenu;


public class MainActivity extends Activity {

    private SlidingMenu mSlidingMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.responsive_content_frame);

        mSlidingMenu = (SlidingMenu) LayoutInflater.from(this).inflate(R.layout.slidingmenumain, null);
        mSlidingMenu.setMenu(getLayoutInflater().inflate(R.layout.menu_frame, null));
        mSlidingMenu.setSlidingEnabled(true);
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width);
        mSlidingMenu.setShadowDrawable(R.drawable.shadow);
        mSlidingMenu.setBehindScrollScale(0.25f);
        mSlidingMenu.setFadeDegree(0.25f);

        ViewGroup contentParent = (ViewGroup) findViewById(android.R.id.content);
        View content = contentParent.getChildAt(0);
        contentParent.removeView(content);
        contentParent.addView(mSlidingMenu);
        mSlidingMenu.setContent(content);
    }

}
