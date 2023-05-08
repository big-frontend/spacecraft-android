@file:JvmName("Crypto")
package com.jamesfchen.myhome.network

import android.util.Base64
import java.security.*
import java.security.interfaces.RSAPrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey


/**
 * cryptography 密码学
 */
object Crypto {
    init {
        System.loadLibrary("clientkey")
    }

    const val KEYSIZE_1024 = 1024


    /**
     * 非对称密钥：服务端生成
     * 对称密钥： 客户端生成
     * 客户端生成cipherText 告诉服务端支持的加密套件，服务端生成非对称密钥并且下发公钥，
     * 客户端端生成对称密钥并且用公钥加密发给服务，服务用私钥解密然后通信
     *
     * 伪随机函数，为了最大可能输出随机数：
     * - 可以将用户滑动鼠标的轨迹取样生成随机素材
     * - 可以当前时间+随机数
     * 然后运行hash函数
     *
     */
    @JvmStatic
    external fun getClientKey(token: String, time: Long): String

    @JvmStatic
    fun getClientKey2(seed: String, time: Long): String {
        try {
            val kg: KeyGenerator = KeyGenerator.getInstance("AES")
            //128, 192或256
            kg.init(128, SecureRandom(seed.toByteArray()))
            val sk: SecretKey = kg.generateKey()
            return bytes2HexString(sk.encoded)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ""
    }

    private val HEX_DIGITS = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
    private fun bytes2HexString(bytes: ByteArray): String {
        if (bytes.isEmpty()) return ""
        val len = bytes.size
        val ret = CharArray(len shl 1)
        var i = 0
        var j = 0
        while (i < len) {
            ret[j++] = HEX_DIGITS[bytes[i].toInt() shr 4 and 0x0f]
            ret[j++] = HEX_DIGITS[bytes[i].toInt() and 0x0f]
            i++
        }
        return String(ret)
    }

    /**
     * 非对称密钥：客户端生成
     * 对称密钥：服务端生成
     *
     * 客户端先生成非对称密钥,把公私传给服务，服务生成的对称密钥被公私加密传回，并且用私密解密。
     * val (privateKeyBase64, publicKeyBase64) = getRSAKeyPair(KEYSIZE_1024)
     * val dataKey:String = getDataKeyFromServer(dataKey_Bytes,privateKeyBase64)
     */
    @JvmStatic
    fun getServerKey(dataKey: ByteArray, privateKeyBase64: String, keySize: Int = KEYSIZE_1024): String {
        val keySpec = PKCS8EncodedKeySpec(Base64.decode(privateKeyBase64, Base64.NO_WRAP))
        val key: PrivateKey = KeyFactory.getInstance("RSA").generatePrivate(keySpec)
        val cipher: Cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.DECRYPT_MODE, key)
        val len: Int = dataKey.size
        var maxLen: Int = keySize / 8
        val count = len / maxLen
        return if (count > 0) {
            var ret = ByteArray(0)
            var buff = ByteArray(maxLen)
            var index = 0
            for (i in 0 until count) {
                System.arraycopy(dataKey, index, buff, 0, maxLen)
                ret = joins(ret, cipher.doFinal(buff))
                index += maxLen
            }
            if (index != len) {
                val restLen = len - index
                buff = ByteArray(restLen)
                System.arraycopy(dataKey, index, buff, 0, restLen)
                ret = joins(ret, cipher.doFinal(buff))
            }
            String(ret)
        } else {
            String(cipher.doFinal(dataKey))
        }
    }

    private fun joins(prefix: ByteArray, suffix: ByteArray): ByteArray {
        val ret = ByteArray(prefix.size + suffix.size)
        System.arraycopy(prefix, 0, ret, 0, prefix.size)
        System.arraycopy(suffix, 0, ret, prefix.size, suffix.size)
        return ret
    }

    @Throws(NoSuchAlgorithmException::class)
    fun getRSAKeyPair(keySize: Int = KEYSIZE_1024): Pair<String, String> {
        val secureRandom = SecureRandom()
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(keySize, secureRandom)
        val keyPair = keyPairGenerator.generateKeyPair()
        val publicKey = keyPair.public as RSAPrivateKey
        val privateKey = keyPair.private as RSAPrivateKey
        val publicKeyBase64 = Base64.encodeToString(publicKey.encoded, Base64.NO_WRAP)
        val privateKeyBase64 = Base64.encodeToString(privateKey.encoded, Base64.NO_WRAP)

        return privateKeyBase64 to publicKeyBase64
    }


}