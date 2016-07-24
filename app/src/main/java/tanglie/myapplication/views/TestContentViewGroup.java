package tanglie.myapplication.views;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import tanglie.myapplication.util.ScreenUtils;

/**
 * Created by Administrator on 2016/7/16 0016.
 */
public class TestContentViewGroup extends ViewGroup {

    private View content;
    private Scroller scroller;
    private int viewGroupRightEndX;
    private TestMenuViewGroup menu;
    private boolean isBeingDragged;

    private static final int LEFT_TOUCH_MARGIN = 150;
    private static final float MENU_SCROLL_SCALE = 0.25f;

    private Interpolator decelerateInterpolator = new Interpolator() {
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * t + 1.0f;
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

        WindowManager wm = ((Activity) context).getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        viewGroupRightEndX = width - TestViewGroup.MENU_RIGHT_MARGIN;
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

    private VelocityTracker mVelocityTracker;

    private int mPointerId;

    private static final int PIXELS_PER_SECOND = 1000;
    private static final int MAX_VELOCITY = 100000;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        boolean consume = false;
        if(onInterceptTouchEvent(event)){
            consume = onTouchEvent(event);
        }else{
            consume = menu.dispatchTouchEvent(event);
        }
//        boolean result = super.dispatchTouchEvent(event);
//        if(!result){
//            menu.dispatchTouchEvent(event);
//        }
        return consume;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if(isBeingDragged){
            return true;
        }
        return event.getX() > -getScrollX();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("onTouchEvent");
        if(event.getAction() == MotionEvent.ACTION_DOWN && !isStartDraggingAllowed(event)){
            return true;
        }
        addMovement(event);
        int maxTargetX = TestViewGroup.MENU_RIGHT_MARGIN - ScreenUtils.getScreenWidth(getContext());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isBeingDragged = true;
                System.out.println("onTouchEvent ACTION_DOWN");
                System.out.println("isBeingDragged = true");
                //求第一个触点的id， 此时可能有多个触点，但至少一个
                mPointerId = event.getPointerId(0);
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("onTouchEvent ACTION_MOVE");
                if(isBeingDragged){
                    menu.scrollTo(getMenuTargetX(-event.getX()), 0);
                    int contentTargetX = (int) -event.getX();
                    if(contentTargetX < maxTargetX){
                        contentTargetX = maxTargetX;
                    }
                    scrollTo(contentTargetX, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("onTouchEvent ACTION_UP");
                if(isBeingDragged) {
                    isBeingDragged = false;
                }
                if((int) -event.getX() > maxTargetX){
                    System.out.println("isBeingDragged = false");
                    mVelocityTracker.computeCurrentVelocity(PIXELS_PER_SECOND, MAX_VELOCITY);
                    final float velocityX = mVelocityTracker.getXVelocity(mPointerId);
                    smoothScroll(-velocityX, -event.getX());
                    releaseVelocityTracker();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                if(isBeingDragged){
                    isBeingDragged = false;
                    System.out.println("isBeingDragged = false");
                    releaseVelocityTracker();
                }
                break;
            default:
                break;
        }
        return true;
    }

    private int getMenuTargetX(float touchEventX) {
        int menuTargetX = (int) ((touchEventX + viewGroupRightEndX) * MENU_SCROLL_SCALE);
        if(menuTargetX < 0){
            menuTargetX = 0;
        }
        return menuTargetX;
    }

    public void setMenu(TestMenuViewGroup menu) {
        this.menu = menu;
    }

    private boolean isStartDraggingAllowed(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            int currentX = getScrollX();
            System.out.println("event.getX(): " + event.getX() + " getScrollX(): " + getScrollX());
            return event.getX() > -currentX && event.getX() < -currentX + LEFT_TOUCH_MARGIN;
        }else{
            return false;
        }
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

    private void smoothScroll(final float velocityX, final float currentX) {
//        System.out.println("velocityX * 0.3 + currentX： " + velocityX * 0.3 + currentX);
//        System.out.println("LEFT_TOUCH_MARGIN / 2： " + LEFT_TOUCH_MARGIN / 2);
        if(-(velocityX * 0.3 + currentX) > viewGroupRightEndX / 2){ // 用经验公式决定滚到最左边还是右边
            scroller.startScroll((int) currentX, 0, - viewGroupRightEndX -(int) currentX, 0);
            menu.smoothScrollTo(0);
        }else{
            scroller.startScroll((int) currentX, 0, -(int) currentX, 0);
            menu.smoothScrollTo(-ScreenUtils.getScreenWidth(getContext()) + TestViewGroup.MENU_RIGHT_MARGIN);
        }
        invalidate();
//        System.out.println("smoothScroll: velocityX " + velocityX + "currentX " + currentX);
    }

}
