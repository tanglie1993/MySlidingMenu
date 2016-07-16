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

	private static final String TAG = "CustomViewAbove";
	private static final boolean DEBUG = false;

	private static final boolean USE_CACHE = false;

	private static final int MAX_SETTLE_DURATION = 600; // ms
	private static final int MIN_DISTANCE_FOR_FLING = 25; // dips

	private static final Interpolator sInterpolator = new Interpolator() {
		public float getInterpolation(float t) {
			t -= 1.0f;
			return t * t * t * t * t + 1.0f;
		}
	};

	private View mContent;

	private int mCurItem;
	private Scroller mScroller;

	private boolean mScrollingCacheEnabled;

	private boolean mScrolling;

	/**
	 * ID of the active pointer. This is used to retain consistency during
	 * drags/flings if multiple pointers are used.
	 */
	protected int mActivePointerId = INVALID_POINTER;
	/**
	 * Sentinel value for no current active pointer.
	 * Used by {@link #mActivePointerId}.
	 */
	private static final int INVALID_POINTER = -1;

	/**
	 * Determines speed during touch scrolling
	 */
	protected int mMaximumVelocity;

	private CustomViewBehind mViewBehind;
	//	private int mMode;



	public CustomViewAbove(Context context) {
		this(context, null);
	}

	public CustomViewAbove(Context context, AttributeSet attrs) {
		super(context, attrs);
		initCustomViewAbove();
	}

	void initCustomViewAbove() {
		setWillNotDraw(false);
		setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
		setFocusable(true);
		final Context context = getContext();
		mScroller = new Scroller(context, sInterpolator);
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


	void setCurrentItemInternal(int item, boolean smoothScroll, boolean always) {
		setCurrentItemInternal(item, smoothScroll, always, 0);
	}

	void setCurrentItemInternal(int item, boolean smoothScroll, boolean always, int velocity) {
		if (!always && mCurItem == item) {
			setScrollingCacheEnabled(false);
			return;
		}

		item = mViewBehind.getMenuPage(item);

		final boolean dispatchSelected = mCurItem != item;
		mCurItem = item;
		final int destX = getDestScrollX(mCurItem);
		if (smoothScroll) {
			smoothScrollTo(destX, 0, velocity);
		} else {
			completeScroll();
			scrollTo(destX, 0);
		}
	}



	// We want the duration of the page snap animation to be influenced by the distance that
	// the screen has to travel, however, we don't want this duration to be effected in a
	// purely linear fashion. Instead, we use this method to moderate the effect that the distance
	// of travel has on the overall snap duration.
	float distanceInfluenceForSnapDuration(float f) {
		f -= 0.5f; // center the values about 0.
		f *= 0.3f * Math.PI / 2.0f;
		return (float) Math.sin(f);
	}

	public int getDestScrollX(int page) {
		switch (page) {
			case 0:
			case 2:
				return mViewBehind.getMenuLeft(mContent, page);
			case 1:
				return mContent.getLeft();
		}
		return 0;
	}

	public int getBehindWidth() {
		if (mViewBehind == null) {
			return 0;
		} else {
			return mViewBehind.getBehindWidth();
		}
	}


	public void setSlidingEnabled(boolean b) {
//		mEnabled = b;
	}


	/**
	 * Like {@link View#scrollBy}, but scroll smoothly instead of immediately.
	 *
	 * @param x the number of pixels to scroll by on the X axis
	 * @param y the number of pixels to scroll by on the Y axis
	 * @param velocity the velocity associated with a fling, if applicable. (0 otherwise)
	 */
	void smoothScrollTo(int x, int y, int velocity) {
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

	@Override
	public void computeScroll() {

	}



	private void completeScroll() {
		boolean needPopulate = mScrolling;
		if (needPopulate) {
			// Done with scroll, no longer want to cache view drawing.
			setScrollingCacheEnabled(false);
			mScroller.abortAnimation();
			int oldX = getScrollX();
			int oldY = getScrollY();
			int x = mScroller.getCurrX();
			int y = mScroller.getCurrY();
			if (oldX != x || oldY != y) {
				scrollTo(x, y);
			}
		}
		mScrolling = false;
	}

	protected int mTouchMode = SlidingMenu.TOUCHMODE_MARGIN;

	public void setTouchMode(int i) {
		mTouchMode = i;
	}

	private boolean mQuickReturn = false;

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {

		return true;
	}


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



	protected float getPercentOpen() {
		return Math.abs(mScrollX-mContent.getLeft()) / getBehindWidth();
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		// Draw the margin drawable if needed.
		mViewBehind.drawShadow(mContent, canvas);
		mViewBehind.drawFade(mContent, canvas, getPercentOpen());
		mViewBehind.drawSelector(mContent, canvas, getPercentOpen());
	}

	// variables for drawing
	private float mScrollX = 0.0f;

	private void setScrollingCacheEnabled(boolean enabled) {
		if (mScrollingCacheEnabled != enabled) {
			mScrollingCacheEnabled = enabled;
			if (USE_CACHE) {
				final int size = getChildCount();
				for (int i = 0; i < size; ++i) {
					final View child = getChildAt(i);
					if (child.getVisibility() != GONE) {
						child.setDrawingCacheEnabled(enabled);
					}
				}
			}
		}
	}



	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// Let the focused view and/or our descendants get the key first
		return super.dispatchKeyEvent(event) || executeKeyEvent(event);
	}

	/**
	 * You can call this function yourself to have the scroll view perform
	 * scrolling from a key event, just as if the event had been dispatched to
	 * it by the view hierarchy.
	 *
	 * @param event The key event to execute.
	 * @return Return true if the event was handled, else false.
	 */
	public boolean executeKeyEvent(KeyEvent event) {
		boolean handled = false;
		return handled;
	}


}