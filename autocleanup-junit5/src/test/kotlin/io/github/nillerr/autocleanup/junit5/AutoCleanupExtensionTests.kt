package io.github.nillerr.autocleanup.junit5

import io.github.nillerr.autocleanup.junit5.internal.*
import org.junit.jupiter.api.Test
import org.junit.platform.engine.discovery.DiscoverySelectors.selectClass
import org.junit.platform.testkit.engine.EngineTestKit

class AutoCleanupExtensionTests {
    // Instance
    @Test
    fun `fails when closeable is not mocked`() {
        EngineTestKit
            .engine("junit-jupiter")
            .configurationParameter("junit.jupiter.conditions.deactivate", "org.junit.*DisabledCondition")
            .selectors(selectClass(AutoCleanupExtensionNonMockedTest::class.java))
            .execute()
            .testEvents()
            .assertStatistics { stats ->
                stats.failed(1L)
            }
    }

    @Test
    fun `succeeds when closeable is mocked`() {
        EngineTestKit
            .engine("junit-jupiter")
            .configurationParameter("junit.jupiter.conditions.deactivate", "org.junit.*DisabledCondition")
            .selectors(selectClass(AutoCleanupExtensionMockedTest::class.java))
            .execute()
            .testEvents()
            .assertStatistics { stats ->
                stats.succeeded(1L)
            }
    }

    @Test
    fun `succeeds when suspend closeable is mocked`() {
        EngineTestKit
            .engine("junit-jupiter")
            .configurationParameter("junit.jupiter.conditions.deactivate", "org.junit.*DisabledCondition")
            .selectors(selectClass(AutoCleanupExtensionMockedSuspendTest::class.java))
            .execute()
            .testEvents()
            .assertStatistics { stats ->
                stats.succeeded(1L)
            }
    }

    @Test
    fun `fails when function is unknown`() {
        EngineTestKit
            .engine("junit-jupiter")
            .configurationParameter("junit.jupiter.conditions.deactivate", "org.junit.*DisabledCondition")
            .selectors(selectClass(AutoCleanupExtensionUnknownFunctionTest::class.java))
            .execute()
            .testEvents()
            .assertStatistics { stats ->
                stats.failed(1L)
            }
    }

    // Companion
    @Test
    fun `companion object - succeeds when closeable is not mocked`() {
        EngineTestKit
            .engine("junit-jupiter")
            .configurationParameter("junit.jupiter.conditions.deactivate", "org.junit.*DisabledCondition")
            .selectors(selectClass(AutoCleanupExtensionCompanionNonMockedTest::class.java))
            .execute()
            .testEvents()
            .assertStatistics { stats ->
                stats.succeeded(1L)
            }
    }

    @Test
    fun `companion object - succeeds when closeable is mocked`() {
        EngineTestKit
            .engine("junit-jupiter")
            .configurationParameter("junit.jupiter.conditions.deactivate", "org.junit.*DisabledCondition")
            .selectors(selectClass(AutoCleanupExtensionCompanionMockedTest::class.java))
            .execute()
            .testEvents()
            .assertStatistics { stats ->
                stats.succeeded(1L)
            }
    }

    @Test
    fun `companion object - succeeds when suspend closeable is mocked`() {
        EngineTestKit
            .engine("junit-jupiter")
            .configurationParameter("junit.jupiter.conditions.deactivate", "org.junit.*DisabledCondition")
            .selectors(selectClass(AutoCleanupExtensionCompanionMockedSuspendTest::class.java))
            .execute()
            .testEvents()
            .assertStatistics { stats ->
                stats.succeeded(1L)
            }
    }

    @Test
    fun `companion object - succeeds when function is unknown`() {
        EngineTestKit
            .engine("junit-jupiter")
            .configurationParameter("junit.jupiter.conditions.deactivate", "org.junit.*DisabledCondition")
            .selectors(selectClass(AutoCleanupExtensionCompanionUnknownFunctionTest::class.java))
            .execute()
            .testEvents()
            .assertStatistics { stats ->
                stats.succeeded(1L)
            }
    }
}
