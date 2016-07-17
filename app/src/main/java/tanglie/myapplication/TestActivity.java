package tanglie.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;

import tanglie.myapplication.views.TestViewGroup;

public class TestActivity extends AppCompatActivity {

    private TestViewGroup testViewGroup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);

        testViewGroup = (TestViewGroup) LayoutInflater.from(this).inflate(R.layout.layout_test_view_group, null);
        testViewGroup.setMenu(getLayoutInflater().inflate(R.layout.test_menu_frame, null));

        ViewGroup contentParent = (ViewGroup) findViewById(android.R.id.content);
        View content = contentParent.getChildAt(0);
        contentParent.removeView(content);
        contentParent.addView(testViewGroup);
        testViewGroup.setContent(content);
    }

    @Override
    protected void onResume(){
        super.onResume();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                testViewGroup.getMenu().scrollTo(-100, 0);
//                testViewGroup.getContent().scrollTo(-200, 0);
//            }
//        }, 2000);
    }

    private VelocityTracker mVelocityTracker;


    private int mPointerId;

    private static final int PIXELS_PER_SECOND = 1000;
    private static final int MAX_VELOCITY = 10000;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //求第一个触点的id， 此时可能有多个触点，但至少一个
                mPointerId = event.getPointerId(0);
                break;
            case MotionEvent.ACTION_MOVE:
                testViewGroup.getContent().scrollTo((int) -event.getX(), 0);
                break;
            case MotionEvent.ACTION_UP:
                mVelocityTracker.computeCurrentVelocity(PIXELS_PER_SECOND, MAX_VELOCITY);
                final float velocityX = mVelocityTracker.getXVelocity(mPointerId);
                smoothScroll(-velocityX, -event.getX());
                releaseVelocityTracker();
                break;
            case MotionEvent.ACTION_CANCEL:
                releaseVelocityTracker();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private void addMovement(final MotionEvent event) {
        if(null == mVelocityTracker) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    private void releaseVelocityTracker() {
        if(null != mVelocityTracker) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private static final String sFormatStr = "velocityX=%f\nvelocityY=%f";

    private void smoothScroll(final float velocityX, final float currentX) {
        testViewGroup.getContent().smoothScrollBy((int) currentX, (int) velocityX / 10);
        System.out.println("smoothScroll: velocityX " + velocityX + "currentX " + currentX);

    }
}
