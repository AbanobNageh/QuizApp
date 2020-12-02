package com.abanobnageh.quizapp.core.network

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketAddress

abstract class NetworkInfo {
  abstract suspend fun internetConnected(): Deferred<Boolean>
}

class NetworkInfoImpl: NetworkInfo() {
  override suspend fun internetConnected(): Deferred<Boolean> {
    try {
      val timeoutMs = 1500
      val socket = Socket()
      val socketAddress: SocketAddress = InetSocketAddress("8.8.8.8", 53)
      withContext(Dispatchers.IO) { socket.connect(socketAddress, timeoutMs) }
      withContext(Dispatchers.IO) { socket.close() }
      return CompletableDeferred(true)
    } catch (e: IOException) {
      return CompletableDeferred(false)
    }
  }
}