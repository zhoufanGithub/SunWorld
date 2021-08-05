package com.steven.baselibrary.retrofit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;


import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by zhoufan on 2018/1/3.
 * 工具类 （添加通用参数、加密等等）
 */
public class HttpTool {

    public static RequestBody convertToRequestBody(String param) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), param);
        return requestBody;
    }

    public static String signTopRequest(Map<String, Object> params, String secret, String signMethod) throws IOException {
        // 第一步：检查参数是否已经排序
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        // 第二步：把所有参数名和参数值串在一起
        StringBuilder query = new StringBuilder();
        for (String key : keys) {
            Object value = params.get(key);
            if (value instanceof String) {
                if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty((String) value)) {
                    query.append(key).append(value);
                }
            } else if (value instanceof File) {
                String strVal = getFileContent((File) value);
                query.append(key).append(strVal);
                params.remove(key);
            }
        }

        // 第三步：使用MD5/HMAC加密
        byte[] bytes;
        if (HttpRequestConstants.SIGN_METHOD_HMAC.equals(signMethod)) {
            bytes = encryptHMAC(query.toString(), secret);
        } else {
            bytes = encryptMD5(query.toString() + secret);
        }

        // 第四步：把二进制转化为大写的十六进制
        return byte2hex(bytes);
    }

    public static byte[] encryptHMAC(String data, String secret) throws IOException {
        byte[] bytes = null;
        try {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes(HttpRequestConstants.CHARSET_UTF8), "HmacMD5");
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            bytes = mac.doFinal(data.getBytes(HttpRequestConstants.CHARSET_UTF8));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse.toString());
        }
        return bytes;
    }

    public static byte[] encryptMD5(String data) throws IOException {
        byte[] md5Byte = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");  // 创建一个md5算法对象
            byte[] messageByte = data.getBytes("UTF-8");
            md5Byte = md.digest(messageByte);              // 获得MD5字节数组,16*8=128位
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5Byte;
    }

    public static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toLowerCase());
        }
        return sign.toString();
    }

    public static TreeMap getTreeCrc(TreeMap maps) {
        try {
            maps.put("app_key", HttpRequestConstants.APP_KEY);
            maps.put("sign_method", "md5");
            maps.put("format", "json");
            maps.put("timestamp", timeStamp2Date());
            maps.put("sign", signTopRequest(maps, HttpRequestConstants.SECRET, HttpRequestConstants.SIGN_METHOD_MD5));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maps;
    }

    // 时间戳转换
    private static String timeStamp2Date() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));//设置TimeZone为上海时间
        Date now = new Date();//获取本地时间
        try {
            now = sdf.parse(sdf.format(now));//将本地时间转换为转换时间为东八区
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(now);
    }

    // 检测是否有网络
    @SuppressLint("MissingPermission")
    public static boolean hasNetwork(Context mContext) {
        // 得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                return true;
            } else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                return true;
            }
        } else {
            return false;
        }
        return false;
    }


    // 将文件进行SHA1加密
    public static String getFileContent(File file) {
        try {
            StringBuffer sb = new StringBuffer();
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            FileInputStream fin = new FileInputStream(file);
            int len = -1;
            byte[] buffer = new byte[1024];//设置输入流的缓存大小 字节
            //将整个文件全部读入到加密器中
            while ((len = fin.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
            //对读入的数据进行加密
            byte[] bytes = digest.digest();
            for (byte b : bytes) {
                // 数byte 类型转换为无符号的整数
                int n = b & 0XFF;
                // 将整数转换为16进制
                String s = Integer.toHexString(n);
                // 如果16进制字符串是一位，那么前面补0
                if (s.length() == 1) {
                    sb.append("0" + s);
                } else {
                    sb.append(s);
                }
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 2 * 判断字符串是否可以转化为json对象
     * 3 * @param content
     * 4 * @return
     * 5
     */
    public static boolean isJsonObject(String content) {
        // 此处应该注意，不要使用StringUtils.isEmpty(),因为当content为"  "空格字符串时，JSONObject.parseObject可以解析成功，
        // 实际上，这是没有什么意义的。所以content应该是非空白字符串且不为空，判断是否是JSON数组也是相同的情况。
        if (content == null || TextUtils.isEmpty(content))
            return false;
        try {
            JSONObject jsonObject = new JSONObject(content);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
