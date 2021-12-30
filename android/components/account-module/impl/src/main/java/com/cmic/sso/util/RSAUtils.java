package com.cmic.sso.util;

import android.util.Base64;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 *
 * @author ct
 *
 */
public class RSAUtils {
    // 加解密算法
    public static final String KEY_ALGORITHM       = "RSA";
    public static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
    public static final String DEFAULT_ENCODING       = "UTF-8";
    // 签名算法 MD5withRSA SHA256withRSA SHA1withRSA NONEwithRSA
    public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    /**
     * Description：公钥加密
     *
     * @param
     * @return
     * @return String
     * @author 拜力文
     **/
    public String publicKeyEncrypt(String sourceData, String publicKeystr) {
        Cipher cipher = null;
        try {
            byte[] data = sourceData.getBytes(DEFAULT_ENCODING);
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKeystr));
            byte[] output = cipher.doFinal(data);
            return byte2hex(output);// byte2hex(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Description：将十六进制的字符串转换成字节数据
     *
     * @param strhex
     * @return
     * @return byte[]
     * @author name：
     */
    public static byte[] hexStr2byte(String strhex) {
        if (strhex == null) {
            return null;
        }
        int l = strhex.length();
        if (l % 2 == 1) {
            return null;
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
        }
        return b;
    }
    /**
     * Description：将二进制转换成16进制字符串
     *
     * @param b
     * @return
     * @return String
     * @author name：
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }
    /**
     * Description：私钥解密
     *
     * @param encodeData 需要解密的数据 hex
     * @return 解密后的数据
     * @author 拜力文
     */
    public static String privateKeyDecrypt(String encodeData, String privateKeyStr) {
        Cipher cipher = null;
        try {
            PrivateKey privateKey = getPrivateKey(privateKeyStr);
            byte[] data = hexStr2byte(encodeData);
            cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(data);
            return new String(output, DEFAULT_ENCODING);// byte2hex(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 生产RSA公钥
     * @param publickey
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String publickey) throws Exception {
        byte[] keyBytes;
        keyBytes = Base64.decode(publickey, Base64.NO_WRAP);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey mpublicKey = keyFactory.generatePublic(keySpec);
        return mpublicKey;
    }
    /**
     * 生产RSA私钥
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        byte[] keyBytes;
        keyBytes = Base64.decode(privateKey, Base64.NO_WRAP);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey mprivateKey = keyFactory.generatePrivate(keySpec);
        return mprivateKey;
    }
    /**
     * Description：签名验证
     *
     * @param sourceData
     * @param signData
     * @return
     * @return boolean
     * @author 拜力文：
     **/
    public boolean signVerify(String sourceData, String signData, String publicKeyStr) {
        Signature signature;
        try {
            signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(getPublicKey(publicKeyStr));
            byte[] srcData = sourceData.getBytes(DEFAULT_ENCODING);
            signature.update(srcData);
            byte [] hexbyte = hexStr2byte(signData);
            if (hexbyte == null) {
                return false;
            }
            return signature.verify(hexbyte);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Description：生成签名
     *
     * @param data
     * @return
     * @return String
     * @author 拜力文：
     **/
    public static String generalSign(String data, String privateKeyStr) {
        Signature signature;
        try {
            signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(getPrivateKey(privateKeyStr));
            signature.update(data.getBytes(DEFAULT_ENCODING));
            return byte2hex(signature.sign());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Description：生成公私钥对
     * @return
     * @throws Exception
     * @return RSA
     **/
    public static void genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = null;
        keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        String privateKey64 = Base64.encodeToString(privateKey.getEncoded(), Base64.NO_WRAP);
        String publicKey64 = Base64.encodeToString(publicKey.getEncoded(), Base64.NO_WRAP);
    }
    /**
     *
     * @param sourceData
     * @param pKey
     * @return
     */
    public static String publicKeyEncrypt(String sourceData, RSAPublicKey pKey) {
        Cipher cipher = null;
        try {
            byte[] data = sourceData.getBytes(DEFAULT_ENCODING);
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, pKey);
            byte[] output = cipher.doFinal(data);
            return byte2hex(output);// byte2hex(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Description：公钥解密
     *
     * @param encodeData 需要解密的数据 hex
     * @return 解密后的数据
     */
    public static String publicKeyDecrypt(String encodeData, PublicKey publicKey) {
        Cipher cipher = null;
        try {
            byte[] data = hexStr2byte(encodeData);
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(data);
            return new String(output, DEFAULT_ENCODING);// byte2hex(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}