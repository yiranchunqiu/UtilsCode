package com.pxz.pxzutils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

/**
 * 类说明：缓存sp
 * 联系：530927342@qq.com
 *
 * @author peixianzhong
 * @date 2019/6/5 16:54
 */
public class CacheSPUtil {
    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "CacheSPUtil";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context 上下文
     * @param key     key
     * @param object  值
     */
    public static void put(Context context, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context       上下文
     * @param key           key
     * @param defaultObject 值类型
     * @return 值
     */
    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return null;
    }

    /**
     * 根据对象保存到本地
     *
     * @param context 上下文
     * @param t       值
     * @param <T>     返回泛型
     * @throws Exception 异常
     */
    public static <T> void saveToLocal(Context context, T t) throws Exception {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        try {
            // 先得到POJO所定义的字段
            Field[] fields = t.getClass().getDeclaredFields();
            for (Field field : fields) {
                // 设置字段可访问
                field.setAccessible(true);
                // 得到字段的属性名
                String name = field.getName();
                Object obj = field.get(t);
                if (obj != null) {
                    if (field.getType().equals(Long.class) || field.getType().equals(long.class)) {
                        editor.putLong(name, field.getLong(t));
                    } else if (field.getType().equals(String.class)) {
                        editor.putString(name, field.get(t).toString());
                    } else if (field.getType().equals(Double.class) || field.getType().equals(double.class)) {
                        editor.putString(name, field.get(t).toString());
                    } else if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
                        editor.putInt(name, (Integer) field.get(t));
                    } else if (field.getType().equals(java.util.Date.class)) {
                        editor.putString(name, ((java.util.Date) (field.get(t))).getTime() + "");
                    } else if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
                        editor.putBoolean(name, field.getBoolean(t));
                    } else {
                        continue;
                    }
                }
            }
            editor.commit();
        } catch (Exception e) {
            throw new Exception("SharePreferenceUtils保存失败", e);
        }
    }

    /**
     * 从本地获取对象
     *
     * @param context 上下文
     * @param obj     数据
     * @param <T>     泛型
     * @return 值
     * @throws Exception 异常
     */
    public static <T> T loadFromLocal(Context context, T obj) throws Exception {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        try {
            // 首先得到所定义的字段
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                // 设置字段可访问
                field.setAccessible(true);
                // 得到字段的属性名
                String name = field.getName();
                if (field.getType().equals(Long.class) || field.getType().equals(long.class)) {
                    field.set(obj, sp.getLong(name, 0));
                } else if (field.getType().equals(String.class)) {
                    field.set(obj, sp.getString(name, null));
                } else if (field.getType().equals(Double.class) || field.getType().equals(double.class)) {
                    field.set(obj, Double.parseDouble(sp.getString(name, "0")));
                } else if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
                    field.set(obj, sp.getInt(name, 0));
                } else if (field.getType().equals(java.util.Date.class)) {
                    field.set(obj, new java.util.Date(Long.parseLong(sp.getString(name, "0"))));
                } else if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
                    field.set(obj, sp.getInt(name, 0));
                } else {
                    continue;
                }
            }
            return obj;
        } catch (Exception e) {
            throw new Exception("SPUtils读取本地失败", e);
        }
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context 上下文
     * @param key     key
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     *
     * @param context 上下文
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context 上下文
     * @param key     key
     * @return 是否存在
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context 上下文
     * @return 键值对
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         */
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("app");
            } catch (NoSuchMethodException e) {
            }
            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }

    /**
     * 将arrayList的内容保存到sp里
     *
     * @param context    上下文
     * @param searchList 数据
     * @param content    数据
     */
    public void saveArrayList(Context context, ArrayList searchList, String content) {
        // searchList里“无数据”
        if (searchList.size() == 0) {
            // 直接存
            searchList.add(content + "");
        } else {
            // searchList里“有数据”
            // 但不包含这条数据，直接在0的位置加上这条数据
            if (!searchList.contains(content)) {
                searchList.add(0, content + "");
            } else {
                // 包含了这条数据，就删除掉，并放在0位置或者原位置（自由选择）
                for (int i = 0; i < searchList.size(); i++) {
                    if (searchList.get(i).equals(content)) {
                        searchList.remove(i);
                        // 0或者i均可。
                        searchList.add(0, content + "");
                    }
                }
            }
        }
        // 定义SP.Editor和文件名称
        SharedPreferences.Editor editor = context.getSharedPreferences("SearchDataList", Context.MODE_PRIVATE).edit();
        // 将结果放入文件，关键是把集合大小放入，为了后面的取出判断大小
        editor.putInt("searchNums", searchList.size());
        for (int i = 0; i < searchList.size(); i++) {
            // 用条目+i,代表键，解决键的问题，也方便等一下取出，值也对应
            editor.putString("item_" + i, searchList.get(i) + "");
        }
        editor.commit();
    }

    /**
     * 读取sp里的数组
     *
     * @param context 上下文
     * @return 数组
     */
    public ArrayList<String> getSearchArrayList(Context context) {
        // 先定位到文件
        SharedPreferences preferDataList = context.getSharedPreferences("SearchDataList", Context.MODE_PRIVATE);
        // 定义一个集合等下返回结果
        ArrayList<String> list = new ArrayList<>();
        // 刚才存的大小此时派上用场了
        int searchNums = preferDataList.getInt("searchNums", 0);
        // 根据键获取到值
        for (int i = 0; i < searchNums; i++) {
            String searchItem = preferDataList.getString("item_" + i, null);
            // 放入新集合并返回
            list.add(searchItem);
        }
        return list;
    }
}