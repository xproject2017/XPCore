package com.xproject.util;

import java.io.*;
import java.util.Date;

/**
 * Created by Fiona on 2016/11/9.
 */
public class CAFileUtils {
    public static void modifyFileContent(File file,String target,String newContent){
        try {
            System.out.println("modifyFileContent-------start time:"+DateUtil.convertDate2String((new Date()),DateUtil.DATE_FORMAT));
            InputStream is = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, "utf-8"));

            String filename = file.getName();
            // tmpfile为缓存文件，代码运行完毕后此文件将重命名为源文件名字。
            File tmpfile = new File(file.getParentFile().getAbsolutePath()
                    + "\\" + filename+".tmp");

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tmpfile),"utf-8"));

            boolean flag = false;
            String str = null;
            while (true) {
                str = reader.readLine();

                if (str == null)
                    break;

                if (str.contains(target)) {
                    String newStr = str.replaceAll(target,newContent);
                    writer.write(newStr + "\n");

                    flag = true;
                } else
                    writer.write(str + "\n");
            }

            is.close();

            writer.flush();
            writer.close();

            if (flag) {
                file.delete();
                tmpfile.renameTo(new File(file.getAbsolutePath()));
            } else
                tmpfile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("modifyFileContent-------end time:"+DateUtil.convertDate2String((new Date()),DateUtil.DATE_FORMAT));
    }
}
