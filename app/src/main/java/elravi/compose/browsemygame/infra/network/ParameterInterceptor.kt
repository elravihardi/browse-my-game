package elravi.compose.browsemygame.infra.network

import elravi.compose.browsemygame.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class ParameterInterceptor(
    private val params: HashMap<String, String> = HashMap()
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val requestBuilder = original
            .newBuilder()
            .url(mapParameters(chain))

        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    private fun mapParameters(chain: Interceptor.Chain): HttpUrl {
        val originalHttpUrl = chain.request().url
        val httpBuilder = originalHttpUrl.newBuilder()
        params["key"] = BuildConfig.RAWG_API_KEY
        for ((key, value) in params) {
            httpBuilder.addQueryParameter(key, value)
        }
        return httpBuilder.build()
    }
}