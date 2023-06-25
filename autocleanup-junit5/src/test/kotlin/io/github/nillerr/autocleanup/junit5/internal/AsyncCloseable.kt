package io.github.nillerr.autocleanup.junit5.internal

interface AsyncCloseable {
    suspend fun close()
}
