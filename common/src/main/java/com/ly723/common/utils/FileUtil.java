package com.ly723.common.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * @Description 文件工具类
 * @Author LiYang
 */
public class FileUtil {

    private static final String DIR_PUBLIC = "common";

    //程序存储的子目录
    //TODO add new sub data dir position
    public static final String SUB_DIR_DATA_IMGS = "imgs";
    public static final String SUB_DIR_DATA_LOGS = "logs";
    public static final String SUB_DIR_DATA_DOWNLOADS = "downloads";


    //程序缓存子目录
    //TODO add new sub cache dir position
    public static final String SUB_DIR_CACHE_IMGS = "imgs";


    public static final int TYPE_PUBLIC = 723;
    public static final int TYPE_PRIVATE = 99;

    /**
     * 获取程序保存数据目录的绝对路径，结尾包含"/"（不存在则创建）
     * 如：
     * "/storage/emulated/0/Android/data/com.ly723.common/files/"
     *
     * @param type   私有==TYPE_PRIVATE or 共享==TYPE_PUBLIC
     * @param subDir 想要创建的子目录 或 已存在的目录 建议传值FileUtil下的DIR目录，没有则自己创建，便于管理
     */
    public String getDirFilePath(int type, String subDir) {
        return handlerDir(getDirFile(type, subDir).getAbsolutePath());
    }

    /**
     * 获取程序保存数据的目录（不存在则创建）
     *
     * @param type   私有==TYPE_PRIVATE or 共享==TYPE_PUBLIC
     * @param subDir 想要创建的子目录 或 已存在的目录 建议传值FileUtil下的DIR目录，没有则自己创建，便于管理
     */
    public File getDirFile(int type, String subDir) {
        File dataDirFile = null;
        switch (type) {
            case TYPE_PRIVATE:
                if (sdcardCanBeUsed()) {//sd卡可用 SDCard/Android/data/你的应用的包名/files/ 目录
                    dataDirFile = mContext.getExternalFilesDir(subDir);
                } else {//内部存储/data/data
                    String dirPath = handlerDir(mContext.getFilesDir().getAbsolutePath()).concat(subDir);
                    dataDirFile = getDirFile(dirPath);
                }
                break;
            case TYPE_PUBLIC:
                if (sdcardCanBeUsed()) {
                    dataDirFile = getDirFile(handlerDir(sDirPublic).concat(subDir));
                } else {
                    LogUtil.info("内置和外置sd卡都不可用");
                }
                break;
            default:
                LogUtil.error("没有指定文件位置是私有还是公有");
                return null;
        }
        return dataDirFile;
    }

    /**
     * 获取文件（不存在则创建）
     *
     * @param type       文件存储位置 TYPE_PUBLIC==文件共享位置  TYPE_PRIVATE==私有路径
     * @param subDirPath 子目录
     * @param fileName   文件名
     */
    public File getFile(int type, String subDirPath, String fileName) {
        String dirFilePath = getDirFilePath(type, subDirPath);
        return getFile(dirFilePath, fileName);
    }

    /**
     * 获取文件（不存在则创建）
     *
     * @param dirPath  文件存储目录
     * @param fileName 文件名
     */
    public File getFile(String dirPath, String fileName) {
        String path = handlerDir(dirPath).concat(fileName);
        File newFile;
        newFile = new File(path);
        if (!newFile.exists()) {
            try {
                if (newFile.createNewFile()) {
                    LogUtil.info("文件 ".concat(path).concat(" 创建成功"));
                } else {
                    LogUtil.info("文件 ".concat(path).concat(" 已存在"));
                }
            } catch (IOException e) {
                LogUtil.error(e);
                e.printStackTrace();
            }
        }
        return newFile;
    }

    /**
     * 获取文件的绝对路径（不存在则创建）
     *
     * @param dirPath  文件存储目录
     * @param fileName 文件名
     */
    public String getFilePath(String dirPath, String fileName) {
        return getFile(dirPath, fileName).getAbsolutePath();
    }

    /**
     * 获取文件绝对路径（不存在则创建）
     *
     * @param type       文件存储位置 TYPE_PUBLIC==文件共享位置  TYPE_PRIVATE==私有路径
     * @param subDirPath 子目录
     * @param fileName   文件名
     */
    public String getFilePath(int type, String subDirPath, String fileName) {
        return getFile(type, subDirPath, fileName).getAbsolutePath();
    }

    private String handlerDir(String dir) {
        if (dir.endsWith(File.separator)) {//路径分隔符
            return dir;
        } else {
            return dir.concat(File.separator);
        }
    }

    /**
     * 获取程序缓存目录
     */
    public File getCacheDirFile(String subDir) {
        String path = mContext.getExternalCacheDir().getAbsolutePath().concat(File.separator).concat(subDir);
        return getDirFile(path);
    }

    /**
     * 获取程序缓存目录绝对路径，结尾包含"/"
     */
    public String getCacheDirFilePath(String subDir) {
        return handlerDir(getCacheDirFile(subDir).getAbsolutePath());
    }

    /**
     * 获取路径为dirPath的文件目录（不存在则创建）
     *
     * @param dirPath 文件目录
     */
    public File getDirFile(String dirPath) {
        File dirFile = new File(dirPath);
        if (dirFile.mkdirs()) {
            LogUtil.info("目录 ".concat(dirPath).concat(" 创建成功"));
        } else {
            LogUtil.info("目录 ".concat(dirPath).concat(" 已存在"));
        }
        return dirFile;
    }

    /**
     * 获取路径为dirPath的文件目录的绝对路径，包含"/"（不存在则创建）
     *
     * @param dirPath 文件目录
     */
    public String getDirFilePath(String dirPath) {
        return getDirFile(dirPath).getAbsolutePath();
    }

    /**
     * SD卡是否可用
     */
    public boolean sdcardCanBeUsed() {
        //SD卡存在或者SD卡不可被移除
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable();
    }

    private static FileUtil sFileUtil;
    private Context mContext;
    private static String sDirPublic;

    private FileUtil(Context context) {
        mContext = context;
        if (sdcardCanBeUsed()) {
            sDirPublic = handlerDir(Environment.getExternalStorageDirectory().getAbsolutePath()).concat(DIR_PUBLIC);
        } else {
            LogUtil.info("内置和外置sd卡都不可用");
        }
    }

    public static FileUtil getInstance(Context context) {
        if (sFileUtil == null) {
            sFileUtil = new FileUtil(context);
        }
        return sFileUtil;
    }

    public static boolean verifyExist(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }
}
