package com.bengal.pic.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Classname PicUtil
 * @Description TODO
 * @Date 2021/7/19 15:49
 * @Author bengal
 */
public class PicUtil {

    /**
     * gps转换
     *
     * @param Gps
     * @return
     */
    public static String translate(String Gps) {
        String a = Gps.split("°")[0].replace(" ", "");
        String b = Gps.split("°")[1].split("'")[0].replace(" ", "");
        String c = Gps.split("°")[1].split("'")[1].replace(" ", "").replace("\"", "");
        double gps = Double.parseDouble(a) + Double.parseDouble(b) / 60 + Double.parseDouble(c) / 60 / 60;
        return String.valueOf(gps);
    }

    /**
     * 获取地址
     * @param param
     */
    public static void getGaodeAddress(Map<String, String> param) {
        String gps_longitude = translate(param.get("GPS Longitude"));
        String gps_latitude = translate(param.get("GPS Latitude"));
        String str = RequestUtils.sendGet("https://restapi.amap.com/v3/geocode/regeo", "key=5a3c45fd68d04bbc&location=" + gps_longitude + "," + gps_latitude);
        JSONObject result = JSON.parseObject(str);
        System.out.println("拍摄时间：" + param.get("Date/Time"));
        System.out.println("拍摄地点：" + result.getJSONObject("regeocode").getString("formatted_address"));
        System.out.println("手机型号：" + param.get("Make") + " " + param.get("Model"));
        System.out.println(str);
    }

    /**
     * 分析图片
     * @param path
     * @return
     * @throws JpegProcessingException
     * @throws IOException
     */
    public static  Map<String,String> PicAnalysis(String path) throws JpegProcessingException, IOException {
        Map<String,String> map = new HashMap<>();
        System.out.println("强大的语言正在识别图片地址...");
        File picFile = new File(path);
        Metadata metadata = JpegMetadataReader.readMetadata(picFile);
        Iterator<Directory> it = metadata.getDirectories().iterator();
        while (it.hasNext()) {
            Directory exif = it.next();
            Iterator<Tag> tags = exif.getTags().iterator();
            while (tags.hasNext()) {
                Tag tag = tags.next();
                map.put(tag.getTagName(),tag.getDescription());
                System.out.println(tag.getTagName() + ":" +tag.getDescription());
            }
        }
        System.out.println("图片分析完毕！");
        return map;
    }


}
