package com.ly723.common.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 版本
 */
public class VersionUtil {
    private VersionUtil() {
    }

    /**
     * 获取APP版本名称和版本号
     *
     * @return 0-name 1-code
     */
    public static String[] getVersionInfo(Context context) {
        String[] infos = new String[2];
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            infos[0] = pi.versionName;
            infos[1] = String.valueOf(pi.versionCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return infos;
    }

    /**
     * 检查是否有新版本
     *
     * @param netVersion 网络获取的版本
     * @return true=有新版本 false=本机版本是最新的
     */
    public boolean checkVersion(Context context, String netVersion) {
        String[] vs = getVersionInfo(context)[0].split(".");
        String[] nvs = netVersion.split(".");
        int vl = vs.length;
        int nvl = nvs.length;
        if (nvl > vl) {
            return true;
        } else if (nvl == vl) {
            int v;
            int nv;
            for (int i = 0; i < vl; i++) {
                v = Integer.parseInt(vs[i]);
                nv = Integer.parseInt(nvs[i]);
                if (nv > v) {
                    return true;
                }
            }
        }
        return false;
    }
}