package elravi.compose.browsemygame.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import elravi.compose.browsemygame.infra.network.OkHttpClientFactory
import elravi.compose.browsemygame.infra.network.ParameterInterceptor
import okhttp3.Interceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val RAWG_BASE_URL: String = "RAWG_BASE_URL"

val apiModule = module {

    single {
        return@single OkHttpClientFactory.create(
            interceptors = arrayOf(
                getParameterInterceptor(),
                ChuckerInterceptor.Builder(get()).build()
            ),
        )
    }

    single(named(RAWG_BASE_URL)) { "https://api.rawg.io/" }

}

private fun getParameterInterceptor(): Interceptor {
    return ParameterInterceptor()
}