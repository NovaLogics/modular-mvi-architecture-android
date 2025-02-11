package com.android.modularmvi.di.component

import android.annotation.SuppressLint
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*

// Returns an SSLSocketFactory that trusts all certificates (not secure for production)
fun getUnsafeSSLSocketFactory(): SSLSocketFactory {
    val trustAllCerts = arrayOf<TrustManager>(getUnsafeTrustManager())

    val sslContext = SSLContext.getInstance("SSL").apply {
        init(null, trustAllCerts, SecureRandom())
    }

    return sslContext.socketFactory
}

// Returns a TrustManager that accepts all certificates (not secure for production)
@SuppressLint("CustomX509TrustManager")
fun getUnsafeTrustManager(): X509TrustManager {
    return object : X509TrustManager {
        @SuppressLint("TrustAllX509TrustManager")
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

        @SuppressLint("TrustAllX509TrustManager")
        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
    }
}


