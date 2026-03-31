package com.suzao.net.speed.netspeed.util;

import org.apache.commons.codec.binary.Base64;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @name AESCoder
 * @author mc
 * @date 2022/4/9 18:29
 * @version 1.0
 **/
public abstract class AESCoder {
    /**
     * 密钥算法
     */
    public static final String KEY_ALGORITHM = "AES";
    public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final String INIT_KEY = "DWwaqDk5drcyPyz0csF+xA==";
    /**
     * 转换密钥
     * @param key 二进制密钥
     * @return Key 密钥
     * @throws Exception
     */
    private static Key toKey(byte[] key) throws Exception {
              // 实例化DES密钥材料
              SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM);
              return secretKey;
    }

    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
              // 还原密钥
              Key k = toKey(key);
              Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
              // 初始化，设置为解密模式
              cipher.init(Cipher.DECRYPT_MODE, k);
              // 执行操作
              return cipher.doFinal(data);
    }
    public static String decrypt(String data) throws Exception {
        byte[] inputData = Base64.decodeBase64(data);
        byte[] key = Base64.decodeBase64(INIT_KEY);
        byte[] outputData = decrypt(inputData, key);
        String outputStr = new String(outputData);
        String[] split = outputStr.split("##");
        return split[0];
    }
    /**
     * 加密
     * @param data 待加密数据
     * @param key 密钥
     * @return byte[] 加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
              // 还原密钥
              Key k = toKey(key);
              Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
              // 初始化，设置为加密模式
              cipher.init(Cipher.ENCRYPT_MODE, k);
              // 执行操作
              return cipher.doFinal(data);
    }

    public static String encrypt(String data) throws Exception {
        byte[] inputData = (data + "##" + System.currentTimeMillis()).getBytes();
        byte[] key = Base64.decodeBase64(INIT_KEY);
        byte[] encryptData = AESCoder.encrypt(inputData, key);
        return Base64.encodeBase64String(encryptData);
    }

    /**
     * 生成密钥 <br>
     * @return byte[] 二进制密钥
     * @throws Exception
     */
    public static byte[] initKey() throws Exception {
              // 实例化
              KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
              // AES 要求密钥长度为128位、192位或256位
              kg.init(128);
              // 生成秘密密钥
              SecretKey secretKey = kg.generateKey();
              // 获得密钥的二进制编码形式
              return secretKey.getEncoded();
    }

    public static void main(String[] args) throws Exception {
        String inputStr = "{'repairPhone':'18547854787','customPhone':'12365478965','captchav':'58m7'}";
        String encrypt = encrypt(inputStr);
        System.out.println(encrypt);
        String decrypt = decrypt(encrypt);
        System.out.println(decrypt);
    }
    /*public static void main(String[] args) throws Exception {
        String inputStr = "{'repairPhone':'18547854787','customPhone':'12365478965','captchav':'58m7'}";
        //String inputStr = "AES";
        byte[] inputData = inputStr.getBytes();
        System.err.println("原文:\t" + inputStr);
        // 初始化密钥
        byte[] key = AESCoder.initKey();
        System.err.println("密钥:\t" + Base64.encodeBase64String(key));
        // 加密
        inputData = AESCoder.encrypt(inputData, key);
        System.err.println("加密后:\t" + Base64.encodeBase64String(inputData));
        // 解密
        byte[] outputData = AESCoder.decrypt(inputData, key);
        String outputStr = new String(outputData);
        System.err.println("解密后:\t" + outputStr);
    }*/
}
