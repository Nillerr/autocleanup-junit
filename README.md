# AutoCleanup for JUnit 5

Provides the `@AutoCleanupTest` and `@AutoCleanup` annotations for automatically cleaning up resources after a test run.

## Installation

```kotlin
dependencies {
    implementation("io.github.nillerr:autocleanup-junit5:1.0.0")
}
```

## Usage

Annotate a test class with the `@AutoCleanupTest` annotation:

```kotlin
@AutoCleanupTest
class UserServiceTests {
    // Resources
    @AutoCleanup
    private val resource = Resource()
    
    // SUT
    private val service = UserService(resource)
    
    @Test
    fun test() {
        // Given
        val id = "5"
        
        // When
        val result = service.get(id)
        
        // Then
        assertEquals(result).isEqualTo(Accounts.default)
    }
}
```

JUnit will now call `close()` on the `Resource`.
