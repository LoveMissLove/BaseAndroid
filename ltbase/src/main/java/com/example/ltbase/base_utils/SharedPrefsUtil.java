package com.example.ltbase.base_utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/**
 * 作者：王健 on 2021/8/9
 * 邮箱：845040970@qq.com
 * 描述：
 */
public class SharedPrefsUtil {

    /**
     * 向SharedPreferences中写入int类型数据
     *
     * @param context 上下文环境
     * @param name 对应的xml文件名称
     * @param key 键
     * @param value 值
     */
    public static void putValue(Context context, String name, String key,
                                int value) {
        Editor sp = getEditor(context, name);
        sp.putInt(key, value);
        sp.commit();
    }

    /**
     * 向SharedPreferences中写入boolean类型的数据
     *
     * @param context 上下文环境
     * @param name 对应的xml文件名称
     * @param key 键
     * @param value 值
     */
    public static void putValue(Context context, String name, String key,
                                boolean value) {
        Editor sp = getEditor(context, name);
        sp.putBoolean(key, value);
        sp.commit();
    }

    /**
     * 向SharedPreferences中写入String类型的数据
     *
     * @param context 上下文环境
     * @param name 对应的xml文件名称
     * @param key 键
     * @param value 值
     */
    public static void putValue(Context context, String name, String key,
                                String value) {
        Editor sp = getEditor(context, name);
        sp.putString(key, value);
        sp.commit();
    }

    /**
     * 向SharedPreferences中写入float类型的数据
     *
     * @param context 上下文环境
     * @param name 对应的xml文件名称
     * @param key 键
     * @param value 值
     */
    public static void putValue(Context context, String name, String key,
                                float value) {
        Editor sp = getEditor(context, name);
        sp.putFloat(key, value);
        sp.commit();
    }

    /**
     * 向SharedPreferences中写入long类型的数据
     *
     * @param context 上下文环境
     * @param name 对应的xml文件名称
     * @param key 键
     * @param value 值
     */
    public static void putValue(Context context, String name, String key,
                                long value) {
        Editor sp = getEditor(context, name);
        sp.putLong(key, value);
        sp.commit();
    }

    /**
     * 从SharedPreferences中读取int类型的数据
     *
     * @param context 上下文环境
     * @param name 对应的xml文件名称
     * @param key 键
     * @param defValue 如果读取不成功则使用默认值
     * @return 返回读取的值
     */
    public static int getValue(Context context, String name, String key,
                               int defValue) {
        SharedPreferences sp = getSharedPreferences(context, name);
        int value = sp.getInt(key, defValue);
        return value;
    }

    /**
     * 从SharedPreferences中读取boolean类型的数据
     *
     * @param context 上下文环境
     * @param name 对应的xml文件名称
     * @param key 键
     * @param defValue 如果读取不成功则使用默认值
     * @return 返回读取的值
     */
    public static boolean getValue(Context context, String name, String key,
                                   boolean defValue) {
        SharedPreferences sp = getSharedPreferences(context, name);
        boolean value = sp.getBoolean(key, defValue);
        return value;
    }

    /**
     * 从SharedPreferences中读取String类型的数据
     *
     * @param context 上下文环境
     * @param name 对应的xml文件名称
     * @param key 键
     * @param defValue 如果读取不成功则使用默认值
     * @return 返回读取的值
     */
    public static String getValue(Context context, String name, String key,
                                  String defValue) {
        SharedPreferences sp = getSharedPreferences(context, name);
        String value = sp.getString(key, defValue);
        return value;
    }

    /**
     * 从SharedPreferences中读取float类型的数据
     *
     * @param context 上下文环境
     * @param name 对应的xml文件名称
     * @param key 键
     * @param defValue 如果读取不成功则使用默认值
     * @return 返回读取的值
     */
    public static float getValue(Context context, String name, String key,
                                 float defValue) {
        SharedPreferences sp = getSharedPreferences(context, name);
        float value = sp.getFloat(key, defValue);
        return value;
    }

    /**
     * 从SharedPreferences中读取long类型的数据
     *
     * @param context 上下文环境
     * @param name 对应的xml文件名称
     * @param key 键
     * @param defValue 如果读取不成功则使用默认值
     * @return 返回读取的值
     */
    public static long getValue(Context context, String name, String key,
                                long defValue) {
        SharedPreferences sp = getSharedPreferences(context, name);
        long value = sp.getLong(key, defValue);
        return value;
    }

    //获取Editor实例
    private static Editor getEditor(Context context, String name) {
        return getSharedPreferences(context, name).edit();
    }

    //获取SharedPreferences实例
    private static SharedPreferences getSharedPreferences(Context context, String name) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }


    /**
     * 清除所有数据
     */
    public static void clear(Context context, String name)
    {
        SharedPreferences sp = context.getSharedPreferences(name,
                Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    private static class SharedPreferencesCompat {
        private static final Method S_APPLY_METHOD = findApplyMethod();

        /**
         * 反射查找apply的方法
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         */
        public static void apply(Editor editor) {
            try {
                if (S_APPLY_METHOD != null) {
                    S_APPLY_METHOD.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }
}