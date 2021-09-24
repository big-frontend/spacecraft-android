package com.jamesfchen.common;

import com.blankj.utilcode.util.EncryptUtils;
import com.jamesfchen.common.util.CryptoUtil;

import org.junit.Test;

import static com.blankj.utilcode.util.ConvertUtils.hexString2Bytes;
import static com.blankj.utilcode.util.EncodeUtils.base64Encode;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    private String dataAES      = "111111111111111111111111111111111";
    private String keyAES       = "11111111111111111111111111111111";
    private String resAES       = "393FBBBC2C774BE50A106A50393E623AC3790781D015BB854359587256581F6D";
    private byte[] bytesDataAES = hexString2Bytes(dataAES);
    private byte[] bytesKeyAES  = hexString2Bytes(keyAES);
    private byte[] bytesResAES  = hexString2Bytes(resAES);
//    @Test
    public  void  testCrypto(){
        assertArrayEquals(
                bytesResAES,
                CryptoUtil.encryptAES(bytesDataAES, bytesKeyAES, "AES/ECB/PKCS5Padding", null)
        );
        assertEquals(
                resAES,
                EncryptUtils.encryptAES2HexString(bytesDataAES, bytesKeyAES, "AES/ECB/PKCS5Padding", null)
        );
        assertArrayEquals(
                base64Encode(bytesResAES),
                EncryptUtils.encryptAES2Base64(bytesDataAES, bytesKeyAES, "AES/ECB/PKCS5Padding", null)
        );
        assertArrayEquals(
                bytesDataAES,
                EncryptUtils.decryptAES(bytesResAES, bytesKeyAES, "AES/ECB/PKCS5Padding", null)
        );
        assertArrayEquals(
                bytesDataAES,
                EncryptUtils.decryptHexStringAES(resAES, bytesKeyAES, "AES/ECB/PKCS5Padding", null)
        );
        assertArrayEquals(bytesDataAES,
                EncryptUtils.decryptBase64AES(base64Encode(bytesResAES), bytesKeyAES, "AES/ECB/PKCS5Padding", null)
        );
    }
}