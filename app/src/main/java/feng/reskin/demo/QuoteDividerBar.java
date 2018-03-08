package feng.reskin.demo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QuoteDividerBar extends LinearLayout {
	
	/** 默认字体大小(SP) */
	private static final int DEFAULT_TEXT_SIZE_SP = 12;

	/** 左边文字TextView */
	private TextView leftTextView;
	
	/** 右边文字TextView */
	private TextView rightTextView;
	
	/** 左边文字 */
	private String leftText;
	
	/** 右边文字 */
	private String rightText;
	
	/** 左边文字颜色 */
	private int leftColor = Color.WHITE;
	
	/** 右边文字颜色 */
	private int rightColor = Color.WHITE;
	
	/** 左边文字字体大小 */
	private float leftTextSize;
	
	/** 右边文字字体大小 */
	private float rightTextSize;
	
	/** 右边图片 */
	private Drawable rightDrawable;
	
	private int rightTextVisibility = View.VISIBLE;
	
	/** 点击事件监听响应 */
	private OnDividerClickListener clickListener;
	
	/**
	 * 构造方法
	 * @param context Context
	 */
	public QuoteDividerBar(Context context) {
		super(context);
		iniView(context);
	}
	
	/**
	 * 构造方法
	 * @param context Context
	 * @param attrs AttributeSet
	 */
	public QuoteDividerBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		final float defaultTextSizePX = DEFAULT_TEXT_SIZE_SP * getResources().getDisplayMetrics().scaledDensity;
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.QuoteDividerBar);
		leftText = a.getString(R.styleable.QuoteDividerBar_left_text);
		rightText = a.getString(R.styleable.QuoteDividerBar_right_text);
		leftColor = a.getColor(R.styleable.QuoteDividerBar_left_textColor, Color.WHITE);
		rightColor = a.getColor(R.styleable.QuoteDividerBar_right_textColor, Color.WHITE);
		leftTextSize = a.getDimension(R.styleable.QuoteDividerBar_left_textSize, defaultTextSizePX);
		if (leftTextSize < defaultTextSizePX) {
			leftTextSize = defaultTextSizePX;
		}
		rightTextSize = a.getDimension(R.styleable.QuoteDividerBar_right_textSize, defaultTextSizePX);
		if (rightTextSize < defaultTextSizePX) {
			rightTextSize = defaultTextSizePX;
		}
		rightDrawable = a.getDrawable(R.styleable.QuoteDividerBar_right_drawable);
		rightTextVisibility = a.getInt(R.styleable.QuoteDividerBar_right_textVisibility, View.VISIBLE);
		a.recycle();
		iniView(context);
	}

	/**
	 * 初始化界面
	 * @param context
	 */
	private void iniView(Context context) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.quote_divider_layout, this);
		leftTextView = (TextView) view.findViewById(R.id.quote_divider_title);
		rightTextView = (TextView) view.findViewById(R.id.quote_divider_more);
		if (leftText != null) {
			leftTextView.setText(leftText);
		}
		leftTextView.setTextColor(leftColor);
		leftTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTextSize);
		if (rightText != null) {
			rightTextView.setText(rightText);
		}
		rightTextView.setTextColor(rightColor);
		rightTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize);
		if (rightDrawable != null) {
			rightTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, rightDrawable, null);
			rightTextView.setCompoundDrawablePadding(10);
		}
		
		rightTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (clickListener != null) {
					clickListener.onRightButtonClicked(v);
				}
			}
		});
		rightTextView.setVisibility(rightTextVisibility);
		
		//屏蔽空白处点击事件
		LinearLayout blankLayout = (LinearLayout) findViewById(R.id.quote_divider_blank);
		blankLayout.setOnClickListener(null);
	}
	
	/**
	 * 
	 * @param left 左边文字资源id
	 * @param right 右边文字资源id
	 */
	public void setBarTexts(int left, int right) {
		setLeftText(left);
		setRightText(right);
	}
	
	/**
	 * 
	 * @param left 左边文字
	 * @param right 右边文字
	 */
	public void setTexts(String left, String right) {
		setLeftText(left);
		setRightText(right);
	}
	
	/**
	 * 
	 * @param left int, 左边文字资源id
	 */
	public void setLeftText(int left) {
		leftTextView.setText(left);
	}
	
	/**
	 * 左边文字
	 * @param left String
	 */
	public void setLeftText(String left) {
		leftTextView.setText(left);
	}
	
	/**
	 * 返回左边文字
	 * @return 
	 */
	public CharSequence getLeftText() {
		return leftTextView.getText();
	}
	
	/**
	 * 
	 * @param right int, 右边文字资源id
	 */
	public void setRightText(int right) {
		leftTextView.setText(right);
	}
	
	/**
	 * 右边文字
	 * @param right String
	 */
	public void setRightText(String right) {
		leftTextView.setText(right);
	}
	
	/**
	 * 设置字体大小
	 * @param left 左边文字字体大小
	 * @param right 右边文字字体大小
	 */
	public void setTextSizes(float left, float right) {
		setLeftTextSize(left);
		setRightTextSize(right);
	}
	
	/**
	 * 设置左边字体大小
	 * @param left float
	 */
	public void setLeftTextSize(float left) {
		leftTextView.setTextSize(left);
	}
	
	/**
	 * 设置右边字体大小
	 * @param right float
	 */
	public void setRightTextSize(float right) {
		rightTextView.setTextSize(right);
	}
	
	/**
	 * 设置右侧部分是可见状态,包括相关联的图片(箭头等)，若要显示图片，文字设为空字符串("")即可
	 * @param visibility {@code int, View.VISIBLE, View.INVISIBLE, View.GONE}
	 */
	public void setRightTextVisibility(int visibility) {
		rightTextView.setVisibility(visibility);
	}
	
	/**
	 * 监听点击响应事件
	 * @param listener {@link OnDividerClickListener}
	 */
	public void setOnDividerClickListener(OnDividerClickListener listener) {
		clickListener = listener;
	}
	
	/**
	 * 点击事件响应接口
	 * @Package com.eastmoney.android.ui
	 * @Name QuoteDividerBar.java
	 * @Author Chanming Li
	 * @Email changming@eastmoney.com
	 * @Company EastMoney Info. Co.,Ltd.
	 * @Date 2014-9-9 下午1:34:07
	 * @Version V1.0
	 */
	public interface OnDividerClickListener {
		/**
		 * 需要重写此方法
		 * @param view View 被点击的控件
		 */
		public void onRightButtonClicked(View view);
	}
}
