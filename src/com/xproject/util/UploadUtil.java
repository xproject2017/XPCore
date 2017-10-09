package com.xproject.util;

//import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by hay on 2017/1/16.
 */
public class UploadUtil {
    /**
     * 图片上传工具类
     * 方法名：upLoadImage
     * 时间：2016年4月15日-下午2:05:20
     *
     * @param request 需要上传的request对象
     * @param image   上传的图片数组
     * @param path1   路径
     * @param path2   文件夹名称
     * @return String   返回的数据
     */
    public String upLoadImage(HttpServletRequest request, MultipartFile image, String path1, String path2) {
        List<String> fileTypes = new ArrayList<String>();
        path1 += path2;  //拼接固定路径
        String path3 = new SimpleDateFormat("yyyyMMdd").format(new Date());//创建文件夹路径
        String savePath = "";
        fileTypes.add("jpg");
        fileTypes.add("png");
        fileTypes.add("pdf");
        fileTypes.add("xls");
        fileTypes.add("xlsx");
        // 保存文件
        if (!(image.getOriginalFilename() == null || "".equals(image.getOriginalFilename()))) {
                    /*
                     * 下面调用的方法，主要是用来检测上传的文件是否属于允许上传的类型范围内，及根据传入的路径名
                     * 自动创建文件夹和文件名，返回的File文件我们可以用来做其它的使用，如得到保存后的文件名路径等这里我就先不做多的介绍。
                     */
            File file = this.getFile(image, path1, path3, fileTypes);
            savePath = file.getName();
        }

        return "/" + path2 + "/" + path3 + "/" + savePath;
    }

    private File getFile(MultipartFile imgFile, String typeName, String brandName, List fileTypes) {
        String nowTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + new Random().nextInt(10); //重命名
        String fileName = imgFile.getOriginalFilename();
        //获取上传文件类型的扩展名,先得到.的位置，再截取从.的下一个位置到文件的最后，最后得到扩展名
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        //对扩展名进行小写转换
        ext = ext.toLowerCase();

        File file = null;
        if (fileTypes.contains(ext)) {                      //如果扩展名属于允许上传的类型，则创建文件
            fileName = nowTime + '.' + ext;
            file = this.creatFolder(typeName, brandName, fileName);
            try {
                imgFile.transferTo(file);                   //保存上传的文件
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    private File creatFolder(String typeName, String brandName, String fileName) {
        File file = null;
        typeName = typeName.replaceAll("/", "");               //去掉"/"
        typeName = typeName.replaceAll(" ", "");               //替换半角空格
        typeName = typeName.replaceAll(" ", "");               //替换全角空格

        brandName = brandName.replaceAll("/", "");             //去掉"/"
        brandName = brandName.replaceAll(" ", "");             //替换半角空格
        brandName = brandName.replaceAll(" ", "");             //替换全角空格

        File firstFolder = new File(typeName);         //一级文件夹
        if (firstFolder.exists()) {                             //如果一级文件夹存在，则检测二级文件夹
            File secondFolder = new File(firstFolder, brandName);
            if (secondFolder.exists()) {                        //如果二级文件夹也存在，则创建文件
                file = new File(secondFolder, fileName);
            } else {                                            //如果二级文件夹不存在，则创建二级文件夹
                secondFolder.mkdir();
                file = new File(secondFolder, fileName);        //创建完二级文件夹后，再合建文件
            }
        } else {                                                //如果一级不存在，则创建一级文件夹
            firstFolder.mkdir();
            File secondFolder = new File(firstFolder, brandName);
            if (secondFolder.exists()) {                        //如果二级文件夹也存在，则创建文件
                file = new File(secondFolder, fileName);
            } else {                                            //如果二级文件夹不存在，则创建二级文件夹
                secondFolder.mkdir();
                file = new File(secondFolder, fileName);
            }
        }
        return file;
    }

    public static FileInputStream getImageInput(Integer w, Integer h, File file, String path,String prefix) throws FileNotFoundException {
        if (w==null||w == 0){
            w = 1024;
        }
        if (h==null||h == 0 ){
            h = 768;
        }
        if(w > 0 && h > 0){
            File dir = new File(path+"/zip/");
            if (!dir.exists())
                dir.mkdirs();

            File smallfile= new File(path+"/zip/"+file.getName().replace(prefix,"")+"_"+w+"_"+h+prefix);
            if(!smallfile.exists()){
                try {
                    //keepAspectRatio(false) 不按照比例，指定大小进行缩放
                    Thumbnails.of(file).size(w, h).keepAspectRatio(false).toFile(smallfile);
                    Thumbnails.of(file).size(w, h).toFile(smallfile);
                }catch (Exception e){
                    smallfile = file;
                }
            }
            return new FileInputStream(smallfile);
        }else {
            return new FileInputStream(file);
        }
    }
    public static FileInputStream getImageInput(int w, int h, File file, String path) throws FileNotFoundException {
        if (w == 0){
            w = 1024;
        }
        if (h == 0){
            h = 768;
        }
        if(w > 0 && h > 0){
            File smallfile= new File(path+"_"+w+"_"+h+".jpg");
            if(!smallfile.exists()){
                try {
                    //keepAspectRatio(false) 不按照比例，指定大小进行缩放
                    //Thumbnails.of(file).size(w, h).keepAspectRatio(false).toFile(smallfile);
//                    Thumbnails.of(file).size(w, h).toFile(smallfile);
                }catch (Exception e){
                    smallfile = file;
                }
            }
            return new FileInputStream(smallfile);
        }else {
            return new FileInputStream(file);
        }
    }


    public static JSONArray setup(String pdfpath,String outDir){
        JSONArray jsonArray=new JSONArray();
        JSONObject   imagename=null;
        //String imagename = "";
        try{
            if(new File(pdfpath).exists()){
//                PDDocument doc=PDDocument.load(new FileInputStream(pdfpath));
//                PDFRenderer renderer = new PDFRenderer(doc);
//                int pageCount = doc.getNumberOfPages();
                Document document = new Document();
                document.setFile(pdfpath);
                float scale = 1.3f;
                float rotation = 0f;
                for(int i=0;i<document.getNumberOfPages();i++){
                    BufferedImage image = (BufferedImage)
                            document.getPageImage(i, GraphicsRenderingHints.SCREEN, Page.BOUNDARY_CROPBOX, rotation, scale);
//                    BufferedImage image = renderer.renderImageWithDPI(i, 168, ImageType.BINARY);
//                    BufferedImage image = renderer.renderImage(i, 2.5f);
                    String temp = "tmppdf" + StringUtil.generateString(5) +System.currentTimeMillis()+".jpg";
                    String outPath = outDir+temp;
                    ImageIO.write(image, "jpg", new File(outPath));
                    imagename=new JSONObject();
                    imagename.put(i+"",temp);
                    jsonArray.add(imagename);
                    if(i==4){
                        break;
                    }
                }
            }

        }catch(IOException ie){
            ie.printStackTrace();
        } catch (PDFSecurityException e) {
            e.printStackTrace();
        } catch (PDFException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }


    public static void copyfile(String sourcefile,String destfile)  // 使用FileInputStream和FileOutStream
    {
        try {
            File inFile = new File(sourcefile);
            FileInputStream fis = new FileInputStream(inFile);
            FileOutputStream fos = new FileOutputStream(destfile);
            fis.getChannel().transferTo(0, fis.getChannel().size(), fos.getChannel());
            fos.close();
            fis.close();
            inFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //拷贝不删除原文件
    public static void copyfilenotdel(String sourcefile,String destfile)  // 使用FileInputStream和FileOutStream
    {
        try {
            File inFile = new File(sourcefile);
            FileInputStream fis = new FileInputStream(inFile);
            FileOutputStream fos = new FileOutputStream(destfile);
            fis.getChannel().transferTo(0, fis.getChannel().size(), fos.getChannel());
            fos.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 删除整个文件夹
     * @param   path    被删除文件夹路径
     * @return 文件夹删除成功返回true，否则返回false
     */
    public static void deleteAllFilesOfDir(File path) {
        if (!path.exists())
            return;
        if (path.isFile()) {
            path.delete();
            return;
        }
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            deleteAllFilesOfDir(files[i]);
        }
        path.delete();
    }


    // 在 path中找filename相关的文件  并删除
    public static void delFile(File folder,final String filename){
        // {"piclist":[{"url":""}],"haspdf":"0","pdfurl":"","filename":[],"pdfname":""};
        /*
        商品图片  ~/p/pid/pvendorid/[prstypeid]_[index].jpg
        商品图片  ~/p/pid/pvendorid/[prstypeid]_[index]_WIDTH_HEIGHT.jpg
        中文标签样张：  ~/s/pid/pvendorid/sample[prstypeid]_[index].jpg
        中文标签样张：  ~/s/pid/pvendorid/sample[prstypeid]_[index]_WIDTH_HEIGHT.jpg
        管理体系及相关证书：  ~/r/pid/pvendorid/report[prstypeid]_[index].jpg
        管理体系及相关证书：  ~/r/pid/pvendorid/report[prstypeid]_[index]_WIDTH_HEIGHT.jpg

        批次原材料信息：  ~/b/pid/pvendorid/typeid/source[batchid]_[index].jpg
        批次原材料信息：  ~/b/pid/pvendorid/typeid/source[batchid]_[index]_WIDTH_HEIGHT.jpg
        批次企业检查报告：  ~/br/pid/pvendorid/typeid/inspect[batchid]_[index].jpg
        批次企业检查报告：  ~/br/pid/pvendorid/typeid/inspect[batchid]_[index]_WIDTH_HEIGHT.jpg
        预检验报告：  ~/third/pid/pvendorid/typeid/source[precheckid]_[index].jpg
        预检验报告：  ~/third/pid/pvendorid/typeid/source[precheckid]_[index]_WIDTH_HEIGHT.jpg
        检测报告：  ~/third/pid/pvendorid/inspectcode/source[inspectcode]_[index].jpg
        检测报告：  ~/third/pid/pvendorid/inspectcode/source[inspectcode]_[index]_WIDTH_HEIGHT.jpg
        合格证：   ~/ibr/pid/pvendorid/inspectcode/inspect[inspectcode]_[index].jpg
        合格证：   ~/ibr/pid/pvendorid/inspectcode/inspect[inspectcode]_[index]_WIDTH_HEIGHT.jpg
        */
        if (!folder.exists())
            folder.mkdirs();

        File[] subFolders = folder.listFiles(new FileFilter() {// 运用内部匿名类获得文件
            @Override
            public boolean accept(File pathname) {// 实现FileFilter类的accept方法
                if (pathname.isDirectory()
                        || (pathname.isFile() && pathname.getName().toLowerCase().contains(filename.toLowerCase())))// 目录或文件包含关键字
                    return true;
                return false;
            }
        });

        List<File> result = new ArrayList<File>();// 声明一个集合
        if (subFolders != null) {
            for (int i = 0; i < subFolders.length; i++) {// 循环显示文件夹或文件
                if (subFolders[i].isFile()) {// 如果是文件则将文件添加到结果列表中
                    result.add(subFolders[i]);
                } else {// 如果是文件夹，则递归调用本方法，然后把所有的文件加到结果列表中
                    File[] foldResult = searchFile(subFolders[i], filename);
                    for (int j = 0; j < foldResult.length; j++) {// 循环显示文件
                        result.add(foldResult[j]);// 文件保存到集合中
                    }
                }
            }

            for (File dfile : result) {
                if (dfile.isFile() && dfile.exists()) {
                    dfile.delete();
                }
            }
        }
    }

    // 在 path中找filename相关的文件
    public static  File[] searchFile(File folder,final String filename){
        // {"piclist":[{"url":""}],"haspdf":"0","pdfurl":"","filename":[],"pdfname":""};
        /*
        商品图片  ~/p/pid/pvendorid/[prstypeid]_[index].jpg
        商品图片  ~/p/pid/pvendorid/[prstypeid]_[index]_WIDTH_HEIGHT.jpg
        中文标签样张：  ~/s/pid/pvendorid/sample[prstypeid]_[index].jpg
        中文标签样张：  ~/s/pid/pvendorid/sample[prstypeid]_[index]_WIDTH_HEIGHT.jpg
        管理体系及相关证书：  ~/r/pid/pvendorid/report[prstypeid]_[index].jpg
        管理体系及相关证书：  ~/r/pid/pvendorid/report[prstypeid]_[index]_WIDTH_HEIGHT.jpg

        批次原材料信息：  ~/b/pid/pvendorid/typeid/source[batchid]_[index].jpg
        批次原材料信息：  ~/b/pid/pvendorid/typeid/source[batchid]_[index]_WIDTH_HEIGHT.jpg
        批次企业检查报告：  ~/br/pid/pvendorid/typeid/inspect[batchid]_[index].jpg
        批次企业检查报告：  ~/br/pid/pvendorid/typeid/inspect[batchid]_[index]_WIDTH_HEIGHT.jpg
        预检验报告：  ~/third/pid/pvendorid/typeid/source[precheckid]_[index].jpg
        预检验报告：  ~/third/pid/pvendorid/typeid/source[precheckid]_[index]_WIDTH_HEIGHT.jpg
        检测报告：  ~/third/pid/pvendorid/inspectcode/source[inspectcode]_[index].jpg
        检测报告：  ~/third/pid/pvendorid/inspectcode/source[inspectcode]_[index]_WIDTH_HEIGHT.jpg
        合格证：   ~/ibr/pid/pvendorid/inspectcode/inspect[inspectcode]_[index].jpg
        合格证：   ~/ibr/pid/pvendorid/inspectcode/inspect[inspectcode]_[index]_WIDTH_HEIGHT.jpg
        */
        if (!folder.exists())
            folder.mkdirs();

        File[] subFolders = folder.listFiles(new FileFilter() {// 运用内部匿名类获得文件
            @Override
            public boolean accept(File pathname) {// 实现FileFilter类的accept方法
                if (pathname.isDirectory()
                        || (pathname.isFile() && pathname.getName().toLowerCase().contains(filename.toLowerCase())))// 目录或文件包含关键字
                    return true;
                return false;
            }
        });

        List<File> result = new ArrayList<File>();// 声明一个集合
        for (int i = 0; i < subFolders.length; i++) {// 循环显示文件夹或文件
            if (subFolders[i].isFile()) {// 如果是文件则将文件添加到结果列表中
                result.add(subFolders[i]);
            } else {// 如果是文件夹，则递归调用本方法，然后把所有的文件加到结果列表中
//                File[] foldResult = searchFile(subFolders[i], filename);
//                for (int j = 0; j < foldResult.length; j++) {// 循环显示文件
//                    result.add(foldResult[j]);// 文件保存到集合中
//                }
            }
        }

        File files[] = new File[result.size()];// 声明文件数组，长度为集合的长度
        result.toArray(files);// 集合数组化
        Arrays.sort(files);
        return files;
    }
    // 在 path中找filename相关的文件
    public static JSONObject searchFile(String imgurl,String pdfurl,String path,final String filename) {
        // {"piclist":[{"url":""}],"haspdf":"0","pdfurl":"","filenamelist":[{filename:}],"pdfname":""};
        JSONObject base = new JSONObject();
        JSONArray piclist = new JSONArray();
        JSONArray filenamelist = new JSONArray();
        JSONArray sizelist = new JSONArray();
        JSONObject tmp;

        File[] result = searchFile(new File(path), filename);// 调用方法获得文件数组
        String fname="",pdfname="";
        int flag = 0;
        for (int i = 0; i < result.length; i++) {// 循环显示文件
            JSONObject size = new JSONObject();
            File file = result[i];
//            System.out.println(file.getAbsolutePath() + " " + file.getName());// 显示文件绝对路径
            fname = file.getName();
            if (fname.endsWith(".pdf") || fname.endsWith(".PDF")) {
                flag = 1;
                pdfname=fname;
            }else {
                size.put("w",getImgWidth(file));
                size.put("h",getImgHeight(file));
                sizelist.add(size);
                tmp = new JSONObject();
                tmp.put("url", imgurl);
                piclist.add(tmp);
                tmp.remove("url");

                tmp.put("filename", fname);
                filenamelist.add(tmp);
                tmp.remove("filename");
            }
        }
        base.put("piclist",piclist);
        base.put("filenamelist",filenamelist);
        base.put("sizelist",sizelist);

        if(flag==1){
            base.put("haspdf",1);
            base.put("pdfurl",pdfurl);
            base.put("pdfname",pdfname);
        }else {
            base.put("haspdf",0);
            base.put("pdfurl","");
            base.put("pdfname","");
        }
        return base;
    }

    /**
     * 上传多张图片，从临时目录转到正式目录
     * @param formalpath  正式目录
     * @param tmppath 临时目录
     * @param oldname 上传文件名
     * @param newname 存储文件名
     */
    public static void uploadMuchImg(String formalpath,String tmppath, String oldname, String oldpdfname, String newname){
        //上传多图
        if (oldname != null) {
            File dir = new File(formalpath);
            File[] oldfiles1 = UploadUtil.searchFile(dir,newname + "_");  //获取已经存在的文件，和新上传的文件做比较
            List<File> oldfiles = new ArrayList<>();
            for (int i = 0; i < oldfiles1.length; i ++){
                if (!oldfiles1[i].getName().endsWith("pdf")){
                    oldfiles.add(oldfiles1[i]);
                }
            }
            String zip = formalpath + "zip/";
            File zipdir = new File(zip);
            if (!zipdir.exists()) {
                zipdir.mkdirs();
            }
            //删除zip目录下的缓存
            UploadUtil.delFile(zipdir,newname + "_");
            //如果前端传空字符串，则删除所有图片
            if("".equals(oldname.trim())){
                UploadUtil.delFile(dir,newname + "_");
            }else {
                String[] oldnames = oldname.split(",");
                for (int i = 0; i < 5; i++) {
                    if (i < oldnames.length) {
                        String name = oldnames[i];  //上传的文件名
                        if (name.startsWith("tmp") && !name.startsWith("tmpproduct") && !name.startsWith("tmpsample") && !name.startsWith("tmpreport") && !name.startsWith("tmpsource") && !name.startsWith("tmpinspect") && !name.startsWith("tmpproduct")) {
                            String sourcefile = tmppath + name;
                            String destfile = formalpath + newname + "_" + i + name.substring(oldnames[i].lastIndexOf("."));
                            UploadUtil.delFile(dir,newname + "_" + i);
                            UploadUtil.copyfile(sourcefile, destfile);
                        } else if (oldfiles != null && i < oldfiles.size()) {
                            String oldfilename = oldfiles.get(i).getName();  //已存在的文件名
                            if (!oldfilename.equals(name)) {
                                //删除这个已经存在的文件
                                UploadUtil.delFile(dir, oldfilename);
                                //重命名上传的文件
                                UploadUtil.renameFile(formalpath, name, oldfilename);
                            }
                        }
                    }else{
                        if (oldfiles != null && i < oldfiles.size()){
                            String oldfilename = oldfiles.get(i).getName();  //已存在的文件名
                            //删除这个已经存在的文件
                            UploadUtil.delFile(dir, oldfilename);
                        }
                    }
                    //删除以前生产的单张图片文件名
                    if (oldfiles != null && i < oldfiles.size()) {
                        String oldfilename = oldfiles.get(0).getName();  //已存在的文件名
                        if (oldfilename.toUpperCase().startsWith((newname + "_.jpg").toUpperCase()) || oldfilename.toUpperCase().startsWith((newname + "_.png").toUpperCase())) {
                            //删除这个已经存在的文件
                            UploadUtil.delFile(dir, oldfilename);
                        }
                    }
                }
//                for (int i = 0; i < oldnames.length; i++) {
//                    if (oldnames[i].startsWith("tmp")){
//                        String sourcefile = tmppath + oldnames[i];
//                        String destfile = formalpath + newname + "_" + i + oldnames[i].substring(oldnames[i].lastIndexOf("."));
//                        UploadUtil.deleteFile(destfile);
//                        UploadUtil.copyfile(sourcefile, destfile);
//                    }
//                }
//                if(oldnames.length>=1){
//                    if(oldnames[0].startsWith("tmp")) {
//                        UploadUtil.delFile(dir,newname + "_");
//                    }
//                }

//                for (int i = oldnames.length; i < 5; i++) {
//                    UploadUtil.delFile(dir,newname + "_" + i);
//                }
            }
            //上传pdf
            if (oldpdfname != null) {
                //如果前端传空字符串，则删除所有pdf图片
                if("".equals(oldpdfname.trim())){
                    UploadUtil.delFile(dir,newname + "_.pdf");
                }else {
                    String sourcefile = tmppath + oldpdfname;
                    String destfile = formalpath + newname + "_.pdf";
                    UploadUtil.copyfile(sourcefile, destfile);
                }
            }
        }
    }

    /** *//**文件重命名
     * @param path 文件目录
     * @param oldname  原来的文件名
     * @param newname 新文件名
     */
    public static void renameFile(String path,String oldname,String newname){
        if(!oldname.equals(newname)){//新的文件名和以前文件名不同时,才有必要进行重命名
            File oldfile=new File(path+"/"+oldname);
            File newfile=new File(path+"/"+newname);
            if(!oldfile.exists()){
                return;//重命名文件不存在
            }
            if(newfile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名
                System.out.println(newname+"已经存在！");
            else{
                oldfile.renameTo(newfile);
            }
        }else{
            System.out.println("新文件名和旧文件名相同...");
        }
    }

    public static void main(String[] args) {
        String path="D:\\home\\webservice\\imageupload\\s\\1\\1";
        String filename="35";
        JSONObject base=searchFile("","",path, filename);// 调用方法获得文件数组
        System.out.println(base);
    }

    /**
     * 获取图片宽度
     * @param file  图片文件
     * @return 宽度
     */
    public static int getImgWidth(File file) {
        InputStream is = null;
        BufferedImage src = null;
        int ret = -1;
        try {
            is = new FileInputStream(file);
            src = javax.imageio.ImageIO.read(is);
            ret = src.getWidth(null); // 得到源图宽
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 获取图片高度
     * @param file  图片文件
     * @return 高度
     */
    public static int getImgHeight(File file) {
        InputStream is = null;
        BufferedImage src = null;
        int ret = -1;
        try {
            is = new FileInputStream(file);
            src = javax.imageio.ImageIO.read(is);
            ret = src.getHeight(null); // 得到源图高
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}
