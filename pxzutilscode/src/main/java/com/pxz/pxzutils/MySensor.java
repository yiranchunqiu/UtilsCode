package com.pxz.pxzutils;

/**
 * 类说明：
 * 联系：530927342@qq.com
 *
 * @author peixianzhong
 * @date 2019/12/30 10:46
 */


import java.text.DecimalFormat;
import java.util.ArrayList;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;

/**
 * 传感器监听，用于实现传感器来获取数据，得到方位角，俯仰角等数据 通过传入的handler来实现数据的回调
 * {@link @handlerMessage MESSAGE_GUIHUAYUAN_ANALYZE_SUCCESS } 自己测试使用的方位角数据
 * {@link @handlerMessage MESSAGE_ANALYZE_SUCCESS }
 */
@SuppressWarnings("deprecation")
public class MySensor {
    private Context context;
    private float[] accelerometerValues;
    private float[] magneticFieldValues;
    private float[] orientationValues;
    private float[] collector = new float[3];// 采集实时数分析
    // 传感器
    private SensorManager mSensorManager;
    private Sensor accelerometer;
    private Sensor magnetic;
    private Sensor mOrientation;
    // private LinkedList<float[]> list = new LinkedList<float[]>();//
    // 缓冲数据加载过快的问题
    private boolean shooting;// 在拍照中，用差值计算方位角
    private AngleThread task;
    private static MySensorEventListener a;
    private static MySensorEventListener b;
    private static MySensorEventListener c;
    private static MySensor mySensor;

    // 1声明2.注册3.监听4.注销
    /**
     *
     * @param context 用于获取方位角的
     */
    public MySensor(Context context) {
        this.context = context;
        getInstance();//创建一个线程来计算方位角
    }

    public MySensor(Context context, Handler handler) {
        if (handler != null) {
            this.context = context;
            init();// 初始化
            regist();// 注册
            if (task == null) {
                task = new AngleThread(handler);
            }

        }
    }

    public AngleThread getInstance() {
        return task;
    }

    private void init() {// 初始化
        // 实例化传感器管理者
        mSensorManager = (SensorManager) context
                .getSystemService(Context.SENSOR_SERVICE);
        // 初始化加速度传感器
        accelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // 初始化地磁场传感器
        magnetic = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mOrientation = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

    }

    public void regist() {// 注册
        a = new MySensorEventListener();
        b = new MySensorEventListener();
        c = new MySensorEventListener();
        mSensorManager.registerListener(a, accelerometer,
                Sensor.TYPE_ACCELEROMETER);
        mSensorManager
                .registerListener(b, magnetic, Sensor.TYPE_MAGNETIC_FIELD);
        mSensorManager.registerListener(c, mOrientation,
                Sensor.TYPE_ORIENTATION);

    }

    public void unregist() {// 注销
        mSensorManager.unregisterListener(a);
        mSensorManager.unregisterListener(b);
        mSensorManager.unregisterListener(c);

    }

    class MySensorEventListener implements SensorEventListener {// 监听

        @Override
        public void onSensorChanged(SensorEvent event) {

            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                accelerometerValues = event.values;
            }
            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                magneticFieldValues = event.values;
            }
            if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
                orientationValues = event.values;

            }
            // if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            // float[] rotationMatrix = new float[16];
            // SensorManager.getRotationMatrixFromVector(rotationMatrix,
            // event.values);
            // determineOrientation(rotationMatrix);
            // }
            // 不能为空，不然刚开始的时候就会报空指针，因为三个值不是同事加载的，
            // 但是每加载一次都会去回调3次这个方法，所以这个加载的速度非常的快(三个传感器的数据是并行的)
            if (accelerometerValues != null && orientationValues != null
                    && magneticFieldValues != null) {
                calculateOrientation();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

    }

    // 计算方向
    private void calculateOrientation() {
        float[] values = new float[3];
        float[] R = new float[9];
        SensorManager.getRotationMatrix(R, null, accelerometerValues,
                magneticFieldValues);
        SensorManager.getOrientation(R, values);
        values[0] = (float) Math.toDegrees(values[0]);
        values[0] = values[0] < 0 ? values[0] + 360 : values[0];
        int cor = context.getResources().getConfiguration().orientation;
        //获取手机的方向
        // int c = ((Activity)context).getWindowManager().getDefaultDisplay()
        // .getRotation();// 界面的方向
        values[1] = (float) Math.toDegrees(values[1]);
        values[2] = (float) Math.toDegrees(values[2]);
        float angle3 = values[0];
        double AZIM = orientationValues[0];
        double TILT = orientationValues[1];
        double ROLL = orientationValues[2];
        // 确认传感器打开与否

        if (cor == 1) {// 0--竖屏这里就不考虑手机倒置的情况，cor=4，
            angle3 = (float) AZIM;
            TILT += 90;
            angleFuZhi(angle3, TILT, ROLL);

        } else { // 横屏

            if (ROLL > 60) {// 1--左向横屏
                angle3 += 90;
                if (angle3 > 360) {
                    angle3 -= 360;
                }
                TILT = 90 - ROLL;
                ROLL = orientationValues[1];

                angleFuZhi(angle3, TILT, ROLL);
            } else if (ROLL < -60) {// 3--右向横屏
                angle3 -= 90;
                if (angle3 < 0) {
                    angle3 += 360;
                }
                TILT = ROLL + 90;
                ROLL = -(orientationValues[1]);
                angleFuZhi(angle3, TILT, ROLL);
            }

        }
        if (shooting) {
            collector[0] = angle3;
            collector[1] = (float) TILT;
            collector[2] = (float) ROLL;
        }
        DecimalFormat df = new DecimalFormat("0.0");
        // 改写TITLE内容中的内容让用户实时看见
        String s = "";
//        if (TBinfoActivity.CurrentThis != null) {
//            s = TBinfoActivity.Appname + "(方位:" + Math.round(Consts.AZIM) + ")";
//            TBinfoActivity.CurrentThis.setTitle(s);
//        }
//        if (CameraCapture.CurrentThis != null) {
//            s = "方位角：" + df.format(Consts.AZIM) + "俯仰角："
//                    + df.format(Consts.TILT);
//        }

    }

    /**
     * 赋值给公共变量
     */
    private void angleFuZhi(double AZIM, double TILT, double ROLL) {
//在Consts类中定义三个公共变量，来进行直接赋值和取值
        Consts.AZIM = AZIM;
        Consts.TILT = TILT;
        Consts.ROLL = ROLL;
    }

    private void addData(ArrayList<float[]> angleData, float[] collector,
                         Handler handler) {

        if (angleData.size() > 2) {
            float x1_x2 = chazhi(angleData.get(angleData.size() - 2)[0],
                    collector[0]);
            float y1_y2 = chazhi(angleData.get(angleData.size() - 2)[0],
                    collector[0]);
            float z1_z2 = chazhi(angleData.get(angleData.size() - 2)[0],
                    collector[0]);
            // TODO 判断差直大小，在范围内添加，范围外置空所有数据重新添加
            if ((x1_x2 > 10 && x1_x2 != 0) || (y1_y2 > 10 && y1_y2 != 0)
                    || (z1_z2 > 10 && z1_z2 != 0)) {
                angleData.removeAll(angleData);
                handler.sendEmptyMessage(Consts.MESSAGE_ANALYZE_FILED);
            } else {
                angleData.add(collector);
            }
        } else {
            angleData.add(collector);
        }

    }

    private float chazhi(float f, float g) {// 计算差值，使计算值为正数
        return f > g ? f - g : g - f;
    }

    /**
     * @return通过差值计算方位角的线程
     */

    public class AngleThread extends Thread {//开启线程通过获取5组数据求差值

        private Handler handler;

        private AngleThread(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            angle(handler);

        }
    }

    private void angle(Handler handler) {//这里可以通过handler，实现回调
        shooting = true;
        ArrayList<float[]> angleData = new ArrayList<float[]>(10);
        float a = 0;
        float b = 0;
        float c = 0;

        while (angleData.size() != 10) {// 添加数据
            addData(angleData, collector, handler);
        }

        for (int i = 0; i < angleData.size(); i++) {
            a += angleData.get(i)[0];
            b += angleData.get(i)[1];
            c += angleData.get(i)[2];
        }
        a = a / angleData.size();
        b = b / angleData.size();
        c = c / angleData.size();

        float[] differenceValues = new float[] { a, b, c };
        angleData.removeAll(angleData);// 得出结果清空数据
        Message msg = new Message();
        msg.what = Consts.MESSAGE_ANALYZE_SUCCESS;
        msg.obj = differenceValues;
        handler.sendMessage(msg);
        shooting = false;

    }
    static class Consts{
        public static  int MESSAGE_ANALYZE_FILED=0;
        public static int MESSAGE_ANALYZE_SUCCESS=1;
        public static double AZIM;
        public static double TILT;
        public static double ROLL;
    }
}
