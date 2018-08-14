package com.wq.photo.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.yalantis.ucrop.UCrop;
import com.yanzhenjie.permission.AndPermission;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.wq.photo.MediaChoseActivity.REQUEST_CODE_CAMERA;

public class StartCamera {
    public static File currentfile;
    public static boolean isSquareCrop = true;

    public static void sendStarCamera(Activity context) {
        currentfile = CacheUtils.getAppCameraFile();

        Uri compatUri = AndPermission.getFileUri(context, currentfile);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, compatUri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        context.startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }


    public static void sendStarCrop(Activity context, String path) {
        File file = CacheUtils.getSquareImgFile();

        UCrop uCrop = UCrop.of(Uri.fromFile(new File(path)), Uri.fromFile(file));
        if (isSquareCrop) {
            uCrop = uCrop.withAspectRatio(1, 1);
        } else {
            uCrop = uCrop.useSourceImageAspectRatio();
        }
        UCrop.Options options = new UCrop.Options();
        options.setStatusBarColor(Color.parseColor("#f08300"));
        options.setToolbarColor(Color.parseColor("#f08300"));
        uCrop.withOptions(options);
        uCrop.start(context);

    }


}
