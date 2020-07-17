package com.just.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 拍照及相册
 * Created by Administrator on 2017/2/7.
 */

public class TakeCamera extends Activity implements View.OnClickListener{

    private Button btn_takePhoto;
    private Button btn_takeImage;
    private ImageView iv_takeImages;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int PICK_IMAGE_ACTIVITY_REQUEST_CODE = 200;
    private String picFileFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_takecamera);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "拍照及相册");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        btn_takePhoto = (Button)findViewById(R.id.btn_takePhoto);
        btn_takeImage = (Button)findViewById(R.id.btn_takeImage);
        iv_takeImages = (ImageView)findViewById(R.id.iv_takeImages);

        btn_takePhoto.setOnClickListener(this);
        btn_takeImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_takePhoto){
            takePicure();
        }else if (view == btn_takeImage){
            openAlbum();
        }
    }

    /**
     * 拍照
     */
    private void takePicure(){
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File outDr = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (!outDr.exists()){
                outDr.mkdirs();
            }

            File outFile = new File(outDr,getTime()+".jpg");
            picFileFullName = outFile.getAbsolutePath();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile));
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
            startActivityForResult(intent,CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }else {
            Toast.makeText(TakeCamera.this,"请确认已经插入SD卡",Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 打开本地相册
     */
    private void openAlbum(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        this.startActivityForResult(intent,PICK_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    /**
     * 获取当前时间
     * @return
     */
    private String getTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String currentTimeString = format.format(date);
        return currentTimeString;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
            //拍照回传
            if (resultCode == RESULT_OK){
                Toast.makeText(TakeCamera.this,"获取图片成功",Toast.LENGTH_LONG).show();
                setImageView(picFileFullName);
            }else if (resultCode == RESULT_CANCELED){
                Toast.makeText(TakeCamera.this,"已取消",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(TakeCamera.this,"拍照失败",Toast.LENGTH_LONG).show();
            }
        }else if (requestCode == PICK_IMAGE_ACTIVITY_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                Uri uri = data.getData();
                if (uri != null){
                    String realPath = getRealPathFromURI(uri);
                    setImageView(realPath);
                    Toast.makeText(TakeCamera.this,"获取图片成功",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(TakeCamera.this,"从相册获取图片失败",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    /**
     * 设置回传的图片
     * @param path
     */
    private void setImageView(String path){
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        int degree = readPictureDegree(path);
        if (degree <= 0){
            iv_takeImages.setImageBitmap(bitmap);
        }else {
            Matrix matrix = new Matrix();
            matrix.postRotate(degree);//旋转图片
            Bitmap resizeBitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
            iv_takeImages.setImageBitmap(resizeBitmap);
        }
    }

    /**
     * 读取图片的方向
     * @param path
     * @return
     */
    public static int readPictureDegree(String path){
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL);
            switch (orientation){
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return degree;
    }

    /**
     * 获取相册的uri
     */
    public String getRealPathFromURI(Uri uri){
        try {
            String [] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = this.managedQuery(uri,proj,null,null,null);
            int colume_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();

            return cursor.getString(colume_index);
        }catch (Exception e){
            return uri.getPath();
        }
    }
}
