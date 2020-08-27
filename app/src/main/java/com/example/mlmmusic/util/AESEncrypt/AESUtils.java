package com.example.mlmmusic.util.AESEncrypt;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author TangHongChang  是个垃圾
 * @version 1.0
 * @date 创建时间： 2018/7/16 下午3:17
 * @Description AES加密
 * @fileName AESUtils.java
 */
public class AESUtils {

    private final static String HEX = "0123456789ABCDEF";
    private static final String AES = "AES";//AES 加密
    private static final String SHA1PRNG = "SHA1PRNG";//// SHA1PRNG 强随机种子算法, 要区别4.2以上版本的调用方法

    private static final String TAG = "tanghongchang_AESUtils";
    private static final String UTF8 = "UTF-8";
    private static final String AES_CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";
    private static final String AES_CBC_NO_PADDING = "AES/CBC/NoPadding";


    /*
     * 生成随机数，可以当做动态的密钥 加密和解密的密钥必须一致，不然将不能解密
     */
    public static String generateKey() {
        try {
            SecureRandom localSecureRandom = SecureRandom.getInstance(SHA1PRNG);
            byte[] bytes_key = new byte[20];
            localSecureRandom.nextBytes(bytes_key);
            String str_key = toHex(bytes_key);
            return str_key;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 二进制转字符
     *
     * @param buf 定义偏移量（16位的数字/字母+数字/字母）
     * @return
     */
    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }

    public static SecretKey generateKey(String seed) throws Exception {
        // 获取秘钥生成器
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        // 通过种子初始化
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(seed.getBytes("UTF-8"));
        keyGenerator.init(128, secureRandom);
        // 生成秘钥并返回
        return keyGenerator.generateKey();
    }


    /**
     * AES 加密
     *
     * @param password       定义偏移量（16位的数字/字母+数字/字母）
     * @param content        AES 加密的内容
     * @param CBC_NO_PADDING AES/CBC/NoPadding  AES是加密方式 CBC是工作模式 NoPadding是填充模式
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(String password, String content, String CBC_NO_PADDING) throws Exception {
        // 创建AES秘钥
        SecretKeySpec key = new SecretKeySpec(password.getBytes(), CBC_NO_PADDING);
        // 创建密码器
        Cipher cipher = Cipher.getInstance("AES");
        // 初始化加密器
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // 加密
        return cipher.doFinal(content.getBytes("UTF-8"));
    }

    /**
     * AES 解密
     *
     * @param password       定义偏移量（16位的数字/字母+数字/字母）
     * @param content        AES 界面的字节数组(byte[] 类型)
     * @param CBC_NO_PADDING AES/CBC/NoPadding  AES是加密方式 CBC是工作模式 NoPadding是填充模式
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(String password, byte[] content, String CBC_NO_PADDING) throws Exception {

        // 创建AES秘钥
        SecretKeySpec key = new SecretKeySpec(password.getBytes(), CBC_NO_PADDING);

        // 创建密码器
        Cipher cipher = Cipher.getInstance("AES");

        // 初始化解密器
        cipher.init(Cipher.DECRYPT_MODE, key);

        // 解密
        return cipher.doFinal(content);
    }


    /**
     * JDK只支持AES-128加密，也就是密钥长度必须是128bit；参数为密钥key，key的长度小于16字符时用"0"补充，key长度大于16字符时截取前16位
     **/
    private static SecretKeySpec create128BitsKey(String key) {
        if (key == null) {
            key = "";
        }
        byte[] data = null;
        StringBuffer buffer = new StringBuffer(16);
        buffer.append(key);
        //小于16后面补0
        while (buffer.length() < 16) {
            buffer.append("0");
        }
        //大于16，截取前16个字符
        if (buffer.length() > 16) {
            buffer.setLength(16);
        }
        try {
            data = buffer.toString().getBytes(UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new SecretKeySpec(data, AES);
    }

    /**
     * 创建128位的偏移量，iv的长度小于16时后面补0，大于16，截取前16个字符;
     *
     * @param iv
     * @return
     */
    private static IvParameterSpec create128BitsIV(String iv) {
        if (iv == null) {
            iv = "";
        }
        byte[] data = null;
        StringBuffer buffer = new StringBuffer(16);
        buffer.append(iv);
        while (buffer.length() < 16) {
            buffer.append("0");
        }
        if (buffer.length() > 16) {
            buffer.setLength(16);
        }
        try {
            data = buffer.toString().getBytes(UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new IvParameterSpec(data);
    }

    /**
     * 填充方式为Pkcs5Padding时，最后一个块需要填充χ个字节，填充的值就是χ，也就是填充内容由JDK确定
     *
     * @param srcContent
     * @param password
     * @param iv
     * @return
     */
    public static byte[] aesCbcPkcs5PaddingEncrypt(byte[] srcContent, String password, String iv) {
        SecretKeySpec key = create128BitsKey(password);
        IvParameterSpec ivParameterSpec = create128BitsIV(iv);
        try {
            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
            byte[] encryptedContent = cipher.doFinal(srcContent);
            return encryptedContent;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static byte[] aesCbcPkcs5PaddingDecrypt(byte[] encryptedContent, String password, String iv) {
        SecretKeySpec key = create128BitsKey(password);
        IvParameterSpec ivParameterSpec = create128BitsIV(iv);
        try {
            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
            byte[] decryptedContent = cipher.doFinal(encryptedContent);
            return decryptedContent;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 填充方式为NoPadding时，最后一个块的填充内容由程序员确定，通常为0.
     * AES/CBC/NoPadding加密的明文长度必须是16的整数倍，明文长度不满足16时，程序员要扩充到16的整数倍
     *
     * @param sSrc
     * @param aesKey
     * @param aesIV
     * @return
     */
    public static byte[] aesCbcNoPaddingEncrypt(byte[] sSrc, String aesKey, String aesIV) {
        //加密的数据长度不是16的整数倍时，原始数据后面补0，直到长度满足16的整数倍
        int len = sSrc.length;
        //计算补0后的长度
        while (len % 16 != 0) len++;
        byte[] result = new byte[len];
        //在最后补0
        for (int i = 0; i < len; ++i) {
            if (i < sSrc.length) {
                result[i] = sSrc[i];
            } else {
                //填充字符'a'
                //result[i] = 'a';
                result[i] = 0;
            }
        }
        SecretKeySpec skeySpec = create128BitsKey(aesKey);
        //使用CBC模式，需要一个初始向量iv，可增加加密算法的强度
        IvParameterSpec iv = create128BitsIV(aesIV);
        Cipher cipher = null;
        try {
            //算法/模式/补码方式
            cipher = Cipher.getInstance(AES_CBC_NO_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "aesCbcNoPaddingEncrypt Exception");
        }
        byte[] encrypted = null;
        try {
            encrypted = cipher.doFinal(result);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "aesCbcNoPaddingEncrypt  Exception");
        }
        return encrypted;
    }

    public static byte[] aesCbcNoPaddingDecrypt(byte[] sSrc, String aesKey, String aesIV) {
        SecretKeySpec skeySpec = create128BitsKey(aesKey);
        IvParameterSpec iv = create128BitsIV(aesIV);
        try {
            Cipher cipher = Cipher.getInstance(AES_CBC_NO_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] decryptContent = cipher.doFinal(sSrc);
            return decryptContent;
        } catch (Exception ex) {
            Log.i(TAG, "aesCbcNoPaddingDecrypt Exception");
        }
        return null;
    }

    /**
     * AES 加密
     *
     * @param srcContent 要加密的内容
     * @return
     */

    public static byte[] AESEncrypt(byte[] srcContent) {

        String iv = "tanghongchang123";

        byte[] b = AESUtils.aesCbcPkcs5PaddingEncrypt(srcContent, iv, iv);

        return b;

//        byte[] PolicyService = Base64Decoder.decodeToBytes(strEncoder);
//        String strBase64Decrypt = Base64Decoder.decode(strEncoder);
//        byte[] CbcPkcs5Padding = AESUtils.aesCbcPkcs5PaddingDecrypt(PolicyService, iv,iv);
//
//        String strAESDecrypt = new String(CbcPkcs5Padding);


    }

    /**
     * AES 加密
     *
     * @param result 要加密的内容
     * @return
     */

    public static String AESEncrypt(String result) {
        try {
            String iv = "tanghongchang123";
            String strEncoder = "";
            if (null != result) {
                byte[] b = AESUtils.aesCbcPkcs5PaddingEncrypt(result.getBytes(), iv, iv);
                strEncoder = Base64Encoder.encode(b).replace("\n", "");
            }
            return strEncoder;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";

    }


    public static String AESCodePhone(String phone, String code) {
        String value = phone + code;
        String sign = AESUtils.AESEncrypt(value);
        return sign;


    }


    /**
     * AES解密
     *
     * @param strEncoder Base64编码的值
     * @return
     */

    public static String AESDecode(String strEncoder) {

        String iv = "tanghongchang123";

        byte[] d = Base64Decoder.decodeToBytes(strEncoder);
        String strBase64Decrypt = Base64Decoder.decode(strEncoder);
        byte[] CbcPkcs5Padding = AESUtils.aesCbcPkcs5PaddingDecrypt(d, iv, iv);

        String strAESDecrypt = new String(CbcPkcs5Padding);

        return strAESDecrypt;

    }


    public static void main(String[] args) throws Exception {

        String result = "1535349357_1";

        //String iv ="1234567890123456";
        String iv = "tanghongchang123";

        byte[] b = AESUtils.aesCbcPkcs5PaddingEncrypt(result.getBytes(), iv, iv);

        String strEncoder = Base64Encoder.encode(b);

        byte[] d = Base64Decoder.decodeToBytes(strEncoder);
        String strBase64Decrypt = Base64Decoder.decode(strEncoder);
        byte[] CbcPkcs5Padding = AESUtils.aesCbcPkcs5PaddingDecrypt(d, iv, iv);

        String strAESDecrypt = new String(CbcPkcs5Padding);

    }

}
