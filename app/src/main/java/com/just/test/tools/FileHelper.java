package com.just.test.tools;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

/**
 * Created by user on 2016/12/5.
 */

public class FileHelper {

    private final Context mContext;
    private final boolean hasSD;
    private final String SDPATH;
    private final String FILEPATH;

    public FileHelper(Context context){
        this.mContext = context;
        hasSD = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        SDPATH = Environment.getExternalStorageDirectory().getPath();
        FILEPATH = this.mContext.getFilesDir().getPath();
    }

    /**
     * 创建文件
     */

    public File createFile(String fileName) throws IOException{
        File file = new File(SDPATH+"//"+fileName);
        if (!file.exists()){
            file.createNewFile();
        }
        return file;
    }

    public boolean hasFile(String fileName)throws IOException{
        File file = new File(SDPATH+"//"+fileName);
        if (!file.exists()){
            file.createNewFile();
            return true;
        }
        return false;
    }

    /**
     * 删除文件
     */
    public boolean deleteFile(String fileName) throws IOException{
        File file = new File(SDPATH+"//"+fileName);
        if (file == null || !file.exists() || file.isDirectory()){
            return false;
        }
        return file.delete();
    }

    /**
     * 写入sd卡
     */
    public void writeFile(String str,String fileName){
        try {
            FileWriter fileWriter = new FileWriter(SDPATH+"//"+fileName);
            File file = new File(SDPATH+"//"+fileName);
            fileWriter.write(str);

            FileOutputStream os = new FileOutputStream(file);
            DataOutputStream out = new DataOutputStream(os);
            out.writeShort(2);
            out.writeUTF("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件内容
     */
    public String readFile(String fileName){
        StringBuffer sb = new StringBuffer();
        File file = new File(SDPATH+"//"+fileName);
        try {
            FileInputStream is = new FileInputStream(file);
            int c;
            while ((c = is.read()) != -1){
                sb.append((char) c);
            }
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    public static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("0.00");
        String fileSizeString = "";
        String wrongSize = "0";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 从assets取得档案
     *适用于studio
     * @param context
     * @param fileName
     * @return
     */
    public static String getFromAssets(Context context, String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getClass().getClassLoader().getResourceAsStream("assets/"+fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";

            while ((line = bufReader.readLine()) != null) {
                Result = Result + line + "\n";
            }

            return Result;
        } catch (Exception e) {
            return "";
        }
    }
}
