package com.example.ltbase.base_utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 作者：王健 on 2021/8/23
 * 邮箱：845040970@qq.com
 * 描述：获取网页内的数据
 */

public class HtmlListImagePathUtils {
    private static final Pattern pImg = Pattern.compile("<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\.svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic)\\b)[^>]*>", Pattern.CASE_INSENSITIVE);
    private static final Pattern pAlt = Pattern.compile("<img\\b[^>]*\\balt\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\.svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic)\\b)[^>]*>", Pattern.CASE_INSENSITIVE);
    private static final Pattern pVideo = Pattern.compile("<video\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.mp4)\\b)[^>]*>", Pattern.CASE_INSENSITIVE);
    private static final Pattern pAudio = Pattern.compile("<audio\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.mp3)\\b)[^>]*>", Pattern.CASE_INSENSITIVE);

    /**
     * @param s
     * @return 获得图片
     */
    public static List<String> getImg(String s)
    {
        String regex;
        List<String> list = new ArrayList<String>();
        regex = "src=\"(.*?)\"";
        Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        Matcher ma = pa.matcher(s);
        while (ma.find())
        {
            list.add(ma.group());
        }
        return list;
    }
    /**
     * 返回存有图片地址的数组
     * @param tar
     * @return
     */
    public static String[] getImgAddress(String tar){
        List<String> imgList = getImg(tar);

        String res[] = new String[imgList.size()];

        if(imgList.size()>0){
            for (int i = 0; i < imgList.size(); i++) {
                int begin = imgList.get(i).indexOf("\"")+1;
                int end = imgList.get(i).lastIndexOf("\"");
                String url[] = imgList.get(i).substring(begin,end).split("/");
                res[i]=url[url.length-1];
            }
        }else{
        }
        return res;
    }
    /**
     * 获取网页上所有的图片路径
     * @param htmlCode
     * @return
     */
    public static List<String> getImageSrc(String htmlCode) {
        List<String> imageSrcList = new ArrayList<String>();
        Matcher m = pImg.matcher(htmlCode);
        String quote = null;
        String src = null;
        while (m.find()) {
            quote = m.group(1);
            src = (quote == null || quote.trim().length() == 0) ? m.group(2).split("\\s+")[0] : m.group(2);
            imageSrcList.add(src);

        }
        return imageSrcList;
    }
    /**
     * 获取网页上所有的图片alt
     * @param htmlCode
     * @return
     */
    public static List<String> getImageAlt(String htmlCode) {
        List<String> imageSrcList = new ArrayList<String>();
        Matcher m = pAlt.matcher(htmlCode);
        String quote = null;
        String src = null;
        while (m.find()) {
            quote = m.group(1);
            src = (quote == null || quote.trim().length() == 0) ? m.group(2).split("\\s+")[0] : m.group(2);
            imageSrcList.add(src);

        }
        return imageSrcList;
    }

    /**
     * 获取网页上所有的视频路径
     * @param htmlCode
     * @return
     */
    public static List<String> getVideoSrc(String htmlCode) {
        List<String> imageSrcList = new ArrayList<String>();
        Matcher m = pVideo.matcher(htmlCode);
        String quote = null;
        String src = null;
        while (m.find()) {
            quote = m.group(1);
            src = (quote == null || quote.trim().length() == 0) ? m.group(2).split("\\s+")[0] : m.group(2);
            imageSrcList.add(src);

        }
        return imageSrcList;
    }
    /**
     * 获取网页上所有的音频路径
     * @param htmlCode
     * @return
     */
    public static List<String> getAudioSrc(String htmlCode) {
        List<String> imageSrcList = new ArrayList<String>();
        Matcher m = pAudio.matcher(htmlCode);
        String quote = null;
        String src = null;
        while (m.find()) {
            quote = m.group(1);
            src = (quote == null || quote.trim().length() == 0) ? m.group(2).split("\\s+")[0] : m.group(2);
            imageSrcList.add(src);
        }
        return imageSrcList;
    }
}
