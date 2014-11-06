package com.virgil.androidfunctiontest.widget;

/**
 * Created by liuwujing on 14-8-1.
 */
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import com.virgil.androidfunctiontest.R;

public class ExpandablePanel extends LinearLayout {

    private final int mHandleId;
    private final int mContentId;

    private View mHandle;
    private View mContent;

    private boolean mExpanded = true;
    private int mCollapsedHeight = 0;
    private int mContentHeight = 0;

    public ExpandablePanel(Context context) {
        this(context, null);
    }

    public ExpandablePanel(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.ExpandablePanel, 0, 0);

        // How high the content should be in "collapsed" state
        mCollapsedHeight = (int) a.getDimension(
                R.styleable.ExpandablePanel_collapsedHeight, 0.0f);

        int handleId = a.getResourceId(R.styleable.ExpandablePanel_handle_Expandable, 0);
        if (handleId == 0) {
            throw new IllegalArgumentException(
                    "The handle attribute is required and must refer "
                            + "to a valid child.");
        }

        int contentId = a.getResourceId(R.styleable.ExpandablePanel_content_Expandable, 0);
        if (contentId == 0) {
            throw new IllegalArgumentException(
                    "The content attribute is required and must refer "
                            + "to a valid child.");
        }

        mHandleId = handleId;
        mContentId = contentId;

        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mHandle = findViewById(mHandleId);
        if (mHandle == null) {
            throw new IllegalArgumentException(
                    "The handle attribute is must refer to an"
                            + " existing child.");
        }

        mContent = findViewById(mContentId);
        if (mContent == null) {
            throw new IllegalArgumentException(
                    "The content attribute is must refer to an"
                            + " existing child.");
        }

        mHandle.setOnClickListener(new PanelToggler());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mContentHeight == 0) {
            // First, measure how high content wants to be
            mContent.measure(widthMeasureSpec, MeasureSpec.UNSPECIFIED);
            mContentHeight = mContent.getMeasuredHeight();
        }

        // Then let the usual thing happen
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private class PanelToggler implements OnClickListener {
        public void onClick(View v) {
            Animation a;
            if (mExpanded) {
                a = new ExpandAnimation(mContentHeight, mCollapsedHeight);
            } else {
                a = new ExpandAnimation(mCollapsedHeight, mContentHeight);
            }
            a.setDuration(500);
            mContent.startAnimation(a);
            mExpanded = !mExpanded;
        }
    }

    private class ExpandAnimation extends Animation {
        private final int mStartHeight;
        private final int mDeltaHeight;

        public ExpandAnimation(int startHeight, int endHeight) {
            mStartHeight = startHeight;
            mDeltaHeight = endHeight - startHeight;
        }

        @Override
        protected void applyTransformation(float interpolatedTime,
                                           Transformation t) {
            android.view.ViewGroup.LayoutParams lp = mContent.getLayoutParams();
            lp.height = (int) (mStartHeight + mDeltaHeight * interpolatedTime);
            mContent.setLayoutParams(lp);
        }

        @Override
        public boolean willChangeBounds() {
            // TODO Auto-generated method stub
            return true;
        }
    }
}