package com.wq.photo.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CacheUtils {

    public static final String PATH_HEADER = Environment.getExternalStorageDirectory().getAbsolutePath() + "/com.zcl.showphone/";

    /**
     * 裁剪临时文件
     */
    public static final String destinationFileName = "crop_temp.jpg";

    public static File getCropTempFile(Context context) {
        return new File(context.getCacheDir(), destinationFileName);
    }


    public static boolean saveImg(Bitmap bitmap, Context context) {
        try {

            File file = getSquareImgFile();
            FileOutputStream outputStream = new FileOutputStream(file);     //构建输出流
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);  //compress到输出outputStream
            Uri uri = Uri.fromFile(file);                                  //获得图片的uri
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri)); //发送广播通知更新图库，这样系统图库可以找到这张图片
            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;


    }

    public static File getSquareImgFile() {
        String dir = PATH_HEADER + "SquareImage/";                    //图片保存的文件夹名
        File file = new File(dir);                                 //已File来构建
        if (!file.exists()) {                                     //如果不存在  就mkdirs()创建此文件夹
            file.mkdirs();
        }
        return new File(dir + "SquareImage_" + System.currentTimeMillis() + ".jpg");                        //将要保存的图片文件

    }

    public static File getAppCameraFile() {
        String dir = PATH_HEADER + "Camera/";                    //图片保存的文件夹名
        File file = new File(dir);                                 //已File来构建
        if (!file.exists()) {                                     //如果不存在  就mkdirs()创建此文件夹
            file.mkdirs();
        }
        return new File(dir + "IMG_" + System.currentTimeMillis() + ".jpg");                        //将要保存的图片文件

    }

    private static File getDCIMFile() {
        String str = null;
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        date = new Date(System.currentTimeMillis());
        str = format.format(date);
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "IMG_" + str + ".jpg");
    }
}
