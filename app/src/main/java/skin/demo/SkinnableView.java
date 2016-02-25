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
import skin.lib.SkinTheme;

/**
 * 自定义View,实现了{@link android.view.View#onDraw(Canvas)}
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
     * 换肤资源,需设置成全局变量,在{@link #setSkinTheme(SkinTheme)}初始化,在{@link #onDraw(Canvas)}使用
     */
    int mColor;
    Drawable mImage;
    String mText;

    /**
     * 获取主题资源
     *
     * @param theme 主题
     */
    @Override
    protected void setSkinTheme(SkinTheme theme) {
        /** Color/Drawable可直接通过SkinTheme获取 */
        mColor = theme.getColor(R.color.textColor);
        mImage = theme.getDrawable(R.drawable.image);

        /** 其它资源也支持换肤,通过theme.getId()来获取主题资源的id,再通过系统的Resource获取主题资源 */
        // 有了这,text都可以换掉啦!
        mText = getResources().getString(theme.getId(R.string.demo_text));
    }

    /**
     * 设置主题资源
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /** 设置主题资源 */
        mImage.setBounds(mFrameRect);
        mImage.draw(canvas);
        mPaint.setColor(mColor);
        canvas.drawText(mText, mTextRect.centerX(), mTextRect.centerY() + mPaint.getFontMetrics()
                .descent, mPaint);
    }
}

