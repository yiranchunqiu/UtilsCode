package com.pxz.pxzutils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * 类说明：App设备信息
 * 联系：530927342@qq.com
 *
 * @author peixianzhong
 * @date 2019/6/5 16:39
 */
public class AppDeviceUtil {
    /**
     * 是否是手机
     *
     * @param context 上下文
     * @return true：是手机 false：不是手机
     */
    public static boolean isPhone(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null && tm.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
    }

    /**
     * 设备登录ip
     *
     * @return 登录ip
     */
    public static String getPhoneIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 获取 Sim 卡运营商名称
     *
     * @param context 上下文
     * @return Sim 卡运营商名称
     */
    public static String getSimOperatorName(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getSimOperatorName() : "";
    }

    /**
     * 获取 Sim 卡运营商名称
     *
     * @param context 上下文
     * @return Sim 卡运营商名称
     */
    public static String getSimOperatorByMnc(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String operator = tm != null ? tm.getSimOperator() : null;
        if (operator == null) {
            return null;
        }
        if (operator.equals("46000") || operator.equals("46002") || operator.equals("46007") || operator.equals("46020")) {
            return "中国移动";
        } else if (operator.equals("46001") || operator.equals("46006") || operator.equals("46009")) {
            return "中国联通";
        } else if (operator.equals("46003") || operator.equals("46005") || operator.equals("46011")) {
            return "中国电信";
        } else {
            return operator;
        }
    }

    /**
     * 设备电话号码 (不是所有都能拿到，运营商将手机号码已写入到sim卡中的就行)（使用前判断动态权限）
     *
     * @param context 上下文
     * @return 设备电话号码
     */
    @SuppressLint("MissingPermission")
    public static String getPhoneNumber(Context context) {
        TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telManager.getLine1Number();
    }

    /**
     * 获取手机状态信息（使用前判断动态权限）
     *
     * @param context 上下文
     * @return 手机状态信息
     */
    @SuppressLint("MissingPermission")
    public static String getPhoneStatus(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        String str = "";
        str += "DeviceId(IMEI) = " + tm.getDeviceId() + "\n";
        str += "DeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion() + "\n";
        str += "Line1Number = " + tm.getLine1Number() + "\n";
        str += "NetworkCountryIso = " + tm.getNetworkCountryIso() + "\n";
        str += "NetworkOperator = " + tm.getNetworkOperator() + "\n";
        str += "NetworkOperatorName = " + tm.getNetworkOperatorName() + "\n";
        str += "NetworkType = " + tm.getNetworkType() + "\n";
        str += "PhoneType = " + tm.getPhoneType() + "\n";
        str += "SimCountryIso = " + tm.getSimCountryIso() + "\n";
        str += "SimOperator = " + tm.getSimOperator() + "\n";
        str += "SimOperatorName = " + tm.getSimOperatorName() + "\n";
        str += "SimSerialNumber = " + tm.getSimSerialNumber() + "\n";
        str += "SimState = " + tm.getSimState() + "\n";
        str += "SubscriberId(IMSI) = " + tm.getSubscriberId() + "\n";
        str += "VoiceMailNumber = " + tm.getVoiceMailNumber() + "\n";
        return str;
    }

    /**
     * 获取设备 AndroidID
     *
     * @param context 上下文
     * @return AndroidID
     */
    public static String getAndroidID(Context context) {
        String id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return id == null ? "" : id;
    }

    /**
     * 获取Cpu信息
     *
     * @return Cpu信息
     */
    public static String getCpuNumber() {
        String str = "";
        String strCPU = "";
        String cpuAddress = "000000000000000000000000000000";
        try {
            // 读取CPU信息
            Process pp = Runtime.getRuntime().exec("cat /proc/cpuinfo");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            // 查找CPU序列号
            for (int i = 1; i < 100; i++) {
                str = input.readLine();
                if (str != null) {
                    // 查找到序列号所在行
                    if (str.indexOf("Serial") > -1) {
                        // 提取序列号
                        strCPU = str.substring(str.indexOf(":") + 1, str.length());
                        // 去空格
                        cpuAddress = strCPU.trim();
                        break;
                    }
                } else {
                    // 文件结尾
                    break;
                }
            }
        } catch (IOException ex) {
            // 赋予默认值
            ex.printStackTrace();
        }
        return cpuAddress.toUpperCase();
    }

    /**
     * 获取设备码（使用前判断动态权限）
     *
     * @param context 上下文
     * @return 设备码
     */
    @SuppressLint("MissingPermission")
    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (tm == null) {
                return "";
            }
            String imei = tm.getImei();
            if (!TextUtils.isEmpty(imei)) {
                return imei;
            }
            String meid = tm.getMeid();
            return TextUtils.isEmpty(meid) ? "" : meid;
        }
        return tm != null ? tm.getDeviceId() : "";
    }

    /**
     * 获取序列号（使用前判断动态权限）
     *
     * @return 序列号
     */
    @SuppressLint("MissingPermission")
    public static String getSerial() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? Build.getSerial() : Build.SERIAL;
    }

    /**
     * 获取 IMEI 码（使用前判断动态权限）
     *
     * @param context 上下文
     * @return IMEI码
     */
    @SuppressLint("MissingPermission")
    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return tm != null ? tm.getImei() : "";
        }
        return tm != null ? tm.getDeviceId() : "";
    }

    /**
     * 获取 MEID 码（使用前判断动态权限）
     *
     * @param context 上下文
     * @return MEID 码
     */
    @SuppressLint("MissingPermission")
    public static String getMEID(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return tm != null ? tm.getMeid() : "";
        } else {
            return tm != null ? tm.getDeviceId() : "";
        }
    }

    /**
     * 获取 IMSI 码（使用前判断动态权限）
     *
     * @param context 上下文
     * @return IMSI 码
     */
    @SuppressLint("MissingPermission")
    public static String getIMSI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getSubscriberId() : "";
    }

    /**
     * 设备串号
     *
     * @param context 上下文
     * @return 设备串号
     */
    public static String getPhoneIMEI(Context context) {
        TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        // 检查是否有权限
        if (PackageManager.PERMISSION_GRANTED == context.getPackageManager().checkPermission(Manifest.permission.READ_PHONE_STATE, context.getPackageName())) {
            return telManager.getDeviceId();
        } else {
            return null;
        }
    }

    /**
     * 设备系统基带版本
     *
     * @return 基带版本
     */
    public static String getPhoneSystemBasebandVersion() {
        return Build.RADIO;
    }

    /**
     * 设备设置版本号
     *
     * @return 设备设置版本号
     */
    public static String getPhoneSystemVersionID() {
        return Build.ID;
    }

    /**
     * 获取设备系统版本号
     *
     * @return 设备系统版本号
     */
    public static String getSDKVersionName() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取设备系统版本码
     *
     * @return 设备系统版本码
     */
    public static int getSDKVersionCode() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 设备品牌
     *
     * @return 品牌
     */
    public static String getPhoneName1() {
        return Build.BRAND;
    }

    /**
     * 获取设备厂商
     *
     * @return 设备厂商
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取设备型号
     *
     * @return 设备型号
     */
    public static String getModel() {
        String model = Build.MODEL;
        if (model != null) {
            model = model.trim().replaceAll("\\s*", "");
        } else {
            model = "";
        }
        return model;
    }

    /**
     * 获取设备cpu型号
     *
     * @return 设备cpu型号
     */
    public static String[] getABIs() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return Build.SUPPORTED_ABIS;
        } else {
            if (!TextUtils.isEmpty(Build.CPU_ABI2)) {
                return new String[]{Build.CPU_ABI, Build.CPU_ABI2};
            }
            return new String[]{Build.CPU_ABI};
        }
    }

    /**
     * 设备CPU类型名称
     *
     * @return CPU类型名称
     */
    public static String getPhoneCPUName() {
        return Build.CPU_ABI;
    }

    /**
     * 设备CPU最大频率
     *
     * @return CPU最大频率
     */
    public static String getPhoneCpuMaxFreq() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        return result.trim();
    }

    /**
     * 设备CPU最小频率
     *
     * @return CPU最小频率
     */
    public static String getPhoneCpuMinFreq() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        return result.trim();
    }

    /**
     * 设备CPU当前频率
     *
     * @return CPU当前频率
     */
    public static String getPhoneCpuCurrentFreq() {
        String result = "N/A";
        try {
            FileReader fr = new FileReader("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            result = text.trim();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 设备CPU名称
     *
     * @return CPU名称
     */
    public static String getPhoneCpuName() {
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            return array[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设备CPU数量
     *
     * @return CPU数量
     */
    public static int getPhoneCpuNumber() {
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                return Pattern.matches("cpu[0-9]", pathname.getName());
            }
        }
        try {
            File dir = new File("/sys/devices/system/cpu/");
            File[] files = dir.listFiles(new CpuFilter());
            return files.length;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    /**
     * 判断设备是否支持多点触控
     *
     * @param context 上下文
     * @return ture：支持 false：不支持
     */
    public static boolean isSupportMultiTouch(Context context) {
        PackageManager pm = context.getPackageManager();
        boolean isSupportMultiTouch = pm.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH);
        return isSupportMultiTouch;
    }

    /**
     * 获取蓝牙连接状态:必须要在配置文件中添加蓝牙权限
     *
     * @return 连接状态
     */
    public static String getBlueToothState() {
        BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bAdapter == null) {
            return "设备不支持蓝牙";
        }
        int state = bAdapter.getState();
        if (state== BluetoothAdapter.STATE_TURNING_OFF){
            return "蓝牙关闭中";
        }else if (state== BluetoothAdapter.STATE_TURNING_ON){
            return "蓝牙开启中";
        }else if (state== BluetoothAdapter.STATE_OFF){
            return "蓝牙关闭";
        }else if (state== BluetoothAdapter.STATE_ON){
            return "蓝牙开启";
        }else {
            return "未知";
        }
    }

    /**
     * 判断设备是否 root
     *
     * @return true：root过  false：未root
     */
    public static boolean isDeviceRooted() {
        String su = "su";
        String[] locations = {"/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/xbin/", "/data/local/bin/", "/data/local/"};
        for (String location : locations) {
            if (new File(location + su).exists()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查是否存在SD卡
     *
     * @return true：存在 false：不存在
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }
}