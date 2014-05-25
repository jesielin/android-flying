package com.example.slidebutton;

import java.io.Flushable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

public class SlideButton extends View implements OnClickListener {

	private Bitmap switch_bg;
	private Bitmap slide_bn;
	private Paint paint;

	public SlideButton(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.Slidebutton);
		int indexCount = ta.getIndexCount();
		for (int i = 0; i < indexCount; i++) {
			int index = ta.getIndex(i);
			switch (index) {
			case R.styleable.Slidebutton_bg:
				BitmapDrawable bg = (BitmapDrawable) ta.getDrawable(index);
				switch_bg = bg.getBitmap();
				break;
			case R.styleable.Slidebutton_bn:
				BitmapDrawable bn = (BitmapDrawable) ta.getDrawable(index);
				slide_bn = bn.getBitmap();
				break;
			}
		}

		initView();
	}

	private void initView() {
		if (switch_bg == null)
			switch_bg = BitmapFactory.decodeResource(getResources(),
					R.drawable.switch_background);
		if (slide_bn == null)
			slide_bn = BitmapFactory.decodeResource(getResources(),
					R.drawable.slide_button);

		paint = new Paint();

		paint.setAntiAlias(true);
		maxRange = switch_bg.getWidth() - slide_bn.getWidth();
		setOnClickListener(this);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(switch_bg.getWidth(), switch_bg.getHeight());

	}

	public void setStatus(boolean isOpen) {
		this.isOpen = isOpen;
		if (!isOpen) {
			bnLeft = 0;
		} else {
			bnLeft = switch_bg.getWidth() - slide_bn.getWidth();
		}
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// super.onDraw(canvas);

		canvas.drawBitmap(switch_bg, 0, 0, paint);
		canvas.drawBitmap(slide_bn, bnLeft, 0, paint);

	}

	@Override
	public void onClick(View v) {
		if (!isDrag) {
			isOpen = !isOpen;
			if (!isOpen) {
				bnLeft = 0;
			} else {
				bnLeft = switch_bg.getWidth() - slide_bn.getWidth();
			}
			invalidate();
		}

	}

	private boolean isOpen = false;
	private int firstX;
	private int lastX;
	private int bnLeft;
	private boolean isDrag;
	private int maxRange;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		super.onTouchEvent(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			firstX = lastX = (int) event.getX();
			isDrag = false;
			break;
		case MotionEvent.ACTION_MOVE:
			if (Math.abs(event.getX() - firstX) > 5) {
				isDrag = true;
			}
			int dis = (int) (event.getX() - lastX);
			bnLeft += dis;
			bnLeft = (bnLeft > 0) ? bnLeft : 0;
			bnLeft = (bnLeft < maxRange) ? bnLeft : maxRange;
			invalidate();
			lastX = (int) event.getX();
			break;
		case MotionEvent.ACTION_UP:

			if (isDrag) {
				if (bnLeft > maxRange / 2) {
					bnLeft = maxRange;
					isOpen = true;
				} else {
					bnLeft = 0;
					isOpen = false;
				}
			}
			invalidate();

			break;
		}
		return true;
	}

}
