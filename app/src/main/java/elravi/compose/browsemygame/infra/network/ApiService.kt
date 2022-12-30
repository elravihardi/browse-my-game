package elravi.compose.browsemygame.infra.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    fun <S> createService(serviceClass: Class<S>, okhttpClient: OkHttpClient, baseURl: String): S {

        val retrofit = Retrofit.Builder()
                .baseUrl(baseURl)
                .client(okhttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofit.create(serviceClass)
    }
}
