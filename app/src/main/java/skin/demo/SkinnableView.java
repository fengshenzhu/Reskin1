package skin.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;

import skin.lib.CustomSkinView;
import skin.lib.SkinManager;
import skin.lib.SkinTheme;

/**
 * 自定义View,继承View
 * <p/>
 * Created by dfl on 2016/1/26.
 * Just For Demo
 */
public class SkinnableView extends CustomSkinView {
    private Rect mDrawRect;
    private Rect mFrameRect;
    private RectF mTextRect;
    private Paint mPaint;

    public SkinnableView(Context context) {
        super(context);
        init();
    }

    public SkinnableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SkinnableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mDrawRect = new Rect();
        mFrameRect = new Rect();
        mTextRect = new RectF();
        mPaint = new Paint();
        mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 22f,
                getResources().getDisplayMetrics()));
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!changed) {
            return;
        }
        left += getPaddingLeft();
        right -= getPaddingRight();
        top += getPaddingTop();
        bottom -= getPaddingBottom();
        mDrawRect.set(left, top, right, bottom);
        mFrameRect.set(mDrawRect);
        mFrameRect.offsetTo(getPaddingLeft(), getPaddingTop());
        mTextRect.set(mFrameRect.left, mFrameRect.top, mFrameRect.centerX(), mFrameRect.centerY());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        SkinTheme theme = SkinManager.getTheme();
        String mText = getResources().getString(theme.getId(R.string.demo_text));
        Drawable mImage = theme.getDrawable(R.drawable.image);

        mImage.setBounds(mFrameRect);
        mImage.draw(canvas);
        mPaint.setColor(theme.getColor(R.color.textColor));
        canvas.drawText(mText, mTextRect.centerX(), mTextRect.centerY() + mPaint.getFontMetrics()
                .descent, mPaint);
    }
}

