package com.example.ltbase.base_utils;

import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：王健 on 2021/8/23
 * 邮箱：845040970@qq.com
 * 描述：List工具类
 */

public class ListUtils {
    /**
     * 将一个list均分成n个list,主要通过偏移量来实现的
     * @param source
     * @return
     */
    public static <T> List<List<T>> averageAssign(List<T> source, int n){
        List<List<T>> result=new ArrayList<List<T>>();
        int remaider=source.size()%n;  //(先计算出余数)
        int number=source.size()/n;  //然后是商
        int offset=0;//偏移量
        for(int i=0;i<n;i++){
            List<T> value=null;
            if(remaider>0){
                value=source.subList(i*number+offset, (i+1)*number+offset+1);
                remaider--;
                offset++;
            }else{
                value=source.subList(i*number+offset, (i+1)*number+offset);
            }
            result.add(value);
        }
        return result;
    }

    //将list集合转换成字符串

    public static String SceneList2String(List SceneList) throws IOException {
// 实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件。
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
// 然后将得到的字符数据装载到ObjectOutputStream
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
// writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
        objectOutputStream.writeObject(SceneList);
// 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
        String SceneListString = new String(Base64.encode(
                byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
// 关闭objectOutputStream
        objectOutputStream.close();
        return SceneListString;
    }

    //将字符串转换成list集合

    @SuppressWarnings("unchecked")
    public static List String2SceneList(String SceneListString)
            throws StreamCorruptedException, IOException,
            ClassNotFoundException {
        byte[] mobileBytes = Base64.decode(SceneListString.getBytes(),
                Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                mobileBytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        List SceneList = (List) objectInputStream.readObject();
        objectInputStream.close();
        return SceneList;
    }
}
