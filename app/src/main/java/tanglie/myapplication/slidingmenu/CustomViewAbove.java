package tanglie.myapplication.slidingmenu;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.Scroller;
//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnCloseListener;
//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenListener;

public class CustomViewAbove extends ViewGroup {

	private View mContent;
	private int mCurItem;
	private int mMaximumVelocity;
	private CustomViewBehind mViewBehind;

	public CustomViewAbove(Context context) {
		this(context, null);
	}

	public CustomViewAbove(Context context, AttributeSet attrs) {
		super(context, attrs);
		initCustomViewAbove();
	}

	private void initCustomViewAbove() {
		setWillNotDraw(false);
		setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
		setFocusable(true);
		final Context context = getContext();
		final ViewConfiguration configuration = ViewConfiguration.get(context);
		mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();

		final float density = context.getResources().getDisplayMetrics().density;
	}


	/**
	 * Set the currently selected page.
	 *
	 * @param item Item index to select
	 * @param smoothScroll True to smoothly scroll to the new item, false to transition immediately
	 */
	public void setCurrentItem(int item, boolean smoothScroll) {
		setCurrentItemInternal(item, smoothScroll, false);
	}


	private void setCurrentItemInternal(int item, boolean smoothScroll, boolean always) {
		setCurrentItemInternal(item, smoothScroll, always, 0);
	}

	private void setCurrentItemInternal(int item, boolean smoothScroll, boolean always, int velocity) {
//		if (!always && mCurItem == item) {
//			setScrollingCacheEnabled(false);
//			return;
//		}
//
//		item = mViewBehind.getMenuPage(item);
//
//		final boolean dispatchSelected = mCurItem != item;
//		mCurItem = item;
//		final int destX = getDestScrollX(mCurItem);
//		if (smoothScroll) {
//			smoothScrollTo(destX, 0, velocity);
//		} else {
//			completeScroll();
//			scrollTo(destX, 0);
//		}
	}

	private int getDestScrollX(int page) {
		switch (page) {
			case 0:
			case 2:
				return mViewBehind.getMenuLeft(mContent, page);
			case 1:
				return mContent.getLeft();
		}
		return 0;
	}



	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int width = getDefaultSize(0, widthMeasureSpec);
		int height = getDefaultSize(0, heightMeasureSpec);
		setMeasuredDimension(width, height);

		final int contentWidth = getChildMeasureSpec(widthMeasureSpec, 0, width);
		final int contentHeight = getChildMeasureSpec(heightMeasureSpec, 0, height);
		mContent.measure(contentWidth, contentHeight);
	}


	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		final int width = r - l;
		final int height = b - t;
		mContent.layout(0, 0, width, height);
	}

	private int mTouchMode = SlidingMenu.TOUCHMODE_MARGIN;

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		scrollTo(-200, 0);
		return true;
	}

	@Override
	public void scrollTo(int x, int y) {
		super.scrollTo(x, y);
		mScrollX = x;
		mViewBehind.scrollBehindTo(mContent, x, y);
		((SlidingMenu)getParent()).manageLayers(getPercentOpen());
	}






	private int getBehindWidth() {
		if (mViewBehind == null) {
			return 0;
		} else {
			return mViewBehind.getBehindWidth();
		}
	}

	public void setContent(View v) {
		if (mContent != null)
			this.removeView(mContent);
		mContent = v;
		addView(mContent);
	}

	public View getContent() {
		return mContent;
	}

	public void setCustomViewBehind(CustomViewBehind cvb) {
		mViewBehind = cvb;
	}

	protected float getPercentOpen() {
		return Math.abs(mScrollX-mContent.getLeft()) / getBehindWidth();
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// Let the focused view and/or our descendants get the key first
		return super.dispatchKeyEvent(event) || executeKeyEvent(event);
	}


	private boolean executeKeyEvent(KeyEvent event) {
		boolean handled = false;
		return handled;
	}

	// variables for drawing
	private float mScrollX = 0.0f;

	public void setTouchMode(int i) {
		mTouchMode = i;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return true;
	}

	@Override
	public void computeScroll() {

	}

}