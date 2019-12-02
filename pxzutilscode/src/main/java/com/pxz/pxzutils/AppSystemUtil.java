package com.pxz.pxzutils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 类说明：App系统工具类
 * 联系：530927342@qq.com
 *
 * @author peixianzhong
 * @date 2019/6/5 14:15
 */
public class AppSystemUtil {
    /**
     * 保存图片到相册
     *
     * @param context 上下文
     * @param bmp     图片的bitmap
     */
    public static void saveImageToGallery(Context context, Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "相册");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //广播同步到相册
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsoluteFile())));
    }

    /**
     * 保存文字到剪贴板
     *
     * @param context 上下文
     * @param text    复制的内容
     */
    public static void copyToClipBoard(Context context, String text) {
        ClipData clipData = ClipData.newPlainText("text", text);
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(clipData);
    }

    /**
     * 获取剪切板的文字（第一个）
     *
     * @param context 上下文
     */
    public static CharSequence getToClipBoard(Context context) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        //noinspection ConstantConditions
        ClipData clip = cm.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            return clip.getItemAt(0).coerceToText(context);
        }
        return null;
    }

    /**
     * 跳转到拨号面板
     *
     * @param activity    上下文
     * @param phoneNumber 号码
     */
    public static void startIntentPhone(Activity activity, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    /**
     * 跳转短信
     */
    public static void startMessage(Activity activity){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setType("vnd.android-dir/mms-sms");
        activity.startActivity(intent);
    }
}