package com.just.test.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.widget.RGBLuminanceSource;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

public class QRCode extends Activity implements OnClickListener{

	private ImageView iv_showCreate,iv_showLogoCreate;
	private Button btn_jsonqrcode,btn_jsonLogoqrcode;
	private Button btn_createqrcode,btn_createLogoqrcode;
	private static int QR_WIDTH = 800;
	private static int QR_HEIGHT = 800;
	private Bitmap logoBitmap;
	private TextView txt_resultqrcode,txt_resultLogoqrcode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_qrcode);

		iv_showCreate = (ImageView) findViewById(R.id.iv_showCreate);
		iv_showLogoCreate = (ImageView) findViewById(R.id.iv_showLogoCreate);
		txt_resultqrcode = (TextView) findViewById(R.id.txt_resultqrcode);
		txt_resultLogoqrcode = (TextView) findViewById(R.id.txt_resultLogoqrcode);
		btn_createqrcode = (Button) findViewById(R.id.btn_createqrcode);
		btn_createLogoqrcode = (Button) findViewById(R.id.btn_createLogoqrcode);
		btn_jsonqrcode = (Button) findViewById(R.id.btn_jsonqrcode);
		btn_jsonLogoqrcode = (Button) findViewById(R.id.btn_jsonLogoqrcode);
		btn_createqrcode.setOnClickListener(this);
		btn_createLogoqrcode.setOnClickListener(this);
		btn_jsonqrcode.setOnClickListener(this);
		btn_jsonLogoqrcode.setOnClickListener(this);
	}
	//创建普通二维码
	public static Bitmap createQRImage(String url) {
		Bitmap bitmap;
		try {
			// 判断URL合法性
			if (url == null || "".equals(url) || url.length() < 1) {
				return null;
			}
			Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			// 图像数据转换，使用了矩阵转换
			BitMatrix bitMatrix = new QRCodeWriter().encode(url,
					BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
			int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
			// 下面这里按照二维码的算法，逐个生成二维码的图片，
			// 两个for循环是图片横列扫描的结果
			for (int y = 0; y < QR_HEIGHT; y++) {
				for (int x = 0; x < QR_WIDTH; x++) {
					if (bitMatrix.get(x, y)) {
						pixels[y * QR_WIDTH + x] = 0xff000000;
					} else {
						pixels[y * QR_WIDTH + x] = 0xffffffff;
					}
				}
			}
			bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
					Bitmap.Config.ARGB_8888);
			bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);

			return bitmap;
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return null;
	}
	//创建logo二维码
	public void createLogoQRImage(String url,Bitmap logoBm) {
		try {
			// 判断URL合法性
			if (url == null || "".equals(url) || url.length() < 1) {
				return;
			}
			Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			// 图像数据转换，使用了矩阵转换
			BitMatrix bitMatrix = new QRCodeWriter().encode(url,
					BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
			int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
			// 下面这里按照二维码的算法，逐个生成二维码的图片，
			// 两个for循环是图片横列扫描的结果
			for (int y = 0; y < QR_HEIGHT; y++) {
				for (int x = 0; x < QR_WIDTH; x++) {
					if (bitMatrix.get(x, y)) {
						pixels[y * QR_WIDTH + x] = 0xff000000;
					} else {
						pixels[y * QR_WIDTH + x] = 0xffffffff;
					}
				}
			}
			logoBitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
					Bitmap.Config.ARGB_8888);
			logoBitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
			if (logoBm != null) {
				logoBitmap = addLogo(logoBitmap, logoBm);
			}
			// 显示到一个ImageView上面
			iv_showLogoCreate.setImageBitmap(logoBitmap);
		} catch (WriterException e) {
			e.printStackTrace();
		}
	}
	//解析二维码图片,返回结果封装在Result对象中
	public Result parsePic(Bitmap bitmap) {
		//解析转换类型UTF-8
		Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
		hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
		//新建一个RGBLuminanceSource对象，将bitmap图片传给此对象
		RGBLuminanceSource rgbLuminanceSource = new RGBLuminanceSource(bitmap);
		//将图片转换成二进制图片
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(rgbLuminanceSource));
		//初始化解析对象
		QRCodeReader reader = new QRCodeReader();
		//开始解析
		Result result = null;
		try {
			result = reader.decode(binaryBitmap, hints);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 在二维码中间加上logo
	 *
	 */
	private static Bitmap addLogo(Bitmap src,Bitmap logo) {
		if (src == null) return null;
		if (logo == null) return src;
		//获取图片宽高
		int srcWidth = src.getWidth();
		int srcHeight = src.getHeight();
		int logoWidth = logo.getWidth();
		int logoHeight = logo.getHeight();

		if (srcWidth == 0 || srcHeight == 0) return null;
		if (logoWidth == 0 || logoHeight == 0) return src;

		//logo大小为二维码的五分之一大小
		float scaleFator = srcWidth * 1.0f / 5 / logoWidth;
		Bitmap bitmap = Bitmap.createBitmap(srcWidth,srcHeight,Bitmap.Config.ARGB_8888);
		try {
			Canvas canvas = new Canvas(bitmap);
			canvas.drawBitmap(src, 0, 0,null);
			canvas.scale(scaleFator, scaleFator,srcWidth/2,srcHeight/2);
			canvas.drawBitmap(logo, (srcWidth-logoWidth)/2, (srcHeight-logoHeight)/2,null);
			canvas.save();
			canvas.restore();
		} catch (Exception e) {
			bitmap = null;
			e.getStackTrace();
		}
		return bitmap;
	}

	@Override
	public void onClick(View v) {
		if (v == btn_createqrcode) {
			Bitmap bitmap = createQRImage("钟志华");
			if (bitmap != null){
				// 显示到一个ImageView上面
				iv_showCreate.setImageBitmap(bitmap);
			}
		}else if (v == btn_jsonqrcode) {
			Bitmap bitmap = createQRImage("钟志华");
			if (bitmap != null) {
				Result result = parsePic(bitmap);
				txt_resultqrcode.setText("二维码结果："+result.toString());
			}
		}else if (v == btn_createLogoqrcode) {
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wechat_icon);
			createLogoQRImage("钟志华", bitmap);
		}else if (v == btn_jsonLogoqrcode) {
			if (logoBitmap != null) {
				Result result = parsePic(logoBitmap);
				Toast.makeText(QRCode.this, result.toString(), Toast.LENGTH_LONG).show();
				txt_resultLogoqrcode.setText("logo二维码结果："+result.toString());
			}
		}
	}

}
