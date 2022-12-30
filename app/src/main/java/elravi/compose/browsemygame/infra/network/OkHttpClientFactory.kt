package elravi.compose.browsemygame.infra.network

import okhttp3.*
import okhttp3.ConnectionSpec.Builder
import okhttp3.TlsVersion.*
import java.util.*
import java.util.concurrent.TimeUnit

object OkHttpClientFactory {

    private const val DEFAULT_MAX_REQUEST = 30

    fun create(
        interceptors: Array<Interceptor>,
        certificatePinner: CertificatePinner? = null,
        readTimeOut: Int = 120,
        connectTimeOut: Int = 120,
        maxRequest: Int = DEFAULT_MAX_REQUEST
    ): OkHttpClient {

        val spec = Builder(ConnectionSpec.COMPATIBLE_TLS)
            .tlsVersions(TLS_1_2, TLS_1_1, TLS_1_0)
            .allEnabledCipherSuites()
            .build()

        val builder = OkHttpClient.Builder()
            .readTimeout(readTimeOut.toLong(), TimeUnit.SECONDS)
            .connectTimeout(connectTimeOut.toLong(), TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .connectionSpecs(Collections.singletonList(spec))
            .connectionSpecs(listOf(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT))

        certificatePinner?.let {
            builder.certificatePinner(it)
        }

        interceptors.forEach {
            builder.addInterceptor(it)
        }

        val dispatcher = Dispatcher()
        dispatcher.maxRequests = maxRequest
        builder.dispatcher(dispatcher)

        return builder.build()
    }
}
