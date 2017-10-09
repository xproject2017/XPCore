//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.baidu.ueditor.upload;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;
import com.baidu.ueditor.upload.StorageManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class BinaryUploader {
    public BinaryUploader() {
    }

    public static final State save(HttpServletRequest request, Map<String, Object> conf) {
        FileItemStream fileStream = null;
        boolean isAjaxUpload = request.getHeader("X_Requested_With") != null;
        if (!ServletFileUpload.isMultipartContent(request)) {
            return new BaseState(false, 5);
        } else {
            ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
            if (isAjaxUpload) {
                upload.setHeaderEncoding("UTF-8");
            }

            try {
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                MultipartFile multipartFile = multipartRequest.getFile(conf.get("fieldName").toString());
                String savePath = (String) conf.get("savePath");
                String url = (String)conf.get("url");
                String originFileName = multipartFile.getOriginalFilename();
                String suffix = FileType.getSuffixByFilename(originFileName);
                originFileName = originFileName.substring(0, originFileName.length() - suffix.length());
                savePath = savePath + suffix;
                long maxSize = ((Long) conf.get("maxSize")).longValue();
                if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
                    return new BaseState(false, 8);
                } else {
                    savePath = PathFormat.parse(savePath, originFileName);
                    String physicalPath = savePath;
                    InputStream is = multipartFile.getInputStream();
                    State storageState = StorageManager.saveFileByInputStream(is, physicalPath, maxSize);
                    is.close();
                    if (storageState.isSuccess()) {
                        storageState.putInfo("url", url);
                        storageState.putInfo("type", suffix);
                        storageState.putInfo("original", originFileName + suffix);
                    }

                    return storageState;
                }

            } catch (IOException var15) {
                return new BaseState(false, 4);
            }
        }
    }

    private static boolean validType(String type, String[] allowTypes) {
        List list = Arrays.asList(allowTypes);
        return list.contains(type);
    }
}
