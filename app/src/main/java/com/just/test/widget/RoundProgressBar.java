//package com.just.test.widget;
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.RectF;
//import android.graphics.Typeface;
//import android.os.Handler;
//import android.util.AttributeSet;
//import android.view.View;
//
///**
// * com.rongteng.weight.RoundProgressBar
// *
// * @author xiaanming
// */
//public class RoundProgressBar extends View {
//
//    /**
//     * 鐢荤瑪
//     */
//    private Paint paint;
//
//    /**
//     * 鍦嗙幆鐨勯鑹�
//     */
//    private int roundColor;
//
//    /**
//     * 鍦嗙幆杩涘害棰滆壊
//     */
//    private int roundProgressColor;
//
//    /**
//     * 鏂囧瓧棰滆壊
//     */
//    private int textColor;
//
//    /**
//     * 鏂囧瓧澶у皬
//     */
//    private float textSize;
//
//    /**
//     * 鍦嗙幆瀹藉害
//     */
//    private float roundWidth;
//
//    /**
//     * 鏈�澶ц繘搴�
//     */
//    private int max;
//
//    /**
//     * 褰撳墠杩涘害
//     */
//    private int progress;
//
//    /**
//     * 鏂囧瓧鏄惁鏄剧ず
//     */
//    private boolean textIsDisplayable;
//
//    private int style;
//    private int percent;
//    public static final int STROKE = 0;
//    public static final int FILL = 1;
//    private int curProgress = 1;
//    private int maxProgress;
//    private static final int INTERVAL = 1;
//    private Handler mHandler = new Handler();
//
//    public RoundProgressBar(Context context) {
//        this(context, null);
//    }
//
//    public RoundProgressBar(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public RoundProgressBar(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//
//        paint = new Paint();
//
//        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
//                R.styleable.RoundProgressBar);
//
//        roundColor = mTypedArray.getColor(
//                R.styleable.RoundProgressBar_roundColor, getResources().getColor(R.color.main_color));
//        roundProgressColor = mTypedArray.getColor(
//                R.styleable.RoundProgressBar_roundProgressColor, getResources().getColor(R.color.gray));
//        textColor = mTypedArray.getColor(
//                R.styleable.RoundProgressBar_textColor, Color.RED);
//        textSize = mTypedArray.getDimension(
//                R.styleable.RoundProgressBar_textSize, 16);
//        roundWidth = mTypedArray.getDimension(
//                R.styleable.RoundProgressBar_roundWidth, 5);
//        max = mTypedArray.getInteger(R.styleable.RoundProgressBar_max, 100);
//        textIsDisplayable = mTypedArray.getBoolean(
//                R.styleable.RoundProgressBar_textIsDisplayable, false);
//        style = mTypedArray.getInt(R.styleable.RoundProgressBar_style, 0);
//
//        mTypedArray.recycle();
//    }
//
//    @Override
//    protected void onDraw(final Canvas canvas) {
//        super.onDraw(canvas);
//
//        /**
//         * 缁樺埗鍐呭渾
//         */
//        int centre = getWidth() / 2;  // 瀹藉害鐨勪腑蹇�
//        int radius = (int) (centre - roundWidth / 2);  // 鍐呭渾鍗婂緞
//
//        paint.setColor(roundColor); // 锟斤拷锟斤拷圆锟斤拷锟斤拷锟斤拷色
//        paint.setStyle(Paint.Style.STROKE); // 锟斤拷锟矫匡拷锟斤拷
//        paint.setStrokeWidth(roundWidth); // (瀹炲績)
//        paint.setAntiAlias(true); // 鏄惁缁橮aint鍔犱笂鎶楅敮榻挎爣蹇�
//        canvas.drawCircle(centre, centre, radius, paint); // 锟斤拷锟斤拷圆锟斤拷
//
//
//        /**
//         * 锟斤拷锟斤拷劝俜直锟�
//         */
//        paint.setStrokeWidth(0);
//        if (percent == 100) {
//            //褰撳墠鐨勮繘搴�
////			paint.setColor(Color.argb(100, 0xbb, 0xbb, 0xbb));//鐏拌壊
//            paint.setColor(getResources().getColor(R.color.main_color));//钃濊壊
//        } else {
//            paint.setColor(textColor);
//        }
//        paint.setTextSize(45);
//
//        paint.setTypeface(Typeface.DEFAULT_BOLD);  // 璁剧疆瀛椾綋鏍峰紡
//        percent = (int) (((float) progress / (float) max) * 100);  // 杩涘害鍊�
//        float textWidth = paint.measureText(percent + "%");// 鑾峰彇鏂囧瓧鐨勫搴﹀��
//
//        if (textIsDisplayable && style == STROKE) {
//            canvas.drawText(percent + "%", centre - textWidth / 2, centre
//                    + textSize / 2, paint); //
//        }
//        /**
//         * 鐢诲鍦�
//         */
//        paint.setStrokeWidth(roundWidth); //澶栧渾鐨勫搴�
//        if (percent == 100) {
//            paint.setColor(roundProgressColor);
//        } else {
//            paint.setColor(roundProgressColor);
//
//        }
//        final RectF oval = new RectF(centre - radius, centre - radius, centre
//                + radius, centre + radius);
//
//        switch (style) {
//            case STROKE: {
//                paint.setStyle(Paint.Style.STROKE);
//                canvas.drawArc(oval, 270, 360 * progress / max, false, paint);
//                break;
//            }
//            case FILL: {
//                paint.setStyle(Paint.Style.FILL_AND_STROKE);
//                if (progress != 0)
//                    canvas.drawArc(oval, 270, 360 * progress / max, false, paint);
//
//                break;
//            }
//        }
//
//    }
//
//    public synchronized int getMax() {
//        return max;
//    }
//
//    /**
//     * 锟斤拷锟矫斤拷鹊锟斤拷锟斤拷值
//     *
//     * @param max
//     */
//    public synchronized void setMax(int max) {
//        if (max < 0) {
//            throw new IllegalArgumentException("max not less than 0");
//        }
//        this.max = max;
//    }
//
//    /**
//     * 鑾峰彇杩涘害鍊�
//     *
//     * @return
//     */
//    public synchronized int getProgress() {
//        return progress;
//    }
//
//    /**
//     * @param progress
//     */
//    public void setProgress(int progress) {
//        if (progress < 0) {
//            throw new IllegalArgumentException("progress not less than 0");
//        }
//        if (progress > max) {
//            progress = max;
//        }
//        if (progress <= max) {
//            this.progress = progress;
//            postInvalidate();
//        }
//    }
//
//    public synchronized void setAnimProgress(int progress, boolean isMax) {
//        maxProgress = progress;
//        curProgress = 0;
////		if(progress==0)
////		{
////			mHandler1.sendEmptyMessageDelayed(INTERVAL, progress);
////		}else {
////			mHandler1.sendEmptyMessageDelayed(INTERVAL, 250/progress);
////		}
//        if (!isMax) {
//            mHandler1.sendEmptyMessageDelayed(INTERVAL, 1);
//        } else {
//            mHandler1.removeMessages(INTERVAL);
//            setProgress(progress);
//        }
//
//    }
//
//
//    private Handler mHandler1 = new Handler() {
//        public void handleMessage(android.os.Message msg) {
//            switch (msg.what) {
//                case INTERVAL:
//                    if (curProgress < maxProgress) {
//                        setProgress(curProgress);
//                        curProgress += 1;
//                        mHandler1.sendEmptyMessageDelayed(INTERVAL, 1);
//                    } else {
//                        setProgress(curProgress);
//                    }
//                    break;
//                default:
//                    setProgress(curProgress);
//                    break;
//            }
//        }
//
//        ;
//    };
//
//    public synchronized void showText(boolean show) {
//        textIsDisplayable = show;
//    }
//
//    public int getCricleColor() {
//        return roundColor;
//    }
//
//    public void setCricleColor(int cricleColor) {
//        this.roundColor = cricleColor;
//    }
//
//    public int getCricleProgressColor() {
//        return roundProgressColor;
//    }
//
//    public void setCricleProgressColor(int cricleProgressColor) {
//        this.roundProgressColor = cricleProgressColor;
//    }
//
//    public int getTextColor() {
//        return textColor;
//    }
//
//    public void setTextColor(int textColor) {
//        this.textColor = textColor;
//    }
//
//    public float getTextSize() {
//        return textSize;
//    }
//
//    public void setTextSize(float textSize) {
//        this.textSize = textSize;
//    }
//
//    public float getRoundWidth() {
//        return roundWidth;
//    }
//
//    public void setRoundWidth(float roundWidth) {
//        this.roundWidth = roundWidth;
//    }
//
//}
