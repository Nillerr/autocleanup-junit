package io.github.nillerr.autocleanup.junit5

import org.junit.jupiter.api.extension.ExtendWith

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@ExtendWith(AutoCleanupExtension::class)
annotation class AutoCleanup(
    val function: String = "close",
    val quiet: Boolean = false,
)
