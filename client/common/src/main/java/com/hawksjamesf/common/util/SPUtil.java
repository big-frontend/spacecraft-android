package com.hawksjamesf.common.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;
import java.util.Set;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.collection.SimpleArrayMap;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @since: Oct/29/2018  Mon
 */
public class SPUtil {
    public static final int USER = 0;
    public static final int GLOBAL = 1;
    private SharedPreferences sp;

    @IntDef({USER, GLOBAL})
    @Retention(value = RetentionPolicy.SOURCE)
    private @interface Level {
    }

    public static SPUtil getInstance(@Level int level) {
        if (level == USER) {
//            int profileId
            return getInstance(/*String.valueOf(profileId)*/);
        } else {
            return getInstance();
        }
    }

    private static SimpleArrayMap<String, SPUtil> SP_UTIL_MAP = new SimpleArrayMap<>();

    public static SPUtil getInstance() {
        return getInstance("");
    }

    public static SPUtil getInstance(String spName) {
        if (isSpace(spName)) spName = "spUtil";
        SPUtil spUtil = SP_UTIL_MAP.get(spName);
        if (spUtil == null) {
            spUtil = new SPUtil(spName);
            SP_UTIL_MAP.put(spName, spUtil);
        }
        return spUtil;
    }

    private static boolean isSpace(String spName) {
        if (spName == null) return true;
        for (int i = 0, len = spName.length(); i < len; ++i) {
            if (!Character.isWhitespace(spName.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private SPUtil(final String spName) {
        sp = Util.getApp().getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    /**
     * 默认采用apply操作
     *
     * @param key
     * @param value
     */
    public void put(@NonNull final String key, @NonNull String value) {
        put(key, value, false);
    }

    /**
     * @param key
     * @param value
     * @param isCommit
     * @return 存在三种情况，如果返回true/false则使用了commit操作；如果返回null则使用了apply操作
     */
    public Boolean put(@NonNull final String key, @NonNull final String value, final boolean isCommit) {
        Boolean commit = null;
        if (isCommit) {
            commit = sp.edit().putString(key, value).commit();

        } else {
            //asynchronous
            sp.edit().putString(key, value).apply();
        }

        return commit;
    }

    public String getString(@NonNull final String key) {
        return getString(key, "");
    }

    public String getString(@NonNull final String key, @NonNull final String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    public void put(@NonNull final String key, @NonNull final int value) {
        put(key, value, false);
    }

    /**
     * @param key
     * @param value
     * @param isCommit
     * @return 存在三种情况，如果返回true/false则使用了commit操作；如果返回null则使用了apply操作
     */
    public Boolean put(@NonNull final String key, @NonNull final int value, final boolean isCommit) {
        Boolean commit = null;
        if (isCommit) {
            commit = sp.edit().putInt(key, value).commit();

        } else {
            //asynchronous
            sp.edit().putInt(key, value).apply();
        }

        return commit;
    }

    public int getInt(@NonNull final String key, @NonNull int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    public void put(@NonNull String key, @NonNull final long value) {
        put(key, value, false);
    }

    /**
     * @param key
     * @param value
     * @param isCommit
     * @return 存在三种情况，如果返回true/false则使用了commit操作；如果返回null则使用了apply操作
     */
    public Boolean put(@NonNull final String key, @NonNull final long value, final boolean isCommit) {
        Boolean commit = null;
        if (isCommit) {
            commit = sp.edit().putLong(key, value).commit();

        } else {
            //asynchronous
            sp.edit().putLong(key, value).apply();
        }

        return commit;
    }

    public long getLong(@NonNull final String key, @NonNull final long defauleValue) {
        return sp.getLong(key, defauleValue);
    }


    public void put(@NonNull final String key, @NonNull final float value) {
        put(key, value, false);

    }

    /**
     * @param key
     * @param value
     * @param isCommit
     * @return 存在三种情况，如果返回true/false则使用了commit操作；如果返回null则使用了apply操作
     */
    public Boolean put(@NonNull final String key, @NonNull final float value, final boolean isCommit) {
        Boolean commit = null;
        if (isCommit) {
            commit = sp.edit().putFloat(key, value).commit();

        } else {
            //asynchronous
            sp.edit().putFloat(key, value).apply();
        }

        return commit;
    }

    public float getFloat(@NonNull final String key, @NonNull final float defauleValue) {
        return sp.getFloat(key, defauleValue);
    }

    public void put(@NonNull final String key, @NonNull final boolean value) {
        put(key, value, false);
    }

    /**
     * @param key
     * @param value
     * @param isCommit
     * @return 存在三种情况，如果返回true/false则使用了commit操作；如果返回null则使用了apply操作
     */
    public Boolean put(@NonNull final String key, @NonNull final boolean value, final boolean isCommit) {
        Boolean commit = null;
        if (isCommit) {
            commit = sp.edit().putBoolean(key, value).commit();

        } else {
            //asynchronous
            sp.edit().putBoolean(key, value).apply();
        }

        return commit;
    }

    public boolean getBoolean(@NonNull final String key, @NonNull final boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    public void put(@NonNull final String key, @NonNull final Set<String> values) {
        put(key, values, false);
    }

    /**
     * @param key
     * @param values
     * @param isCommit
     * @return 存在三种情况，如果返回true/false则使用了commit操作；如果返回null则使用了apply操作
     */
    public Boolean put(@NonNull final String key, @NonNull final Set<String> values, final boolean isCommit) {
        Boolean commit = null;
        if (isCommit) {
            commit = sp.edit().putStringSet(key, values).commit();

        } else {
            //asynchronous
            sp.edit().putStringSet(key, values).apply();
        }

        return commit;
    }

    /**
     * @param key
     * @param defaultValues:you can use {@code Collections<String>.emptySet()}
     * @return
     */
    public Set<String> getStringSet(@NonNull final String key, @NonNull final Set<String> defaultValues) {
        return sp.getStringSet(key, defaultValues);
    }

    public Map<String, ?> getAll() {
        return sp.getAll();
    }

    public boolean contains(@NonNull final String key) {
        return sp.contains(key);
    }


    public void remove(@NonNull final String key) {
        remove(key, false);
    }

    /**
     * @param key
     * @param isCommit
     * @return 存在三种情况，如果返回true/false则使用了commit操作；如果返回null则使用了apply操作
     */
    public Boolean remove(@NonNull final String key, final boolean isCommit) {
        Boolean commit = null;
        if (isCommit) {
            commit = sp.edit().remove(key).commit();
        } else {
            //asynchronous
            sp.edit().remove(key).apply();
        }
        return commit;
    }


    /**
     * @param isCommit
     * @return 存在三种情况，如果返回true/false则使用了commit操作；如果返回null则使用了apply操作
     */
    public Boolean clear(@NonNull final boolean isCommit) {
        Boolean commit = null;
        if (isCommit) {
            commit = sp.edit().clear().commit();
        } else {
            //asynchronous
            sp.edit().clear().apply();
        }
        return commit;
    }


}
