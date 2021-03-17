package edu.purdue.cerias.ciphers;

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class Kotlin_AES_Cipher {
    /**
     * Encrypt data using AES Cipher (CBC) with 128 bit key
     *
     *
     * @param key  - key to use should be 16 bytes long (128 bits)
     * @param iv - initialization vector
     * @param data - data to encrypt
     * @return encryptedData data in base64 encoding with iv attached at end after a :
     */
    fun encrypt(key: String, iv: String, data: String): String? {
        var key = key
        try {
            if (key.length < CIPHER_KEY_LEN) {
                val numPad = CIPHER_KEY_LEN - key.length
                for (i in 0 until numPad) {
                    key += "0" //0 pad to len 16 bytes
                }
            } else if (key.length > CIPHER_KEY_LEN) {
                key = key.substring(0, CIPHER_KEY_LEN) //truncate to 16 bytes
            }
            val initVector = IvParameterSpec(iv.toByteArray(charset("ISO-8859-1")))
            val skeySpec = SecretKeySpec(key.toByteArray(charset("ISO-8859-1")), "AES")
            val cipher = Cipher.getInstance(CIPHER_NAME)
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, initVector)
            val encryptedData = cipher.doFinal(data.toByteArray())
            val base64_EncryptedData: String = Base64.encodeToString(encryptedData, Base64.DEFAULT)
            val base64_IV: String = Base64.encodeToString(iv.toByteArray(charset("ISO-8859-1")), Base64.DEFAULT)
            return "$base64_EncryptedData:$base64_IV"
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    /**
     * Decrypt data using AES Cipher (CBC) with 128 bit key
     *
     * @param key - key to use should be 16 bytes long (128 bits)
     * @param data - encrypted data with iv at the end separate by :
     * @return decrypted data string
     */
    fun decrypt(key: String, data: String): String? {
        var key = key
        try {
            if (key.length < CIPHER_KEY_LEN) {
                val numPad = CIPHER_KEY_LEN - key.length
                for (i in 0 until numPad) {
                    key += "0" //0 pad to len 16 bytes
                }
            } else if (key.length > CIPHER_KEY_LEN) {
                key = key.substring(0, CIPHER_KEY_LEN) //truncate to 16 bytes
            }
            val parts = data.split(":".toRegex()).toTypedArray()
            val iv = IvParameterSpec(Base64.decode(parts[1], Base64.DEFAULT))
            val skeySpec = SecretKeySpec(key.toByteArray(charset("ISO-8859-1")), "AES")
            val cipher = Cipher.getInstance(CIPHER_NAME)
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv)
            val decodedEncryptedData = Base64.decode(parts[0], Base64.DEFAULT)
            val original = cipher.doFinal(decodedEncryptedData)
            return String(original)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    companion object {
        private const val CIPHER_NAME = "AES/CBC/PKCS5PADDING"
        private const val CIPHER_KEY_LEN = 16 //128 bits
    }
}
