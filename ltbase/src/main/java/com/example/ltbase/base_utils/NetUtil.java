package com.example.ltbase.base_utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.ltbase.base_dialog.AlertDialogUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;


/**
 * 作者：王健 on 2021/8/23
 * 邮箱：845040970@qq.com
 * 描述：网络监听
 */
public class NetUtil {
    /**
     * 描述：dada
     */
    public  enum netType {
        wifi, CMNET, CMWAP, noneNet
    }
    /**
     * 描述：判断WIFI网络是否可用
     * @return true=可用，false=不可用
     * @param context 上下文
     */
    public static boolean isWifiConnected(Context context)
    {
        if (context != null)
        {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null)
            {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }
    /**
     * 描述：判断MOBILE网络是否可用
     * @return true=可用，false=不可用
     * @param context 上下文
     */
    public static boolean isMobileConnected(Context context)
    {
        if (context != null)
        {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null)
            {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }
    /**
     * 描述：获取当前网络连接的类型信息
     * @param context 上下文
     * @return 返回类型信息
     */
    public static int getConnectedType(Context context)
    {
        if (context != null)
        {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable())
            {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }
    /**
     * 描述：获取当前的网络状态
     * @param context 上下文
     * @return -1：没有网络 1：WIFI网络2：wap 网络3：net网络
     */
    public static netType getAPNType(Context context)
    {
        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null)
        {
            return netType.noneNet;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE)
        {
            if ("cmnet".equals(networkInfo.getExtraInfo().toLowerCase()))
            {
                return netType.CMNET;
            }
            else
            {
                return netType.CMWAP;
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI)
        {
            return netType.wifi;
        }
        return netType.noneNet;
    }
    /**
     * 描述：判断是否有网络连接
     * @param context 上下文
     * @return true=有，false=没有
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
    /**
     * 描述：网络是否可用
     * @param context 上下文
     * @return true=有，false=没有
     */
    public static boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager mgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info = mgr.getAllNetworkInfo();
        if (info != null)
        {
            for (int i = 0; i < info.length; i++)
            {
                if (info[i].getState() == NetworkInfo.State.CONNECTED)
                {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 描述：判断是否是手机网络
     * @param context 上下文
     * @return true=是，false=不是
     */
    public static boolean is3GNet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }



    /**
     * 描述：判断当前网络是否是2G网络
     * @return true=是，false=不是
     * @param context 上下文
     */
    public static boolean is2G(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && (activeNetInfo.getSubtype() == TelephonyManager.NETWORK_TYPE_EDGE
                || activeNetInfo.getSubtype() == TelephonyManager.NETWORK_TYPE_GPRS || activeNetInfo
                .getSubtype() == TelephonyManager.NETWORK_TYPE_CDMA)) {
            return true;
        }
        return false;
    }

    /**
     * 描述：wifi是否打开
     * @return true=打开，false=关闭
     * @param context 上下文
     */
    public static boolean isWifiEnabled(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return null != wifiManager && wifiManager.isWifiEnabled();
    }



    /**
     * 描述：判断GPS是否打开，ACCESS_FINE_LOCATION权限
     * @return true=打开，false=关闭
     * @param context 上下文
     */
    public static boolean isGPSEnabled(Context context) {
        //获取手机所有连接LOCATION_SERVICE对象
        LocationManager locationManager = ((LocationManager) context.getSystemService(Context
                .LOCATION_SERVICE));
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
    /**
     * 描述：获得本机ip地址
     * @return 地址信息
     */
    public static String GetHostIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> ipAddr = intf.getInetAddresses(); ipAddr
                        .hasMoreElements(); ) {
                    InetAddress inetAddress = ipAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
        } catch (Exception e) {
        }
        return null;
    }
    /**
     * 描述：获取本机串号imei
     * @return 信息
     */
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }
    /**
     * 描述：判断是否有外网连接（普通方法不能判断外网的网络是否连接，比如连接上局域网）
     * @return true=有，false=没有
     */
    public static final boolean ping() {
        String result = null;
        try {
            String ip = "www.baidu.com";// ping 的地址，可以换成任何一种可靠的外网
            Process p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + ip);// ping网址3次
            // 读取ping的内容，可以不加
            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer stringBuffer = new StringBuffer();
            String content = "";
            while ((content = in.readLine()) != null) {
                stringBuffer.append(content);
            }
            Log.d("------ping-----", "result content : " + stringBuffer.toString());
            // ping的状态
            int status = p.waitFor();
            if (status == 0) {
                result = "success";
                return true;
            } else {
                result = "failed";
            }
        } catch (IOException e) {
            result = "IOException";
        } catch (InterruptedException e) {
            result = "InterruptedException";
        } finally {
            Log.d("----result---", "result = " + result);
        }
        return false;
    }


    /**
     * 描述：如果没有网络，则弹出网络设置对话框
     * @param activity 上下文
     */
    public static void checkNetwork(final Activity activity) {
        if (!NetUtil.isNetworkAvailable(activity)) {
            AlertDialogUtil alertDialogUtil=new AlertDialogUtil();
            alertDialogUtil.showAlertDialog(activity,"网络状态提示","当前没有可以使用的网络，请设置网络！", "取消", "确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // 跳转到设置界面
                    activity.startActivityForResult(new Intent(Settings.ACTION_WIRELESS_SETTINGS), 0);
                }
            });
        }
    }

    /**
     * 描述：在连接到网络基础之上,判断设备是否是SIM网络连接
     * @param context 上下文
     * @return true=是，false=不是
     */
    public static boolean IsMobileNetConnect(Context context) {
        try {
            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo.State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
            if (NetworkInfo.State.CONNECTED == state) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
