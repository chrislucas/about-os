package com.br.tutorials

fun ByteArray.toHex(): String {
    val stringBuilder = StringBuilder()
    val twoBytesFormat = "%02x"
    for (b in this) {
        stringBuilder.append(String.format(twoBytesFormat, b))
    }
    return stringBuilder.toString()
}