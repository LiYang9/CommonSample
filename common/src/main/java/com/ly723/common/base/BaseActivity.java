package com.ly723.common.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ly723.common.interfaces.ISaveData;
import com.ly723.common.utils.LogUtil;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 数据保存恢复，结构化代码
 * @Author LiYang
 */
public abstract class BaseActivity extends AppCompatActivity {

    public Activity mActivity;

    protected abstract int getLayoutId();

    public abstract void initViews();

    protected abstract void initListener();

    public abstract void initData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutID = getLayoutId();
        if (layoutID == 0) {
            LogUtil.error(new Exception("没有指定activity的布局"));
        } else {
            setContentView(layoutID);
        }
        mActivity = this;
        initData();
        initViews();
        initListener();
        if (savedInstanceState != null) {
            restoreData(savedInstanceState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            Class c = this.getClass();
            Field[] fields = c.getDeclaredFields();
            Field.setAccessible(fields, true);//private的filed会报错
            for (Field field : fields) {
                Annotation[] anns = field.getDeclaredAnnotations();
                for (Annotation ann : anns) {
                    if (ann instanceof ISaveData) {
                        try {
                            String fieldName = field.getName();
                            Object object = field.get(this);
                            if (object != null) {
                                if (object instanceof Byte) {
                                    outState.putByte(fieldName, (byte) object);
                                } else if (object instanceof Short) {
                                    outState.putShort(fieldName, (short) object);
                                } else if (object instanceof Integer) {
                                    outState.putInt(fieldName, (int) object);
                                } else if (object instanceof Long) {
                                    outState.putLong(fieldName, (long) object);
                                } else if (object instanceof Float) {
                                    outState.putFloat(fieldName, (float) object);
                                } else if (object instanceof Double) {
                                    outState.putDouble(fieldName, (double) object);
                                } else if (object instanceof Character) {
                                    outState.putChar(fieldName, (char) object);
                                } else if (object instanceof String) {
                                    outState.putString(fieldName, (String) object);
                                } else if (object instanceof Boolean) {
                                    outState.putBoolean(fieldName, (boolean) object);
                                } else if (object instanceof Parcelable) {
                                    outState.putParcelable(fieldName, (Parcelable) object);
                                } else if (object instanceof Serializable) {
                                    outState.putSerializable(fieldName, (Serializable) object);
                                } else if (object instanceof Bundle) {
                                    outState.putBundle(fieldName, (Bundle) object);
                                } else if (object instanceof byte[]) {
                                    outState.putByteArray(fieldName, (byte[]) object);
                                } else if (object instanceof short[]) {
                                    outState.putShortArray(fieldName, (short[]) object);
                                } else if (object instanceof int[]) {
                                    outState.putIntArray(fieldName, (int[]) object);
                                } else if (object instanceof long[]) {
                                    outState.putLongArray(fieldName, (long[]) object);
                                } else if (object instanceof float[]) {
                                    outState.putFloatArray(fieldName, (float[]) object);
                                } else if (object instanceof double[]) {
                                    outState.putDoubleArray(fieldName, (double[]) object);
                                } else if (object instanceof char[]) {
                                    outState.putCharArray(fieldName, (char[]) object);
                                } else if (object instanceof String[]) {
                                    outState.putStringArray(fieldName, (String[]) object);
                                } else if (object instanceof boolean[]) {
                                    outState.putBooleanArray(fieldName, (boolean[]) object);
                                } else if (object instanceof List) {
                                    List list = (List) object;
                                    Object o = list.get(0);
                                    if (o instanceof Integer) {
                                        outState.putIntegerArrayList(fieldName, (ArrayList<Integer>) list);
                                    } else if (o instanceof String) {
                                        outState.putStringArrayList(fieldName, (ArrayList<String>) object);
                                    } else if (o instanceof Parcelable) {
                                        outState.putParcelableArrayList(fieldName, (ArrayList<? extends Parcelable>) object);
                                    } else if (o instanceof CharSequence) {
                                        outState.putCharSequenceArrayList(fieldName, (ArrayList<CharSequence>) object);
                                    }
                                }
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            restoreData(savedInstanceState);
        }
    }

    private void restoreData(Bundle savedInstanceState) {
        Class c = this.getClass();
        Field[] fields = c.getDeclaredFields();
        Field.setAccessible(fields, true);
        for (Field field : fields) {
            Annotation[] anns = field.getDeclaredAnnotations();
            for (Annotation ann : anns) {
                if (ann instanceof ISaveData) {
                    try {
                        String fieldName = field.getName();
                        Object object = field.get(this);
                        if (object != null) {
                            if (object instanceof Byte) {
                                field.setByte(this, savedInstanceState.getByte(fieldName));
                            } else if (object instanceof Short) {
                                field.setShort(this, savedInstanceState.getShort(fieldName));
                            } else if (object instanceof Integer) {
                                field.setInt(this, savedInstanceState.getInt(fieldName));
                            } else if (object instanceof Long) {
                                field.setLong(this, savedInstanceState.getLong(fieldName));
                            } else if (object instanceof Float) {
                                field.setFloat(this, savedInstanceState.getFloat(fieldName));
                            } else if (object instanceof Double) {
                                field.setDouble(this, savedInstanceState.getDouble(fieldName));
                            } else if (object instanceof Character) {
                                field.setChar(this, savedInstanceState.getChar(fieldName));
                            } else if (object instanceof String) {
                                field.set(this, savedInstanceState.getString(fieldName));
                            } else if (object instanceof Boolean) {
                                field.setBoolean(this, savedInstanceState.getBoolean(fieldName));
                            } else if (object instanceof Parcelable) {
                                field.set(this, savedInstanceState.getParcelable(fieldName));
                            } else if (object instanceof Serializable) {
                                field.set(this, savedInstanceState.getSerializable(fieldName));
                            } else if (object instanceof Bundle) {
                                field.set(this, savedInstanceState.getBundle(fieldName));
                            } else if (object instanceof byte[]) {
                                field.set(this, savedInstanceState.getByteArray(fieldName));
                            } else if (object instanceof short[]) {
                                field.set(this, savedInstanceState.getShortArray(fieldName));
                            } else if (object instanceof int[]) {
                                field.set(this, savedInstanceState.getIntArray(fieldName));
                            } else if (object instanceof long[]) {
                                field.set(this, savedInstanceState.getLongArray(fieldName));
                            } else if (object instanceof float[]) {
                                field.set(this, savedInstanceState.getFloatArray(fieldName));
                            } else if (object instanceof double[]) {
                                field.set(this, savedInstanceState.getDoubleArray(fieldName));
                            } else if (object instanceof char[]) {
                                field.set(this, savedInstanceState.getCharArray(fieldName));
                            } else if (object instanceof String[]) {
                                field.set(this, savedInstanceState.getStringArray(fieldName));
                            } else if (object instanceof boolean[]) {
                                field.set(this, savedInstanceState.getBooleanArray(fieldName));
                            } else if (object instanceof List) {
                                List list = (List) object;
                                Object o = list.get(0);
                                if (o instanceof Integer) {
                                    field.set(this, savedInstanceState.getIntegerArrayList(fieldName));
                                } else if (o instanceof String) {
                                    field.set(this, savedInstanceState.getStringArrayList(fieldName));
                                } else if (o instanceof Parcelable) {
                                    field.set(this, savedInstanceState.getParcelableArrayList(fieldName));
                                } else if (o instanceof CharSequence) {
                                    field.set(this, savedInstanceState.getCharSequenceArrayList(fieldName));
                                }
                            }
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
