package io.github.nillerr.autocleanup.junit5

import io.github.nillerr.autocleanup.junit5.internal.pairWithNotNull
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.platform.commons.logging.LoggerFactory
import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty1
import kotlin.reflect.full.callSuspend
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

class AutoCleanupExtension : AfterEachCallback, AfterAllCallback {
    private val logger = LoggerFactory.getLogger(AutoCleanupExtension::class.java)

    override fun afterEach(context: ExtensionContext) {
        cleanup(context.requiredTestInstance)
    }

    override fun afterAll(context: ExtensionContext) {
        val type: KClass<*> = context.requiredTestClass.kotlin

        val companionObject = type.companionObjectInstance
        if (companionObject != null) {
            cleanup(companionObject)
        }
    }

    private fun cleanup(instance: Any) {
        val type = instance::class

        type.memberProperties
            .filterIsInstance<KProperty1<Any, Any>>()
            .pairWithNotNull { prop -> prop.findAnnotation<AutoCleanup>() }
            .forEach { (prop, annotation) ->
                log(prop, annotation)

                prop.isAccessible = true

                val value = prop.get(instance)
                cleanup(value, annotation)
            }
    }

    private fun log(callable: KCallable<*>, annotation: AutoCleanup) {
        val type = callable.returnType.classifier as KClass<*>
        logger.info { "Cleaning up `${callable.name}` by calling `${type.simpleName}#${annotation.function}()`" }
    }

    private fun cleanup(value: Any, annotation: AutoCleanup) {
        val functionName = annotation.function

        val type = value::class
        val function = type.memberFunctions
            .firstOrNull { function -> function.name == functionName }

        if (function == null) {
            throw AutoCleanupException("The type `$type` has no member function named `$functionName`")
        }

        cleanup(value, function, annotation)
    }

    private fun cleanup(instance: Any, function: KFunction<*>, annotation: AutoCleanup) {
        try {
            if (function.isSuspend) {
                runBlocking { function.callSuspend(instance) }
            } else {
                function.call(instance)
            }
        } catch (e: Exception) {
            if (!annotation.quiet) {
                throw AutoCleanupException("An exception occurred while cleaning up the resource.", e)
            }
        }
    }
}
