package com.xproject.util;


import com.xproject.util.id.IDUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 移动图片
 */
public class ImageUpLoadUtil {

    //删除多商品报检中的图片
    public static void deleteInspectImage(String prefix,Long vendorID,String inspectCode,String imageuploadpath ){
        try{
            //清理文件
            String path = imageuploadpath + "/" + vendorID;
            if ("third".equals(prefix)){
                path += "/third"+ "/" +inspectCode;
            }else if ("qualify".equals(prefix)) {
                path += "/ibr/" +inspectCode;
            }else {
                path += "/" +prefix+"/" +inspectCode;
            }
            File dir = new File(path);
            if(dir.exists()) {
                File[] files = dir.listFiles();
                if (files != null && files.length > 0){
                    for (File f : files) {
                        f.delete();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //复制多商品报检中的图片
    public static void fileCopyInspectUpload(String prefix,String inspectCode,Long vendorId,String imageuploadpath,String fname,String pdfname){
        try{
            String path = imageuploadpath + "/" + vendorId;
            if ("third".equals(prefix)){
                path = path + "/third"+ "/" +inspectCode;
            }else if ("qualify".equals(prefix)) {
                path = path + "/ibr/" +inspectCode;
            }else{
                path = path + "/"+prefix+"/" +inspectCode;
            }
            String filename[] = fname.split(",");
            File dir = new File(path);
            if(dir.exists()){
                File[] files = dir.listFiles();
                if(files != null && files.length > 0) {
                    for (File f : files) {
                        if (f.isFile() && f.getName().endsWith(".jpg")) {
                            if (prefix.equals("third") && f.getName().startsWith("source")) {
                                deleteNoExistFile("source", filename, f);
                            } else if (prefix.equals("qualify") && f.getName().startsWith("inspect")) {
                                deleteNoExistFile("inspect", filename, f);
                            }else if (f.getName().startsWith(prefix)) {
                                deleteNoExistFile(prefix, filename, f);
                            }
                        }
                    }
                }
            }else{
                dir.mkdirs();
            }

            String temppref = prefix;
            if(temppref.equals("third")){
                temppref = "source";
            }else if(temppref.equals("qualify")){
                temppref = "inspect";
            }
            if(null!=pdfname && !pdfname.equals("")){
                if(!pdfname.equals(inspectCode+".pdf")){
                    File pdffile = new File(path + "/" + temppref + inspectCode + ".pdf");
                    if(pdffile.exists()){
                        pdffile.delete();
                    }
                    //复制pdf文件
                    File inFile = new File(imageuploadpath + "/tmp/" + pdfname);
                    FileInputStream fis = new FileInputStream(inFile);
                    FileOutputStream fos = new FileOutputStream(path + "/" + temppref + inspectCode + ".pdf");
                    fis.getChannel().transferTo(0, fis.getChannel().size(), fos.getChannel());
                    fos.close();
                    fis.close();
                    inFile.delete();
                }
            }
            //从tmp中复制文件到目录下
            for(int i=0;i<filename.length;i++){
                if(!filename[i].equals("")){
                    if(filename[i].split("_").length==1) {
                        copyfile(imageuploadpath,path,filename[i],"",inspectCode,temppref);
                        copyfile(imageuploadpath,path,filename[i]+"_300_200","_300_200",inspectCode,temppref);
                        copyfile(imageuploadpath,path,filename[i]+"_50_50","_50_50",inspectCode,temppref);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //复制第三方评估报告的图片和pdf
    public static void fileCopyAssessUpload(String prefix,Long applyId,Long vendorId,String imageuploadpath,String fname,String pdfname){
        try{
            String path = imageuploadpath + "/" + vendorId;
            if ("assess".equals(prefix)){
                path = path + "/assess"+ "/" +applyId;
            }
            String filename[] = fname.split(",");
            File dir = new File(path);
            if(dir.exists()){
                File[] files = dir.listFiles();
                if(files != null && files.length > 0) {
                    for (File f : files) {
                        if (f.isFile() && f.getName().endsWith(".jpg")) {
                            if (prefix.equals("assess") && f.getName().startsWith("assess")) {
                                deleteNoExistFile(prefix, filename, f);
                            }
                        }
                    }
                }

            }else{
                dir.mkdirs();
            }
            if(null!=pdfname && !pdfname.equals("")){
                if(!pdfname.equals(applyId+".pdf")){
                    File pdffile = new File(path + "/" + prefix + applyId + ".pdf");
                    if(pdffile.exists()){
                        pdffile.delete();
                    }
                    //复制pdf文件
                    File inFile = new File(imageuploadpath + "/tmp/" + pdfname);
                    FileInputStream fis = new FileInputStream(inFile);
                    FileOutputStream fos = new FileOutputStream(path + "/" + prefix + applyId + ".pdf");
                    fis.getChannel().transferTo(0, fis.getChannel().size(), fos.getChannel());
                    fos.close();
                    fis.close();
                    inFile.delete();
                }
            }
            //从tmp中复制文件到目录下
            for(int i=0;i<filename.length;i++){
                if(!filename[i].equals("")){
                    if(filename[i].split("_").length==1) {
                        copyfile(imageuploadpath,path,filename[i],"",applyId.toString(),prefix);
                        copyfile(imageuploadpath,path,filename[i]+"_300_200","_300_200",applyId.toString(),prefix);
                        copyfile(imageuploadpath,path,filename[i]+"_50_50","_50_50",applyId.toString(),prefix);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void batchSplitCopy(String prefix,Long batchid,Long addbatchid,String imageuploadpath,Long typeid){
        Long vendorId = IDUtil.getVendorID_Dec(typeid);
        String path = imageuploadpath + "/" + vendorId + "/" + typeid;
        if ("source".equals(prefix)){
            path += "/b"+ "/";
        }else if ("inspect".equals(prefix)) {
            path += "/br"+ "/";
        }
        File dir = new File(path+batchid);
        if(dir.exists()) {
            File addfile = new File(path+addbatchid);
            addfile.mkdirs();
            File[] files = dir.listFiles();
            if (files != null && files.length > 0) {
                for (File f : files) {
                    try{
                        FileInputStream fis = new FileInputStream(f);
                        String fname = f.getName().substring(0,f.getName().lastIndexOf(".")).split("_")[0];
                        String addfname = f.getName().replace(fname,prefix+addbatchid);
                        FileOutputStream fos = new FileOutputStream(path+addbatchid+"/"+addfname);
                        fis.getChannel().transferTo(0, fis.getChannel().size(), fos.getChannel());
                        fos.close();
                        fis.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    //复制批次信息中的图片
    public static void filecopy2UploadPath(String prefix,Long batchid,Long typeid,String imageuploadpath,String fname,String pdfname){
        Long vendorId = IDUtil.getVendorID_Dec(typeid);
        if (vendorId == 0){
            vendorId = batchid;
        }
        try{
            String path = imageuploadpath + "/" + vendorId + "/" + typeid;
            if ("source".equals(prefix)){
                path += "/b"+ "/" +batchid;
            }else if ("inspect".equals(prefix)) {
                path += "/br"+ "/" +batchid;
            }else if ("product".equals(prefix)){
                path +=  "/p"; //商品图片
            }else if ("sample".equals(prefix)) {
                path +=  "/s"; //中文标签样张
            }else if ("report".equals(prefix)) {
                path += "/r"; //管理体系及相关证书
            }else if ("csource".equals(prefix)){
                path += "/cb"+ "/" +batchid;//草稿
            }else if ("cinspect".equals(prefix)) {
                path += "/cbr"+ "/" +batchid;//草稿
            }
            String filename[] = fname.split(",");
            File dir = new File(path);
            if(dir.exists()){
                File[] files = dir.listFiles();
                if(files != null && files.length > 0) {
                    for (File f : files) {
                        if (f.isFile() && f.getName().endsWith(".jpg")) {
                            if (prefix.equals("product")) {
                                deleteNoExistFile("", filename, f);
                            }else {
                                deleteNoExistFile(prefix, filename, f);
                            }
                        }
                    }
                }
            }else{
                dir.mkdirs();
            }

            if(null!=pdfname && !pdfname.equals("")){
                if(prefix.equals("source") || prefix.equals("inspect") || prefix.equals("csource") || prefix.equals("cinspect")){
                    if(!pdfname.equals(batchid+".pdf")){
                        File pdffile = new File(path + "/" + prefix + batchid + ".pdf");
                        if(pdffile.exists()){
                            pdffile.delete();
                        }
                        //复制pdf文件
                        File inFile = new File(imageuploadpath + "/tmp/" + pdfname);
                        FileInputStream fis = new FileInputStream(inFile);
                        FileOutputStream fos = new FileOutputStream(path + "/" + prefix + batchid + ".pdf");
                        fis.getChannel().transferTo(0, fis.getChannel().size(), fos.getChannel());
                        fos.close();
                        fis.close();
                        inFile.delete();
                    }
                }else if(prefix.equals("product")){
                    if(!pdfname.equals(typeid+".pdf")){
                        File pdffile = new File(path + "/"  + typeid + ".pdf");
                        if(pdffile.exists()){
                            pdffile.delete();
                        }
                        //复制pdf文件
                        File inFile = new File(imageuploadpath + "/tmp/" + pdfname);
                        FileInputStream fis = new FileInputStream(inFile);
                        FileOutputStream fos = new FileOutputStream(path + "/" + typeid + ".pdf");
                        fis.getChannel().transferTo(0, fis.getChannel().size(), fos.getChannel());
                        fos.close();
                        fis.close();
                        inFile.delete();
                    }
                }
            }else{
                String pdffilename = path;
                if(prefix.equals("source") || prefix.equals("inspect") || prefix.equals("csource") || prefix.equals("cinspect")){
                    pdffilename += "/" + prefix + batchid + ".pdf";
                    File pdffile = new File(pdffilename);
                    if(pdffile.exists()){
                        pdffile.delete();
                    }
                }else if(prefix.equals("product")){
                    pdffilename += "/"  + typeid + ".pdf";
                    File pdffile = new File(pdffilename);
                    if(pdffile.exists()){
                        pdffile.delete();
                    }
                }
            }

            //从tmp中复制文件到目录下
            if(prefix.equals("report") || prefix.equals("sample")){
                if(!fname.equals(typeid.toString())) {
                    copyManagerImage(prefix, path, fname, "", imageuploadpath, typeid);
                    copyManagerImage(prefix, path, fname, "_300_200", imageuploadpath, typeid);
                }
            }else{
                for(int i=0;i<filename.length;i++){
                    if(!filename[i].equals("")){
                        if(filename[i].split("_").length==1 && (prefix.equals("source") || prefix.equals("inspect") || prefix.equals("csource") || prefix.equals("cinspect"))) {
                            copyfile(imageuploadpath,path,filename[i],"",batchid.toString(),prefix);
                            copyfile(imageuploadpath,path,filename[i]+"_300_200","_300_200",batchid.toString(),prefix);
                            copyfile(imageuploadpath,path,filename[i]+"_50_50","_50_50",batchid.toString(),prefix);
                        }else if(filename[i].split("_").length==1 && prefix.equals("product")){
                            copyfile(imageuploadpath,path,filename[i],"",typeid.toString(),"");
                            copyfile(imageuploadpath,path,filename[i]+"_300_200","_300_200",typeid.toString(),"");
                            copyfile(imageuploadpath,path,filename[i]+"_50_50","_50_50",typeid.toString(),"");
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //复制批次信息中的图片
    public static void filecopy2UploadPath(String prefix,Long batchid,Long typeid,String imageuploadpath,String fname,String pdfname,int applystatus){
        Long vendorId = 0l;
        if (applystatus==-1){
            vendorId = batchid;
        }else {
            vendorId = IDUtil.getVendorID_Dec(typeid);
            if (vendorId == 0) {
                vendorId = batchid;
            }
        }
        try{
            String path = imageuploadpath + "/" + vendorId + "/" + typeid;
            if ("source".equals(prefix)){
                path += "/b"+ "/" +batchid;
            }else if ("inspect".equals(prefix)) {
                path += "/br"+ "/" +batchid;
            }else if ("product".equals(prefix)){
                path +=  "/p"; //商品图片
            }else if ("sample".equals(prefix)) {
                path +=  "/s"; //中文标签样张
            }else if ("report".equals(prefix)) {
                path += "/r"; //管理体系及相关证书
            }else if ("csource".equals(prefix)){
                path += "/cb"+ "/" +batchid;//草稿
            }else if ("cinspect".equals(prefix)) {
                path += "/cbr"+ "/" +batchid;//草稿
            }
            String filename[] = fname.split(",");
            File dir = new File(path);
            if(dir.exists()){
                File[] files = dir.listFiles();
                if(files != null && files.length > 0) {
                    for (File f : files) {
                        if (f.isFile() && f.getName().endsWith(".jpg")) {
                            if (prefix.equals("product")) {
                                deleteNoExistFile("", filename, f);
                            }else {
                                deleteNoExistFile(prefix, filename, f);
                            }
                        }
                    }
                }
            }else{
                dir.mkdirs();
            }

            if(null!=pdfname && !pdfname.equals("")){
                if(prefix.equals("source") || prefix.equals("inspect") || prefix.equals("csource") || prefix.equals("cinspect")){
                    if(!pdfname.equals(batchid+".pdf")){
                        File pdffile = new File(path + "/" + prefix + batchid + ".pdf");
                        if(pdffile.exists()){
                            pdffile.delete();
                        }
                        //复制pdf文件
                        File inFile = new File(imageuploadpath + "/tmp/" + pdfname);
                        FileInputStream fis = new FileInputStream(inFile);
                        FileOutputStream fos = new FileOutputStream(path + "/" + prefix + batchid + ".pdf");
                        fis.getChannel().transferTo(0, fis.getChannel().size(), fos.getChannel());
                        fos.close();
                        fis.close();
                        inFile.delete();
                    }
                }else if(prefix.equals("product")){
                    if(!pdfname.equals(typeid+".pdf")){
                        File pdffile = new File(path + "/"  + typeid + ".pdf");
                        if(pdffile.exists()){
                            pdffile.delete();
                        }
                        //复制pdf文件
                        File inFile = new File(imageuploadpath + "/tmp/" + pdfname);
                        FileInputStream fis = new FileInputStream(inFile);
                        FileOutputStream fos = new FileOutputStream(path + "/" + typeid + ".pdf");
                        fis.getChannel().transferTo(0, fis.getChannel().size(), fos.getChannel());
                        fos.close();
                        fis.close();
                        inFile.delete();
                    }
                }
            }else{
                String pdffilename = path;
                if(prefix.equals("source") || prefix.equals("inspect") || prefix.equals("csource") || prefix.equals("cinspect")){
                    pdffilename += "/" + prefix + batchid + ".pdf";
                    File pdffile = new File(pdffilename);
                    if(pdffile.exists()){
                        pdffile.delete();
                    }
                }else if(prefix.equals("product")){
                    pdffilename += "/"  + typeid + ".pdf";
                    File pdffile = new File(pdffilename);
                    if(pdffile.exists()){
                        pdffile.delete();
                    }
                }
            }

            //从tmp中复制文件到目录下
            if(prefix.equals("report") || prefix.equals("sample")){
                if(!fname.equals(typeid.toString())) {
                    copyManagerImage(prefix, path, fname, "", imageuploadpath, typeid);
                    copyManagerImage(prefix, path, fname, "_300_200", imageuploadpath, typeid);
                }
            }else{
                for(int i=0;i<filename.length;i++){
                    if(!filename[i].equals("")){
                        if(filename[i].split("_").length==1 && (prefix.equals("source") || prefix.equals("inspect") || prefix.equals("csource") || prefix.equals("cinspect"))) {
                            copyfile(imageuploadpath,path,filename[i],"",batchid.toString(),prefix);
                            copyfile(imageuploadpath,path,filename[i]+"_300_200","_300_200",batchid.toString(),prefix);
                            copyfile(imageuploadpath,path,filename[i]+"_50_50","_50_50",batchid.toString(),prefix);
                        }else if(filename[i].split("_").length==1 && prefix.equals("product")){
                            copyfile(imageuploadpath,path,filename[i],"",typeid.toString(),"");
                            copyfile(imageuploadpath,path,filename[i]+"_300_200","_300_200",typeid.toString(),"");
                            copyfile(imageuploadpath,path,filename[i]+"_50_50","_50_50",typeid.toString(),"");
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //复制草稿中的图片到正式备案
    public static void filecopydraft2product(String prefix,Long id,Long typeid,String imageuploadpath,String fname,String pdfname){
        Long vendorId = IDUtil.getVendorID_Dec(typeid);
        if (vendorId == 0){
            vendorId = id;
        }
        try{
            String path = imageuploadpath + "/" + vendorId + "/" + typeid;
            if ("source".equals(prefix)){
                path += "/b"+ "/" +id;
            }else if ("inspect".equals(prefix)) {
                path += "/br"+ "/" +id;
            }else if ("product".equals(prefix)){
                path +=  "/p"; //商品图片
            }else if ("sample".equals(prefix)) {
                path +=  "/s"; //中文标签样张
            }else if ("report".equals(prefix)) {
                path += "/r"; //管理体系及相关证书
            }else if ("csource".equals(prefix)){
                path += "/cb"+ "/" +id;
            }else if ("cinspect".equals(prefix)) {
                path += "/cbr"+ "/" +id;
            }
            String filename[] = fname.split(",");
            File dir = new File(path);
            if(dir.exists()){
                File[] files = dir.listFiles();
                if(files != null && files.length > 0) {
                    for (File f : files) {
                        if (f.isFile() && f.getName().endsWith(".jpg")) {
                            if (prefix.equals("product")) {
                                deleteNoExistFile("", filename, f);
                            }else {
                                deleteNoExistFile(prefix, filename, f);
                            }
                        }
                    }
                }
            }else{
                dir.mkdirs();
            }

            if(null!=pdfname && !pdfname.equals("")){
                if(prefix.equals("source") || prefix.equals("inspect") || prefix.equals("csource") || prefix.equals("cinspect")){
                    if(!pdfname.equals(id+".pdf")){
                        File pdffile = new File(path + "/" + prefix + id + ".pdf");
                        if(pdffile.exists()){
                            pdffile.delete();
                        }
                        //复制pdf文件
                        File inFile = new File(imageuploadpath + "/tmp/" + pdfname);
                        FileInputStream fis = new FileInputStream(inFile);
                        FileOutputStream fos = new FileOutputStream(path + "/" + prefix + id + ".pdf");
                        fis.getChannel().transferTo(0, fis.getChannel().size(), fos.getChannel());
                        fos.close();
                        fis.close();
                        inFile.delete();
                    }
                }else if(prefix.equals("product")){
                    if(!pdfname.equals(typeid+".pdf")){
                        File pdffile = new File(path + "/"  + typeid + ".pdf");
                        if(pdffile.exists()){
                            pdffile.delete();
                        }
                        //复制pdf文件
                        File inFile = new File(imageuploadpath + "/tmp/" + pdfname);
                        FileInputStream fis = new FileInputStream(inFile);
                        FileOutputStream fos = new FileOutputStream(path + "/" + typeid + ".pdf");
                        fis.getChannel().transferTo(0, fis.getChannel().size(), fos.getChannel());
                        fos.close();
                        fis.close();
                        inFile.delete();
                    }
                }
            }else{
                String pdffilename = path;
                if(prefix.equals("source") || prefix.equals("inspect") || prefix.equals("csource") || prefix.equals("cinspect")){
                    pdffilename += "/" + prefix + id + ".pdf";
                    File pdffile = new File(pdffilename);
                    if(pdffile.exists()){
                        pdffile.delete();
                    }
                }else if(prefix.equals("product")){
                    pdffilename += "/"  + typeid + ".pdf";
                    File pdffile = new File(pdffilename);
                    if(pdffile.exists()){
                        pdffile.delete();
                    }
                }

            }

            //从tmp中复制文件到目录下
            if(prefix.equals("report") || prefix.equals("sample")){
                if(!fname.equals(typeid.toString())) {
                    String draft_path = imageuploadpath + "/" + vendorId + "/" + id;
                    if ("sample".equals(prefix)) {
                        draft_path +=  "/s"; //中文标签样张
                    }else if ("report".equals(prefix)) {
                        draft_path += "/r"; //管理体系及相关证书
                    }
                    File draftdir = new File(draft_path);
                    if (draftdir.exists()) {
                        copyDraftImage(draft_path, fname, path, "", prefix, typeid);
                        copyDraftImage(draft_path, fname, path, "_300_200", prefix, typeid);
                    }else {
                        if(!filename[0].equals("")){
                            copyfile1(imageuploadpath,path,filename[0],"",typeid.toString(),prefix);
                            copyfile1(imageuploadpath,path,filename[0]+"_300_200","_300_200",typeid.toString(),prefix);
//                            copyfile1(imageuploadpath,path,filename[0]+"_50_50","_50_50",typeid.toString(),prefix);
                        }

                    }
                }
            }else{
                for(int i=0;i<filename.length;i++){
                    if(!filename[i].equals("")){
                        if(filename[i].split("_").length==1 && (prefix.equals("source") || prefix.equals("inspect") || prefix.equals("csource") || prefix.equals("cinspect"))) {
                            copyfile(imageuploadpath,path,filename[i],"",id.toString(),prefix);
                            copyfile(imageuploadpath,path,filename[i]+"_300_200","_300_200",id.toString(),prefix);
                            copyfile(imageuploadpath,path,filename[i]+"_50_50","_50_50",id.toString(),prefix);
                        }else if(filename[i].split("_").length==1 && prefix.equals("product")){
                            copyfile(imageuploadpath,path,filename[i],"",typeid.toString(),"");
                            copyfile(imageuploadpath,path,filename[i]+"_300_200","_300_200",typeid.toString(),"");
                            copyfile(imageuploadpath,path,filename[i]+"_50_50","_50_50",typeid.toString(),"");
                        }else if (filename[i].split("_").length==2&&prefix.equals("product")){
                            String draft_path = imageuploadpath + "/" + vendorId + "/" + id;
                            if ("product".equals(prefix)){
                                draft_path +=  "/p"; //商品图片
                            }else if ("sample".equals(prefix)) {
                                draft_path +=  "/s"; //中文标签样张
                            }else if ("report".equals(prefix)) {
                                draft_path += "/r"; //管理体系及相关证书
                            }
                            copyfile_draft(draft_path,filename[i],path,"",typeid.toString(),"");
                            copyfile_draft(draft_path,filename[i]+"_300_200",path,"_300_200",typeid.toString(),"");
                            copyfile_draft(draft_path,filename[i]+"_50_50",path,"_50_50",typeid.toString(),"");
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void deleteNoExistFile(String prefix, String[] filename, File f) {
        boolean b = false;
        for (String str : filename) {
            if (!str.equals("")) {
                if (f.getName().startsWith(prefix + str)) {
                    b = true;
                    break;
                }
            }
        }
        if (!b) {
            f.delete();
        }
    }

    public static void copyDraftfile(String target,String source,String replaceTarget,String replaceSource) throws IOException // 使用FileInputStream和FileOutStream
    {
        File outFile = new File(source);
        if(!outFile.exists()){
            outFile.mkdirs();
        }
        File inFile = new File(target);
        if(inFile.isDirectory()){
            File[] files = inFile.listFiles();
            for(File f : files){
                FileInputStream fis = new FileInputStream(f);
                String filename = f.getName().replace(replaceTarget,replaceSource);
                FileOutputStream fos = new FileOutputStream(source+"/"+filename);
                fis.getChannel().transferTo(0, fis.getChannel().size(), fos.getChannel());
                fos.close();
                fis.close();
            }
        }
    }


    public static void copyfile(String imageuploadpath,String path,String filename,String subff,String namefalg,String prefix) throws IOException // 使用FileInputStream和FileOutStream
    {
        File inFile = new File(imageuploadpath + "/tmp/" + filename + ".jpg");
        FileInputStream fis = new FileInputStream(inFile);
        int index = 0;
        for(int j = 0;j<5;j++){
            File tempfile = new File(path + "/"+prefix + namefalg + "_" + j  +subff + ".jpg");
            if(!tempfile.exists()){
                index = j;
                break;
            }
        }
        FileOutputStream fos = new FileOutputStream(path + "/" + prefix+ namefalg + "_" + index +subff+ ".jpg");
        fis.getChannel().transferTo(0, fis.getChannel().size(), fos.getChannel());
        fos.close();
        fis.close();
        inFile.delete();
    }

    public static void copyfile1(String imageuploadpath,String path,String filename,String subff,String namefalg,String prefix) throws IOException // 使用FileInputStream和FileOutStream
    {
        File inFile = new File(imageuploadpath + "/tmp/" + filename + ".jpg");
        FileInputStream fis = new FileInputStream(inFile);

        FileOutputStream fos = new FileOutputStream(path + "/" + prefix+ namefalg+subff+ ".jpg");
        fis.getChannel().transferTo(0, fis.getChannel().size(), fos.getChannel());
        fos.close();
        fis.close();
        inFile.delete();
    }

    //从草稿目录拷贝到
    public static void copyfile_draft(String draft_path,String filename,String product_path,String subff,String namefalg,String prefix) throws IOException {
        File inFile = new File(draft_path+"/" + filename + ".jpg");
        FileInputStream fis = new FileInputStream(inFile);
        int index = 0;
        for(int j = 0;j<5;j++){
            File tempfile = new File(product_path + "/"+prefix + namefalg + "_" + j  +subff + ".jpg");
            if(!tempfile.exists()){
                index = j;
                break;
            }
        }
        FileOutputStream fos = new FileOutputStream(product_path + "/" + prefix+ namefalg + "_" + index +subff+ ".jpg");
        fis.getChannel().transferTo(0, fis.getChannel().size(), fos.getChannel());
        fos.close();
        fis.close();
        inFile.delete();
    }

    public static void copyManagerImage(String prefix, String path, String fname,String subbf,String imageuploadpath,Long typeid) throws IOException {
        File inFile = new File(imageuploadpath + "/tmp/" + fname + subbf + ".jpg");
        FileInputStream fis = new FileInputStream(inFile);
        FileOutputStream fos = new FileOutputStream(path + "/" + prefix + typeid + subbf +".jpg");
        fis.getChannel().transferTo(0, fis.getChannel().size(), fos.getChannel());
        inFile.delete();
        fos.close();
        fis.close();
    }

    public static void copyDraftImage(String draft_path,String filename,String product_path,String subff,String prefix,Long typeid) throws IOException {
        File inFile = new File(draft_path+"/" + prefix+filename+".jpg");
        FileInputStream fis = new FileInputStream(inFile);
        FileOutputStream fos = new FileOutputStream(product_path + "/"+prefix + typeid  +subff + ".jpg");
        fis.getChannel().transferTo(0, fis.getChannel().size(), fos.getChannel());
        inFile.delete();
        fos.close();
        fis.close();
    }

    public static void deleteUploadImage(String prefix,Long batchid,Long typeid,String imageuploadpath ){
        Long vendorId = IDUtil.getVendorID_Dec(typeid);
        try {
            String path = imageuploadpath + "/" + vendorId + "/" + typeid;
            if ("source".equals(prefix)) {
                path += "/b" + "/" + batchid;
            }else if ("inspect".equals(prefix)) {
                path += "/br" + "/" + batchid;
            }else if ("product".equals(prefix)){
                path +=  "/p"; //商品图片
            }else if ("sample".equals(prefix)) {
                path +=  "/s"; //中文标签样张
            }else if ("report".equals(prefix)) {
                path += "/r"; //管理体系及相关证书
            }else if ("csource".equals(prefix)) {
                path += "/cb" + "/" + batchid;
            }else if ("cinspect".equals(prefix)) {
                path += "/cbr" + "/" + batchid;
            }
            File dir = new File(path);
            if(dir.exists()) {
                File[] files = dir.listFiles();
                if (files != null && files.length > 0)
                    for (File f : files) {
                        if (f.isFile()) {
                            f.delete();
                        }
                    }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * \home\webservice\imageupload\1058582\69375232671\b\12\source12_0.jpg    批次信息原料信息报告
     * \home\webservice\imageupload\1058582\69375232671\br\12\inspect12_0.jpg  批次信息企业检测报告
     * \home\webservice\imageupload\1058582\69375232671\p\69375232671_0.jpg  商品图片
     * \home\webservice\imageupload\1058582\69375232671\s\sample69375232671_0.jpg  中文标签样张
     * \home\webservice\imageupload\1058582\69375232671\r\report69375232671_0.jpg  管理体系及相关证书
     * \home\webservice\imageupload\1058582\third\111111111111111\source111111111111111_0.jpg  第三方检测报告
     * \home\webservice\imageupload\1058582\ibr\111111111111111\inspect111111111111111_0.jpg   合格证
     */
    public static Map<String,Object> getDirImage(String imageuploadpath,Long typeId,Long vendorId,String prefix,Long batchId,String inspectCode){
        Map<String,Object> map = new HashMap<String, Object>();
        List<Integer> list = new ArrayList<Integer>();
        String filenamestr = "";
        String path = imageuploadpath + "/" + vendorId + "/" + typeId;
        if ("source".equals(prefix)){
            path += "/b"+ "/" +batchId;  //批次信息原料信息报告
        }else if ("inspect".equals(prefix)) {
            path += "/br"+ "/" +batchId; //批次信息企业检测报告
        }else if ("product".equals(prefix)){
            path +=  "/p"; //商品图片
        }else if ("sample".equals(prefix)) {
            path +=  "/s"; //中文标签样张
        }else if ("report".equals(prefix)) {
            path += "/r"; //管理体系及相关证书
        }else if("third".equals(prefix)){
            path = imageuploadpath + "/" + vendorId + "/third"+ "/" +inspectCode;//检测报告
        }else if("qualify".equals(prefix)){
            path = imageuploadpath + "/" + vendorId + "/ibr"+ "/" +inspectCode;//合格证
        }else if ("assess".equals(prefix)){
            path = imageuploadpath + "/" + vendorId + "/assess"+ "/" +batchId;//预检验报告
        }else if ("wapth".equals(prefix)){
            path = imageuploadpath + "/" + vendorId+ "/" + typeId + "/third";//预检验报告
        }else if ("csource".equals(prefix)){
            path += "/cb"+ "/" +batchId;  //批次信息原料信息报告
        }else if ("cinspect".equals(prefix)) {
            path += "/cbr"+ "/" +batchId; //批次信息企业检测报告
        }else{
            path = imageuploadpath + "/" + vendorId + "/"+ prefix +"/" +inspectCode;//合格证
        }

        File file = new File(path);
        if(file.isDirectory()){
            for(int i=0;i<5;i++){
                if(prefix.equals("source") || prefix.equals("inspect") || prefix.equals("csource") || prefix.equals("cinspect")){
                    file = new File(path+"/"+prefix+batchId+"_"+i+".jpg");
                    if(file.exists()){
                        list.add(i);
                        filenamestr += batchId+"_"+i+",";
                    }
                }else if(prefix.equals("product") || prefix.equals("sample") || prefix.equals("report")){
                    String tempfix = prefix;
                    if(tempfix.equals("product"))
                        tempfix = "";
                    file = new File(path+"/"+tempfix+typeId+"_"+i+".jpg");
                    if(file.exists()){
                        list.add(i);
                        filenamestr += typeId+"_"+i+",";
                    }
                }else if(prefix.equals("third") || prefix.equals("qualify")){
                    String tempfix = prefix;
                    if(tempfix.equals("third"))
                        tempfix = "source";
                    else if(tempfix.equals("qualify"))
                        tempfix = "inspect";
                    file = new File(path+"/"+tempfix+inspectCode+"_"+i+".jpg");
                    if(file.exists()){
                        list.add(i);
                        filenamestr += inspectCode+"_"+i+",";
                    }
                }else if (prefix.equals("assess")){
                    file = new File(path+"/"+prefix+batchId+"_"+i+".jpg");
                    if(file.exists()){
                        list.add(i);
                        filenamestr += batchId+"_"+i+",";
                    }
                }else if (prefix.equals("wapth")){
                    file = new File(path+"/source"+batchId+"_"+i+".jpg");
                    if(file.exists()){
                        list.add(i);
                        filenamestr += batchId+"_"+i+",";
                    }
                }else{
                    file = new File(path+"/"+prefix+inspectCode+"_"+i+".jpg");
                    if(file.exists()){
                        list.add(i);
                        filenamestr += inspectCode+"_"+i+",";
                    }
                }
            }
        }
        map.put("list",list);
        map.put("filename",filenamestr);
        return map;
    }

    public static String checkPdfExists(String imageuploadpath,Long typeId,String prefix,Long batchId){
        Long vendorId = IDUtil.getVendorID_Dec(typeId);
        String path = imageuploadpath + "/" + vendorId + "/" + typeId;
        if ("source".equals(prefix)){
            path += "/b"+ "/" +batchId+"/"+prefix+batchId+".pdf";  //批次信息原料信息报告
        }else if ("inspect".equals(prefix)) {
            path += "/br"+ "/" +batchId+"/"+prefix+batchId+".pdf"; //批次信息企业检测报告
        }else if("product".equals(prefix)){
            path +=  "/p"+"/"+typeId+".pdf"; //商品图片
        }else if ("csource".equals(prefix)){
            path += "/cb"+ "/" +batchId+"/"+prefix+batchId+".pdf";  //批次信息原料信息报告
        }else if ("cinspect".equals(prefix)) {
            path += "/cbr"+ "/" +batchId+"/"+prefix+batchId+".pdf"; //批次信息企业检测报告
        }
        File file = new File(path);
        if(file.exists()){
            return "true";
        }
        return "false";
    }

    //检查多商品报检中pdf文件是否存在
    public static String checkInspectPdfExists(String imageuploadpath,Long vendorId,String prefix,String inspectCode){
        String path = imageuploadpath + "/" + vendorId;
        if ("third".equals(prefix)){
            path += "/third"+ "/" +inspectCode+"/source"+inspectCode+".pdf";  //批次信息原料信息报告
        }else if ("qualify".equals(prefix)) {
            path += "/ibr"+ "/" +inspectCode+"/inspect"+inspectCode+".pdf"; //批次信息企业检测报告
        }else if ("assess".equals(prefix)){
            path += "/assess"+ "/" +inspectCode+"/"+prefix+inspectCode+".pdf"; //第三方预检验机构评估
        }else{
            path +="/"+ prefix+ "/" +inspectCode+"/"+prefix+inspectCode+".pdf";
        }
        File file = new File(path);
        if(file.exists()){
            return "true";
        }
        return "false";
    }
    //生成临时文件名称
    public static String generateFilename(String suffix){
        String tmpfilename = "tmp" + StringUtil.generateString(5) + System.currentTimeMillis() + suffix;
        return tmpfilename;
    }

    public static boolean copyImageToLocalFromNet(String url, String desDir, String filename) {
        HttpClient httpClient = null;
        try {
            httpClient = new SSLClient();


            HttpGet get = new HttpGet(url);
            HttpResponse response = httpClient.execute(get);
            InputStream is = response.getEntity().getContent();
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            File sf = new File(desDir);
            if (!sf.exists()) {
                sf.mkdirs();
            }
            int index = sf.listFiles().length;
            String desPath = desDir + filename;
            OutputStream os = new FileOutputStream(desPath);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            // 完毕，关闭所有链接
            os.close();
            is.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
