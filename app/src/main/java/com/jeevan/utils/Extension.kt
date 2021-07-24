package com.jeevan.utils

import android.app.Activity
import android.content.Context
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resumeWithException

fun Context.toast(message: String, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, length).show()
}

fun Activity.toast(message: String, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, length).show()
}

fun Fragment.toast(message: String, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(requireContext(), message, length).show()
}

// firebase
suspend fun <T> Task<T>.suspendAndWait(): T =
    suspendCancellableCoroutine { continuation ->
        addOnSuccessListener { result ->
            continuation.resume(result) {
                continuation.resumeWithException(it)
            }
        }
        addOnFailureListener { exception ->
            continuation.resumeWithException(exception)
        }
        addOnCanceledListener {
            continuation.resumeWithException(Exception("Firebase Task was cancelled"))
        }
    }

// textChannel
fun EditText.textChannel(): ReceiveChannel<String> =
    Channel<String>(capacity = Channel.UNLIMITED).also { channel ->
        doOnTextChanged { text, _, _, _ ->
            text?.toString().orEmpty().let {
                channel.trySend(it)
            }
        }
    }


fun <E> ReceiveChannel<E>.debounce(
    wait: Long = 50,
    context: CoroutineContext = Dispatchers.Default
): ReceiveChannel<E> = GlobalScope.produce(context) {
    var lastTimeout: Job? = null
    consumeEach {
        lastTimeout?.cancel()
        lastTimeout = launch {
            delay(wait)
            send(it)
        }
    }
    lastTimeout?.join()
}