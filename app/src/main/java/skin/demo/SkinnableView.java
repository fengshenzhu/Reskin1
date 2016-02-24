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
 * 自定义View,原来直接继承View的,可继承CustomSkinView支持换肤
 * <p/>
 * 添加换肤支持只需修改{@link #onDraw(Canvas)},获取主题资源并设置
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

    /**
     * 自定义View实现换肤,由换肤库主动调用View的invalidate()方法,View只需在onDraw()里重新获取当前主题下的资源
     * 步骤:
     * 1. 获取主题
     * 2. 获取主题资源
     * 3. 设置主题资源
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /** 1. 获取主题 */
        SkinTheme theme = SkinManager.getTheme();
        /** 2. 获取主题资源 */
        /** 2.1 Color/Drawable可直接通过SkinTheme获取 */
        int color = theme.getColor(R.color.textColor);
        Drawable mImage = theme.getDrawable(R.drawable.image);
        /** 2.2 其它资源也支持换肤,不过要通过theme.getId()来获取主题资源的id,再通过系统的Resource获取主题资源 */
        // 有了这,text都可以换掉啦!
        String mText = getResources().getString(theme.getId(R.string.demo_text));

        /** 3. 设置主题资源 */
        mImage.setBounds(mFrameRect);
        mImage.draw(canvas);
        mPaint.setColor(color);
        canvas.drawText(mText, mTextRect.centerX(), mTextRect.centerY() + mPaint.getFontMetrics()
                .descent, mPaint);
    }
}

