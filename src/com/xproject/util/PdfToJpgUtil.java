package com.xproject.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * pdf文件转成图片
 */
public class PdfToJpgUtil {


    public static boolean setup(String pdfpath,String outDir,String barcode){
        String imagename = "";
        try{
            if(new File(pdfpath).exists()){
                PDDocument doc= PDDocument.load(new FileInputStream(pdfpath));
                PDFRenderer renderer = new PDFRenderer(doc);
                int pageCount = doc.getNumberOfPages();
                for(int i=0;i<pageCount;i++){
                    BufferedImage image = renderer.renderImageWithDPI(i, 168);
//                    BufferedImage image = renderer.renderImage(i);
                    long temp = System.currentTimeMillis();
                    String outPath = outDir+barcode+"_"+i+".jpg";
                    ImageIO.write(image, "jpg", new File(outPath));
                    imagename += temp+",";
                    if(i==4){
                        break;
                    }
                }
                doc.close();
            }

        }catch(IOException ie){
            ie.printStackTrace();
            return false;
        }
        return true;
    }
}
