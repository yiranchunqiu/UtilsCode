package com.pxz.pxzutils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.view.View;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import androidx.core.content.ContextCompat;

/**
 * 类说明：图片处理
 * 联系：530927342@qq.com
 *
 * @author peixianzhong
 * @date 2019/12/2 9:35
 */
public class BitmapImageUtil {
    /**
     * 将Bitmap转换成InputStream
     *
     * @param bm bitmap图片
     * @return InputStream
     */
    public static InputStream bitmap2InputStream(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }

    /**
     * 将Bitmap转换成InputStream
     *
     * @param bm   bitmap图片
     * @param path 路径
     * @return FileInputStream
     */
    public static FileInputStream bitmap2FileInputStream(Bitmap bm, String path) {
        File file = new File(path);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return new FileInputStream(file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将Drawable转换成Bitmap
     *
     * @param drawable Drawable
     * @return Bitmap
     */
    public static Bitmap drawable2Bitmap(final Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        Bitmap bitmap;
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1,
                    drawable.getOpacity() != PixelFormat.OPAQUE
                            ? Bitmap.Config.ARGB_8888
                            : Bitmap.Config.RGB_565);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(),
                    drawable.getOpacity() != PixelFormat.OPAQUE
                            ? Bitmap.Config.ARGB_8888
                            : Bitmap.Config.RGB_565);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 将Bitmap转换成Drawable
     *
     * @param context 上下文
     * @param bitmap  Bitmap
     * @return Drawable
     */
    public static Drawable bitmap2Drawable(Context context, final Bitmap bitmap) {
        return bitmap == null ? null : new BitmapDrawable(context.getResources(), bitmap);
    }

    /**
     * 将Bitmap转换成byte数组
     *
     * @param bm bitmap图片
     * @return 二进制
     */
    private byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 将Bitmap转换成byte数组
     *
     * @param bitmap Bitmap
     * @param format 格式
     * @return byte数组
     */
    public static byte[] bitmap2Bytes(final Bitmap bitmap, final Bitmap.CompressFormat format) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(format, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 将byte数组转换成Bitmap
     *
     * @param bytes byte数组
     * @return Bitmap
     */
    public static Bitmap bytes2Bitmap(final byte[] bytes) {
        return (bytes == null || bytes.length == 0)
                ? null
                : BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 将drawable转换成byte数组
     *
     * @param drawable drawable
     * @param format   格式
     * @return byte数组
     */
    public static byte[] drawable2Bytes(final Drawable drawable, final Bitmap.CompressFormat format) {
        return drawable == null ? null : bitmap2Bytes(drawable2Bitmap(drawable), format);
    }

    /**
     * 将byte数组转换成drawable
     *
     * @param bytes byte数组
     * @return drawable
     */
    public static Drawable bytes2Drawable(Context context, final byte[] bytes) {
        return bitmap2Drawable(context, bytes2Bitmap(bytes));
    }

    /**
     * 将view转换成Bitmap
     *
     * @param view view
     * @return Bitmap
     */
    public static Bitmap view2Bitmap(final View view) {
        if (view == null) {
            return null;
        }
        Bitmap ret = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(ret);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return ret;
    }

    /**
     * 获取bitmap
     *
     * @param file 文件
     * @return bitmap
     */
    public static Bitmap getBitmap(final File file) {
        if (file == null) {
            return null;
        }
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }

    /**
     * 返回bitmap
     *
     * @param file      文件
     * @param maxWidth  宽度
     * @param maxHeight 高度
     * @return bitmap
     */
    public static Bitmap getBitmap(final File file, final int maxWidth, final int maxHeight) {
        if (file == null) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }

    /**
     * 返回bitmap
     *
     * @param filePath 文件路径
     * @return bitmap
     */
    public static Bitmap getBitmap(final String filePath) {
        if (isSpace(filePath)) {
            return null;
        }
        return BitmapFactory.decodeFile(filePath);
    }

    /**
     * 返回bitmap
     *
     * @param filePath  文件路径
     * @param maxWidth  宽度
     * @param maxHeight 高度
     * @return bitmap
     */
    public static Bitmap getBitmap(final String filePath, final int maxWidth, final int maxHeight) {
        if (isSpace(filePath)) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 返回bitmap
     *
     * @param is 输入流
     * @return bitmap
     */
    public static Bitmap getBitmap(final InputStream is) {
        if (is == null) {
            return null;
        }
        return BitmapFactory.decodeStream(is);
    }

    /**
     * 返回bitmap
     *
     * @param is        输入流
     * @param maxWidth  宽度
     * @param maxHeight 高度
     * @return bitmap
     */
    public static Bitmap getBitmap(final InputStream is, final int maxWidth, final int maxHeight) {
        if (is == null) {
            return null;
        }
        byte[] bytes = input2Byte(is);
        return getBitmap(bytes, 0, maxWidth, maxHeight);
    }

    /**
     * 返回bitmap
     *
     * @param data   byte
     * @param offset 位移
     * @return bitmap
     */
    public static Bitmap getBitmap(final byte[] data, final int offset) {
        if (data.length == 0) {
            return null;
        }
        return BitmapFactory.decodeByteArray(data, offset, data.length);
    }

    /**
     * 返回bitmap
     *
     * @param data      byte
     * @param offset    位移
     * @param maxWidth  宽度
     * @param maxHeight 高度
     * @return bitmap
     */
    public static Bitmap getBitmap(final byte[] data,
                                   final int offset,
                                   final int maxWidth,
                                   final int maxHeight) {
        if (data.length == 0) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, offset, data.length, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(data, offset, data.length, options);
    }

    /**
     * 返回bitmap
     *
     * @param resId 资源id
     * @return bitmap
     */
    public static Bitmap getBitmap(Context context, final int resId) {
        Drawable drawable = ContextCompat.getDrawable(context, resId);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 返回bitmap
     *
     * @param context   上下文
     * @param resId     资源id
     * @param maxWidth  宽度
     * @param maxHeight 高度
     * @return bitmap
     */
    public static Bitmap getBitmap(Context context, final int resId, final int maxWidth, final int maxHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        final Resources resources = context.getResources();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, resId, options);
    }

    /**
     * 返回bitmap
     *
     * @param fd FileDescriptor
     * @return bitmap
     */
    public static Bitmap getBitmap(final FileDescriptor fd) {
        if (fd == null) {
            return null;
        }
        return BitmapFactory.decodeFileDescriptor(fd);
    }

    /**
     * 返回bitmap
     *
     * @param fd        FileDescriptor
     * @param maxWidth  宽度
     * @param maxHeight 高度
     * @return bitmap
     */
    public static Bitmap getBitmap(final FileDescriptor fd, final int maxWidth, final int maxHeight) {
        if (fd == null) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, null, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd, null, options);
    }

    /**
     * 缩放图片
     *
     * @param src       图片
     * @param newWidth  宽度
     * @param newHeight 高度
     * @return 缩放后图片
     */
    public static Bitmap scale(final Bitmap src, final int newWidth, final int newHeight) {
        return scale(src, newWidth, newHeight, false);
    }

    /**
     * 缩放图片
     *
     * @param src       图片
     * @param newWidth  宽度
     * @param newHeight 高度
     * @param recycle   是否回收
     * @return 缩放后图片
     */
    public static Bitmap scale(final Bitmap src, final int newWidth, final int newHeight, final boolean recycle) {
        if (isEmptyBitmap(src)) {
            return null;
        }
        Bitmap ret = Bitmap.createScaledBitmap(src, newWidth, newHeight, true);
        if (recycle && !src.isRecycled()) {
            src.recycle();
        }
        return ret;
    }

    /**
     * 缩放图片
     *
     * @param src         图片
     * @param scaleWidth  宽度
     * @param scaleHeight 高度
     * @return 缩放后图片
     */
    public static Bitmap scale(final Bitmap src, final float scaleWidth, final float scaleHeight) {
        return scale(src, scaleWidth, scaleHeight, false);
    }

    /**
     * 缩放图片
     *
     * @param src         图片
     * @param scaleWidth  宽度
     * @param scaleHeight 高度
     * @param recycle     是否回收
     * @return 缩放后图片
     */
    public static Bitmap scale(final Bitmap src, final float scaleWidth, final float scaleHeight, final boolean recycle) {
        if (isEmptyBitmap(src)) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.setScale(scaleWidth, scaleHeight);
        Bitmap ret = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        if (recycle && !src.isRecycled()) {
            src.recycle();
        }
        return ret;
    }

    /**
     * 裁剪图片
     *
     * @param src    图片
     * @param x      子位图第一个像素在源位图的X坐标
     * @param y      子位图第一个像素在源位图的y坐标
     * @param width  宽度
     * @param height 高度
     * @return 返回剪裁后的位图
     */
    public static Bitmap clip(final Bitmap src,
                              final int x,
                              final int y,
                              final int width,
                              final int height) {
        return clip(src, x, y, width, height, false);
    }

    /**
     * 裁剪图片
     *
     * @param src     图片
     * @param x       子位图第一个像素在源位图的X坐标
     * @param y       子位图第一个像素在源位图的y坐标
     * @param width   宽度
     * @param height  高度
     * @param recycle 是否回收位图源
     * @return 返回剪裁后的位图
     */
    public static Bitmap clip(final Bitmap src,
                              final int x,
                              final int y,
                              final int width,
                              final int height,
                              final boolean recycle) {
        if (isEmptyBitmap(src)) {
            return null;
        }
        Bitmap ret = Bitmap.createBitmap(src, x, y, width, height);
        if (recycle && !src.isRecycled()) {
            src.recycle();
        }
        return ret;
    }

    /**
     * 倾斜图片
     *
     * @param src 图片
     * @param kx  x偏移系数
     * @param ky  y偏移系数
     * @return 返回倾斜后的位图
     */
    public static Bitmap skew(final Bitmap src, final float kx, final float ky) {
        return skew(src, kx, ky, 0, 0, false);
    }

    /**
     * 倾斜图片
     *
     * @param src     图片
     * @param kx      x偏移系数
     * @param ky      y偏移系数
     * @param recycle 是否回收位图源
     * @return 返回倾斜后的位图
     */
    public static Bitmap skew(final Bitmap src,
                              final float kx,
                              final float ky,
                              final boolean recycle) {
        return skew(src, kx, ky, 0, 0, recycle);
    }

    /**
     * 倾斜图片
     *
     * @param src 图片
     * @param kx  x偏移系数
     * @param ky  y偏移系数
     * @param px  中心点x
     * @param py  中心点y
     * @return 返回倾斜后的位图
     */
    public static Bitmap skew(final Bitmap src,
                              final float kx,
                              final float ky,
                              final float px,
                              final float py) {
        return skew(src, kx, ky, px, py, false);
    }

    /**
     * 倾斜图片
     *
     * @param src     图片
     * @param kx      x偏移系数
     * @param ky      y偏移系数
     * @param px      中心点x
     * @param py      中心点y
     * @param recycle 是否回收位图源
     * @return 返回倾斜后的位图
     */
    public static Bitmap skew(final Bitmap src,
                              final float kx,
                              final float ky,
                              final float px,
                              final float py,
                              final boolean recycle) {
        if (isEmptyBitmap(src)) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.setSkew(kx, ky, px, py);
        Bitmap ret = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        if (recycle && !src.isRecycled()) {
            src.recycle();
        }
        return ret;
    }

    /**
     * 设置水印图片在左上角
     *
     * @param context     上下文
     * @param src         要添加水印的图片
     * @param watermark   要添加的水印
     * @param paddingLeft 距离左边距离
     * @param paddingTop  距离上边距离
     * @return 图片
     */
    public static Bitmap createWaterMaskLeftTop(Context context, Bitmap src, Bitmap watermark, int paddingLeft, int paddingTop) {
        return createWaterMaskBitmap(src, watermark, ScreenUtil.dip2px(context, paddingLeft), ScreenUtil.dip2px(context, paddingTop));
    }

    /**
     * 设置水印图片在右下角
     *
     * @param context       上下文
     * @param src           要添加水印的图片
     * @param watermark     要添加的水印
     * @param paddingRight  距离右边距离
     * @param paddingBottom 距离下边距离
     * @return 图片
     */
    public static Bitmap createWaterMaskRightBottom(Context context, Bitmap src, Bitmap watermark, int paddingRight, int paddingBottom) {
        return createWaterMaskBitmap(src, watermark, src.getWidth() - watermark.getWidth() - ScreenUtil.dip2px(context, paddingRight), src.getHeight() - watermark.getHeight() - ScreenUtil.dip2px(context, paddingBottom));
    }

    /**
     * 设置水印图片到右上角
     *
     * @param context      上下文
     * @param src          要添加水印的图片
     * @param watermark    要添加的水印
     * @param paddingRight 距离右边距离
     * @param paddingTop   距离上边距离
     * @return 图片
     */
    public static Bitmap createWaterMaskRightTop(Context context, Bitmap src, Bitmap watermark, int paddingRight, int paddingTop) {
        return createWaterMaskBitmap(src, watermark, src.getWidth() - watermark.getWidth() - ScreenUtil.dip2px(context, paddingRight), ScreenUtil.dip2px(context, paddingTop));
    }

    /**
     * 设置水印图片到左下角
     *
     * @param context       上下文
     * @param src           要添加水印的图片
     * @param watermark     要添加的水印
     * @param paddingLeft   距离左边距离
     * @param paddingBottom 距离下边距离
     * @return 图片
     */
    public static Bitmap createWaterMaskLeftBottom(Context context, Bitmap src, Bitmap watermark, int paddingLeft, int paddingBottom) {
        return createWaterMaskBitmap(src, watermark, ScreenUtil.dip2px(context, paddingLeft), src.getHeight() - watermark.getHeight() - ScreenUtil.dip2px(context, paddingBottom));
    }

    /**
     * 设置水印图片到中间
     *
     * @param src       要添加水印的图片
     * @param watermark 要添加的水印
     * @return 图片
     */
    public static Bitmap createWaterMaskCenter(Bitmap src, Bitmap watermark) {
        return createWaterMaskBitmap(src, watermark, (src.getWidth() - watermark.getWidth()) / 2, (src.getHeight() - watermark.getHeight()) / 2);
    }

    /**
     * 给图片添加文字到左上角
     *
     * @param context     上下文
     * @param bitmap      要添加文字的图片
     * @param text        要添加文字
     * @param size        要添加文字的大小
     * @param color       要添加文字的颜色
     * @param paddingLeft 距离左边距离
     * @param paddingTop  距离上边距离
     * @return 图片
     */
    public static Bitmap drawTextToLeftTop(Context context, Bitmap bitmap, String text, int size, int color, int paddingLeft, int paddingTop) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(ScreenUtil.dip2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(bitmap, text, paint, ScreenUtil.dip2px(context, paddingLeft), ScreenUtil.dip2px(context, paddingTop) + bounds.height());
    }

    /**
     * 绘制文字到右下角
     *
     * @param context       上下文
     * @param bitmap        要添加文字的图片
     * @param text          要添加文字
     * @param size          要添加文字的大小
     * @param color         要添加文字的颜色
     * @param paddingRight  距离右边距离
     * @param paddingBottom 距离下边距离
     * @return 图片
     */
    public static Bitmap drawTextToRightBottom(Context context, Bitmap bitmap, String text, int size, int color, int paddingRight, int paddingBottom) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(ScreenUtil.dip2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(bitmap, text, paint, bitmap.getWidth() - bounds.width() - ScreenUtil.dip2px(context, paddingRight), bitmap.getHeight() - ScreenUtil.dip2px(context, paddingBottom));
    }

    /**
     * 绘制文字到右上方
     *
     * @param context      上下文
     * @param bitmap       要添加文字的图片
     * @param text         要添加文字
     * @param size         要添加文字的大小
     * @param color        要添加文字的颜色
     * @param paddingRight 距离右边距离
     * @param paddingTop   距离上边距离
     * @return 图片
     */
    public static Bitmap drawTextToRightTop(Context context, Bitmap bitmap, String text, int size, int color, int paddingRight, int paddingTop) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(ScreenUtil.dip2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(bitmap, text, paint, bitmap.getWidth() - bounds.width() - ScreenUtil.dip2px(context, paddingRight), ScreenUtil.dip2px(context, paddingTop) + bounds.height());
    }

    /**
     * 绘制文字到左下方
     *
     * @param context       上下文
     * @param bitmap        要添加文字的图片
     * @param text          要添加文字
     * @param size          要添加文字的大小
     * @param color         要添加文字的颜色
     * @param paddingLeft   距离左边距离
     * @param paddingBottom 距离下边距离
     * @return 图片
     */
    public static Bitmap drawTextToLeftBottom(Context context, Bitmap bitmap, String text, int size, int color, int paddingLeft, int paddingBottom) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(ScreenUtil.dip2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(bitmap, text, paint, ScreenUtil.dip2px(context, paddingLeft), bitmap.getHeight() - ScreenUtil.dip2px(context, paddingBottom));
    }

    /**
     * 绘制文字到中间
     *
     * @param context 上下文
     * @param bitmap  要添加文字的图片
     * @param text    要添加文字
     * @param size    要添加文字的大小
     * @param color   要添加文字的颜色
     * @return 图片
     */
    public static Bitmap drawTextToCenter(Context context, Bitmap bitmap, String text, int size, int color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(ScreenUtil.dip2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(bitmap, text, paint, (bitmap.getWidth() - bounds.width()) / 2, (bitmap.getHeight() + bounds.height()) / 2);
    }

    /**
     * 设置图片水印
     *
     * @param src         要添加水印的图片
     * @param watermark   要添加的水印
     * @param paddingLeft 距离左边距离
     * @param paddingTop  距离上边距离
     * @return 图片
     */
    private static Bitmap createWaterMaskBitmap(Bitmap src, Bitmap watermark, int paddingLeft, int paddingTop) {
        if (src == null) {
            return null;
        }
        int width = src.getWidth();
        int height = src.getHeight();
        // 创建一个bitmap
        // 创建一个新的和SRC长度宽度一样的位图
        Bitmap newb = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // 将该图片作为画布
        Canvas canvas = new Canvas(newb);
        // 在画布 0，0坐标上开始绘制原始图片
        canvas.drawBitmap(src, 0, 0, null);
        // 在画布上绘制水印图片
        canvas.drawBitmap(watermark, paddingLeft, paddingTop, null);
        // 保存
        canvas.save();
        // 存储
        canvas.restore();
        return newb;
    }

    /**
     * 图片上绘制文字
     *
     * @param bitmap      要添加文字的图片
     * @param text        要添加文字
     * @param paint       要添加文字样式
     * @param paddingLeft 距离左边距离
     * @param paddingTop  距离上边距离
     * @return 图片
     */
    private static Bitmap drawTextToBitmap(Bitmap bitmap, String text, Paint paint, int paddingLeft, int paddingTop) {
        Bitmap.Config bitmapConfig = bitmap.getConfig();
        // 获取跟清晰的图像采样
        paint.setDither(true);
        // 过滤一些
        paint.setFilterBitmap(true);
        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText(text, paddingLeft, paddingTop, paint);
        return bitmap;
    }

    /**
     * 对图片进行毛玻璃化
     *
     * @param sentBitmap       位图
     * @param radius           虚化程度
     * @param canReuseInBitmap 是否重用
     * @return 位图
     */
    public static Bitmap doBlur(Bitmap sentBitmap, int radius, boolean canReuseInBitmap) {
        Bitmap bitmap;
        if (canReuseInBitmap) {
            bitmap = sentBitmap;
        } else {
            bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
        }
        if (radius < 1) {
            return (null);
        }
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int[] pix = new int[w * h];
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);
        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;
        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];
        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }
        yw = yi = 0;
        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;
        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;
            for (x = 0; x < w; x++) {
                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];
                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;
                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];
                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];
                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];
                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;
                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];
                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];
                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];
                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;
                sir = stack[i + radius];
                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];
                rbs = r1 - Math.abs(i);
                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];
                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;
                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];
                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];
                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];
                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];
                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];
                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;
                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];
                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];
                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];
                yi += w;
            }
        }
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);
        return (bitmap);
    }

    /**
     * 对图片进行毛玻璃化  数值越大效果越明显
     *
     * @param originBitmap 位图
     * @param scaleRatio   缩放比率
     * @param blurRadius   毛玻璃化比率，虚化程度
     * @return 位图
     */
    public static Bitmap doBlur(Bitmap originBitmap, int scaleRatio, int blurRadius) {
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originBitmap, originBitmap.getWidth() / scaleRatio, originBitmap.getHeight() / scaleRatio, false);
        Bitmap blurBitmap = doBlur(scaledBitmap, blurRadius, false);
        scaledBitmap.recycle();
        return blurBitmap;
    }

    /**
     * 对图片进行 毛玻璃化，虚化   数值越大效果越明显
     *
     * @param originBitmap 位图
     * @param width        缩放后的期望宽度
     * @param height       缩放后的期望高度
     * @param blurRadius   虚化程度
     * @return 位图
     */
    public static Bitmap doBlur(Bitmap originBitmap, int width, int height, int blurRadius) {
        Bitmap thumbnail = ThumbnailUtils.extractThumbnail(originBitmap, width, height);
        Bitmap blurBitmap = doBlur(thumbnail, blurRadius, true);
        thumbnail.recycle();
        return blurBitmap;
    }

    /**
     * 设置高斯模糊
     * 设置高斯模糊是依靠scaleFactor和radius配合使用的，比如这里默认设置是：scaleFactor = 8;radius = 2; 模糊效果和scaleFactor = 1;radius = 20;是一样的，而且效率高
     *
     * @param fromView    从某个View获取截图
     * @param toView      高斯模糊设置到某个View上
     * @param radius      模糊度
     * @param scaleFactor 缩放比例
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void blur(View fromView, View toView, float radius, float scaleFactor) {
        /*获取View的截图*/
        fromView.buildDrawingCache();
        Bitmap bkg = fromView.getDrawingCache();
        if (radius < 1 || radius > 26) {
            scaleFactor = 8;
            radius = 2;
        }
        Bitmap overlay = Bitmap.createBitmap((int) (toView.getMeasuredWidth() / scaleFactor), (int) (toView.getMeasuredHeight() / scaleFactor), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-toView.getLeft() / scaleFactor, -toView.getTop() / scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0, 0, paint);
        overlay = doBlur(overlay, (int) radius, true);
        toView.setBackground(new BitmapDrawable(overlay));
    }

    /**
     * 是否是bitmap
     *
     * @param src bitmap
     * @return 是否是bitmap
     */
    private static boolean isEmptyBitmap(final Bitmap src) {
        return src == null || src.getWidth() == 0 || src.getHeight() == 0;
    }

    /**
     * 返回样本大小
     *
     * @param options   样本
     * @param maxWidth  宽度
     * @param maxHeight 高度
     * @return 大小
     */
    private static int calculateInSampleSize(final BitmapFactory.Options options, final int maxWidth, final int maxHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
      /*  while ((width >>= 1) >= maxWidth && (height >>= 1) >= maxHeight) {
            inSampleSize <<= 1;
        }*/
        while (width >= maxWidth && height  >= maxHeight) {
            inSampleSize <<= 1;
        }
        return inSampleSize;
    }

    /**
     * 是空吗
     *
     * @param s 字符串
     * @return 是否为空
     */
    private static boolean isSpace(final String s) {
        if (s == null) {
            return true;
        }
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 输入转byte字节
     *
     * @param is 输入
     * @return 字节
     */
    private static byte[] input2Byte(final InputStream is) {
        if (is == null) {
            return null;
        }
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len;
            while ((len = is.read(b, 0, 1024)) != -1) {
                os.write(b, 0, len);
            }
            return os.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}