package com.pxz.pxzutils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Settings;

import java.util.List;

/**
 * 类说明：App信息
 * 联系：530927342@qq.com
 *
 * @author peixianzhong
 * @date 2019/6/5 16:37
 */
public class AppBaseUtil {
    /**
     * 判断 App 是否安装
     *
     * @param context     上下文
     * @param packageName 包名
     * @return true：安装  false：未安装
     */
    public static boolean isAppInstalled(Context context, final String packageName) {
        return !isSpace(packageName) && context.getPackageManager().getLaunchIntentForPackage(packageName) != null;
    }

    /**
     * 判断当前App是否是Debug版本
     *
     * @param context 上下文
     * @return true：是debug版本  false：不是debug版本
     */
    public static boolean isAppDebug(Context context) {
        return isAppDebug(context, context.getPackageName());
    }

    /**
     * 判断指定包名的App是否是Debug版本
     *
     * @param context     上下文
     * @param packageName 包名
     * @return true：是debug版本  false：不是debug版本
     */
    public static boolean isAppDebug(Context context, String packageName) {
        if (isSpace(packageName)) {
            return false;
        }
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断当前App是否是系统应用
     *
     * @param context 上下文
     * @return true：是系统应用  false：不是系统应用
     */
    public static boolean isAppSystem(Context context) {
        return isAppSystem(context, context.getPackageName());
    }

    /**
     * 判断指定包名的App是否是系统应用
     *
     * @param context     上下文
     * @param packageName 包名
     * @return true：是系统应用  false：不是系统应用
     */
    public static boolean isAppSystem(Context context, String packageName) {
        if (isSpace(packageName)) {
            return false;
        }
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断指定包名的App是否处于前台
     *
     * @param context     上下文
     * @param packageName 包名
     * @return true：处于前台  false：不处于前台
     */
    public static boolean isAppForeground(Context context,final String packageName) {
        return !isSpace(packageName) && packageName.equals(getForegroundProcessName(context));
    }

    /**
     * 打开app，不带返回值
     *
     * @param context     上下文
     * @param packageName 包名
     */
    public static void launchApp(Context context, final String packageName) {
        if (isSpace(packageName)) {
            return;
        }
        context.startActivity(getLaunchAppIntent(context, packageName, true));
    }

    /**
     * 打开app，带返回值
     *
     * @param activity    上下文
     * @param packageName 包名
     * @param requestCode 返回标识
     */
    public static void launchApp(final Activity activity, final String packageName, final int requestCode) {
        if (isSpace(packageName)) {
            return;
        }
        activity.startActivityForResult(getLaunchAppIntent(activity, packageName), requestCode);
    }

    /**
     * 重启当前app
     *
     * @param context 上下文
     */
    public static void relaunchApp(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
        if (intent == null) {
            return;
        }
        ComponentName componentName = intent.getComponent();
        Intent mainIntent = Intent.makeRestartActivityTask(componentName);
        context.startActivity(mainIntent);
        System.exit(0);
    }

    /**
     * 打开当前App具体设置
     *
     * @param context 上下文
     */
    public static void launchAppDetailsSettings(Context context) {
        launchAppDetailsSettings(context, context.getPackageName());
    }

    /**
     * 打开包名下App具体设置
     *
     * @param context     上下文
     * @param packageName 包名
     */
    public static void launchAppDetailsSettings(Context context, final String packageName) {
        if (isSpace(packageName)) {
            return;
        }
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + packageName));
        context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    /**
     * 获取当前App图标
     *
     * @param context 上下文
     * @return 当前App图标drawable
     */
    public static Drawable getAppIcon(Context context) {
        return getAppIcon(context, context.getPackageName());
    }

    /**
     * 获取包名下的App图标
     *
     * @param context     上下文
     * @param packageName 包名
     * @return 名下的App图标drawable
     */
    public static Drawable getAppIcon(Context context, final String packageName) {
        if (isSpace(packageName)) {
            return null;
        }
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前App包名
     *
     * @param context 上下文
     * @return 当前App包名
     */
    public static String getAppPackageName(Context context) {
        return context.getPackageName();
    }

    /**
     * 获取当前App名称
     *
     * @param context 上下文
     * @return 当前App名称
     */
    public static String getAppName(Context context) {
        return getAppName(context, context.getPackageName());
    }

    /**
     * 获取包名下的App名称
     *
     * @param context     上下文
     * @param packageName 包名
     * @return 包名下的App名称
     */
    public static String getAppName(Context context, final String packageName) {
        if (isSpace(packageName)) {
            return "";
        }
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取当前App路径
     *
     * @param context 上下文
     * @return 当前App路径
     */
    public static String getAppPath(Context context) {
        return getAppPath(context.getPackageName(), context);
    }

    /**
     * 获取包名下的App路径
     *
     * @param packageName 包名
     * @param context     上下文
     * @return 包名下的App路径
     */
    public static String getAppPath(final String packageName, Context context) {
        if (isSpace(packageName)) {
            return "";
        }
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取当前App版本名
     *
     * @param context 上下文
     * @return 当前App版本名
     */
    public static String getAppVersionName(Context context) {
        return getAppVersionName(context.getPackageName(), context);
    }

    /**
     * 获取包名下的App版本名
     *
     * @param packageName 包名
     * @param context     上下文
     * @return 包名下的App版本名
     */
    public static String getAppVersionName(final String packageName, Context context) {
        if (isSpace(packageName)) {
            return "";
        }
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取当前App版本号
     *
     * @param context 上下文
     * @return 当前App版本号
     */
    public static int getAppVersionCode(Context context) {
        return getAppVersionCode(context.getPackageName(), context);
    }

    /**
     * 获取包名下的App版本号
     *
     * @param packageName 包名
     * @param context     上下文
     * @return 包名下的App版本号
     */
    public static int getAppVersionCode(final String packageName, Context context) {
        if (isSpace(packageName)) {
            return -1;
        }
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取当前App签名
     *
     * @param context 上下文
     * @return 当前App签名
     */
    public static Signature[] getAppSignature(Context context) {
        return getAppSignature(context.getPackageName(), context);
    }

    /**
     * 获取包名下的App签名
     *
     * @param packageName 包名
     * @param context     上下文
     * @return 包名下的App签名
     */
    public static Signature[] getAppSignature(final String packageName, Context context) {
        if (isSpace(packageName)) {
            return null;
        }
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return pi == null ? null : pi.signatures;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*-------------------------------- 分割线 --------------------------------*/

    /**
     * 是否有包名
     *
     * @param s 包名
     * @return true：不是包名   false：是包名
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

    private static Intent getLaunchAppIntent(Context context, final String packageName) {
        return getLaunchAppIntent(context, packageName, false);
    }

    private static Intent getLaunchAppIntent(Context context, final String packageName, final boolean isNewTask) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent == null) {
            return null;
        }
        return isNewTask ? intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) : intent;
    }

    private static String getForegroundProcessName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) {
            return null;
        }
        List<ActivityManager.RunningAppProcessInfo> pInfo = am.getRunningAppProcesses();
        if (pInfo != null && pInfo.size() > 0) {
            for (ActivityManager.RunningAppProcessInfo aInfo : pInfo) {
                if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return aInfo.processName;
                }
            }
        }
        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.LOLLIPOP) {
            PackageManager pm = context.getPackageManager();
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            List<ResolveInfo> list = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            if (list.size() <= 0) {
                return "";
            }
            try {
                // Access to usage information.
                ApplicationInfo info = pm.getApplicationInfo(context.getPackageName(), 0);
                AppOpsManager aom = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
                if (aom != null) {
                    if (aom.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, info.uid, info.packageName) != AppOpsManager.MODE_ALLOWED) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                    if (aom.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, info.uid, info.packageName) != AppOpsManager.MODE_ALLOWED) {
                        return "";
                    }
                }
                UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
                List<UsageStats> usageStatsList = null;
                if (usageStatsManager != null) {
                    long endTime = System.currentTimeMillis();
                    long beginTime = endTime - 86400000 * 7;
                    usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, beginTime, endTime);
                }
                if (usageStatsList == null || usageStatsList.isEmpty()) {
                    return null;
                }
                UsageStats recentStats = null;
                for (UsageStats usageStats : usageStatsList) {
                    if (recentStats == null || usageStats.getLastTimeUsed() > recentStats.getLastTimeUsed()) {
                        recentStats = usageStats;
                    }
                }
                return recentStats == null ? null : recentStats.getPackageName();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}