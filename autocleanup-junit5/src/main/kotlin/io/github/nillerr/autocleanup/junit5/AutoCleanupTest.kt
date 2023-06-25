package io.github.nillerr.autocleanup.junit5

import org.junit.jupiter.api.extension.ExtendWith

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@ExtendWith(AutoCleanupExtension::class)
annotation class AutoCleanupTest
