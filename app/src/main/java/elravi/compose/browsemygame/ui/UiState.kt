package elravi.compose.browsemygame.ui

import androidx.compose.runtime.Composable
import java.net.SocketException
import java.net.UnknownHostException

const val NO_INTERNET_CONN_ERROR = "Please check your internet connection and try again"
const val UNKNOWN_ERROR = "Unknown error occured, please check your internet connection and try again"

sealed class UiState<T> {
    class Loading<T> : UiState<T>()
    class Default<T> : UiState<T>()
    class Empty<T> : UiState<T>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Failure<T>(val throwable: Throwable?, val message: String?) : UiState<T>()

    companion object {
        fun <T> loading(): UiState<T> = Loading()
        fun <T> default(): UiState<T> = Default()
        fun <T> success(data: T): UiState<T> = Success(data)
        fun <T> empty(): UiState<T> = Empty()
        fun <T> fail(throwable: Throwable, message: String?): UiState<T> {
            return when (throwable) {
                is UnknownHostException, is SocketException -> Failure(throwable, NO_INTERNET_CONN_ERROR)
                else -> Failure(throwable, message ?: throwable.cause?.message ?: UNKNOWN_ERROR)
            }
        }
        fun <T> fail(message: String?): UiState<T> = Failure(null, message)

        fun <T> UiState<T>.getSuccessData(): T? = (this as? Success)?.data
        fun <T> UiState<T>.getErrorMessage(): String =
            (this as? Failure)?.let {
                it.message ?: it.throwable?.message ?: UNKNOWN_ERROR
            } ?: ""

        @Composable
        fun <T> UiState<T>.checkState(
            onLoading: @Composable () -> Unit = { },
            onEmpty: @Composable () -> Unit = { },
            onSuccess: @Composable (T) -> Unit = { },
            onFailure: @Composable (Throwable?, String) -> Unit = {_, _ -> }
        ): UiState<T> {
            when (this) {
                is Loading -> { onLoading() }
                is Empty -> { onEmpty() }
                is Success -> { onSuccess(this.data) }
                is Failure -> { onFailure(this.throwable, getErrorMessage()) }
                else -> { }
            }
            return this
        }
    }
}