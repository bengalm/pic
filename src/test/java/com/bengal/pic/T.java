package com.bengal.pic;

import com.bengal.pic.util.PicUtil;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @Classname T
 * @Description TODO
 * @Date 2021/7/19 15:29
 * @Author bengal
 */
public class T {

    @Test
    public void test() throws ImageProcessingException, IOException {
        Metadata metadata = ImageMetadataReader.readMetadata(new File("G:\\share.jpg"));

        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                System.out.format("[%s] - %s = %s",
                        directory.getName(), tag.getTagName(), tag.getDescription());
                System.out.println();
            }
            if (directory.hasErrors()) {
                for (String error : directory.getErrors()) {
                    System.err.format("ERROR: %s", error);
                    System.out.println();
                }
            }
        }
    }

    @Test
    public void a() throws JpegProcessingException, IOException {
        Map<String, String> stringStringMap = PicUtil.PicAnalysis("E:\\IMG_2428.jpg");

        PicUtil.getGaodeAddress(stringStringMap);
        /*Metadata metadata = JpegMetadataReader.readMetadata(new File("E:\\IMG_2428.jpg"));
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                System.out.format("[%s] - %s = %s",
                        directory.getName(), tag.getTagName(), tag.getDescription());
                System.out.println();
            }
            if (directory.hasErrors()) {
                for (String error : directory.getErrors()) {
                    System.err.format("ERROR: %s", error);
                    System.out.println();
                }
            }
        }*/
    }
}
