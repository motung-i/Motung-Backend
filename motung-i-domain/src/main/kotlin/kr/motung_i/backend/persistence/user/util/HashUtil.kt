package kr.motung_i.backend.persistence.user.util

import java.security.MessageDigest

class HashUtil {
    companion object {
        fun String.sha256(): ByteArray =
            MessageDigest.getInstance("SHA-256").digest(this.toByteArray(Charsets.UTF_8))

        fun ByteArray.toHex(): String =
            joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }
    }
}
