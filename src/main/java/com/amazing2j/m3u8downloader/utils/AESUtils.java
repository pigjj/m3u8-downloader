package com.amazing2j.m3u8downloader.utils;

import com.amazing2j.m3u8downloader.exception.M3u8Exception;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.AlgorithmParameterSpec;

public class AESUtils {

    public static byte[] decrypt(byte[] sSrc, byte[] sKey, String iv) throws Exception {
        if (sKey.length < 16) {
            throw new M3u8Exception("Key长度小于16位！");
        }
        SecretKeySpec keySpec = new SecretKeySpec(sKey, "AES");

        byte[] ivByte;
        if (iv.startsWith("0x"))
            ivByte = hexStringToByteArray(iv.substring(2));
        else ivByte = iv.getBytes();
        if (ivByte.length != 16)
            ivByte = new byte[16];

        //如果m3u8有IV标签，那么IvParameterSpec构造函数就把IV标签后的内容转成字节数组传进去
        AlgorithmParameterSpec paramSpec = new IvParameterSpec(ivByte);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        cipher.init(Cipher.DECRYPT_MODE, keySpec, paramSpec);

        return cipher.doFinal(sSrc);
    }

    private static byte hexToByte(String hex) {
        return (byte) Integer.parseInt(hex, 16);
    }

    private static byte[] hexStringToByteArray(String hex) {
        int hexLen = hex.length();
        byte[] result;
        if (hexLen % 2 == 1) {
            hexLen++;
            result = new byte[(hexLen / 2)];
            hex = "0" + hex;
        } else {
            result = new byte[(hexLen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexLen; i += 2) {
            result[j] = hexToByte(hex.substring(i, i + 2));
            j++;
        }
        return result;
    }

}
