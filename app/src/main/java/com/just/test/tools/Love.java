package com.just.test.tools;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * ================================================
 * setTypeface
 * ================================================
 * public Typeface setTypeface (Typeface typeface)
 * Since: API Level 1 Set or clear the typeface object.
 * Pass null to clear any previous typeface.
 * As a convenience, the parameter passed is also returned.
 * <p/>
 * Parameters
 * typeface  May be null. The typeface to be installed in the paint
 * Returns
 * typeface
 * <p/>
 * <p/>
 * ================================================
 * create
 * ================================================
 * public static Typeface create (Typeface family, int style)
 * Since: API Level 1 Create a typeface object that best matches
 * the specified existing typeface and the specified Style.
 * Use this call if you want to pick a new style from the same family
 * of an existing typeface object.
 * If family is null, this selects from the default font's family.
 * <p/>
 * Parameters
 * family  May be null. The name of the existing type face.
 * style  The style (normal, bold, italic) of the typeface.
 * e.g. NORMAL, BOLD, ITALIC, BOLD_ITALIC
 * Returns
 * The best matching typeface.
 * <p/>
 * Typeface.create(Typeface family, int style)
 * 鍒涘缓涓�涓贩鍚堝瀷鏂扮殑瀛椾綋锛氭湁4*5绉嶆惌閰�
 * <p/>
 * Typeface绫诲瀷
 * DEFAULT                   榛樿瀛椾綋
 * DEFAULT_BOLD              榛樿绮椾綋
 * MONOSPACE                 鍗曢棿闅斿瓧浣�
 * SANS_SERIF                鍗曢棿闅斿瓧浣�
 * SERIF                     琛嚎瀛椾綋
 * <p/>
 * int Style绫诲瀷
 * BOLD                      绮椾綋
 * BOLD_ITALIC               绮楁枩浣�
 * ITALIC                    鏂滀綋
 * NORMAL                    鏅�氬瓧浣�
 * <p/>
 * <p/>
 * ================================================
 * drawRoundRect 鐢讳竴涓渾瑙掔煩褰�
 * ================================================
 * public void drawRoundRect (RectF rect, float rx, float ry, Paint paint)
 * Since: API Level 1 Draw the specified round-rect using the specified paint.
 * The roundrect will be filled or framed based on the Style in the paint.
 * Parameters
 * rect  The rectangular bounds of the roundRect to be drawn
 * rx  The x-radius of the oval used to round the corners
 * ry  The y-radius of the oval used to round the corners
 * paint  The paint used to draw the roundRect
 *
 * @author wp
 */
public class Love extends SurfaceView implements SurfaceHolder.Callback,
        Runnable {
    private SurfaceHolder sfh = null;
    private Paint paint = null;
    private Canvas canvas = null;
    private int SH = 0, SW = 0;
    private int miCount = 0;
    private boolean loop = false;

    public Love(Context context) {
        super(context);
        this.setKeepScreenOn(true);
        this.setFocusable(true);
        sfh = this.getHolder();
        sfh.addCallback(this);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(32);
        paint.setTypeface(Typeface.create(Typeface.SERIF, Typeface.ITALIC));
        loop = true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        SW = getWidth();
        SH = getHeight();
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        loop = false;
    }

    @Override
    public void run() {
        while (loop) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            /*
             * synchronized 鍏抽敭瀛�,浠ｈ〃杩欎釜鏂规硶鍔犻攣
			 * 鐩稿綋浜庝笉绠″摢涓�涓嚎绋婣姣忔杩愯鍒拌繖涓柟娉曟椂,
			 * 閮借妫�鏌ユ湁娌℃湁鍏跺畠姝ｅ湪鐢ㄨ繖涓柟娉曠殑绾跨▼B(鎴栬�匔 D绛�)
			 *
			 * 鏈夌殑璇濊绛夋鍦ㄤ娇鐢ㄨ繖涓柟娉曠殑绾跨▼B(鎴栬�匔 D)
			 * 杩愯瀹岃繖涓柟娉曞悗鍐嶈繍琛屾绾跨▼A,
			 *
			 * 娌℃湁鐨勮瘽,鐩存帴杩愯
			 *
			 * 瀹冨寘鎷袱绉嶇敤娉曪細synchronized 鏂规硶鍜� synchronized 鍧椼��
			 */
            synchronized (sfh) {
                draw();
            }
        }
    }

    private void draw() {
        canvas = sfh.lockCanvas();
        try {
            if (canvas == null)
                return;

            /** 鍒峰睆 **/
            paint.setColor(Color.WHITE);
            canvas.drawRect(0, 0, SW, SH, paint);

            /** 鍙樻崲鐢荤瑪鐨勯鑹� **/
            if (miCount < 100) {
                miCount++;
            } else {
                miCount = 0;
            }
            switch (miCount % 6) {
                case 0:
                    paint.setColor(Color.BLUE);
                    break;
                case 1:
                    paint.setColor(Color.GREEN);
                    break;
                case 2:
                    paint.setColor(Color.RED);
                    break;
                case 3:
                    paint.setColor(Color.YELLOW);
                    break;
                case 4:
                    paint.setColor(Color.argb(255, 255, 181, 216));
                    break;
                case 5:
                    paint.setColor(Color.argb(255, 0, 255, 255));
                    break;
                default:
                    paint.setColor(Color.WHITE);
                    break;
            }

            /** 鐢讳竴涓績 **/
            int i, j;
            double x, y, r;
            for (i = 0; i <= 90; i++) {
                for (j = 0; j <= 90; j++) {
                    r = Math.PI / 45 * i * (1 - Math.sin(Math.PI / 45 * j))
                            * 20;
                    x = r * Math.cos(Math.PI / 45 * j)
                            * Math.sin(Math.PI / 45 * i) + 320 / 2;
                    y = -r * Math.sin(Math.PI / 45 * j) + 400 / 4;
                    canvas.drawPoint((float) x, (float) y, paint);
                }
            }
            /** 缁樺埗鏂囧瓧 **/
            canvas.drawText("Loving You", 75, 400, paint);
            /** 缁樺埗鍦嗚鐭╁舰 **/
            RectF rect = new RectF(60, 403, 260, 408);
            canvas.drawRoundRect(rect, (float) 1.0, (float) 1.0, paint);

            sfh.unlockCanvasAndPost(canvas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
