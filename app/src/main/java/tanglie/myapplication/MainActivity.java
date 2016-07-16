package tanglie.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

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

        mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
    }

}
