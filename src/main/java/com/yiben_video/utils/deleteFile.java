package com.yiben_video.utils;

import java.io.File;

/**
 * @author 王飞
 * @create 2021/7/5 9:48
 * @emai 936886169@qq.com
 */
public class deleteFile {
    public static boolean deleteFile(File dirFile) {
        // 如果dir对应的文件不存在，则退出
        if (!dirFile.exists()) {
            return false;
        }

        if (dirFile.isFile()) {
            return dirFile.delete();
        } else {

            for (File file : dirFile.listFiles()) {
                deleteFile(file);
            }
        }

        return dirFile.delete();
    }

    public static void main(String[] args) {
        File file = new File("C:\\Users\\王飞\\Downloads\\yiben.com_video");
        deleteFile(file);
    }
}
