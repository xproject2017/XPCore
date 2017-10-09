package com.xproject.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
/**
 * 对指定文件或目录进行压缩和解压缩的工具类
 *
 * @author chenlm
 */
public class ZipUtil {

    public static void zip(List<File> files, String baseFolder, ZipOutputStream zos)
            throws Exception {
        FileInputStream fis = null;
        ZipEntry entry = null;
        int count = 0;
        for (File file : files) {
            entry = new ZipEntry(baseFolder + file.getName());
            zos.putNextEntry(entry);
            fis = new FileInputStream(file);
            byte[] buffer = new byte[2048];
            while ((count = fis.read(buffer, 0, buffer.length)) != -1) {
                zos.write(buffer, 0, count);
            }
        }
        fis.close();
    }
}