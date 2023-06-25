package io.github.nillerr.autocleanup.junit5.internal

import io.github.nillerr.autocleanup.junit5.AutoCleanup
import io.github.nillerr.autocleanup.junit5.AutoCleanupTest
import io.mockk.mockk
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled
@AutoCleanupTest
class AutoCleanupExtensionNonMockedTest {
    @AutoCleanup
    val closeable = mockk<AutoCloseable>()

    @Test
    fun test() {
        // Nothing
    }
}
