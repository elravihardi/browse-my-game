package elravi.compose.browsemygame.util

import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.HttpException
import retrofit2.Response

fun showToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

fun <T> Response<T>.handleApiError(): T {
    val responseBody = this.body()
    return if (this.isSuccessful && responseBody != null) {
        responseBody
    } else {
        when (this.code()) {
            404 -> throw DataNotFoundException()
            else -> throw HttpException(this)
        }
    }
}

fun Any.toMap(): Map<String, String>? {
    return try {
        val gson = GsonBuilder().create()
        val json = gson.toJson(this)
        Gson().fromJson<Map<String, String>>(json, Map::class.java)
    } catch (ex: Exception) {
        null
    }
}

fun Any?.orDash() = this?.toString().orDash()

class DataNotFoundException(message: String = "Data not found"): Exception(message)